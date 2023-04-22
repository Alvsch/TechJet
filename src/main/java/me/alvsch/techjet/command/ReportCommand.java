package me.alvsch.techjet.command;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJet;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ReportCommand implements TabExecutor {

    private final TechJet plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("console-cant-execute"));
            return false;
        }

        if(args.length < 2) {
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            return false;
        }
        List<String> reason = new ArrayList<>(List.of(args));
        reason.remove(0);


        String message = """
                &a--- Report ---
                Reported: %s
                Submitted by: %s
                Reason: %s
                --- Report End ---""";

        player.sendMessage(plugin.getMessage("report"));
        Bukkit.broadcast(String.format(Utils.color(message),
                        target.getName(), player.getName(), String.join(" ", reason)
                ), plugin.getPermission("reports-see"));

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName)
                    .filter(entry -> entry.startsWith(args[0]))
                    .toList());
        }

        if(args.length == 2) {
            list.addAll(plugin.getConfig().getStringList("report-reasons").stream()
                    .filter(entry -> entry.startsWith(args[1]))
                    .toList());
        }


        return list;
    }

}
