package rien.bijl.Scoreboard.r;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rien.bijl.Scoreboard.r.util.ConfigControl;
import rien.bijl.Scoreboard.r.util.Func;

/**
 * Created by Rien on 23-10-2018.
 */
public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage("This is a player-only command!");
        } else {

            Player player = (Player) commandSender;

            if(args.length < 1)
            {
                Func.msg(player, "Too few arguments!");
                help(player);
            } else {
                 if(args[0].equalsIgnoreCase("reload")) {
                    if(Func.perm(player, "reload"))
                    {
                        Main.disolveBoards();
                        ConfigControl.get().reloadConfigs();
                        Main.loadBoards();
                        Func.smsg(player, "Scoreboard reloaded");
                    }
                }  else {
                    Func.msg(player,"Unknown command!");
                    help(player);
                }
            }
        }

        return false;
    }

    private void help(Player player)
    {
        Func.smsg(player, "/sb reload (Reload config and application)");
    }
}
