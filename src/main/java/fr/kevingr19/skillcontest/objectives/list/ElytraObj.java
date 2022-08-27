package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.CheckForArmorEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class ElytraObj extends Objective {

    public ElytraObj(GameTeam gameTeam) {
        super(ObjectiveType.ELYTRA, Rarity.MODERATE, "Ailes de cire",
                "Porter une paire d'Élytra.", null,
                new ItemStack(Material.ELYTRA), false, gameTeam);
    }

    @EventHandler
    public void onArmorCheck(final CheckForArmorEvent e){
        if(!isInTeam(e.getPlayer())) return;

        final ItemStack chestplate = e.getPlayer().getInventory().getChestplate();
        if(!ItemUtil.isNull(chestplate) && chestplate.getType() == Material.ELYTRA) complete(e.getPlayer());
    }
}
