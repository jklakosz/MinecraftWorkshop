package fr.jklakosz.testplugin;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerListener implements Listener {

    private void openChoiceInventory(Player p) {
        ItemStack saber = new ItemStack(Material.STICK);
        ItemMeta imSaber = saber.getItemMeta();
        imSaber.setDisplayName(ChatColor.AQUA + "LE STICK DE PARTICULES");
        saber.setItemMeta(imSaber);

        ItemStack cowGun = new ItemStack(Material.COAL);
        ItemMeta imCowGun = cowGun.getItemMeta();
        imCowGun.setDisplayName(ChatColor.GREEN + "PISTOLET A VACHE");
        cowGun.setItemMeta(imCowGun);

        ItemStack remove = new ItemStack(Material.BARRIER);
        ItemMeta imRemove = remove.getItemMeta();
        imRemove.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "ENLEVER LE GADGET");
        remove.setItemMeta(imRemove);

        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED + "" + ChatColor.BOLD + "Choisir un gadget");

        inv.setItem(20, saber);
        inv.setItem(22, cowGun);
        inv.setItem(24, remove);

        p.openInventory(inv);
    }

    private void prepareInventory(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta imCompass = compass.getItemMeta();
        imCompass.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "MENU GADGET");
        compass.setItemMeta(imCompass);

        p.getInventory().clear();
        p.getInventory().setItem(0, compass);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        prepareInventory(ev.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent ev) {
        ev.setCancelled(true);
        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK && ev.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        switch(ev.getMaterial()) {
            case COMPASS:
                openChoiceInventory(ev.getPlayer());
                break;
            case BARRIER:
                prepareInventory(ev.getPlayer());
                break;
            case STICK:
                UtilsGadgets.executeSaber(ev.getPlayer());
                break;
            case COAL:
                UtilsGadgets.executeCowGun(ev.getPlayer());
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent ev) {
        ev.setCancelled(true);
        switch(ev.getCurrentItem().getType()) {
            case STICK:
            case COAL:
                ev.getWhoClicked().getInventory().setItem(8, ev.getCurrentItem());
                ev.getWhoClicked().closeInventory();
                break;
            case BARRIER:
                ev.getWhoClicked().getInventory().setItem(8, new ItemStack(Material.AIR));
                ev.getWhoClicked().closeInventory();
                break;
            default:
                break;
        }
    }
}
