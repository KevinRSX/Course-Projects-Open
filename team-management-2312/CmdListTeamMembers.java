public class CmdListTeamMembers implements Command
{
	public void execute(String[] cmd)
	{
		Company company = Company.getInstance();
		company.listTeamMembers();
	}
}
