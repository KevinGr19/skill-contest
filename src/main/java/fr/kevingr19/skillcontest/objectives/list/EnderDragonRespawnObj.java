package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.PlayerRespawnDragonEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class EnderDragonRespawnObj extends Objective {

    public EnderDragonRespawnObj(GameTeam gameTeam) {
        super(ObjectiveType.ENDER_DRAGON_RESPAWN, Rarity.DIFFICULT, "Faut pas réveiller la bête",
                "Ressusciter l'Ender Dragon.", null,
                new ItemStack(Material.END_CRYSTAL), true, gameTeam);
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnDragonEvent e){
        if(isInTeam(e.getOfflinePlayer())) complete(e.getOfflinePlayer());
    }
}
