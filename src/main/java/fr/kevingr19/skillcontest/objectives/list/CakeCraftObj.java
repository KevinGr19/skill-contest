package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.CompleteCraftItemEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class CakeCraftObj extends TeamObjective<Integer> {

    public CakeCraftObj(GameTeam gameTeam) {
        super(ObjectiveType.CAKE_CRAFT, Rarity.MODERATE, "Cake or Fake ?",
                "Fabriquer 10 gâteaux.",
                null, new ItemStack(Material.CAKE), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 10);
    }

    @EventHandler
    public void onCakeCraft(final CompleteCraftItemEvent e){
        if(isInTeam(e.getPlayer()) && e.getResult().getType() == Material.CAKE)
            objectiveData.addProgress(e.getPlayer(), e.getAmount());
    }
}
