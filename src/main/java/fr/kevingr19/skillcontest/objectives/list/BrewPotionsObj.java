package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerBrewPotionEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.ListChoiceData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class BrewPotionsObj extends TeamObjective<String> {

    public BrewPotionsObj(GameTeam gameTeam) {
        super(ObjectiveType.BREW_POTIONS, Rarity.DIFFICULT, "Yeah Science, bitch !",
                "Concoter 6 effets de potions différents.", null,
                new ItemStack(Material.CAULDRON), gameTeam);
    }

    @Override
    protected ObjectiveData<String> createObjectiveData() {
        return new ListChoiceData(this, false, 6, "Rapidité", "Lenteur", "Saut amélioré",
                "Force", "Faiblesse", "Soin", "Dégâts", "Régénération", "Poison", "Résistance au feu", "Respiration aquatique",
                "Vision nocturne", "Invisibilité", "Chute lente", "Maître Tortue");
    }

    @EventHandler
    public void onBrew(final PlayerBrewPotionEvent e){
        if(!isInTeam(e.getPlayer())) return;

        String type = null;
        switch(e.getPotionMeta().getBasePotionData().getType())
        {
            case FIRE_RESISTANCE -> type = "Résistance au feu";
            case INSTANT_DAMAGE -> type = "Dégâts";
            case INSTANT_HEAL -> type = "Soin";
            case JUMP -> type = "Saut amélioré";
            case REGEN -> type = "Régénération";
            case SPEED -> type = "Rapidité";
            case POISON -> type = "Poison";
            case SLOWNESS -> type = "Lenteur";
            case STRENGTH -> type = "Force";
            case WEAKNESS -> type = "Faiblesse";
            case INVISIBILITY -> type = "Invisibilité";
            case NIGHT_VISION -> type = "Vision nocture";
            case SLOW_FALLING -> type = "Chute lente";
            case TURTLE_MASTER -> type = "Maître Tortue";
            case WATER_BREATHING -> type = "Respiration aquatique";
        }

        if(type != null) objectiveData.addProgress(e.getPlayer(), type);
    }
}
