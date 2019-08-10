public class CmdStartNewDay extends RecordedCommand
{
	private Day previousDate;
	private Day changedDate;
	public void execute(String[] cmdParts)
	{
		previousDate = SystemDate.getInstance().clone();
		changedDate = new Day(cmdParts[1]);
		SystemDate.getInstance().set(cmdParts[1]);
		addUndoCommand(this);
		clearRedoList();
		System.out.println("Done.");
	}
	
	public void undoMe()
	{
		SystemDate.getInstance().set(previousDate.toString());
		addRedoCommand(this);
	}
	
	public void redoMe()
	{
		SystemDate.getInstance().set(changedDate.toString());
		addUndoCommand(this);
	}
}