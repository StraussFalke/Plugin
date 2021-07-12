package de.straussfalke.translateplugin.listeners;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mysql.fabric.xmlrpc.base.Array;
import de.straussfalke.translateplugin.Datenbank.addPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class JoinListener implements Listener {

    private final String GUI_NAME = "choose your language";
    Inventory inventory = Bukkit.createInventory(null, 9*3, GUI_NAME);

    private ItemStack customSkull(String url) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        if(url == null || url.isEmpty())
            return null;
        ItemMeta skullMeta = skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodeData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodeData)));
        Field profileField = null;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        ItemStack skull = customSkull("http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f");

        Player player = event.getPlayer();

        addPlayer.addPlayer(player.getName(), player.getUniqueId(), 100);

        inventory.setItem(0, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(1, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(2, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(3, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        inventory.setItem(4, new ItemStack(skull));
        for(int i = 5; i < inventory.getSize(); i++){
            inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        }
            player.openInventory(inventory);



    }

    @EventHandler
    public void handleSAGUIClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked(); {
            if(event.getView().getTitle().equals(GUI_NAME)) {
                event.setCancelled(true);

                switch(event.getCurrentItem().getType()) {
                    case BLACK_STAINED_GLASS_PANE:
                            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 10, 29);
                            player.sendMessage("Dieser Slot ist leer!");

                        break;
                    case PLAYER_HEAD:
                        if(event.getCurrentItem().hasItemMeta()) {
                            if(event.getCurrentItem().getItemMeta().hasDisplayName()) {
                            if(event.getCurrentItem().getItemMeta().getDisplayName() == "§egerman") {
                            if(event.getSlot() == 4) {
                                if (inventory.getItem(13).getType() == Material.BLACK_STAINED_GLASS_PANE) {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 29);
                                    inventory.setItem(13, new ItemStack(Material.LIME_STAINED_GLASS_PANE));
                                    player.updateInventory();
                                } else if (inventory.getItem(13).getType() == Material.LIME_STAINED_GLASS_PANE) {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 29);
                                    inventory.setItem(13, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                                    player.updateInventory();
                                            }
                                        }
                                    }
                                }
                            }
                        break;
                    default:
                        break;
                    }
                }
            }
        }
    }

