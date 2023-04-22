package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.TechJetRegistry;
import me.alvsch.techjet.enums.VanishType;
import me.alvsch.techjet.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

@RequiredArgsConstructor
public class Runnable {

    private final TechJet plugin;

    public void startActionBarTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Map.Entry<Player, VanishType> entry : plugin.getRegistry().getVanish().entrySet()) {
                    if(entry.getKey().isOnline()) {
                        entry.getKey().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.color("&aVanish: " + entry.getValue().toString())));
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 2 * 20);
    }

}
