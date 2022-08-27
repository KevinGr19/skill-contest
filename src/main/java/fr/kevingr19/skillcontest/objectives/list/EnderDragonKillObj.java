package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EnderDragonKillObj extends Objective {

    public EnderDragonKillObj(GameTeam gameTeam) {
        super(ObjectiveType.ENDER_DRAGON_KILL, Rarity.DIFFICULT, "Repos Explosif",
                "Tuer un Ender Dragon.",
                "Les explosions de lit ne peuvent pas\nêtre détectés comme venant de joueurs.\nFaites le coup final d'une autre manière.",
                new ItemStack(Material.DRAGON_EGG), false, gameTeam);
    }

    @EventHandler
    public void onDragonKill(final EntityDeathEvent e){
        if(e.getEntityType() == EntityType.ENDER_DRAGON && isInTeam(e.getEntity().getKiller())) complete(e.getEntity().getKiller());
    }
}