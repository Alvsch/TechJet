package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MainCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(args.length >= 1)) {
            return false;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission(plugin.getPermission("techjet-reload"))) {
                sender.sendMessage(plugin.getMessage("no-permission"));
                return false;
            }
            sender.sendMessage(plugin.getMessage("reload-start"));
            plugin.saveFiles();
            plugin.createFiles();
            sender.sendMessage(plugin.getMessage("reload-finish"));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.addAll(Stream.of("reload")
                    .filter(entry -> entry.startsWith(args[0]))
                    .toList());
        }


        return list;
    }
}
