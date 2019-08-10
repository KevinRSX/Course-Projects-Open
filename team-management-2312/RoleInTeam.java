public abstract class RoleInTeam implements Comparable<RoleInTeam>
{
	Team belongTeam;
	
	public RoleInTeam(Team bt)
	{
		belongTeam = bt;
	}
	
	public Team getBelongTeam()
	{
		return belongTeam;
	}
	
	public abstract String getDescriptionForTeam(Employee e);
	public abstract String getDescriptionForEmployee();
	
	@Override
	public int compareTo(RoleInTeam another)
	{
		return this.belongTeam.getTeamName().compareTo(another.belongTeam.getTeamName());
	}
}
