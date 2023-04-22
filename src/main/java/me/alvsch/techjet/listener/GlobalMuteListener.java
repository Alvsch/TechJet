package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.TechJetRegistry;
import me.alvsch.techjet.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class GlobalMuteListener implements Listener {

    private final TechJetRegistry registry;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(!registry.isGlobalmute()) {
            return;
        }
        if(event.getPlayer().hasPermission("techjet.globalmute.bypass")) {
            return;
        }
        event.getPlayer().sendMessage(Utils.color("&cYou can't type right now, chat is muted"));
        event.setCancelled(true);
    }

}
