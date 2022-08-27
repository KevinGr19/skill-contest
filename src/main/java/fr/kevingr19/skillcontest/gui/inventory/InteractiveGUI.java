package fr.kevingr19.skillcontest.gui.inventory;

import fr.kevingr19.skillcontest.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * Base class to make interactive GUI menus.
 */

abstract public class InteractiveGUI implements Listener {

    protected Inventory inv;
    abstract protected Inventory createInventory(String title);

    public InteractiveGUI(String title){
        inv = createInventory(title);
        register();
    }

    public InteractiveGUI(){
        inv = createInventory(null);
        register();
    }

    public void openInventory(Player player){
        if(inv != null) player.openInventory(inv);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onClick(final InventoryClickEvent e)
    {
        if(inv == null || e.getClickedInventory() == null) return;
        if(e.getClickedInventory() != inv){
            if(e.getInventory() == inv && e.getAction() == InventoryAction.COLLECT_TO_CURSOR) externCollectToCursor(e);
        }
        else clickOnInventory(e);
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onDrag(final InventoryDragEvent e)
    {
        if(inv == null || e.getInventory() != inv) return;
        for(int i : e.getRawSlots()){
            if(isSlotIn(i)){
                itemDragOnInventory(e);
                return;
            }
        }
    }

    public boolean isSlotIn(int slot){
        return inv != null && slot >= 0 && slot < inv.getSize();
    }

    abstract protected void clickOnInventory(InventoryClickEvent e);
    abstract protected void externCollectToCursor(InventoryClickEvent e);
    abstract protected void itemDragOnInventory(InventoryDragEvent e);

    private boolean registered = false;
    public void register(){
        if(registered) return;
        Bukkit.getPluginManager().registerEvents(this, Main.inst());
        registered = true;
    }

    public void unregister(){
        if(!registered) return;
        HandlerList.unregisterAll(this);
        registered = false;
    }

}
