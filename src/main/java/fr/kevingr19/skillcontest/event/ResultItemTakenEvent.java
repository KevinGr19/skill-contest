package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
/**
 * Event called when a player takes out an item from a RESULT slot.
 */

public class ResultItemTakenEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Inventory inv;
    private final boolean isCrafting;

    private ItemStack result;
    private boolean hasChanged = false;

    public ResultItemTakenEvent(Player player, ItemStack result, Inventory inv){
        this.player = player;
        this.result = result;
        this.inv = inv;

        isCrafting = inv.getType() == InventoryType.CRAFTING || inv.getType() == InventoryType.WORKBENCH;
    }

    public Player getPlayer() {
        return player;
    }
    public Inventory getInventory() {
        return inv;
    }

    public ItemStack getResult() {
        return result;
    }
    public void setResult(ItemStack result){
        this.result = result;
        hasChanged = true;
    }
    public boolean hasChangedResult() {
        return hasChanged;
    }
    public boolean isCrafting() {
        return isCrafting;
    }
}
