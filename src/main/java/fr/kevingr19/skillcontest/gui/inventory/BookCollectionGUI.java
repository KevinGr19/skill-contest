package fr.kevingr19.skillcontest.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
/**
 * {@link ClickGUI} gimmick that contains the first 54 books written by players.
 */

final public class BookCollectionGUI extends ClickGUI{

    private static int books = 0;
    private static final BookCollectionGUI gui = new BookCollectionGUI();

    public static void openPlayerInventory(Player player){
        gui.openInventory(player);
    }

    public static void addBook(BookMeta meta){
        if(books >= 54) return;
        books++;

        ItemStack newBook = new ItemStack(Material.WRITTEN_BOOK);
        newBook.setItemMeta(meta);
        gui.inv.setItem(books - 1, newBook);
    }

    @Override
    protected Inventory createInventory(String title) {
        return Bukkit.createInventory(null, 54, "Bibliothèque");
    }

    @Override
    protected void clickOnInventory(InventoryClickEvent e) {
        e.setCancelled(true);

        final ItemStack item = inv.getItem(e.getSlot());
        if(item != null && item.getType() == Material.WRITTEN_BOOK)
            ((Player) e.getWhoClicked()).openBook(item);
    }
}
