package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class FurnaceOresObj extends TeamObjective<Integer> {

    public FurnaceOresObj(GameTeam gameTeam) {
        super(ObjectiveType.FURNACE_ORES, Rarity.MODERATE, "Attention : Très chaud",
                "Faire fondre puis récupérer 64 minerais\ndans des fours.",
                null, new ItemStack(Material.FURNACE), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 64);
    }

    @EventHandler
    public void onOreGet(final ResultItemTakenEvent e){
        if(e.getInventory().getType() != InventoryType.FURNACE && e.getInventory().getType() != InventoryType.BLAST_FURNACE) return;
        if(!isInTeam(e.getPlayer())) return;

        if(ItemUtil.isMineral(e.getResult().getType()) && !e.getResult().getType().isFuel())
            objectiveData.addProgress(e.getPlayer(), e.getResult().getAmount());

    }
}
