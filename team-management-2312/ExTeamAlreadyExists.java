public class ExTeamAlreadyExists extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExTeamAlreadyExists()
	{
		super("Team already exists!");
	}
	public ExTeamAlreadyExists(String message)
	{
		super(message);
	}
}
