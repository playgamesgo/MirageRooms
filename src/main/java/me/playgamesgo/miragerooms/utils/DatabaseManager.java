package me.playgamesgo.miragerooms.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.leonhard.storage.Config;
import me.playgamesgo.miragerooms.MirageRooms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class DatabaseManager {
    Config config = MirageRooms.configFile;
    Plugin plugin;
    public static Connection connection;

    public DatabaseManager(Plugin plugin) {
        this.plugin = plugin;
        initDB();
    }

    public static void createRoom(RoomData roomData) {
        try {
            StringBuilder players = new StringBuilder();
            if (roomData.getPlayers() != null) {
                for (Player player : roomData.getPlayers()) {
                    players.append(player.getUniqueId().toString()).append(",");
                }
            }

            String ownerUUID = null;
            if (roomData.getOwner() != null) {
                ownerUUID = roomData.getOwner().getUniqueId().toString();
            }

            connection.createStatement().execute(
                    "INSERT INTO rooms (regionId, name, price, maxPlayers, ownerUUID, players, days, dateBought, dateExpired) VALUES ('" +
                            roomData.getRegion().getId() + "', '" + roomData.getName() + "', " + roomData.getPrice() + ", " + roomData.getMaxPlayers() + ", '" +
                            ownerUUID + "', '" + players.toString() + "', " + roomData.getDays() + ", '" + roomData.getDateBought() + "', '" + roomData.getDateExpired() + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteRoom(ProtectedRegion regionName) {
        try {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM rooms WHERE regionId = '").append(regionName.getId()).append("'");
            connection.createStatement().execute(query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateRoom(RoomData roomData) {
        try {
            StringBuilder players = new StringBuilder();
            if (roomData.getPlayers() != null) {
                for (Player player : roomData.getPlayers()) {
                    players.append(player.getUniqueId().toString()).append(",");
                }
            }

            String ownerUUID = null;
            if (roomData.getOwner() != null) {
                ownerUUID = roomData.getOwner().getUniqueId().toString();
            }

            connection.createStatement().execute(
                    "UPDATE rooms SET name = '" + roomData.getName() + "', price = " + roomData.getPrice() + ", maxPlayers = " + roomData.getMaxPlayers() + ", ownerUUID = '" +
                            ownerUUID + "', players = '" + players.toString() + "', days = " + roomData.getDays() + ", dateBought = '" + roomData.getDateBought() +
                            "', dateExpired = '" + roomData.getDateExpired() + "' WHERE regionId = '" + roomData.getRegion().getId() + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static RoomData getRoom(String regionId) {
        try {
            List<Player> players = new ArrayList<>();
            if (!connection.createStatement().executeQuery("SELECT players FROM rooms WHERE regionId = '" + regionId + "'").getString(1).isEmpty()) {
                for (String s : connection.createStatement().executeQuery("SELECT players FROM rooms WHERE regionId = '" + regionId + "'").getObject(1).toString().split(",")) {
                    players.add(Bukkit.getOfflinePlayer(UUID.fromString(s)).getPlayer());
                }
            }

            Player owner = null;
            if (!(connection.createStatement().executeQuery("SELECT ownerUUID FROM rooms WHERE regionId = '" + regionId + "'").getString(1) + "1").equals("null1")) {
                owner = Bukkit.getOfflinePlayer(UUID.fromString(connection.createStatement().executeQuery("SELECT ownerUUID FROM rooms WHERE regionId = '" + regionId + "'").getObject(1).toString())).getPlayer();
            }

            return new RoomData(
                    MirageRooms.worldGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(Bukkit.getWorld("world"))).getRegion(regionId),
                    connection.createStatement().executeQuery("SELECT name FROM rooms WHERE regionId = '" + regionId + "'").getObject(1).toString(),
                    connection.createStatement().executeQuery("SELECT price FROM rooms WHERE regionId = '" + regionId + "'").getInt(1),
                    connection.createStatement().executeQuery("SELECT maxPlayers FROM rooms WHERE regionId = '" + regionId + "'").getInt(1),
                    owner,
                    players,
                    connection.createStatement().executeQuery("SELECT days FROM rooms WHERE regionId = '" + regionId + "'").getInt(1),
                    connection.createStatement().executeQuery("SELECT dateBought FROM rooms WHERE regionId = '" + regionId + "'").getObject(1).toString(),
                    connection.createStatement().executeQuery("SELECT dateExpired FROM rooms WHERE regionId = '" + regionId + "'").getObject(1).toString()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean roomExists(String regionId) {
        try {
            if (connection.createStatement().executeQuery("SELECT COUNT(*) AS rowcount FROM rooms WHERE regionId = '" + regionId + "'").getInt("rowcount") == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initDB() {
        if (config.getBoolean("use-mysql")) {
            connection = getMySQLConnection();
        } else {
            connection = getSQLiteConnection();
        }

        if (connection == null) {
            plugin.getLogger().log(Level.SEVERE, "Could not establish a connection to the database.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

        try {
            int maxPlayers = config.getInt("max-players") * 36;
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS rooms (id INTEGER PRIMARY KEY AUTOINCREMENT, regionId VARCHAR(256), name VARCHAR(32), price INTEGER, maxPlayers INTEGER, ownerUUID VARCHAR(36), players VARCHAR(" + maxPlayers + "), days INTEGER, dateBought VARCHAR(32), dateExpired VARCHAR(32))");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not create the rooms table.", e);
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private Connection getSQLiteConnection() {
        File dataFile = new File(plugin.getDataFolder(), "rooms.db");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: rooms.db");
            }
        }

        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + dataFile);
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", e);
        }
        return null;
    }

    private Connection getMySQLConnection() {
        String host = config.getString("mysql.host");
        String port = config.getString("mysql.port");
        String database = config.getString("mysql.database");
        String username = config.getString("mysql.username");
        String password = config.getString("mysql.password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "MySQL exception on initialize", e);
        }
        return null;
    }

    public static void closeConnection() {
        try {
            DatabaseManager.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}