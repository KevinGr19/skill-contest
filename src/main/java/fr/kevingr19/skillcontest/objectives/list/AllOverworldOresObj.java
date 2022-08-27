package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerInventoryNewItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class AllOverworldOresObj extends TeamObjective<String> {

    public AllOverworldOresObj(GameTeam gameTeam) {
        super(ObjectiveType.ALL_OVERWORLD_ORES, Rarity.MODERATE, "Hank Schrader",
                "Obtenir chaque minerai de l'Overworld.", null,
                new ItemStack(Material.DIAMOND_PICKAXE), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Charbon", "Charbon de bois", "Lingot de fer",
                "Lingot d'or", "Lingot de cuivre", "Redstone", "Lapis-lazuli", "Diamant", "Émeraude");
    }

    @EventHandler
    public void onOreObtain(final PlayerInventoryNewItemEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getMaterial())
        {
            case COAL -> type = "Charbon";
            case CHARCOAL -> type = "Charbon de bois";
            case IRON_INGOT -> type = "Lingot de fer";
            case GOLD_INGOT -> type = "Lingot d'or";
            case COPPER_INGOT -> type = "Lingot de cuivre";
            case REDSTONE -> type = "Redstone";
            case LAPIS_LAZULI -> type = "Lapis-lazuli";
            case DIAMOND -> type = "Diamant";
            case EMERALD -> type = "Émeraude";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}