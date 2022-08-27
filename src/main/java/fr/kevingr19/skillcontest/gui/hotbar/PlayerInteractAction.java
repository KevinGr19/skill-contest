package fr.kevingr19.skillcontest.gui.hotbar;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Class that wrap together an {@link Action} and a boolean value.
 */

public class PlayerInteractAction {

    private Action action;
    public boolean active;

    public PlayerInteractAction(Action action, boolean active){
        this.action = action;
        this.active = active;
    }

    public PlayerInteractAction(Action action){
        this.action = action;
        this.active = true;
    }

    public Action getAction(){
        return action;
    }

    public void setAction(Action action){
        this.action = action;
    }

    public interface Action{
        void invoke(Player player, PlayerInteractEvent e);
    }

}
