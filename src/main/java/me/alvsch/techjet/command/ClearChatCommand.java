package me.alvsch.techjet.command;

import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ClearChatCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean silent = false;
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("-s")) {
                silent = true;
            }
        }

        for (int i = 0; i < 50; i++) {
            Bukkit.broadcastMessage(" ");
        }
        if(silent) {
            Bukkit.broadcast(Utils.formatMessage(plugin.getMessage("clearchat-silent"), sender), plugin.getPermission("clearchat-silent-see"));
        } else {
           Bukkit.broadcastMessage(Utils.formatMessage(plugin.getMessage("clearchat"), sender));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1) {
            list.add("-s");
        }

        return list;
    }

}
