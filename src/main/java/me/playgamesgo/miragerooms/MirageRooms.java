package me.playgamesgo.miragerooms;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldguard.WorldGuard;
import de.leonhard.storage.Config;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.playgamesgo.miragerooms.commands.RoomsCommand;
import me.playgamesgo.miragerooms.tasks.DateCheck;
import me.playgamesgo.miragerooms.utils.ConfigManager;
import me.playgamesgo.miragerooms.utils.DatabaseManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class MirageRooms extends JavaPlugin {
    public static Config configFile;
    public static Economy econ = null;
    public static WorldEdit worldEdit = null;
    public static WorldGuard worldGuard = null;
    public static DatabaseManager databaseManager = null;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
    }

    @Override
    public void onEnable() {
        setupDecencies();

        configFile = new Config("config.yml", getDataFolder().toString());
        ConfigManager.init(configFile);

        CommandAPI.registerCommand(RoomsCommand.class);

        CommandAPI.onEnable();

        databaseManager = new DatabaseManager(this);

        DateCheck dateCheck = new DateCheck();

        dateCheck.runTaskTimer(this, 0, 20L * 60 * 60 * configFile.getInt("hours-to-check"));
    }
    
    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        DatabaseManager.closeConnection();
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

        if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") == null) {
            getLogger().severe(String.format("[%s] - Disabled due to no WorldGuard dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        worldGuard = WorldGuard.getInstance();
    }
}
