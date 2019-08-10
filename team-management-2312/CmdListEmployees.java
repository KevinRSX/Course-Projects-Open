public class CmdListEmployees implements Command
{
	public void execute(String[] cmdParts)
	{
		Company company = Company.getInstance();
		company.listEmployees();
	}
}
