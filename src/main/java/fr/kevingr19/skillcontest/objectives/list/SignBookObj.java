package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.SignBookEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.CounterData;
import fr.kevingr19.skillcontest.objectives.ObjectiveData;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.objectives.TeamObjective;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class SignBookObj extends TeamObjective<Integer> {

    public SignBookObj(GameTeam gameTeam) {
        super(ObjectiveType.SIGN_BOOK, Rarity.EASY, "Mon Tweet Longer",
                "Signer 3 livres.", null,
                new ItemStack(Material.BOOK), gameTeam);
    }

    @Override
    protected ObjectiveData<Integer> createObjectiveData() {
        return new CounterData(this, false, 3);
    }

    @EventHandler
    public void onSign(final SignBookEvent e){
        if(isInTeam(e.getPlayer())) objectiveData.addProgress(e.getPlayer(), 1);
    }
}
