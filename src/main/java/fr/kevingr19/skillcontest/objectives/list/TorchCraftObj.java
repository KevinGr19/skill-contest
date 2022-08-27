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

public class TorchCraftObj extends TeamObjective<Integer> {

    public TorchCraftObj(GameTeam gameTeam) {
        super(ObjectiveType.TORCH_CRAFT, Rarity.EASY, "C'est pas Versailles ici",
                "Fabriquer 12 torches.",
                null, new ItemStack(Material.TORCH), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 12);
    }

    @EventHandler
    public void onTorchCraft(final CompleteCraftItemEvent e){
        if(isInTeam(e.getPlayer()) && e.getResult().getType() == Material.TORCH)
            objectiveData.addProgress(e.getPlayer(), e.getAmount());
    }
}
