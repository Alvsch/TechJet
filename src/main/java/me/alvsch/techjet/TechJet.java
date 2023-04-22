package me.alvsch.techjet;

import lombok.Getter;
import me.alvsch.techjet.command.*;
import me.alvsch.techjet.command.wip.SetSpawnCommand;
import me.alvsch.techjet.command.wip.SpawnCommand;
import me.alvsch.techjet.listener.*;
import me.alvsch.techjet.listener.Runnable;
import me.alvsch.techjet.util.Utils;
import net.luckperms.api.LuckPerms;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class TechJet extends JavaPlugin {

    private FileConfiguration config;
    private FileConfiguration permissions;
    private FileConfiguration messages;

    @Getter
    private LuckPerms luckPerms;
    @Getter
    private final TechJetRegistry registry = new TechJetRegistry();

    @Override
    public void onEnable() {
        // Plugin startup logic

        luckPerms = getServer().getServicesManager().load(LuckPerms.class);
        createFiles();

        registerCommands();
        registerListeners();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveFiles();
        
        for(Player p : registry.getModmode().keySet()) {
            p.performCommand("modmode");
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void registerCommands() {
        getCommand("techjet").setExecutor(new MainCommand(this));
        getCommand("techjet").setTabCompleter(new MainCommand(this));

        getCommand("discord").setExecutor(new DiscordCommand(this));
        getCommand("discord").setTabCompleter(new DiscordCommand(this));

        getCommand("invsee").setExecutor(new InvseeCommand(this));
        getCommand("invsee").setTabCompleter(new InvseeCommand(this));

        getCommand("echest").setExecutor(new EChestCommand(this));
        getCommand("echest").setTabCompleter(new EChestCommand(this));

        getCommand("itemrename").setExecutor(new ItemRenameCommand(this));
        getCommand("itemrename").setTabCompleter(new ItemRenameCommand(this));

        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("fly").setTabCompleter(new FlyCommand(this));

        getCommand("flyspeed").setExecutor(new FlySpeedCommand(this));
        getCommand("flyspeed").setTabCompleter(new FlySpeedCommand(this));

        getCommand("clearchat").setExecutor(new ClearChatCommand(this));
        getCommand("clearchat").setTabCompleter(new ClearChatCommand(this));

        getCommand("globalmute").setExecutor(new GlobalMuteCommand(this));
        getCommand("globalmute").setTabCompleter(new GlobalMuteCommand(this));

        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("report").setTabCompleter(new ReportCommand(this));

        getCommand("nick").setExecutor(new NickCommand(this));
        getCommand("nick").setTabCompleter(new NickCommand(this));

        getCommand("silentcontainer").setExecutor(new SlientContainerCommand(this));
        getCommand("silentcontainer").setTabCompleter(new SlientContainerCommand(this));

        getCommand("modmode").setExecutor(new ModModeCommand(this));
        getCommand("modmode").setTabCompleter(new ModModeCommand(this));

        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("staffchat").setTabCompleter(new StaffChatCommand(this));

        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("vanish").setTabCompleter(new VanishCommand(this));

        getCommand("freeze").setExecutor(new FreezeCommand(this));
        getCommand("freeze").setTabCompleter(new FreezeCommand(this));

        getCommand("playtime").setExecutor(new PlaytimeCommand(this));
        getCommand("playtime").setTabCompleter(new PlaytimeCommand(this));

        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("spawn").setTabCompleter(new SpawnCommand(this));

        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("setspawn").setTabCompleter(new SetSpawnCommand(this));

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new FreezeListener(this.getRegistry()), this);
        getServer().getPluginManager().registerEvents(new GlobalMuteListener(this.getRegistry()), this);
        getServer().getPluginManager().registerEvents(new MentionListener(this), this);
        getServer().getPluginManager().registerEvents(new ModModeListener(this.getRegistry()), this);
        getServer().getPluginManager().registerEvents(new SilentContainerListener(this.getRegistry()), this);
        getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
        getServer().getPluginManager().registerEvents(new StaffChatListener(this), this);
        getServer().getPluginManager().registerEvents(new VanishListener(this), this);

        Runnable runnable = new Runnable(this);
        runnable.startActionBarTask();

    }


    @SuppressWarnings("all")
    private void loadFile(File file, String resourcePath) {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource(resourcePath, false);
        }
    }

    public void saveFiles() {
        File configf = new File(getDataFolder(), "config.yml");
        loadFile(configf, "config.yml");

        try {
            config.save(configf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create and load config files
     * @return If the files was created and loaded successfully
     */
    public boolean createFiles() {
        File configf = new File(getDataFolder(), "config.yml");
        loadFile(configf, "config.yml");
        File permissionsf = new File(getDataFolder(), "permissions.yml");
        loadFile(permissionsf, "permissions.yml");
        File messagesf = new File(getDataFolder(), "messages.yml");
        loadFile(messagesf, "messages.yml");

        config = new YamlConfiguration();
        permissions = new YamlConfiguration();
        messages = new YamlConfiguration();
        try {
            config.load(configf);
            permissions.load(permissionsf);
            messages.load(messagesf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getPermission(String key) {
        String permission = permissions.getString(key);
        if(permission == null) throw new NullPointerException("Permission not found");
        return permission;
    }

    public String getMessage(String key) {
        String message = messages.getString(key);
        if(message == null) message = "Message not found: " + key;
        return Utils.color(message);
    }

    public FileConfiguration getConfig() {
        return config;
    }

}
