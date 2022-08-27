package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

public class EnterBoatObj extends Objective {

    public EnterBoatObj(GameTeam gameTeam) {
        super(ObjectiveType.ENTER_BOAT, Rarity.EASY, "Un petit navire",
                "Entrer dans un bateau.", null,
                new ItemStack(Material.OAK_BOAT), false, gameTeam);
    }

    @EventHandler
    public void onRideBoat(final VehicleEnterEvent e){
        if(!(e.getEntered() instanceof Player player) || !isInTeam(player)) return;
        if(e.getVehicle().getType() == EntityType.BOAT || e.getVehicle().getType() == EntityType.CHEST_BOAT)
            complete(player);
    }
}
