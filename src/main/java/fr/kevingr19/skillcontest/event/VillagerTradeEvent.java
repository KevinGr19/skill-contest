package fr.kevingr19.skillcontest.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.MerchantInventory;

/**
 * Event called when a player trades with a villager.
 */

public class VillagerTradeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    private final Player player;
    private final Villager villager;
    private final MerchantInventory inventory;

    public VillagerTradeEvent(Player player, Villager villager, MerchantInventory inventory){
        this.player = player;
        this.villager = villager;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }
    public Villager getVillager() {
        return villager;
    }
    public MerchantInventory getMerchantInventory() {
        return inventory;
    }
}
