package fr.kevingr19.skillcontest.event.listener;

import fr.kevingr19.skillcontest.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Listener started after game initialisation, that handles async player messages.
 */

public class MessageListener implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent e){
        if(Game.inst().isPlaying(e.getPlayer(), true)) {
            Game.inst().getGamePlayer(e.getPlayer()).getGameTeam().sendTeamMsg(e.getPlayer(), e.getMessage());
            e.setCancelled(true);
        }
    }

}
