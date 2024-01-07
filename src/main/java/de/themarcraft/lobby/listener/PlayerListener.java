package de.themarcraft.lobby.listener;

import de.themarcraft.lobby.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(player.getWorld(), 0.5, 51, 0.5, 0, 0));
        player.getInventory().clear();

        ItemStack fly = new ItemStack(Material.FEATHER);
        ItemMeta flyItem = (ItemMeta) fly.getItemMeta();
        flyItem.setDisplayName(ChatColor.YELLOW + "Fliegen");
        fly.setItemMeta(flyItem);
        player.getInventory().setItem(1, fly);

        ItemStack navigator = new ItemStack(Material.COMPASS);
        ItemMeta navigatorItem = navigator.getItemMeta();
        navigatorItem.setDisplayName(ChatColor.GREEN + "Server Navigator");
        navigator.setItemMeta(navigatorItem);
        player.getInventory().setItem(4, navigator);

        ItemStack playerhead = new ItemStack(Material.LIME_DYE);
        ItemMeta itemMeta = playerhead.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Du siehst andere Spieler");
        playerhead.setItemMeta(itemMeta);
        player.getInventory().setItem(7, playerhead);

        player.setInvulnerable(true);

    }

    @EventHandler
    public void onGoldJump(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Hole die Position des Blocks unter den Füßen des Spielers
        double blockX = player.getLocation().getBlockX();
        double blockY = player.getLocation().getBlockY(); // Unter den Füßen
        double blockZ = player.getLocation().getBlockZ();

        // Überprüfe, ob der Block eine Gold-Druckplatte ist
        if (player.getWorld().getBlockAt(new Location(player.getWorld(), blockX, blockY, blockZ)).getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {

            Vector direction = player.getLocation().getDirection().setY(0.5);

            player.setVelocity(direction.multiply(2));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("RIGHT")) {
            Material type = player.getInventory().getItemInMainHand().getType();
            switch (type) {
                case FEATHER:
                    if (player.hasPermission("themarcraft.fly")) {
                        player.performCommand("fly");
                    } else {
                        player.sendMessage(plugin.getPrefix() + "§cDafür hast du keine Rechte");
                    }
                    break;
                case COMPASS:
                    player.performCommand("serverselector");
                    break;
                case LIME_DYE: {
                    plugin.hideUtils.hideOn(player);
                    player.sendMessage(plugin.getPrefix() + "Du siehst nun keine anderen Spieler mehr");

                    ItemStack playerhead = new ItemStack(Material.RED_DYE);
                    ItemMeta itemMeta = playerhead.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.RED + "Du siehst keine anderen Spieler");
                    playerhead.setItemMeta(itemMeta);
                    player.getInventory().setItem(7, playerhead);
                    break;
                }
                case RED_DYE: {
                    plugin.hideUtils.hideOff(player);
                    player.sendMessage(plugin.getPrefix() + "Du siehst nun wieder andere Spieler");

                    ItemStack playerhead = new ItemStack(Material.LIME_DYE);
                    ItemMeta itemMeta = playerhead.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.GREEN + "Du siehst andere Spieler");
                    playerhead.setItemMeta(itemMeta);
                    player.getInventory().setItem(7, playerhead);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        plugin.hideUtils.hideOff(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Überprüfe, ob es sich um das gesperrte Inventar handelt
        if (!event.getWhoClicked().hasPermission("themarcraft.lobby.builder")) {
            event.setCancelled(true); // Blockiere das Event, um Aktionen zu verhindern
        }
    }
}
