package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class HoneyBottlePoisonObj extends Objective {

    public HoneyBottlePoisonObj(GameTeam gameTeam) {
        super(ObjectiveType.HONEY_BOTTLE_POISON, Rarity.EASY, "Remède de grand-mères",
                "Boire une fiole de miel pour se\nsoigner de l'effet Poison.", null,
                new ItemStack(Material.HONEY_BOTTLE), false, gameTeam);
    }

    @EventHandler
    public void onHoneyDrink(final PlayerItemConsumeEvent e){
        if(!isInTeam(e.getPlayer()) || e.getItem().getType() != Material.HONEY_BOTTLE) return;
        if(e.getPlayer().getPotionEffect(PotionEffectType.POISON) != null) complete(e.getPlayer());
    }
}
