public class ExDateStartLaterThanEnd extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExDateStartLaterThanEnd()
	{
		super("Start date is later than end date!");
	}
	public ExDateStartLaterThanEnd(String message)
	{
		super(message);
	}
}
