package fr.kevingr19.skillcontest.gui.hotbar;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages {@link PlayerInteractAction} objects, for being used along with {@link org.bukkit.inventory.ItemStack}'s.
 */

public class ItemInteractHandler {

    private final String tagMark;
    private final Map<String, PlayerInteractAction> actions = new HashMap<>();

    public ItemInteractHandler(String tagMark){
        this.tagMark = tagMark;
    }

    public boolean hasAction(String id){
        return actions.containsKey(id);
    }

    public boolean hasAction(ItemStack item){
        if(item == null || item.getType() == Material.AIR) return false;
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        CompoundTag compoundTag = nmsItem.getTag();
        if(compoundTag == null) return false;
        return compoundTag.contains(tagMark);
    }

    public void setAction(String id, PlayerInteractAction action){
        actions.put(id, action);
    }

    public void removeAction(String id){
        actions.remove(id);
    }

    @Nullable
    public PlayerInteractAction getAction(String id){
        return actions.getOrDefault(id, null);
    }

    @Nullable
    public PlayerInteractAction getAction(ItemStack item){
        if(item == null || item.getType() == Material.AIR) return null;
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        CompoundTag compoundTag = nmsItem.getTag();
        if(compoundTag == null) return null;

        Tag tag = compoundTag.get(tagMark);
        if(tag == null || tag.getId() != Tag.TAG_STRING) return null;
        return getAction(tag.getAsString());
    }

    @Nullable
    public PlayerInteractAction getActionIfActive(String id){
        PlayerInteractAction action = getAction(id);
        return action != null && action.active ? action : null;
    }

    @Nullable
    public PlayerInteractAction getActionIfActive(ItemStack item){
        PlayerInteractAction action = getAction(item);
        return action != null && action.active ? action : null;
    }

    public ItemStack setActionTag(ItemStack item, String actionId){
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        CompoundTag compoundTag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
        compoundTag.putString(tagMark, actionId);
        nmsItem.setTag(compoundTag);

        return CraftItemStack.asCraftMirror(nmsItem);
    }

}
