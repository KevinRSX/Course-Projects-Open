public class ExNotLeadingRequiredTeam extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExNotLeadingRequiredTeam(Employee e, Team t)
	{
		super(e.getName() + " is not leading " + t.getTeamName());
	}
	public ExNotLeadingRequiredTeam(String message)
	{
		super(message);
	}
}
