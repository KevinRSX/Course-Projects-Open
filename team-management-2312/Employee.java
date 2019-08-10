import java.util.ArrayList;
import java.util.Collections;

public class Employee implements Comparable<Employee>
{
	private String name;
	private int yrLeavesEntitled;	//entitle annual leaves given as a count of days
	private ArrayList<LeaveRecord> allLeaveRecords;
	private ArrayList<RoleInTeam> allRoles; 
	
	public Employee(String n, int yle)
	{
		name = n;
		yrLeavesEntitled = yle;
		allLeaveRecords = new ArrayList<>();
		allRoles = new ArrayList<>();
	}
	
	public String getName()
	{
		return name;
	}
	
	//methods about taking leaves
	public ArrayList<RActingHeadInTeam> addLeaveRecord(String sStartDate, String sEndDate, String[] TeamsAndActingHeads)
			throws ExDateStartLaterThanEnd, ExOverlappedLeaves, ExInsufficientBalance, ExDateHasAlreadyPassed, ExTeamNotFound,
			ExEmployeeNotFound, ExActingHeadNotDesignated, ExNotLeadingRequiredTeam, ExInsufficientArgument
	{
		// this part deals with exceptions normal members' leaving
		LeaveRecord LR = new LeaveRecord(sStartDate, sEndDate);	// may throw ExDateStartLaterThanEnd
		if (LR.getStartDate().compareTo(SystemDate.getInstance()) < 0)
			throw new ExDateHasAlreadyPassed();
		for (LeaveRecord lere : allLeaveRecords)
		{
			if (lere.getEndDate().compareTo(LR.getStartDate()) > 0 && lere.getStartDate().compareTo(LR.getEndDate()) < 0)
				throw new ExOverlappedLeaves(lere.getStartDate(), lere.getEndDate());
		}
		for (RoleInTeam r : allRoles)
		{
			if (r instanceof RActingHeadInTeam)
			{
				LeaveRecord lere = ((RActingHeadInTeam) r).getLeaveRecord();
				if (lere.getEndDate().compareTo(LR.getStartDate()) > 0 && lere.getStartDate().compareTo(LR.getEndDate()) < 0)
					throw new ExOverlappedLeaves(r.getBelongTeam(), this, lere);
			}
		}
		if (yrLeavesEntitled - calcActualLeaves() < LR.getLength())
			throw new ExInsufficientBalance(yrLeavesEntitled - calcActualLeaves());
		
		// this part deals with headers' leaving
		ArrayList<RActingHeadInTeam> actingRoles = new ArrayList<>();
		Company company = Company.getInstance();
		ArrayList<Team> actualLeadingTeams = leadingTeams();
		ArrayList<Team> expectedLeadingTeams = new ArrayList<>();
		ArrayList<Employee> expectedActingHeads = new ArrayList<>();
		if (TeamsAndActingHeads == null)
		{
			if (!actualLeadingTeams.isEmpty())
				throw new ExActingHeadNotDesignated(actualLeadingTeams.get(0));
		}
		else
		{
			for (int i = 0; i < TeamsAndActingHeads.length; i++)
			{
				if (i % 2 == 0)
					expectedLeadingTeams.add(company.findTeam(TeamsAndActingHeads[i]));
				else
				{
					if (!expectedLeadingTeams.get(i / 2).hasMember(TeamsAndActingHeads[i]))
						throw new ExEmployeeNotFound(expectedLeadingTeams.get(i / 2), TeamsAndActingHeads[i]);
					expectedActingHeads.add(company.findEmployee(TeamsAndActingHeads[i]));
				}
					
			}
			if (actualLeadingTeams.isEmpty())
				throw new ExNotLeadingRequiredTeam(this, expectedLeadingTeams.get(0));
			else
			{
				// check whether there's any team that exists in CMD but not led by this employee
				for (int i = 0; i < expectedLeadingTeams.size(); i++)
				{
					boolean leadingTeamExist = false;
					for (int j = 0; j < actualLeadingTeams.size(); j++)
					{
						if (expectedLeadingTeams.get(i) == actualLeadingTeams.get(j))
							leadingTeamExist = true;
					}
					if (!leadingTeamExist)
					{
						throw new ExNotLeadingRequiredTeam(this, expectedLeadingTeams.get(i));
					}
				}
				
				// check whether there's any team led by this employee but no acting head is designated
				for (int i = 0; i < actualLeadingTeams.size(); i++)
				{
					boolean actingHeadDesignated = false;
					for (int j = 0; j < expectedLeadingTeams.size(); j++)
					{
						if (actualLeadingTeams.get(i) == expectedLeadingTeams.get(j))
							actingHeadDesignated = true;
					}
					if (!actingHeadDesignated)
					{
						throw new ExActingHeadNotDesignated(actualLeadingTeams.get(i));
					}
				}
				
				// check whether a member designated to be the acting head belongs to that team
				if (expectedLeadingTeams.size() != expectedActingHeads.size())
					throw new ExInsufficientArgument();
				for (int i = 0; i < expectedLeadingTeams.size(); i++)
				{
					if (!expectedLeadingTeams.get(i).hasMember(expectedActingHeads.get(i)))
						throw new ExEmployeeNotFound(expectedLeadingTeams.get(i), expectedActingHeads.get(i).getName());
				}
				
				// check whether there is any overlap of acting period and leaving period
				for (Employee e : expectedActingHeads)
				{
					for (LeaveRecord lere : e.allLeaveRecords)
					{
						if (lere.getEndDate().compareTo(LR.getStartDate()) > 0 && lere.getStartDate().compareTo(LR.getEndDate()) < 0)
							throw new ExOverlappedLeaves(e, lere);
					}
				}
				
				for (int i = 0; i < expectedActingHeads.size(); i++)
				{
					RActingHeadInTeam rah = new RActingHeadInTeam(expectedLeadingTeams.get(i), LR, expectedActingHeads.get(i));
					actingRoles.add(rah);
					expectedActingHeads.get(i).addRole(rah);
				}
			}
		}
		
		// all exceptions checking done, proceed to add leave records
		allLeaveRecords.add(LR);
		Collections.sort(allLeaveRecords);
		
		return actingRoles;
	}
	
