package me.playgamesgo.miragerooms.utils;

import me.playgamesgo.miragerooms.MirageRooms;
import org.bukkit.ChatColor;

public class TranslationStrings {
    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', MirageRooms.configFile.getString("messages.prefix"));
    public static final String RELOADED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.reloaded"));
    public static final String ONLY_PLAYERS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.onlyPlayers"));
    public static final String GAVE_KEY = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.gaveKey"));
    public static final String ROOM_ADDED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomAdded"));
    public static final String ROOM_REMOVED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomRemoved"));
    public static final String REGION_DOESNT_EXIST = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.regionDoesntExist"));
    public static final String ROOM_ALREADY_EXISTS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomAlreadyExists"));
    public static final String ROOM_DOESNT_EXIST = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomDoesntExist"));
    public static final String OWNER_SET = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.ownerSet"));
    public static final String PRICE_SET = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.priceSet"));
    public static final String MAX_PLAYERS_SET = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.maxPlayersSet"));
    public static final String DAYS_SET = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.daysSet"));
    public static final String NAME_SET = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.nameSet"));
    public static final String PLAYER_ADDED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.playerAdded"));
    public static final String PLAYER_REMOVED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.playerRemoved"));
    public static final String TOO_MANY_PLAYERS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.tooManyPlayers"));
    public static final String PLAYER_ALREADY_IN_ROOM = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.playerAlreadyInRoom"));
    public static final String ROOM_ALREADY_BOUGHT = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomAlreadyBought"));
    public static final String ROOM_BOUGHT = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomBought"));
    public static final String NOT_ENOUGH_MONEY = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.notEnoughMoney"));
    public static final String TOO_MANY_ROOMS = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.tooManyRooms"));
    public static final String ROOM_EXTENDED = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomExtended"));
    public static final String ROOM_LEFT = ChatColor.translateAlternateColorCodes('&', PREFIX + MirageRooms.configFile.getString("messages.roomLeft"));
}
