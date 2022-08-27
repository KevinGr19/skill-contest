package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffectType;

public class RaidVictoryObj extends Objective {

    private static final ItemStack banner;
    static{
        banner = new ItemStack(Material.WHITE_BANNER);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.STRIPE_BOTTOM));
        meta.addPattern(new Pattern(DyeColor.GRAY, PatternType.STRIPE_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.BORDER));
        meta.addPattern(new Pattern(DyeColor.GRAY, PatternType.STRIPE_CENTER));
        meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.HALF_HORIZONTAL));
        meta.addPattern(new Pattern(DyeColor.LIGHT_GRAY, PatternType.CIRCLE_MIDDLE));
        meta.addPattern(new Pattern(DyeColor.BLACK, PatternType.BORDER));
        banner.setItemMeta(meta);
    }

    public RaidVictoryObj(GameTeam gameTeam) {
        super(ObjectiveType.RAID_VICTORY, Rarity.DIFFICULT, "Barbares repoussés",
                "Battre un Raid.", null,
                banner, true, gameTeam);
    }

    @EventHandler
    public void onEffect(final EntityPotionEffectEvent e){
        if(!(e.getEntity() instanceof Player player) || !isInTeam(player) || e.getNewEffect() == null) return;
        if(e.getNewEffect().getType().equals(PotionEffectType.HERO_OF_THE_VILLAGE))
            complete(player);
    }
}
