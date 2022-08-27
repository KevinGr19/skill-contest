package fr.kevingr19.skillcontest.gui.inventory;

import org.bukkit.entity.Player;

/**
 * Single method Interface that executes code whenever a
 * {@link MultiClickGUI.Frame} is loaded.
 * Usually used to reset items before opening.
 */

public interface LoadFrameAction {

    void invoke(Player player);

}
