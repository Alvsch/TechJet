package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

@RequiredArgsConstructor
public class SpawnListener implements Listener {

    private final TechJet plugin;

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        if(event.getPlayer().hasPlayedBefore()) {
            return;
        }
        Location spawn = plugin.getConfig().getLocation("spawn");
        if(spawn == null) {
            return;
        }

        event.getPlayer().teleport(spawn);
    }

}
