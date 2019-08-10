public class ExDateHasAlreadyPassed extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExDateHasAlreadyPassed()
	{
		super("Wrong Date. System date is already " + SystemDate.getInstance() + "!");
	}
	public ExDateHasAlreadyPassed(String message)
	{
		super(message);
	}
}
