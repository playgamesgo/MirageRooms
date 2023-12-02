package me.playgamesgo.miragerooms.utils;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;

import java.util.List;

public class ConfigManager {
    private static final List<String> help = List.of(
            "&8&m----------&r &6MirageRooms &8&m----------",
            "&8&m--------------------------------",
            "&e/rooms reload &7- &fReloads the config",
            "&e/rooms giveKey <player> <region> &7- &fGives a key to a player",
            "&e/rooms help &7- &fShows this help message"
    );

    public static void init(Config configFile) {
        configFile.setReloadSettings(ReloadSettings.INTELLIGENT);

        configFile.setPathPrefix("messages");
        configFile.setDefault("prefix", "&8[&6MirageRooms&8] &r");
        configFile.setDefault("help", help);
        configFile.setDefault("reloaded", "&aReloaded config!");
        configFile.setDefault("onlyPlayers", "&cOnly players can use this command!");
        configFile.setDefault("unknownRegion", "&cUnknown region!");
        configFile.setDefault("noPermission", "&cYou don't have permission to use this command!");
        configFile.setDefault("gaveKey", "&aGave key to %player% for region %region%!");
        configFile.setDefault("regionAlreadyExists", "&cRegion already exists!");
        configFile.setDefault("regionAdded", "&aRegion added!");
        configFile.setDefault("regionRemoved", "&aRegion removed!");
        configFile.setDefault("regionDoesntExist", "&cRegion doesn't exist!");
        configFile.write();
    }
}