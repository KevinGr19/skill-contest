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

public class CookieObj extends TeamObjective<Integer> {

    public CookieObj(GameTeam gameTeam) {
        super(ObjectiveType.COOKIE, Rarity.EASY, "C'est l'heure du gouter",
                "Fabriquer 64 cookies.",
                null, new ItemStack(Material.COOKIE), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, true, 64);
    }

    @EventHandler
    public void onCookieCraft(final CompleteCraftItemEvent e){
        if(isInTeam(e.getPlayer()) && e.getResult().getType() == Material.COOKIE)
            objectiveData.addProgress(e.getPlayer(), e.getAmount());
    }
}
