public class ExEmployeeAlreadyExists extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExEmployeeAlreadyExists()
	{
		super("Employee already exists!");
	}
	public ExEmployeeAlreadyExists(String message)
	{
		super(message);
	}
}
