package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static org.bukkit.ChatColor.*;

@RequiredArgsConstructor
public class MentionListener implements Listener {

    private final TechJet plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void asyncPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(message.contains(p.getName())) {
                final CachedMetaData metaData = plugin.getLuckPerms().getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
                String color = metaData.getMetaValue("message-color") != null ? metaData.getMetaValue("message-color") : "";

                message = message.replaceAll(p.getName(),
                        Utils.color(plugin.getConfig().getString("mention-prefix") + p.getName() + color)
                );

                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                break;
            }
        }
        event.setMessage(message);

    }

}
