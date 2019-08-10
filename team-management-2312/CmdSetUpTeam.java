public class CmdSetUpTeam extends RecordedCommand
{
	private Team t;
	public void execute(String[] cmdParts)
	{
		try
		{
			if (cmdParts.length < 3)
			{
				throw new ExInsufficientArgument();
			}
			
			Company company = Company.getInstance();
			if (company.findEmployee(cmdParts[2]) == null)
			{
				throw new ExEmployeeNotFound();
			}
			t = company.createTeam(cmdParts[1], cmdParts[2]);	//may throw ExTeamAlreadyExists
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		}
		catch (ExInsufficientArgument e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExTeamAlreadyExists e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void undoMe()
	{
		t.removeMember(t.getHead());
		Company company = Company.getInstance();
		company.removeTeam(t);
		addRedoCommand(this);
	}
	
	public void redoMe()
	{
		t.addHead(t.getHead());
		Company company = Company.getInstance();
		company.addTeam(t);
		addUndoCommand(this);
	}
}
