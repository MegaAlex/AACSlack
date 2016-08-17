package me.megaalex.aacslack;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.konsolas.aac.api.AACAPIProvider;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;

public class AACSlack extends JavaPlugin {

    private String id1;
    private String id2;
    private String token;


    public void onEnable() {
        loadConfiguration();

        getServer().getPluginManager().registerEvents(new AACListener(this), this);
    }


    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        FileConfiguration config = getConfig();


        id1 = config.getString("id1", null);
        id2 = config.getString("id2", null);
        token = config.getString("token", null);
    }

    public void sendSlackMessage(final String message) {
        if(id1 == null || id2 == null || token == null) {
            getLogger().warning("Tried to send slack message but config not set properly!");
            return;
        }

        final String url = "https://hooks.slack.com/services/" + id1 + "/" + id2 + "/" + token;

        new BukkitRunnable() {
            @Override
            public void run() {
                SlackApi api = new SlackApi(url);
                api.call(new SlackMessage("Punisher", message));
            }
        }.runTaskAsynchronously(this);
    }

    public String getTpsString() {
        double tps = AACAPIProvider.getAPI().getTPS();
        return String.format("%.2f", tps);
    }

    public String getPingString(Player player) {
        int ping = AACAPIProvider.getAPI().getPing(player);
        return String.valueOf(ping);
    }
}
