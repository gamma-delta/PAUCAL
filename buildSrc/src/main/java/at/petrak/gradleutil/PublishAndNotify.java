package at.petrak.gradleutil;

import com.diluv.schoomp.Webhook;
import com.diluv.schoomp.message.Message;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PublishAndNotify implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        var changelog = Misc.getGitChangelog(project);

        project.task("sendWebhook", t -> {
            t.doLast(t2 -> {
                try {
                    String discordWebhook = System.getenv("discordWebhook");
                    String buildUrl = System.getenv("BUILD_URL");
                    if (discordWebhook == null || buildUrl == null) {
                        project.getLogger().warn("Cannot send the webhook without the webhook url or the build url");
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
                        .formatted(project.property("modName"),
                            System.getenv("BUILD_NUMBER"),
                            project.property("minecraftVersion"),
                            buildUrl,
                            changelog));

                    webhook.sendMessage(message);
                } catch (Exception ignored) {
                    project.getLogger().error("Failed to push Discord webhook.");
                }
            });
        });
    }

}
