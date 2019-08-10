public class ExInsufficientBalance extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExInsufficientBalance(int currentBalance)
	{
		super("Insufficient balance. " + currentBalance + " days left only!");
	}
	public ExInsufficientBalance(String message)
	{
		super(message);
	}
}
