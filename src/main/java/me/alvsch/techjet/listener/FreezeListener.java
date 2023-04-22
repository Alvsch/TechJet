package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJetRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class FreezeListener implements Listener {

    private final TechJetRegistry registry;

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(registry.getFreeze().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

}
