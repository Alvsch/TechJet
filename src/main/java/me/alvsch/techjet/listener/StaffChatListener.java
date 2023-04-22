package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class StaffChatListener implements Listener {

    private final TechJet plugin;

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if(message.startsWith(plugin.getConfig().getString("staffchat-prefix"))) {
            if(event.getPlayer().hasPermission("techjet.staffchat")) {
                event.setCancelled(true);
                Utils.sendStaffMessage(message.replace("#", ""), event.getPlayer());
            }
        }
    }

}
