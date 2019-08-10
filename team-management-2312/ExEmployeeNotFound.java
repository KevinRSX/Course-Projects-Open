public class ExEmployeeNotFound extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExEmployeeNotFound()
	{
		super("Employee not found!");
	}
	
	public ExEmployeeNotFound(Team t, String eName)
	{
		super("Employee (" + eName + ") not found for " + t.getTeamName() + "!");
	}
	
	public ExEmployeeNotFound(String message)
	{
		super(message);
	}
}
