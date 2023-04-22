package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.enums.VanishType;
import me.alvsch.techjet.util.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VanishCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        if(args.length >= 1) {
            VanishType type = VanishType.ON;
            switch (args[0]) {
                case "off" -> type = VanishType.OFF;
                case "total" -> {
                    if(!player.hasPermission(plugin.getPermission("vanish-total"))) return false;
                    type = VanishType.TOTAL;
                }
                case "true" -> {
                    if(!player.hasPermission(plugin.getPermission("vanish-true"))) return false;
                    type = VanishType.TRUE;
                }
            }
            vanish(player, type);
            return true;
        }

        if(!plugin.getRegistry().getVanish().containsKey(player)) {
            // Vanish
            vanish(player, VanishType.ON);
        } else {
            // Reveal
            vanish(player, VanishType.OFF);
        }


        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            List<String> values = new ArrayList<>(List.of("on", "off"));
            if(sender.hasPermission(plugin.getPermission("vanish-total"))) values.add("total");
            if(sender.hasPermission(plugin.getPermission("vanish-true"))) values.add("true");

            list.addAll(values.stream()
                    .filter(entry -> entry.startsWith(args[0]))
                    .toList());
        }


        return list;
    }

    private void reveal(Player player) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(plugin, player);
        }
        plugin.getRegistry().getVanish().remove(player);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.color("&aVanish: OFF")));
    }

    private void vanish(Player player, VanishType vanishType) {
        if(vanishType.equals(VanishType.OFF)) {
            reveal(player);
            return;
        }
        switch (vanishType) {
            case ON -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission(plugin.getPermission("vanish-see"))) {
                        continue;
                    }
                    p.hidePlayer(plugin, player);
                }
            }
            case TOTAL -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission(plugin.getPermission("vanish-total-see"))) {
                        continue;
                    }
                    p.hidePlayer(plugin, player);
                }
            }
            case TRUE -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.hidePlayer(plugin, player);
                }
            }
        }
        plugin.getRegistry().getVanish().put(player, vanishType);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.color("&aVanish: " + vanishType)));

    }

}
