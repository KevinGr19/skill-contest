package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class DolphinGraceObj extends Objective {

    public DolphinGraceObj(GameTeam gameTeam) {
        super(ObjectiveType.DOLPHIN_GRACE, Rarity.EASY, "Grâce du dauphin",
                "Nager avec un dauphin.", null,
                new ItemStack(Material.DOLPHIN_SPAWN_EGG), false, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getNewEffect().getType().equals(PotionEffectType.DOLPHINS_GRACE))
            complete(player);
    }
}
