package me.playgamesgo.miragerooms;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.WorldGuard;
import de.leonhard.storage.Config;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.Indyuce.mmoitems.MMOItems;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MirageRooms extends JavaPlugin {
    public static Config configFile, dataFile;
    public static Economy econ = null;
    public static WorldEdit worldEdit = null;
    public static WorldGuard worldGuard = null;
    public static MMOItems mmoItems = null;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).verboseOutput(true));
    }

    @Override
    public void onEnable() {
        setupDecencies();

        configFile = new Config("config.yml", getDataFolder().toString());
        dataFile = new Config("data/rooms.yml", getDataFolder().toString());
        ConfigManager.init(configFile);

        CommandAPI.onEnable();
    }
    
    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }

    private void setupDecencies() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        econ = rsp.getProvider();

        if (getServer().getPluginManager().getPlugin("WorldEdit") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no WorldEdit dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        worldEdit = WorldEdit.getInstance();

        if (getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no WorldGuard dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        worldGuard = WorldGuard.getInstance();

        if (getServer().getPluginManager().getPlugin("MMOItems") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no MMOItems dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        mmoItems = MMOItems.plugin;
    }
}
