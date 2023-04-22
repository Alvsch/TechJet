package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.TechJetRegistry;
import me.alvsch.techjet.util.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ModModeCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        if(!plugin.getRegistry().getModmode().containsKey(player)) {
            // Enable modmode
            Inventory inv = player.getInventory();
            plugin.getRegistry().getModmode().put(player, inv.getContents());
            inv.clear();

            // Flight
            if(player.hasPermission("techjet.modmode.fly"))
                player.setAllowFlight(true);
            if(player.hasPermission("techjet.modmode.gamemode"))
                player.setGameMode(GameMode.CREATIVE);

            if(player.hasPermission("techjet.modmode.vanish")) {
                player.performCommand("techjet:vanish on");
            }

            player.sendMessage(plugin.getMessage("modmode-on"));

        } else {
            // Disable modmode
            ItemStack[] items = plugin.getRegistry().getModmode().remove(player);
            player.getInventory().setContents(items);

            // Flight
            if(!player.hasPermission("techjet.fly"))
                player.setAllowFlight(false);
            // Creative
            if(!player.hasPermission("techjet.gamemode.creative"))
                player.setGameMode(GameMode.SURVIVAL);

            player.performCommand("techjet:vanish off");
            player.sendMessage(plugin.getMessage("modmode-off"));

        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

}
