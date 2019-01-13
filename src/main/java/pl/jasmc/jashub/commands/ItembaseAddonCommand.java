package pl.jasmc.jashub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.objects.MetaStorage;

public class ItembaseAddonCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("ib")) {
            if(commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if(p.hasPermission("jasmc.group.minidev")) {
                    if(args.length == 1 && args[0].equalsIgnoreCase("upload")) {

                    } else if(args.length == 1 && args[0].equalsIgnoreCase("monety")) {
                        p.sendMessage("Stan konta to: " + MetaStorage.getPlayerMeta(p.getName()).getCoins());
                    }
                } else {
                    //SEND NO PERM
                }
            }

        }
        return false;
    }
}
