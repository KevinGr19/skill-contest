package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishObj extends TeamObjective<Integer> {

    public FishObj(GameTeam gameTeam) {
        super(ObjectiveType.FISH, Rarity.EASY, "Sérrées les sardines",
                "Pêcher 25 poissons.", null, new ItemStack(Material.FISHING_ROD), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 25);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e){
        if(!isInTeam(e.getPlayer()) || !(e.getCaught() instanceof Item item)) return;
        Material type = item.getItemStack().getType();

        if(type == Material.COD || type == Material.SALMON || type == Material.PUFFERFISH || type == Material.TROPICAL_FISH)
            objectiveData.addProgress(e.getPlayer(), 1);
    }
}
