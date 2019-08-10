public class ExLeaveOutOfRange extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExLeaveOutOfRange()
	{
		super("Annual leaves out of range (0-300)!");
	}
	public ExLeaveOutOfRange(String message)
	{
		super(message);
	}
}
