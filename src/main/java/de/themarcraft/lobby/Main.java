package de.themarcraft.lobby;

import de.themarcraft.lobby.commands.Hide;
import de.themarcraft.lobby.commands.Lobby;
import de.themarcraft.lobby.listener.PlayerListener;
import de.themarcraft.lobby.utils.HidePlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public List hide = new ArrayList<>();
    public HidePlayers hideUtils;

    @Override
    public void onEnable() {
        // Plugin startup logic

        log("");
        log("Plugin &b" + getName() + " " + getVersion() + "&r loaded");
        log("");

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        hideUtils = new HidePlayers(this);
        hideUtils.runTaskTimer(this, 0L, 5L);
        getCommand("lobby").setExecutor(new Lobby(this));
        getCommand("hide").setExecutor(new Hide(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()) {
            hideUtils.hideOff(player);
        }
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', "&a&lTheMarCraft.de &7» &r&7");
    }

    public void log(String msg) {
        String finalMSG = ChatColor.translateAlternateColorCodes('&', "&a&lTheMarCraft.de &7» &r" + msg);
        Bukkit.getConsoleSender().sendMessage(finalMSG);
    }

    public String getVersion() {
        return "&e" + this.getDescription().getVersion() + "&r";
    }
}
