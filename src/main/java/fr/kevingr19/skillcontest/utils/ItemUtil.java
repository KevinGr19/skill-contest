package fr.kevingr19.skillcontest.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemUtil {

    public static final ItemFlag[] hideFlags = new ItemFlag[]{ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS};
    private static final Set<Material> minerals;

    static{
        minerals = new HashSet<>();
        minerals.add(Material.COAL);
        minerals.add(Material.CHARCOAL);
        minerals.add(Material.IRON_INGOT);
        minerals.add(Material.GOLD_INGOT);
        minerals.add(Material.COPPER_INGOT);
        minerals.add(Material.REDSTONE);
        minerals.add(Material.LAPIS_LAZULI);
        minerals.add(Material.DIAMOND);
        minerals.add(Material.EMERALD);
    }

    public static ItemStack create(Material material, int amount, String name, List<String> lore, ItemFlag[] flags){
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        for(ItemFlag flag : flags) meta.addItemFlags(flag);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material material, int amount, String name){
        return create(material, amount, name, null, hideFlags);
    }

    public static ItemStack clone(ItemStack item, String name){
        final ItemStack newItem = item.clone();
        final ItemMeta meta = newItem.getItemMeta();

        meta.setDisplayName(name);
        for(ItemFlag flag : hideFlags) meta.addItemFlags(flag);

        item.setItemMeta(meta);
        return item;
    }

    public static void setEnchantSkin(final ItemStack item, boolean enchant){
        if(enchant) item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        else item.getEnchantments().keySet().forEach(item::removeEnchantment);
    }

    public static void setLore(@Nonnull final ItemStack item, List<String> lore){
        if(item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void setLore(@Nonnull final ItemStack item, String lore){
        if(item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        meta.setLore(List.of(lore));
        item.setItemMeta(meta);
    }

    public static boolean isType(ItemStack item, Material type){
        return item != null && item.getType() == type;
    }

    public static boolean isNull(ItemStack item){
        return item == null || item.getType() == Material.AIR;
    }

    public enum ArmorType{
        LEATHER(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS),
        GOLD(Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS),
        CHAINMAIL(Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS),
        IRON(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
        DIAMOND(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS),
        NETHERITE(Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS);

        private final Material helmet;
        private final Material chestplate;
        private final Material leggings;
        private final Material boots;

        ArmorType(Material helmet, Material chestplate, Material leggings, Material boots){
            this.helmet = helmet;
            this.chestplate = chestplate;
            this.leggings = leggings;
            this.boots = boots;
        }
    }

    public static boolean isArmorPiece(ItemStack item, ArmorType type){
        if(isNull(item)) return false;
        return item.getType() == type.helmet || item.getType() == type.chestplate || item.getType() == type.leggings || item.getType() == type.boots;
    }

    public static boolean hasArmor(@Nullable EntityEquipment equipment, ArmorType type){
        if(equipment == null) return false;
        if(isNull(equipment.getHelmet()) || equipment.getHelmet().getType() != type.helmet) return false;
        if(isNull(equipment.getChestplate()) || equipment.getChestplate().getType() != type.chestplate) return false;
        if(isNull(equipment.getLeggings()) || equipment.getLeggings().getType() != type.leggings) return false;
        return !isNull(equipment.getBoots()) && equipment.getBoots().getType() == type.boots;
    }

    public static boolean isArmorPiece(ItemStack item){
        return !isNull(item) && item.getType().getEquipmentSlot() != EquipmentSlot.HAND && item.getType().getEquipmentSlot() != EquipmentSlot.OFF_HAND;
    }

    @Nullable
    public static ArmorType getArmorType(EntityEquipment equipment){
        if(equipment == null) return null;
        if(isNull(equipment.getHelmet()) || isNull(equipment.getChestplate()) || isNull(equipment.getLeggings()) || isNull(equipment.getBoots())) return null;

        for(ArmorType type : ArmorType.values()){
            if(equipment.getHelmet().getType() == type.helmet)
            {
                if(equipment.getChestplate().getType() != type.chestplate) return null;
                if(equipment.getLeggings().getType() != type.leggings) return null;
                return equipment.getBoots().getType() == type.boots ? type : null;
            }
        }

        return null;
    }

    public static boolean isMineral(Material type){
        return minerals.contains(type);
    }

}
