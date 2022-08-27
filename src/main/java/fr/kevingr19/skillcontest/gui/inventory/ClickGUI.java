package fr.kevingr19.skillcontest.gui.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import java.util.Map;

/**
 * Class extending {@link InteractiveGUI}, providing the tools to link clicks with actions.
 * All events are cancelled by default.
 */

abstract public class ClickGUI extends InteractiveGUI{

    protected Map<Integer, ClickAction> activeActionMap;

    public ClickGUI() {
        super();
    }
    public ClickGUI(String title) {
        super(title);
    }

    final protected boolean hasActionMap(){
        return activeActionMap != null;
    }

    @Override
    protected void clickOnInventory(InventoryClickEvent e) {
        int slot = e.getSlot();
        e.setCancelled(true);
        if(hasActionMap() && activeActionMap.containsKey(slot)) activeActionMap.get(slot).invoke(e);
    }

    @Override
    protected void externCollectToCursor(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @Override
    protected void itemDragOnInventory(InventoryDragEvent e) {
        e.setCancelled(true);
    }
}
