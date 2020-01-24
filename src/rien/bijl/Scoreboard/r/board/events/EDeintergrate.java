package rien.bijl.Scoreboard.r.board.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import rien.bijl.Scoreboard.r.Main;
import rien.bijl.Scoreboard.r.board.App;

/**
 * Created by Rien on 23-10-2018.
 */
public class EDeintergrate implements Listener {

    private App app;

    public EDeintergrate(App app)
    {
        this.app = app;
    }

    @EventHandler
    public void Deintergrate(PlayerQuitEvent e)
    {
        if(app == null) return;
        app.unregisterHolder(e.getPlayer());
        e.getPlayer().setScoreboard(Main.empty);
    }

}
