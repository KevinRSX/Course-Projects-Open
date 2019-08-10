public class ExEmployeeAlreadyJoined extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExEmployeeAlreadyJoined()
	{
		super("Employee has already joined the team!");
	}
	
	public ExEmployeeAlreadyJoined(String message)
	{
		super(message);
	}
}
