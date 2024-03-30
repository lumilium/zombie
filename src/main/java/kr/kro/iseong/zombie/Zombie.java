package kr.kro.iseong.zombie;

import kr.kro.iseong.zombie.listener.events;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Zombie extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[zombie] plugin Enabled");
        Bukkit.getPluginManager().registerEvents(new events(), this);
    }

    @Override
    public void onDisable() {
        System.out.println("[zombie] plugin Disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("zombie")) {
            p.sendMessage("test");
        } else if (label.equalsIgnoreCase("spawn")) {
            Location loc = p.getLocation();
            p.setRespawnLocation(loc);
        }
        return true;
    }
}
