package de.themarcraft.lobby.utils;

import de.themarcraft.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HidePlayers extends BukkitRunnable {

    Main plugin;

    public HidePlayers(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Object user : plugin.hide) {
            Player player = (Player) user;
            for (Player online : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(online);
            }
        }
    }

    public boolean isHide(Player player) {
        if (plugin.hide.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public void hideOn(Player player) {
        if (!isHide(player)) {
            plugin.hide.add(player);
        }
    }

    public void hideOff(Player player) {
        if (isHide(player)) {
            plugin.hide.remove(player);
            for (Player online : Bukkit.getOnlinePlayers()) {
                player.showPlayer(online);
            }
        }
    }
}
