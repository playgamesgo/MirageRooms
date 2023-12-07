package me.playgamesgo.miragerooms.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.managers.RegionManager;
import dev.jorel.commandapi.annotations.*;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.AMultiLiteralArgument;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import dev.jorel.commandapi.annotations.arguments.ATextArgument;
import me.playgamesgo.miragerooms.MirageRooms;
import me.playgamesgo.miragerooms.tasks.DateCheck;
import me.playgamesgo.miragerooms.utils.DatabaseManager;
import me.playgamesgo.miragerooms.utils.RoomData;
import me.playgamesgo.miragerooms.utils.TranslationStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Command("rooms")
@Alias("miragerooms")
@Permission("miragerooms.command.rooms")
@NeedsOp
public class RoomsCommand {
    @Default
    @Permission("miragerooms.command.rooms")
    public static void defaultCommand(CommandSender sender) {
        MirageRooms.configFile.setPathPrefix("messages");
        for (String line : MirageRooms.configFile.getStringList("help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

    @Subcommand("help")
    @Permission("miragerooms.command.rooms.help")
    public static void help(CommandSender sender) {
        defaultCommand(sender);
    }

    @Subcommand("reload")
    @Permission("miragerooms.command.rooms.reload")
    public static void reload(CommandSender sender) {
        MirageRooms.configFile.forceReload();
        sender.sendMessage(TranslationStrings.RELOADED);
    }

    @Subcommand("addroom")
    @Permission("miragerooms.command.rooms.addRoom")
    public static void addRoom(Player player, @ATextArgument String regionName, @ATextArgument String name, @AIntegerArgument int price, @AIntegerArgument int maxPlayers, @AIntegerArgument int days) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            if (!DatabaseManager.roomExists(regionName)) {
                DatabaseManager.createRoom(new RoomData(regions.getRegion(regionName), name, price, maxPlayers, null, null, days, null, null));
                player.sendMessage(TranslationStrings.ROOM_ADDED);
            } else {
                player.sendMessage(TranslationStrings.ROOM_ALREADY_EXISTS);
            }
        } else {
            player.sendMessage(TranslationStrings.REGION_DOESNT_EXIST);
        }
    }

    @Subcommand("removeroom")
    @Permission("miragerooms.command.rooms.removeRoom")
    public static void removeRoom(Player player, @ATextArgument String regionName) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            if (DatabaseManager.roomExists(regionName)) {
                DatabaseManager.deleteRoom(regions.getRegion(regionName));
                player.sendMessage(TranslationStrings.ROOM_REMOVED);
            } else {
                player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
            }
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("setowner")
    @Permission("miragerooms.command.rooms.setOwner")
    public static void setOwner(Player player, @ATextArgument String regionName, @APlayerArgument Player newOwner) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName) && DatabaseManager.roomExists(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setOwner(newOwner);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            roomData.setDateBought(sdf.format(c.getTime()));
            c.add(Calendar.DATE, roomData.getDays());
            roomData.setDateExpired(sdf.format(c.getTime()));
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.OWNER_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("removeowner")
    @Permission("miragerooms.command.rooms.removeOwner")
    public static void removeOwner(Player player, @ATextArgument String regionName) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setOwner(null);
            roomData.setDateBought(null);
            roomData.setDateExpired(null);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.OWNER_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("setprice")
    @Permission("miragerooms.command.rooms.setPrice")
    public static void setPrice(Player player, @ATextArgument String regionName, @AIntegerArgument int price) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setPrice(price);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.PRICE_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("setmaxplayers")
    @Permission("miragerooms.command.rooms.setMaxPlayers")
    public static void setMaxPlayers(Player player, @ATextArgument String regionName, @AIntegerArgument int maxPlayers) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setMaxPlayers(maxPlayers);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.MAX_PLAYERS_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("setdays")
    @Permission("miragerooms.command.rooms.setDays")
    public static void setDays(Player player, @ATextArgument String regionName, @AIntegerArgument int days) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setDays(days);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.DAYS_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("setname")
    @Permission("miragerooms.command.rooms.setName")
    public static void setName(Player player, @ATextArgument String regionName, @ATextArgument String name) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.setName(name);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.NAME_SET);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("addplayer")
    @Permission("miragerooms.command.rooms.addPlayer")
    public static void addPlayer(Player player, @ATextArgument String regionName, @APlayerArgument Player newPlayer) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            if (roomData.getPlayers().size() >= roomData.getMaxPlayers()) {
                player.sendMessage(TranslationStrings.TOO_MANY_PLAYERS);
                return;
            }
            if (roomData.getPlayers().contains(newPlayer) && roomData.getOwner() != newPlayer) {
                player.sendMessage(TranslationStrings.PLAYER_ALREADY_IN_ROOM);
                return;
            }
            roomData.getPlayers().add(newPlayer);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.PLAYER_ADDED);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    @Subcommand("buyroom")
    @Permission("miragerooms.command.rooms.buyRoom")
    public static void buyRoom(Player player, @ATextArgument String regionName) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (!regions.hasRegion(regionName)) {
            player.sendMessage(TranslationStrings.REGION_DOESNT_EXIST);
            return;
        }
        if (!DatabaseManager.roomExists(regionName)) {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
            return;
        }
        RoomData roomData = DatabaseManager.getRoom(regionName);
        if (roomData.getOwner() != null) {
            player.sendMessage(TranslationStrings.ROOM_ALREADY_BOUGHT);
            return;
        }
        if (roomData.getPrice() > MirageRooms.econ.getBalance(player)) {
            player.sendMessage(TranslationStrings.NOT_ENOUGH_MONEY);
            return;
        }

        int maxHomes = 0;
        for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
            if (!permissionAttachmentInfo.getPermission().startsWith("miragerooms.rooms.max.")) continue;
            maxHomes = Integer.parseInt(permissionAttachmentInfo.getPermission().toLowerCase().replaceAll("miragerooms.rooms.max.", ""));
        }

        if (DatabaseManager.getPlayerRooms(player).size() >= maxHomes) {
            player.sendMessage(TranslationStrings.TOO_MANY_ROOMS);
            return;
        }

        MirageRooms.econ.withdrawPlayer(player, roomData.getPrice());
        setOwner(player, regionName, player);
        player.sendMessage(TranslationStrings.ROOM_BOUGHT);
    }

    @Subcommand("extendbuy")
    @Permission("miragerooms.command.rooms.extendBuy")
    public static void extendBuy(Player player, @ATextArgument String regionName) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (!regions.hasRegion(regionName)) {
            player.sendMessage(TranslationStrings.REGION_DOESNT_EXIST);
            return;
        }
        if (!DatabaseManager.roomExists(regionName)) {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
            return;
        }
        RoomData roomData = DatabaseManager.getRoom(regionName);
        if (roomData.getPrice() > MirageRooms.econ.getBalance(player)) {
            player.sendMessage(TranslationStrings.NOT_ENOUGH_MONEY);
            return;
        }

        MirageRooms.econ.withdrawPlayer(player, roomData.getPrice());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(Date.from(sdf.parse(roomData.getDateExpired()).toInstant()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        c.add(Calendar.DATE, roomData.getDays());
        roomData.setDateExpired(sdf.format(c.getTime()));
        DatabaseManager.updateRoom(roomData);
        player.sendMessage(TranslationStrings.ROOM_EXTENDED);
    }

    @Subcommand("leaveroom")
    @Permission("miragerooms.command.rooms.leaveRoom")
    public static void leaveRoom(Player player, @ATextArgument String regionName) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (!regions.hasRegion(regionName)) {
            player.sendMessage(TranslationStrings.REGION_DOESNT_EXIST);
            return;
        }
        if (!DatabaseManager.roomExists(regionName)) {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
            return;
        }
        if (DatabaseManager.getRoom(regionName).getOwner() == player) {
            removeOwner(player, regionName);
            DatabaseManager.getRoom(regionName).getPlayers().clear();
            DatabaseManager.updateRoom(DatabaseManager.getRoom(regionName));
            player.sendMessage(TranslationStrings.ROOM_LEFT);
        }
        if (DatabaseManager.getRoom(regionName).getPlayers().contains(player)) {
            removePlayer(player, regionName, player);
            player.sendMessage(TranslationStrings.ROOM_LEFT);
        }
    }

    @Subcommand("removeplayer")
    @Permission("miragerooms.command.rooms.removePlayer")
    public static void removePlayer(Player player, @ATextArgument String regionName, @APlayerArgument Player newPlayer) {
        RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regions.hasRegion(regionName)) {
            RoomData roomData = DatabaseManager.getRoom(regionName);
            roomData.getPlayers().remove(newPlayer);
            DatabaseManager.updateRoom(roomData);
            player.sendMessage(TranslationStrings.PLAYER_REMOVED);
        } else {
            player.sendMessage(TranslationStrings.ROOM_DOESNT_EXIST);
        }
    }

    //TODO: Remove this debug command
    @Subcommand("debug")
    @Permission("miragerooms.command.rooms.debug")
    public static void debug(Player player, @AMultiLiteralArgument({"setday1", "setday2", "run"}) String command) {
        if (command.equals("setday1")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            RoomData roomData = DatabaseManager.getRoom("test");
            roomData.setDateExpired(sdf.format(c.getTime()));
            DatabaseManager.updateRoom(roomData);
        }
        if (command.equals("setday2")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, -2);
            RoomData roomData = DatabaseManager.getRoom("test");
            roomData.setDateExpired(sdf.format(c.getTime()));
            DatabaseManager.updateRoom(roomData);
        }
        if (command.equals("run")) {
            new DateCheck().runTask(MirageRooms.getPlugin(MirageRooms.class));
        }
    }
}

