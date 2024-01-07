package de.themarcraft.lobby.commands;

import de.themarcraft.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hide implements CommandExecutor {

    Main plugin;

    public Hide(Main plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Bukkit.getConsoleSender()==commandSender){
            plugin.log("&cDiesen Befehl kann nur ein Spieler ausführen");
            return false;
        }
        Player player = (Player) commandSender;
        if (plugin.hideUtils.isHide(player)){
            plugin.hideUtils.hideOff(player);
            player.sendMessage(plugin.getPrefix() + "Du siehst nun wieder andere Spieler");
        }else {
            plugin.hideUtils.hideOn(player);
            player.sendMessage(plugin.getPrefix() + "Du siehst nun keine anderen Spieler mehr");
        }

        return true;
    }

}
