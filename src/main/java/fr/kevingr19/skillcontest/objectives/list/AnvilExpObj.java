package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilExpObj extends TeamObjective<Integer> {

    public AnvilExpObj(GameTeam gameTeam) {
        super(ObjectiveType.ANVIL_EXP, Rarity.MODERATE, "Pro des armes",
                "Dépenser 30 niveaux dans une enclume.", null,
                new ItemStack(Material.ANVIL), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 30);
    }

    @EventHandler
    public void onAnvilUse(final ResultItemTakenEvent e){
        if(!isInTeam(e.getPlayer()) || e.getInventory().getType() != InventoryType.ANVIL) return;

        int levelCost = ((AnvilInventory) e.getInventory()).getRepairCost();
        if(e.getPlayer().getLevel() >= levelCost || e.getPlayer().getGameMode() == GameMode.CREATIVE)
            objectiveData.addProgress(e.getPlayer(), levelCost);
    }
}
