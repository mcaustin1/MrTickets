package me.Austin.MT;

import me.Austin.MT.Managers.LogToFile;
import me.Austin.MT.Managers.MySQL;
import me.Austin.MT.Managers.Objects.Server;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * This class submits a ticket using the implemented MySQL.
 *
 * @author MrMcaustin1
 * @since 1.0
 */
public class TicketOpen {

    /**
     * Open Ticket Method - Submits the open ticket method to MySQL and logs it
     *
     * @param p   The player opening the ticket
     * @param i   The error number the user was assigned
     * @param msg The message for the ticket
     * @throws SQLException Just in case there is some shit MySQL wants to do
     * @since 1.0
     */

    public static void openTicket(Player p, Integer i, String msg) throws SQLException {
        UUID uuid = p.getUniqueId();
        int error = i;

        PreparedStatement ps = MySQL.getConnection().prepareStatement(
                "INSERT IGNORE INTO " + Server.sUUID + " (UUID, Error, Message, Completed, Assigned, Date, milliseconds, Priority) values (?,?,?,?,?,?,?, 'Normal')");
        ps.setString(1, uuid.toString());
        ps.setInt(2, error);
        ps.setString(3, msg);
        ps.setString(4, "Open");
        ps.setString(5, AssignTickets.assignTicket(p));
        ps.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
        ps.setLong(7, System.currentTimeMillis());
        ps.executeUpdate();
        LogToFile.log("info", "Added " + uuid.toString() + "'s ticket to the table!");
    }
}
