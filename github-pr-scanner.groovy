import java.io.IOException;
import java.util.List;

@Grab(group='org.kohsuke', module='github-api', version='1.59')
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;


/*************
 * Usage message
 ***********/
def usage() {

	def USAGE_MESSAGE = """\
	PRScanner - Scans for open pull requests in all repositories in an organization
	=================================================================================
	Usage:
	groovy github-pr-scanner.groovy <organization-name> <username> <password>
	
	Exit Codes:
	0: If no open pull requests were found
	1: If open pull requests are found
	2: Command line usage problem
	3: Any other error
	"""

	System.err.println(USAGE_MESSAGE);
}


// This is where the script starts
if (args.length != 3) {
	usage();
	System.exit(2);
}
try {
	String organizationName = args[0];
	String username = args[1];
	String password = args[2];

	System.out.println("Connecting to Github as: " + username);
	GitHub github = GitHub.connectUsingPassword(username, password);
	System.out.println("Reading Organization " + organizationName);
	GHOrganization organization = github
			.getOrganization(organizationName);
	System.out
			.println("Loading repositories with open Pull Requests. This may take a few seconds.");
	List<GHRepository> repos = organization
			.getRepositoriesWithOpenPullRequests();
	System.out.println("Found " + repos.size()
			+ " repositories with Open Pull Requests");
	for (GHRepository repo : repos) {
		System.out.println(repo.getName());
	}

	System.out.println("done");
} catch (Exception e) {
	e.printStackTrace();
	System.exit(3);
}
		

