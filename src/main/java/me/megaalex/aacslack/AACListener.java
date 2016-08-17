package me.megaalex.aacslack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.konsolas.aac.api.PlayerViolationCommandEvent;

public class AACListener implements Listener {

    final AACSlack plugin;

    public AACListener(AACSlack plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerHackKick(PlayerViolationCommandEvent e) {
        String message = e.getCommand().replaceAll("aacstaffnotify", "")
                .replaceAll("\\{player\\}", e.getPlayer().getName())
                .replaceAll("\\{tps\\}", plugin.getTpsString())
                .replaceAll("\\{ping\\}", plugin.getPingString(e.getPlayer()));
        plugin.sendSlackMessage(message);
    }
}
