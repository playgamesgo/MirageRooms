package me.playgamesgo.miragerooms.commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.managers.RegionManager;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.jorel.commandapi.annotations.*;
import dev.jorel.commandapi.annotations.arguments.AIntegerArgument;
import dev.jorel.commandapi.annotations.arguments.APlayerArgument;
import dev.jorel.commandapi.annotations.arguments.ATextArgument;
import me.playgamesgo.miragerooms.MirageRooms;
import me.playgamesgo.miragerooms.utils.TranslationStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        //TODO: add room
    }

    @Subcommand("removeroom")
    @Permission("miragerooms.command.rooms.removeRoom")
    public static void removeRoom(Player player, @ATextArgument String roomName) {
        //TODO: remove room
    }

    @Deprecated
    @Subcommand("givekey")
    @Permission("miragerooms.command.rooms.giveKey")
    public static void giveKey(CommandSender sender, @APlayerArgument Player player, @ATextArgument String regionName) {
        if (sender instanceof Player playerSender) {
            RegionManager regions = MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(playerSender.getWorld()));
            if (regions.hasRegion(regionName)) {
                ItemStack key = MirageRooms.mmoItems.getItem(MirageRooms.mmoItems.getTypes().get("MATERIAL"), "KEY");
                NBTItem nbtKey = new NBTItem(key);
                nbtKey.setString("region", regionName);
                key = nbtKey.getItem();
                player.getInventory().addItem(key);
                sender.sendMessage(TranslationStrings.GAVE_KEY
                        .replace("%player%", player.getName())
                        .replace("%region%", regionName));
            }
        } else {
            sender.sendMessage(TranslationStrings.ONLY_PLAYERS);
        }
    }
}

