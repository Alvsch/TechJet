package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public class ItemRenameCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }
        if(!(args.length >= 1)) {
            return false;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR) {
            player.sendMessage(plugin.getMessage("hold-item"));
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utils.color(args[0]));
        item.setItemMeta(meta);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

}
