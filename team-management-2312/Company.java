import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company
{
	private ArrayList<Employee> allEmployees;
	private ArrayList<Team> allTeams;

	private static Company instance = new Company();

	private Company() 
	{
		allEmployees = new ArrayList<Employee>();
		allTeams = new ArrayList<Team>();
	}

	public static Company getInstance() 
	{
		return instance;
	}

	public void listTeams() 
	{
		Team.list(allTeams);
	}
	
	public void listEmployees()
	{
		for (Employee e : allEmployees)
		{
			System.out.println(e);
		}
	}
	
	public void listAllLeaves()
	{
		for (Employee e : allEmployees)
		{
			System.out.println(e.getName() + ":");
			e.listLeaves();
		}
	}

	public Employee createEmployee(String eName, int yle) // See how it is called in main()
	{
		Employee e = new Employee(eName, yle);
		allEmployees.add(e);
		Collections.sort(allEmployees); //allEmployees
		return e;
	}

	public Team createTeam(String tName, String hName) throws ExEmployeeNotFound, ExTeamAlreadyExists
	{
		for (Team te : allTeams)
		{
			if (te.getTeamName().equals(tName))
				throw new ExTeamAlreadyExists();
		}
		Employee e = Employee.searchEmployee(allEmployees, hName);
		Team t = new Team(tName, e);
		allTeams.add(t);
		Collections.sort(allTeams);
		return t;
	}
	
	
	// list all teams with members
	public void listTeamMembers()
	{
		if (allTeams.isEmpty())
			System.out.println("No Team");
		for (Team t : allTeams)
		{
			t.listMembers();
		}
	}

	
	public Employee findEmployee(String nameToSearch) throws ExEmployeeNotFound
	{
		return Employee.searchEmployee(allEmployees, nameToSearch);
	}
	
	public Team findTeam(String nameToSearch) throws ExTeamNotFound
	{
		for (Team t: allTeams)
		{
			if (t.getTeamName().equals(nameToSearch))
				return t;
		}
		throw new ExTeamNotFound();
	}
	
	public void addEmployee(Employee e) throws ExEmployeeAlreadyExists
	{
		for (Employee emp : allEmployees)
		{
			if (emp.getName().equals(e.getName()))
				throw new ExEmployeeAlreadyExists();
		}
		allEmployees.add(e);
		Collections.sort(allEmployees);
	}
	
	public void removeEmployee(Employee e)
	{
		allEmployees.remove(e);
	}
	
	public void addTeam(Team t)
	{
		allTeams.add(t);
		Collections.sort(allTeams);
	}
	public void removeTeam(Team t)
	{
		allTeams.remove(t);
	}


}
