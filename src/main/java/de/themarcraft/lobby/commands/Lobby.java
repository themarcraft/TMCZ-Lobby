package de.themarcraft.lobby.commands;

import de.themarcraft.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lobby implements CommandExecutor {

    Main plugin;

    public Lobby(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Bukkit.getConsoleSender()==commandSender){
            plugin.log("&cDiesen Befehl kann nur ein Spieler ausführen");
            return false;
        }

        Player player = (Player) commandSender;
        player.teleport(new Location(player.getWorld(), 0.5, 51, 0.5, 0, 0));
        commandSender.sendMessage(plugin.getPrefix() + "Du wurdest erfolgreich zum Spawn Teleportiert");
        return true;
    }
}
