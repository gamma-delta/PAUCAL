package at.petrak.gradleutil;

import org.gradle.api.Project;

import java.io.ByteArrayOutputStream;

public class Misc {
    public static String getVersion(Project project) {
        var changelog = getGitChangelog(project);

        String version = project.property("modVersion").toString();
        if (!isRelease(changelog) && System.getenv("BUILD_NUMBER") != null) {
            version += "-pre-" + System.getenv("BUILD_NUMBER");
        } else if (System.getenv("TAG_NAME") != null) {
            version = System.getenv("TAG_NAME").substring(1);
            project.getLogger().info("Version overridden to tag version " + version);
        }

        return version;
    }

    public static String getGitChangelog(Project project) {
        var stdout = new ByteArrayOutputStream();
        var gitHash = System.getenv("GIT_COMMIT");
        var gitPrevHash = System.getenv("GIT_PREVIOUS_COMMIT");
        var travisRange = System.getenv("TRAVIS_COMMIT_RANGE");

        if (gitHash != null && gitPrevHash != null) {
            project.exec(spec -> {
                spec.commandLine("git", "log", "--pretty=tformat:- %s", gitPrevHash + "..." + gitHash);
                spec.setStandardOutput(stdout);
            });
        } else if (travisRange != null) {
            project.exec(spec -> {
                spec.commandLine("git", "log", "--pretty=tformat:- %s", travisRange);
                spec.setStandardOutput(stdout);
            });
        } else {
            return "";
        }

        return stdout.toString();
    }

    public static boolean isRelease(String changelog) {
        return changelog.matches("(?i)^\\[release");
    }
}
