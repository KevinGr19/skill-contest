package fr.kevingr19.skillcontest.constants;

import fr.kevingr19.skillcontest.utils.SoundComponent;
import org.bukkit.Sound;

public class Sounds {

    public static final SoundComponent EASY_OBJ = new SoundComponent(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.3f, 1.2f);
    public static final SoundComponent MODERATE_OBJ = new SoundComponent(Sound.ENTITY_PLAYER_LEVELUP, 0.3f, 1.1f);
    public static final SoundComponent DIFFICULT_OBJ = new SoundComponent(Sound.ENTITY_PLAYER_LEVELUP, 0.6f, 0.5f);
    public static final SoundComponent IMPOSSIBLE_OBJ = new SoundComponent(Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.6f, 1);
    public static final SoundComponent LEGENDARY_OBJ = new SoundComponent(Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 0.8f);

    public static final SoundComponent WINNER = new SoundComponent(Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 0.7f);
    public static final SoundComponent LOSER = new SoundComponent(Sound.BLOCK_BEACON_DEACTIVATE, 1, 0.7f);
    public static final SoundComponent DRAW = new SoundComponent(Sound.ENTITY_WITHER_SPAWN, 1f, 1);
    public static final SoundComponent END_SPECTATOR = new SoundComponent(Sound.ENTITY_BLAZE_SHOOT, 1f, 1);

}
