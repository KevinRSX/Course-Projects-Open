public class RNormalInTeam extends RoleInTeam
{
	public RNormalInTeam(Team bt)
	{
		super(bt);
	}
	
	@Override
	public String getDescriptionForTeam(Employee e)
	{
		return e.getName();
	}

	@Override
	public String getDescriptionForEmployee()
	{
		return belongTeam.getTeamName();
	}
}
