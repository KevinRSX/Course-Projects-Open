public class ExOverlappedLeaves extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExOverlappedLeaves(Day d1, Day d2)
	{
		super("Overlap with leave from " + d1 + " to " + d2 + "!");
	}
	
	public ExOverlappedLeaves(Team t, Employee e, LeaveRecord LR)
	{
		super("Cannot take leave. " + e.getName() + " is the acting head of " + t.getTeamName() + " during " + LR + "!");
	}
	
	public ExOverlappedLeaves(Employee e, LeaveRecord LR)
	{
		super(e.getName() + " is on leave during " + LR +"!");
	}
	
	public ExOverlappedLeaves(String message)
	{
		super(message);
	}
}