	public void addLeaveRecord(LeaveRecord LR)	//does not deal with exceptions for it is only used in redo part
	{
		allLeaveRecords.add(LR);
		Collections.sort(allLeaveRecords);
	}
	
	public void removeLeaveRecord(LeaveRecord LR)
	{
		allLeaveRecords.remove(LR);
	}
	
	//count the total actual leave dates according to the LeaveRecord list
	public int calcActualLeaves()
	{
		int count = 0;
		for (LeaveRecord LR: allLeaveRecords)
		{
			count += LR.getLength();
		}
		return count;
	}
	
	public void listLeaves()
	{
		if (allLeaveRecords.isEmpty())
		{
			System.out.println("No leave record");
		}
		for (LeaveRecord LR: allLeaveRecords)
		{
			System.out.println(LR);
		}
	}
	
	
	//methods about joining teams
	public void addRole(RoleInTeam r)
	{
		allRoles.add(r);
		Collections.sort(allRoles);
	}
	
	public void removeRole(RoleInTeam r)
	{
		allRoles.remove(r);
	}
	
	public RoleInTeam findRoleByTeam(Team t)
	{
		for (RoleInTeam r : allRoles)
		{
			if (r.getBelongTeam().getTeamName().equals(t.getTeamName()))
				return r;
		}
		return null;
	}
	
	public void listRoles()
	{
		if (allRoles.isEmpty())
			System.out.println("No role");
		for (RoleInTeam r : allRoles)
		{
			System.out.println(r.getDescriptionForEmployee());
		}
	}
	
	
	// methods dealing with acting
	public ArrayList<Team> leadingTeams()
	{
		ArrayList<Team> LT = new ArrayList<>();
		for (RoleInTeam r : allRoles)
		{
			if (r instanceof RLeaderInTeam)
				LT.add(r.getBelongTeam());
		}
		return LT;
	}
	
	
	// static method for searching employee in a list
	public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) throws ExEmployeeNotFound
	{
		for (Employee e : list)
		{
			if (e.name.equals(nameToSearch))
			{
				return e;
			}
		}
		throw new ExEmployeeNotFound();
	}
	
	@Override
	public String toString()
	{
		return name + " (Entitled Annual Leaves: " + yrLeavesEntitled + " days)";
	}
	
	@Override
	public int compareTo(Employee another)
	{
		return this.name.compareTo(another.name);
	}
}
