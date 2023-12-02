package me.playgamesgo.miragerooms.utils;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.List;

public class RoomData {
    private ProtectedRegion region;
    private String name;
    private int price;
    private int maxPlayers;
    private Player owner;
    private List<Player> players;
    private int days;
    private String dateBought, dateExpired;

    public RoomData(ProtectedRegion region, String name, int price, int maxPlayers, Player owner, List<Player> players, int days, String dateBought, String dateExpired) {
        this.region = region;
        this.name = name;
        this.price = price;
        this.maxPlayers = maxPlayers;
        this.owner = owner;
        this.players = players;
        this.days = days;
        this.dateBought = dateBought;
        this.dateExpired = dateExpired;
    }

    public ProtectedRegion getRegion() {
        return region;
    }
    public String getRegionName() {
        return region.getType().getName();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Player getOwner() {
        return owner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getDays() {
        return days;
    }

    public String getDateBought() {
        return dateBought;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public void setRegion(ProtectedRegion region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }
}
