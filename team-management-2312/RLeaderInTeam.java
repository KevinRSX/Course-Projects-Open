public class RLeaderInTeam extends RoleInTeam
{
	public RLeaderInTeam(Team bt)
	{
		super(bt);
	}
	
	@Override
	public String getDescriptionForTeam(Employee e)
	{
		return e.getName() + " (Head of Team)";
	}

	@Override
	public String getDescriptionForEmployee()
	{
		return belongTeam.getTeamName() + " (Head of Team)";
	}
}
