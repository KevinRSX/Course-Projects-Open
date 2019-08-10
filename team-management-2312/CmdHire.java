public class CmdHire extends RecordedCommand
{
	private Employee e;
	public void execute(String[] cmdParts)
	{
		try
		{
			Company company = Company.getInstance();
			if (cmdParts.length < 3)
			{
				throw new ExInsufficientArgument();
			}
			
			if (Integer.parseInt(cmdParts[2]) > 300)
			{
				throw new ExLeaveOutOfRange();
			}
			e = new Employee(cmdParts[1],Integer.parseInt(cmdParts[2]));
			company.addEmployee(e);	//may throw ExEmployeeAlreadyExists
			addUndoCommand(this);
			clearRedoList();
			System.out.println("Done.");
		}
		catch (ExInsufficientArgument e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExEmployeeAlreadyExists e)
		{
			System.out.println(e.getMessage());
		}
		catch (ExLeaveOutOfRange e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void undoMe()
	{
		Company company = Company.getInstance();
		company.removeEmployee(e);
		addRedoCommand(this);
	}
	
	public void redoMe()
	{
		try
		{
			Company company = Company.getInstance();
			company.addEmployee(e);
			addUndoCommand(this);
		}
		catch (ExEmployeeAlreadyExists e)
		{
			System.out.println(e.getMessage());
		}
	}
}