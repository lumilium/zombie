package kr.kro.iseong.zombie;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Zombie extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("[zombie] plugin Enabled");
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
        }
        return true;
    }
}
