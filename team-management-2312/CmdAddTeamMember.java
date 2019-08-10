public class CmdAddTeamMember extends RecordedCommand
{
	private Employee e;
	private Team t;
	public void execute(String[] cmdParts)
	{
		try
		{
			if (cmdParts.length < 3)
				throw new ExInsufficientArgument();
			Company company = Company.getInstance();
			e = company.findEmployee(cmdParts[2]);	// may throw ExEmployeeNotFound
			t = company.findTeam(cmdParts[1]);	// may throw ExTeamNotFound
			t.addNormalMember(cmdParts[2]);		// may throw ExEmployeeNotFound or ExEmployeeAlreadyJoined
			
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
			
		}
		catch (ExInsufficientArgument e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExTeamNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeNotFound e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeAlreadyJoined e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void undoMe()
	{
		t.removeMember(e);
		addRedoCommand(this);
	}
	
	public void redoMe()
	{
		t.addNormalMember(e);
		addUndoCommand(this);
	}
}
