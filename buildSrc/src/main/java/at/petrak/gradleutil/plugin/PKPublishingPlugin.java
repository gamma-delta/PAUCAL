package at.petrak.gradleutil.plugin;

import at.petrak.gradleutil.MiscUtil;
import com.diluv.schoomp.Webhook;
import com.diluv.schoomp.message.Message;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class PKPublishingPlugin implements Plugin<Project> {
    private boolean isRelease = false;
    private String changelog = "";
    private String version = "";

    @Override
    public void apply(Project project) {
        this.changelog = MiscUtil.getGitChangelog(project);
        this.isRelease = MiscUtil.isRelease(this.changelog);

        this.version = MiscUtil.getVersion(project);

        project.task("publishToDiscord", t -> t.doLast(this::pushWebhook));
    }

    private void pushWebhook(Task task) {
        try {
            String discordWebhook = System.getenv("discordWebhook");
            String buildUrl = System.getenv("BUILD_URL");
            if (discordWebhook == null || buildUrl == null) {
                task.getLogger().warn("Cannot send the webhook without the webhook url or the build url");
                return;
            }
            var webhook = new Webhook(discordWebhook, "Petrak@ Patreon Gradle");

            var message = new Message();
            message.setUsername("Patreon Early Access");
            message.setContent("""
                New **%s** prerelease -- build %s for %s!
                Download it here: %s
                Changelog: ```
                %s
                ```"""
                .formatted(task.property("modName"),
                    System.getenv("BUILD_NUMBER"),
                    task.property("minecraftVersion"),
                    buildUrl,
                    this.changelog));

            webhook.sendMessage(message);
        } catch (Exception exn) {
            task.getLogger().error("Failed to push Discord webhook.", exn);
        }

    }
}
