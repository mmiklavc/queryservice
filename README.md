# service-core

Welcome to the project

#### Running project from command line

```bash
mvn exec:java -Dexec.mainClass="com.michaelmiklavcic.queryservice.AppMain"
```

#### Cutting a release for service-core

```bash
mvn release:prepare -Dscm-connection.url=<scm readonly url> -Dscm-developer-connection.url=<scm read-write url>
```

**Note**: The main pom assumes "scm:git:<url>" - simply pass in the URL portion as a build parameter as shown above.

Examples: [maven scm] (http://maven.apache.org/scm/git.html)

1. local git - file://localhost/foo/bar/mygitrepodir
1. github connection url (readonly) - git://github.com/mmiklavc/myproject.git
1. github developer connection url (read/write) - git@github.com:mmiklavc/myproject.git

Performing the release prepare will do the following high-level steps:

1. Change pom versions from X.X-SNAPSHOT to X.X
1. Commit the new poms for the release to Git
1. Tag the release commit in Git
1. Increment poms to a new SNAPSHOT version, e.g. Update from X.0-SNAPSHOT to X.1-SNAPSHOT
1. Commit the updated SNAPSHOT poms

*See [Maven release prepare] (http://maven.apache.org/maven-release/maven-release-plugin/examples/prepare-release.html) documentation for more detail*
