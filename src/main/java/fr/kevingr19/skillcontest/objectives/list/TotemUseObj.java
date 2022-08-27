package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;

public class TotemUseObj extends Objective {

    public TotemUseObj(GameTeam gameTeam) {
        super(ObjectiveType.TOTEM_USE, Rarity.MODERATE, "Jésus de Nazareth",
                "Utiliser un Totem d'immortalité.", null,
                new ItemStack(Material.TOTEM_OF_UNDYING), false, gameTeam);
    }

    @EventHandler
    public void onResurrect(final EntityResurrectEvent e){
        if(!e.isCancelled() && e.getEntity() instanceof Player player && isInTeam(player)) complete(player);
    }
}
