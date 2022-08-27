package fr.kevingr19.skillcontest.gui.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Single method Interface that executes code when a slot is clicked.
 * In most cases, the {@link InventoryClickEvent} is cancelled prior to the call,
 * to prevent items from moving in GUI's.
 */

public interface ClickAction {

    void invoke(InventoryClickEvent e);

}
