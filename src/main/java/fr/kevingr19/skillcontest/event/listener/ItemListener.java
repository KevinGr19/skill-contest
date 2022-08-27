package fr.kevingr19.skillcontest.event.listener;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.event.*;
import fr.kevingr19.skillcontest.gui.inventory.BookCollectionGUI;
import fr.kevingr19.skillcontest.utils.InventoryUtil;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemListener implements Listener {

    // Armor
    private boolean isArmor(ItemStack item){
        return ItemUtil.isArmorPiece(item);
    }

    private void callArmorCheck(Player player){
        new BukkitRunnable(){@Override public void run(){
            CheckForArmorEvent event = new CheckForArmorEvent(player);
            Bukkit.getPluginManager().callEvent(event);
        }}.runTaskLater(Main.inst(), 0);
    }

    @EventHandler (ignoreCancelled = true)
    public void onArmorClick(final InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null) return;
        else if(e.getClickedInventory().getType() != InventoryType.PLAYER && e.getClickedInventory().getType() != InventoryType.CRAFTING) return;

        if(isArmor(e.getCurrentItem()) || isArmor(e.getCursor()) || e.getHotbarButton() > -1 && isArmor(e.getInventory().getItem(e.getHotbarButton())))
            callArmorCheck(player);
    }

    @EventHandler
    public void onArmorEquip(final PlayerInteractEvent e){
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(isArmor(e.getItem())) callArmorCheck(e.getPlayer());
    }

    @EventHandler
    public void onArmorDispense(final BlockDispenseArmorEvent e){
        if(isArmor(e.getItem()) && e.getTargetEntity() instanceof Player player) callArmorCheck(player);
    }

    // Chest
    @EventHandler
    public void onChestClosed(final InventoryCloseEvent e){
        if(e.getInventory().getHolder() == null) return;

        if(e.getInventory().getHolder() instanceof Chest chest) {
            ChestClosedEvent event = new ChestClosedEvent((Player) e.getPlayer(), chest.getBlock().getLocation(), e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
        }
        else if(e.getInventory().getHolder() instanceof DoubleChest doubleChest){
            Location loc1 = new Location(doubleChest.getWorld(),
                    Math.floor(doubleChest.getX()), doubleChest.getY(), Math.floor(doubleChest.getZ()));
            Location loc2 = new Location(doubleChest.getWorld(),
                    Math.ceil(doubleChest.getX()), doubleChest.getY(), Math.ceil(doubleChest.getZ()));

            DoubleChestClosedEvent event = new DoubleChestClosedEvent((Player) e.getPlayer(), loc1, loc2, e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Item Result
    private boolean isResultSlot(final InventoryClickEvent e){
        if(e.getClickedInventory() == null) return false;
        if(e.getClickedInventory().getType() == InventoryType.BREWING) return e.getSlot() < 3;
        return e.getSlotType() == InventoryType.SlotType.RESULT;
    }

    @EventHandler (ignoreCancelled = true)
    public void onSpecialCraft(final InventoryClickEvent e){
        if(!isResultSlot(e)) return;
        if(ItemUtil.isNull(e.getCurrentItem())) return;

        Player player = (Player) e.getWhoClicked();

        switch(e.getAction())
        {
            case DROP_ONE_SLOT, DROP_ALL_SLOT ->
                    specialCraftEvent(player, e.getClickedInventory(), e.getCurrentItem(), e.getSlot());

            case PICKUP_ONE, PICKUP_SOME, PICKUP_HALF, PICKUP_ALL -> {
                if(!ItemUtil.isNull(e.getCursor())){
                    if(!e.getCursor().isSimilar(e.getCurrentItem())) return;
                    if(e.getCursor().getAmount() + e.getCurrentItem().getAmount() > e.getCursor().getMaxStackSize()) return;
                }
                specialCraftEvent(player, e.getClickedInventory(), e.getCurrentItem(), e.getSlot());
            }

            case MOVE_TO_OTHER_INVENTORY -> {
                if(InventoryUtil.hasSpaceFor(player.getInventory(), e.getCurrentItem()))
                    specialCraftEvent(player, e.getClickedInventory(), e.getCurrentItem(), e.getSlot());
            }

            case HOTBAR_SWAP -> {
                if(ItemUtil.isNull(player.getInventory().getItem(e.getHotbarButton())))
                    specialCraftEvent(player, e.getClickedInventory(), e.getCurrentItem(), e.getSlot());
            }
        }
    }

    private void specialCraftEvent(Player player, Inventory inv, ItemStack result, int slot){
        ResultItemTakenEvent event = new ResultItemTakenEvent(player, result, inv);
        Bukkit.getPluginManager().callEvent(event);

        if(event.hasChangedResult()) inv.setItem(slot, event.getResult());
    }

    // Books
    @EventHandler
    public void onBookSign(PlayerEditBookEvent e){
        if(!e.getPreviousBookMeta().hasAuthor() && e.getNewBookMeta().hasAuthor()){
            SignBookEvent event = new SignBookEvent(e.getPlayer());
            Bukkit.getPluginManager().callEvent(event);
            BookCollectionGUI.addBook(e.getNewBookMeta());
        }
    }

    // Obtain item
    @EventHandler (ignoreCancelled = true)
    public void onGetItemByClick(final InventoryClickEvent e){
        if(e.getClickedInventory() == null) return;
        Player player = (Player) e.getWhoClicked();

        switch(e.getAction())
        {
            case MOVE_TO_OTHER_INVENTORY -> {
                if(e.getClickedInventory().getType() != InventoryType.PLAYER && !ItemUtil.isNull(e.getCurrentItem()) && InventoryUtil.hasSpaceFor(player.getInventory(), e.getCurrentItem()))
                    playerInventoryNewItem(player, e.getCurrentItem());
            }

            case PLACE_ALL, PLACE_ONE, PLACE_SOME -> {
                if(e.getClickedInventory().getType() == InventoryType.PLAYER && InventoryUtil.hasSpaceInSlot(e.getCurrentItem(), e.getCursor()))
                    playerInventoryNewItem(player, e.getCursor());
            }

            case SWAP_WITH_CURSOR -> playerInventoryNewItem(player, e.getCursor());

            case HOTBAR_SWAP -> {
                if(e.getClickedInventory().getType() != InventoryType.PLAYER && !ItemUtil.isNull(e.getCurrentItem()))
                    playerInventoryNewItem(player, e.getCurrentItem());
            }

            case COLLECT_TO_CURSOR -> {
                if(e.getInventory().getType() == InventoryType.PLAYER || e.getInventory().getType() == InventoryType.CRAFTING) return;
                if(e.getInventory().containsAtLeast(e.getCursor(), 1))
                    playerInventoryNewItem(player, e.getCursor());
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onItemDrag(final InventoryDragEvent e){
        if(e.getInventory().getType() != InventoryType.CRAFTING)
        {
            boolean found = false;
            for(int slot : e.getNewItems().keySet()){
                if(slot >= e.getInventory().getSize()){
                    found = true;
                    break;
                }
            }

            if(!found) return;
        }

        ItemStack item = e.getNewItems().values().iterator().next();
        playerInventoryNewItem((Player) e.getWhoClicked(), item);
    }

    @EventHandler
    public void onItemPickup(final EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player player)) return;
        playerInventoryNewItem(player, e.getItem().getItemStack());
    }

    private void playerInventoryNewItem(final Player player, final ItemStack item){
        final ItemStack copy = item.clone();
        new BukkitRunnable(){
            @Override public void run(){
                PlayerInventoryNewItemEvent event = new PlayerInventoryNewItemEvent(player, copy);
                Bukkit.getPluginManager().callEvent(event);
            }
        }.runTaskLater(Main.inst(), 0);

    }

    // Potion brew
    @EventHandler
    public void onPotionBrew(final BrewEvent e){
        for(int i = 0; i < e.getResults().size(); i++){
            if(ItemUtil.isNull(e.getResults().get(i))) continue;

            final net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(e.getResults().get(i));
            CompoundTag tag = nmsItem.hasTag() ? nmsItem.getTag() : new CompoundTag();
            tag.putBoolean("brewed", true);

            nmsItem.setTag(tag);
            e.getResults().set(i, CraftItemStack.asCraftMirror(nmsItem));
        }
    }

    @EventHandler
    public void onPlayerTakePotion(final ResultItemTakenEvent e){
        if(e.getInventory().getType() != InventoryType.BREWING) return;

        final ItemStack result = checkPotionBrewedAndReplace(e.getResult());
        if(result != null){
            e.setResult(result);
            PotionMeta meta = (PotionMeta) result.getItemMeta();

            PlayerBrewPotionEvent event = new PlayerBrewPotionEvent(e.getPlayer(), meta);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onBrewingStandBreak(final BlockBreakEvent e){
        if(e.getBlock().getType() != Material.BREWING_STAND) return;
        BrewerInventory inv = ((BrewingStand) e.getBlock().getState()).getInventory();

        for(int i = 0; i < 3; i++){
            final ItemStack potion = checkPotionBrewedAndReplace(inv.getItem(i));
            if(potion != null) {
                inv.setItem(i, potion);
                PotionMeta meta = (PotionMeta) potion.getItemMeta();

                PlayerBrewPotionEvent event = new PlayerBrewPotionEvent(e.getPlayer(), meta);
                Bukkit.getPluginManager().callEvent(event);
            }
        }
    }

    @EventHandler
    public void onPotionSpawn(final ItemSpawnEvent e){
        final ItemStack result = checkPotionBrewedAndReplace(e.getEntity().getItemStack());
        if(result != null) e.getEntity().setItemStack(result);
    }

    private ItemStack checkPotionBrewedAndReplace(ItemStack item){
        if(ItemUtil.isNull(item)) return null;
        if(item.getType() != Material.POTION && item.getType() != Material.SPLASH_POTION && item.getType() != Material.LINGERING_POTION) return null;

        final net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        if(!nmsItem.hasTag() || !nmsItem.getTag().contains("brewed")) return null;

        nmsItem.getTag().remove("brewed");
        return CraftItemStack.asCraftMirror(nmsItem);
    }

    // Trade
    @EventHandler
    public void onPlayerTrade(final ResultItemTakenEvent e){
        if(e.getInventory().getType() == InventoryType.MERCHANT && e.getInventory().getHolder() instanceof Villager villager)
        {
            VillagerTradeEvent event = new VillagerTradeEvent(e.getPlayer(), villager, (MerchantInventory) e.getInventory());
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Complete craft
    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCraft(final CraftItemEvent e){
        Player player = (Player) e.getWhoClicked();

        final ItemStack result = e.getRecipe().getResult();
        int amount = 0;

        switch(e.getAction())
        {
            // Simple clicks
            case PICKUP_ONE, PICKUP_SOME, PICKUP_HALF, PICKUP_ALL -> {
                if(!ItemUtil.isNull(e.getCursor())){
                    if(!e.getCursor().isSimilar(e.getCurrentItem())) return;
                    if(e.getCursor().getAmount() + e.getCurrentItem().getAmount() > e.getCursor().getMaxStackSize()) return;
                }
                amount = result.getAmount();
            }

            // Drops
            case DROP_ONE_SLOT, DROP_ALL_SLOT -> amount = result.getAmount();

            // Swap
            case HOTBAR_SWAP -> {
                if(ItemUtil.isNull(player.getInventory().getItem(e.getHotbarButton())))
                    amount = result.getAmount();
            }

            // Shift click
            case MOVE_TO_OTHER_INVENTORY -> {
                int crafts = -1;
                for(ItemStack ingredient : e.getInventory().getMatrix()){
                    if(!ItemUtil.isNull(ingredient) && (crafts == -1 || crafts > ingredient.getAmount()))
                        crafts = ingredient.getAmount();
                }
                if(crafts < 1) return;

                int spaceLeft = 0;
                for(int i = 0; i < 36; i++){
                    final ItemStack slot = player.getInventory().getItem(i);
                    if(ItemUtil.isNull(slot)) spaceLeft += result.getMaxStackSize();
                    else if(result.isSimilar(slot)) spaceLeft += result.getMaxStackSize() - slot.getAmount();
                }

                crafts = Math.min((int) Math.ceil(spaceLeft / (float) result.getAmount()), crafts);
                amount = crafts * result.getAmount();
            }
        }

        if(amount > 0){
            CompleteCraftItemEvent event = new CompleteCraftItemEvent(player, result, amount);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

    // Horn use
    @EventHandler (priority = EventPriority.HIGH)
    public void onGoatHornUse(final PlayerInteractEvent e){
        if(ItemUtil.isNull(e.getItem()) || e.getItem().getType() != Material.GOAT_HORN) return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(e.getPlayer().hasCooldown(Material.GOAT_HORN)) return;

        new BukkitRunnable(){
            @Override public void run(){
                if(e.getPlayer().hasCooldown(Material.GOAT_HORN)){
                    PlayerUseGoatHornEvent event = new PlayerUseGoatHornEvent(e.getPlayer(), e.getItem());
                    Bukkit.getPluginManager().callEvent(event);
                }
            }
        }.runTaskLater(Main.inst(), 1);
    }

    // Respawn anchor charge
    @EventHandler (priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onAnchorCharge(final PlayerInteractEvent e){
        if(e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.RESPAWN_ANCHOR) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || ItemUtil.isNull(e.getItem()) || e.getItem().getType() != Material.GLOWSTONE) return;

        RespawnAnchor anchor = (RespawnAnchor) e.getClickedBlock().getBlockData();
        if(anchor.getCharges() == anchor.getMaximumCharges() - 1){
            MaxChargeAnchorEvent event = new MaxChargeAnchorEvent(e.getPlayer(), anchor);
            Bukkit.getPluginManager().callEvent(event);
        }
    }

}
