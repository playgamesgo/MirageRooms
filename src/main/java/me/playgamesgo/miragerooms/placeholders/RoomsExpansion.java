package me.playgamesgo.miragerooms.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.playgamesgo.miragerooms.MirageRooms;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class RoomsExpansion extends PlaceholderExpansion {
    private final MirageRooms plugin;
    public RoomsExpansion(MirageRooms plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        //TODO: Add placeholders for rooms info (name, price, own days, cords, owner), and for room owner info (name, price, day left, cords, players in room)

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
