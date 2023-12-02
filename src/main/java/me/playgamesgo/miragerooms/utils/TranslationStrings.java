package me.playgamesgo.miragerooms.utils;

import me.playgamesgo.miragerooms.MirageRooms;
import org.bukkit.ChatColor;

public class TranslationStrings {
    public static void init() {
        MirageRooms.configFile.setPathPrefix("messages");
    }

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', MirageRooms.configFile.getString("prefix"));
    public static final String RELOADED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("reloaded"));
    public static final String ONLY_PLAYERS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("onlyPlayers"));
    public static final String UNKNOWN_REGION = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("unknownRegion"));
    public static final String NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("noPermission"));
    public static final String GAVE_KEY = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("gaveKey"));
    public static final String REGION_ALREADY_EXISTS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("regionAlreadyExists"));
    public static final String REGION_ADDED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("regionAdded"));
    public static final String REGION_REMOVED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("regionRemoved"));
    public static final String REGION_DOESNT_EXIST = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("regionDoesntExist"));
}
