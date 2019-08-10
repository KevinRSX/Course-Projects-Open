import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Team implements Comparable<Team>
{
	private String teamName;
	private Employee head;
	private Day dateSetup;	//the date this team was setup
	private ArrayList<Employee> allMembers;
	private ArrayList<RActingHeadInTeam> allActingHeads;
	
	public Team(String n, Employee hd)
	{
		teamName = n;
		head = hd;
		dateSetup = SystemDate.getInstance().clone();
		allMembers = new ArrayList<>();
		allMembers.add(hd);
		allActingHeads = new ArrayList<>();
		hd.addRole(new RLeaderInTeam(this));
	}
	
	public static void list(ArrayList<Team> list)
	{
		System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
		for (Team t : list)
		{
			System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup);
		}
	}
	
	public String getTeamName()
	{
		return teamName;
	}
	
	public Employee getHead()
	{
		return head;
	}
	
	//deal with adding and removing members
	public Employee addNormalMember(String eName) throws ExEmployeeNotFound, ExEmployeeAlreadyJoined
	{
		Company company = Company.getInstance();
		Employee e = company.findEmployee(eName);
		for (Employee member : allMembers)
		{
			if (e.getName().equals(member.getName()))
				throw new ExEmployeeAlreadyJoined();
		}
		allMembers.add(e);
		Collections.sort(allMembers);
		e.addRole(new RNormalInTeam(this));
		return e;
	}
	
	public void addHead(Employee e)
	{
		allMembers.add(e);
		Collections.sort(allMembers);
		e.addRole(new RLeaderInTeam(this));
	}
	
	public void addActingHead(RActingHeadInTeam r)
	{
		allActingHeads.add(r);
		Collections.sort(allActingHeads, new Comparator<RActingHeadInTeam>()
				{
					public int compare(RActingHeadInTeam o1, RActingHeadInTeam o2)
					{
						return o1.getLeaveRecord().compareTo(o2.getLeaveRecord());
					}
				}
		);
	}
	
	public void removeActingHead(RActingHeadInTeam r)
	{
		allActingHeads.remove(r);
	}
	
	public void addNormalMember(Employee e)
	{
		allMembers.add(e);
		Collections.sort(allMembers);
		e.addRole(new RNormalInTeam(this));
	}
	
	public void removeMember(Employee e)
	{
		e.removeRole(e.findRoleByTeam(this));
		allMembers.remove(e);
	}
	
	public void listMembers()
	{
		// not possible that no member is in the team (at least leader is there).
		System.out.println(getTeamName() + ":");
		for (Employee e : allMembers)
		{
			RoleInTeam r = e.findRoleByTeam(this);
			System.out.println(r.getDescriptionForTeam(e));
		}
		if (!allActingHeads.isEmpty())
		{
			System.out.println("Acting heads:");
			for (RActingHeadInTeam r : allActingHeads)
			{
				System.out.println(r.getDescriptionForTeam(r.getPerson()));
			}
		}
		System.out.println();
	}
	
	public boolean hasMember(Employee e)
	{
		for (Employee emp : allMembers)
		{
			if (e == emp)
				return true;
		}
		return false;
	}
	
	public boolean hasMember(String memberName)
	{
		for (Employee e : allMembers)
		{
			if (e.getName().equals(memberName))
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(Team another)
	{
		return this.teamName.compareTo(another.teamName);
	}
}
