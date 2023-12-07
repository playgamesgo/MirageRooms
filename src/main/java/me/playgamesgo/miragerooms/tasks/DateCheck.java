package me.playgamesgo.miragerooms.tasks;

import me.playgamesgo.miragerooms.commands.RoomsCommand;
import me.playgamesgo.miragerooms.utils.DatabaseManager;
import me.playgamesgo.miragerooms.utils.RoomData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateCheck extends BukkitRunnable {
    @Override
    public void run() {
        List<RoomData> rooms = DatabaseManager.getAllRooms();
        for (RoomData room : rooms) {
            if (!(room.getDateExpired() + "1").equals("null1")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(Date.from(sdf.parse(room.getDateExpired()).toInstant()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                c.add(Calendar.DATE, 2);
                System.out.println(sdf.format(c.getTime()));
                if (c.getTime().before(new Date())) {
                    RoomsCommand.removeOwner(Bukkit.getPlayer("playgamesgo").getPlayer(), room.getRegion().getId());
                }
            }
        }

    }
}
