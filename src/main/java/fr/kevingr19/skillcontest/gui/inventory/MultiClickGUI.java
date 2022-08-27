package fr.kevingr19.skillcontest.gui.inventory;

import fr.kevingr19.skillcontest.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class extending {@link ClickGUI} that provides the tools to create separate pages,
 * with their own items and actions each, and a way to save them after use.
 */

abstract public class MultiClickGUI extends ClickGUI{

    protected String defaultId;
    protected String currentId;
    protected Map<String, Frame> frames = new HashMap<>();

    @Override
    protected Inventory createInventory(String title){
        return null;
    }

    public MultiClickGUI(String defaultId){
        super();
        this.defaultId = currentId = defaultId;
    }

    public String getCurrentId(){
        return currentId;
    }
    public String getDefaultId(){
        return defaultId;
    }

    protected Frame getCurrentFrame(){
        return frames.getOrDefault(currentId, null);
    }

    public void openInventory(Player player){
        openInventoryAtFrame(player, defaultId);
    }

    public void openInventoryAtFrame(Player player, String frameId){
        if(!frames.containsKey(frameId)){
            Main.error("MultiClickGUI could not be opened because the given frameId does not exist");
            return;
        }

        new BukkitRunnable(){
            @Override
            public void run(){
                Frame frame = frames.get(frameId);
                frame.setAsCurrent();
                frame.loadFrame(player);
                MultiClickGUI.super.openInventory(player);
            }
        }.runTaskLater(Main.inst(), 0);
    }

    /**
     * Represents a sub-inventory, allows GUI's with multiple independent inventories.
     */

    public class Frame{
        protected Map<Integer, ClickAction> actionMap = new HashMap<>();
        protected Inventory frameInv;
        protected LoadFrameAction loadAction;

        public Frame(int slots, String title){
            frameInv = Bukkit.createInventory(null, slots, title);
        }

        public Inventory inv(){
            return frameInv;
        }

        public void setLoadAction(LoadFrameAction action){
            loadAction = action;
        }

        public void loadFrame(Player player){
            if(loadAction != null) loadAction.invoke(player);
        }

        public void setAsCurrent(){
            activeActionMap = actionMap;
            inv = frameInv;
        }

        public boolean isSlotIn(int slot){
            return slot >= 0 && slot < frameInv.getSize();
        }

        @Nullable
        public ClickAction getAction(int slot){
            return actionMap.getOrDefault(slot, null);
        }
        public void setAction(int slot, ClickAction action){
            actionMap.put(slot, action);
        }
        public void removeAction(int slot){
            actionMap.remove(slot);
        }
    }

}
