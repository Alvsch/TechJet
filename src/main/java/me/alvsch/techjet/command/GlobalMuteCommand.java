package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.TechJetRegistry;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GlobalMuteCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        boolean silent = false;
        if(args.length >= 1) {
            if(args[0].equalsIgnoreCase("-s")) {
                silent = true;
            }
        }

        if(!plugin.getRegistry().isGlobalmute()) {
            // Mute chat
            plugin.getRegistry().setGlobalmute(true);
            if(silent) {
                Bukkit.broadcast(Utils.formatMessage(plugin.getMessage("globalmute-on-silent"), sender), plugin.getPermission("globalmute-silent-see"));
            } else {
                Bukkit.broadcastMessage(Utils.formatMessage(plugin.getMessage("globalmute-on"), sender));
            }
        } else {
            // Unmute chat
            plugin.getRegistry().setGlobalmute(false);
            if(silent) {
                Bukkit.broadcast(Utils.formatMessage(plugin.getMessage("globalmute-off-silent"), sender), plugin.getPermission("globalmute-silent-see"));
            } else {
                Bukkit.broadcastMessage(Utils.formatMessage(plugin.getMessage("globalmute-off"), sender));
            }
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
