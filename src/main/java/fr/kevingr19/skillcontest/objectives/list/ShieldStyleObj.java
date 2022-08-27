package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class ShieldStyleObj extends Objective {

    public ShieldStyleObj(GameTeam gameTeam) {
        super(ObjectiveType.SHIELD_STYLE, Rarity.EASY, "Protection avant rapport",
                "Fabriquer un bouclier stylisé\navec une bannière complète.",
                "Une bannière complète contient 6 motifs.", new ItemStack(Material.LOOM), false, gameTeam);
    }

    @EventHandler
    public void onShieldCraft(final ResultItemTakenEvent e){
        Player player = e.getPlayer();
        if(!isInTeam(player)) return;

        ItemStack item = e.getResult();
        if(item.getType() != Material.SHIELD || !(item.getItemMeta() instanceof BlockStateMeta bmeta)) return;
        if(!(bmeta.getBlockState() instanceof Banner banner)) return;

        if(banner.getPatterns().size() >= 6) complete(player);
    }
}
