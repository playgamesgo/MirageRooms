package me.playgamesgo.miragerooms.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.playgamesgo.miragerooms.MirageRooms;
import me.playgamesgo.miragerooms.utils.DatabaseManager;
import me.playgamesgo.miragerooms.utils.RoomData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomsExpansion extends PlaceholderExpansion {
    private final MirageRooms plugin;

    public RoomsExpansion(MirageRooms plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        final String regex = "(room|owner)_([^_]+)_(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(params);
        if (matcher.matches()) {
            String roomType = matcher.group(1);
            String roomName = matcher.group(2);
            String roomData = matcher.group(3);

            if (roomType.equals("room")) {
                switch (roomData) {
                    case "name":
                        return DatabaseManager.getRoom(roomName).getName();
                    case "price":
                        return DatabaseManager.getRoom(roomName).getPrice() + "";
                    case "days":
                        return DatabaseManager.getRoom(roomName).getDays() + "";
                    case "cords":
                        return DatabaseManager.getRoom(roomName).getRegion().getMinimumPoint().toString() + " - " + DatabaseManager.getRoom(roomName).getRegion().getMaximumPoint().toString();
                    case "owner":
                        if (DatabaseManager.getRoom(roomName).getOwner() == null) {
                            return "None";
                        }
                        return DatabaseManager.getRoom(roomName).getOwner().getName();
                }
            } else if (roomType.equals("owner")) {
                if (player == null) {
                    return null;
                }

                int roomIndex;
                try {
                    roomIndex = Integer.parseInt(roomName);
                } catch (NumberFormatException e) {
                    return null;
                }

                RoomData[] rooms = DatabaseManager.getPlayerRooms(Bukkit.getPlayer(player.getUniqueId())).toArray(new RoomData[0]);
                if (roomIndex >= rooms.length) {
                    return null;
                }
                System.out.println(roomIndex + " " + rooms.length);

                switch (roomData) {
                    case "name":
                        return rooms[roomIndex].getName();
                    case "price":
                        return rooms[roomIndex].getPrice() + "";
                    case "days":
                        return rooms[roomIndex].getDateExpired();
                    case "cords":
                        return rooms[roomIndex].getRegion().getMinimumPoint().toString() + " - " + rooms[roomIndex].getRegion().getMaximumPoint().toString();
                    case "players":
                        StringBuilder players = new StringBuilder();
                        for (OfflinePlayer p : rooms[roomIndex].getPlayers()) {
                            players.append(p.getName()).append(", ");
                        }
                        if (players.isEmpty()) {
                            return "None";
                        }
                        return players.substring(0, players.length() - 2);
                }
            }
        }
        return null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getDescription().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        StringBuilder authors = new StringBuilder();
        for (String author : plugin.getDescription().getAuthors()) {
            authors.append(author).append(", ");
        }
        return authors.substring(0, authors.length() - 2);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
