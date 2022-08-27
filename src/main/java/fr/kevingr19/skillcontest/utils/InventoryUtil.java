package fr.kevingr19.skillcontest.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static void fillOutline(Inventory inventory, final ItemStack item){
        for(int i = 0; i < inventory.getSize(); i++){
            if(i < 9 || i >= inventory.getSize() - 9 || (i+1)%9 <= 1) inventory.setItem(i, item);
        }
    }

    public static void fill(Inventory inventory, final ItemStack item){
        for(int i = 0; i < inventory.getSize(); i++) inventory.setItem(i, item);
    }

    public static boolean hasSpaceFor(Inventory inventory, ItemStack item){
        if(item == null || item.getType() == Material.AIR) return true;

        for(int i = 0; i < inventory.getSize(); i++) if(hasSpaceInSlot(inventory.getItem(i), item)) return true;
        return false;
    }

    public static boolean hasSpaceInSlot(ItemStack slot, ItemStack item){
        if(item == null || slot == null || slot.getType() == Material.AIR) return true;
        return slot.isSimilar(item) && slot.getAmount() + item.getAmount() <= slot.getMaxStackSize();
    }


}
