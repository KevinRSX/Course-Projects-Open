public class ExActingHeadNotDesignated extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExActingHeadNotDesignated(Team t)
	{
		super("Please name a member to be the acting head of " + t.getTeamName());
	}
	
	public ExActingHeadNotDesignated(String message)
	{
		super(message);
	}
}
