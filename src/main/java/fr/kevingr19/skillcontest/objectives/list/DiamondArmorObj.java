package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.CheckForArmorEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class DiamondArmorObj extends Objective {

    public DiamondArmorObj(GameTeam gameTeam) {
        super(ObjectiveType.DIAMOND_ARMOR, Rarity.MODERATE, "Alliage en carbone",
                "Porter une armure complète en diamant.", null,
                new ItemStack(Material.DIAMOND_CHESTPLATE), false, gameTeam);
    }

    @EventHandler
    public void onArmorCheck(final CheckForArmorEvent e){
        Player player = e.getPlayer();
        if(isInTeam(player) && ItemUtil.hasArmor(player.getEquipment(), ItemUtil.ArmorType.DIAMOND)) complete(player);
    }
}
