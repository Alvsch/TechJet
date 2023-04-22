package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJetRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ModModeListener implements Listener {

    private final TechJetRegistry registry;

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if(registry.getModmode().containsKey(player)) {
            if(player.hasPermission("techjet.modmode.drop")) {
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if(!registry.getModmode().containsKey(event.getPlayer())) {
            return;
        }
        event.getPlayer().performCommand("modmode");
    }
}
