package me.alvsch.techjet.util;

import me.alvsch.techjet.enums.VanishType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean isValidContainer(Block block) {
        List<Material> validContainer = new ArrayList<>();
        validContainer.add(Material.CHEST);
        validContainer.add(Material.TRAPPED_CHEST);
        validContainer.add(Material.BARREL);

        return validContainer.contains(block.getType()) || block.getType().toString().toUpperCase().contains("SHULKER_BOX");
    }

    public static String formatMessage(String message, CommandSender sender) {
        message = message.replaceAll("\\{sender}", sender.getName());
        return message;
    }
    public static String formatMessage(String message, CommandSender sender, Player target) {
        message = message.replaceAll("\\{sender}", sender.getName());
        message = message.replaceAll("\\{target}", target.getName());
        return message;
    }

    public static void sendStaffMessage(String message, Player player) {
        // [STAFF CHAT] Alvsch1: Hello
        message = "&b[STAFF CHAT] " + player.getName() + ": &f" + message;
        Bukkit.broadcast(Utils.color(message),"techjet.staffchat");
    }

}
