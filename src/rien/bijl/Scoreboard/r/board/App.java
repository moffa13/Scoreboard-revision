package rien.bijl.Scoreboard.r.board;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rien.bijl.Scoreboard.r.Session;
import rien.bijl.Scoreboard.r.board.events.EDeintergrate;
import rien.bijl.Scoreboard.r.board.events.EIntergrate;
import rien.bijl.Scoreboard.r.util.ConfigControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rien on 21-10-2018.
 */
public class App extends BukkitRunnable {

    private Row title;
    private ArrayList<Row> rows = new ArrayList<>();
    public ArrayList<ScoreboardHolder> holders = new ArrayList<>();
    public static boolean longline = false;
    public String board;
    public boolean isdefault = true;

    /**
     * Construct a new board driver
     * @param board
     */
    public App(String board)
    {
        // conf
        App.longline = ConfigControl.get().gc("settings").getBoolean("settings.longline"); // Are we in longline?
        this.board = board; // What is the current board?

        //Events
        Session.plugin.getServer().getPluginManager().registerEvents(new EIntergrate(this), Session.plugin); // Join event
        Session.plugin.getServer().getPluginManager().registerEvents(new EDeintergrate(this), Session.plugin); // Quit event

        // Setup title row
        List<String> lines = ConfigControl.get().gc("settings").getConfigurationSection(board + ".title").getStringList("liner"); // Get the lines
        int interval = ConfigControl.get().gc("settings").getInt(board + ".title.interval"); // Get the intervals
        title = new Row((ArrayList<String>) lines, interval); // Create the title row!

        for(int i = 1; i<200; i++) // Loop over all lines
        {
            ConfigurationSection section = ConfigControl.get().gc("settings").getConfigurationSection(board + ".rows." + i); // Get their rows
            if(null != section) // Is the section null?
            {
                Row row = new Row((ArrayList<String>)section.getStringList("liner"), section.getInt("interval")); // Create a new row
                rows.add(row); // Add this line to the row list
            }
        }

        // Register already joined players
        if(board == "board") for(Player player : Session.plugin.getServer().getOnlinePlayers()) new ScoreboardHolder(this, player);

    }

    /**
     * Get all the rows
     * @return
     */
    public ArrayList<Row> getRows()
    {
        return rows;
    }

    /**
     * Ge the title
      * @return
     */
    public Row getTitle()
    {
        return title;
    }

    /**
     * Register a scoreboardholder
     * @param holder
     */
    public void registerHolder(ScoreboardHolder holder)
    {
        holders.add(holder);
    }

    /**
     * Unregister a holder
     * @param holder
     */
    public void unregisterHolder(ScoreboardHolder holder)
    {
        holders.remove(holder);
    }

    /**
     * Unregister a holder via player
     * @param player
     */
    public void unregisterHolder(Player player)
    {
        for(ScoreboardHolder holder : holders)
            if(holder.player == player)
            {
                holders.remove(holder);
                break;
            }
    }

    @Override
    public void run() {
        // Update rows
        title.update();
        for(Row row : rows)
            row.update();


        // Update scoreboards
        for(ScoreboardHolder holder : holders)
            holder.update();
    }
}
