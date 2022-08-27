package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;

public class BeeBreedObj extends Objective {

    public BeeBreedObj(GameTeam gameTeam) {
        super(ObjectiveType.BEE_BREED, Rarity.EASY, "Sauveur de rencard",
                "Reproduire 2 abeilles.", null,
                new ItemStack(Material.OXEYE_DAISY), false, gameTeam);
    }

    @EventHandler
    public void onBreed(final EntityBreedEvent e){
        if(e.getEntity().getType() == EntityType.BEE && e.getBreeder() instanceof Player player && isInTeam(player)) complete(player);
    }
}
