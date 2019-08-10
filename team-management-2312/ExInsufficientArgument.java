public class ExInsufficientArgument extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExInsufficientArgument()
	{
		super("Insufficient command arguments!");
	}
	public ExInsufficientArgument(String message)
	{
		super(message);
	}
}
