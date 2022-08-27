package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class RedstoneExpertObj extends TeamObjective<String> {

    public RedstoneExpertObj(GameTeam gameTeam) {
        super(ObjectiveType.REDSTONE_EXPERT, Rarity.DIFFICULT, "Aypierre en PLS",
                "Fabriquer tout les composants redstone\nde la liste de l'objectif.", null,
                new ItemStack(Material.PISTON), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListData(this, false, "Bouton", "Levier", "Plaque de pression", "Plaque de pression légère", "Plaque de pression lourde",
                "Piston", "Piston collant", "Répéteur", "Distributeur", "Dropper", "Entonnoir", "Comparateur", "Lampe de redstone",
                "Observateur", "Détecteur de lumière", "Rail de propulsion", "Rail détecteur", "Rail déclencheur");
    }

    @EventHandler
    public void onItemCraft(final ResultItemTakenEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getResult().getType())
        {
            case BIRCH_BUTTON, ACACIA_BUTTON, CRIMSON_BUTTON, DARK_OAK_BUTTON, JUNGLE_BUTTON, OAK_BUTTON, MANGROVE_BUTTON, SPRUCE_BUTTON, STONE_BUTTON, WARPED_BUTTON, POLISHED_BLACKSTONE_BUTTON
                    -> type = "Bouton";
            case LEVER -> type = "Levier";
            case ACACIA_PRESSURE_PLATE, BIRCH_PRESSURE_PLATE, CRIMSON_PRESSURE_PLATE, DARK_OAK_PRESSURE_PLATE, JUNGLE_PRESSURE_PLATE, POLISHED_BLACKSTONE_PRESSURE_PLATE, MANGROVE_PRESSURE_PLATE, OAK_PRESSURE_PLATE, SPRUCE_PRESSURE_PLATE, STONE_PRESSURE_PLATE, WARPED_PRESSURE_PLATE
                    -> type = "Plaque de pression";
            case HEAVY_WEIGHTED_PRESSURE_PLATE -> type = "Plaque de pression lourde";
            case LIGHT_WEIGHTED_PRESSURE_PLATE -> type = "Plaque de pression légère";
            case PISTON -> type = "Piston";
            case STICKY_PISTON -> type = "Piston collant";
            case REPEATER -> type = "Répéteur";
            case DISPENSER -> type = "Distributeur";
            case DROPPER -> type = "Dropper";
            case HOPPER -> type = "Entonnoir";
            case COMPARATOR -> type = "Comparateur";
            case REDSTONE_LAMP -> type = "Lampe de redstone";
            case OBSERVER -> type = "Observateur";
            case DAYLIGHT_DETECTOR -> type = "Détecteur de lumière";
            case POWERED_RAIL -> type = "Rail de propulsion";
            case ACTIVATOR_RAIL -> type = "Rail déclencheur";
            case DETECTOR_RAIL -> type = "Rail détecteur";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
