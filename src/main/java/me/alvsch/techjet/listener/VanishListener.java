package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.enums.VanishType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class VanishListener implements Listener {

    private final TechJet plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean on = player.hasPermission("techjet.vanish.see");
        boolean total = player.hasPermission("techjet.vanish.total.see");

        for(Map.Entry<Player, VanishType> entry : plugin.getRegistry().getVanish().entrySet()) {
            VanishType type = entry.getValue();
            if(type.equals(VanishType.ON) && on) {
                continue;
            }
            if(type.equals(VanishType.TOTAL) && total) {
                continue;
            }

            player.hidePlayer(plugin, entry.getKey());
        }
    }

}
