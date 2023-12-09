package me.playgamesgo.miragerooms.utils;

import de.leonhard.storage.Config;
import de.leonhard.storage.internal.settings.ReloadSettings;

import java.util.List;

public class ConfigManager {
    private static final List<String> help = List.of(
            "&8&m----------&r &6MirageRooms &8&m----------",
            "&8&m--------------------------------",
            "&e/rooms reload &7- &fReloads the config",
            "&e/rooms help &7- &fShows this help message",
            "&e/rooms addRoom <region> <name> <price> <maxPlayers> <days> &7- &fAdds a room",
            "&e/rooms removeRoom <region> &7- &fRemoves a room",
            "&e/rooms setOwner <region> <player> &7- &fSets the owner of a room",
            "&e/rooms setPrice <region> <price> &7- &fSets the price of a room",
            "&e/rooms setMaxPlayers <region> <maxPlayers> &7- &fSets the max players of a room",
            "&e/rooms setDays <region> <days> &7- &fSets the days of a room",
            "&e/rooms setName <region> <name> &7- &fSets the name of a room",
            "&e/rooms addPlayer <region> <player> &7- &fAdds a player to a room",
            "&e/rooms removePlayer <region> <player> &7- &fRemoves a player from a room",
            "&e/rooms buyRoom <region> &7- &fBuys a room",
            "&e/rooms extendRoom <region> &7- &fExtends a room",
            "&e/rooms leaveRoom &7- &fLeaves a room"
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
        configFile.setDefault("roomAdded", "&aRoom added!");
        configFile.setDefault("roomRemoved", "&aRoom removed!");
        configFile.setDefault("regionDoesntExist", "&cRegion doesn't exist!");
        configFile.setDefault("roomAlreadyExists", "&cRoom already exists!");
        configFile.setDefault("roomDoesntExist", "&cRoom doesn't exist!");
        configFile.setDefault("ownerSet", "&aOwner set!");
        configFile.setDefault("priceSet", "&aPrice set!");
        configFile.setDefault("maxPlayersSet", "&aMax players set!");
        configFile.setDefault("daysSet", "&aDays set!");
        configFile.setDefault("nameSet", "&aName set!");
        configFile.setDefault("playerAdded", "&aPlayer added!");
        configFile.setDefault("playerRemoved", "&aPlayer removed!");
        configFile.setDefault("tooManyPlayers", "&cToo many players!");
        configFile.setDefault("playerAlreadyInRoom", "&cPlayer already in room!");
        configFile.setDefault("roomAlreadyBought", "&cRoom already bought!");
        configFile.setDefault("roomBought", "&aRoom bought!");
        configFile.setDefault("notEnoughMoney", "&cNot enough money!");
        configFile.setDefault("tooManyRooms", "&cToo many rooms!");
        configFile.setDefault("roomExtended", "&aRoom extended!");
        configFile.setDefault("roomLeft", "&aRoom left!");


        configFile.setPathPrefix(null);
        configFile.setDefault("use-mysql", false);
        configFile.setDefault("mysql.host", "localhost");
        configFile.setDefault("mysql.port", 3306);
        configFile.setDefault("mysql.database", "db");
        configFile.setDefault("mysql.username", "root");
        configFile.setDefault("mysql.password", "password");

        configFile.setDefault("max-players", 10);
        configFile.setDefault("hours-to-check", 6);
        configFile.write();
    }
}