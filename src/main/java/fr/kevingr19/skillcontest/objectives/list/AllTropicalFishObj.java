package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AllTropicalFishObj extends TeamObjective<String> {

    public AllTropicalFishObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_TROPICAL_FISH, Rarity.DIFFICULT, "Pokédex complet",
                "Attraper chaque espèce de poisson\ntropical dans un seau.", null,
                new ItemStack(Material.TROPICAL_FISH_BUCKET), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, true, "Argilécaille", "Betty", "Bigarrin", "Constèle", "Cubécaille",
                "Fonceur", "Fouineur", "Frétilleur", "Héliorai", "Saumuret", "Téraglin", "Truiton");
    }

    @EventHandler
    public void onBucketFill(final PlayerBucketEntityEvent e){
        if(e.getEntity().getType() != EntityType.TROPICAL_FISH || !isInTeam(e.getPlayer())) return;
        TropicalFish fish = (TropicalFish) e.getEntity();

        String type = null;
        switch(fish.getPattern())
        {
            case BETTY -> type = "Betty";
            case BLOCKFISH -> type = "Cubécaille";
            case BRINELY -> type = "Saumuret";
            case CLAYFISH -> type = "Argilécaille";
            case DASHER -> type = "Fonceur";
            case FLOPPER -> type = "Frétilleur";
            case GLITTER -> type = "Constèle";
            case KOB -> type = "Téraglin";
            case SNOOPER -> type = "Fouineur";
            case SPOTTY -> type = "Truiton";
            case STRIPEY -> type = "Bigarrin";
            case SUNSTREAK -> type = "Héliorai";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}