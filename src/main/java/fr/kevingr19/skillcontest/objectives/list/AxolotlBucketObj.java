package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AxolotlBucketObj extends Objective {

    public AxolotlBucketObj(GameTeam gameTeam) {
        super(ObjectiveType.AXOLOTL_BUCKET, Rarity.MODERATE, "Surprise au puits",
                "Attraper un axolotl dans un seau.", null,
                new ItemStack(Material.AXOLOTL_BUCKET), false, gameTeam);
    }

    @EventHandler
    public void onBucketFill(final PlayerBucketEntityEvent e){
        if(e.getEntity().getType() == EntityType.AXOLOTL && isInTeam(e.getPlayer())) complete(e.getPlayer());
    }
}