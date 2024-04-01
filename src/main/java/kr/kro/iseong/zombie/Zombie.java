package kr.kro.iseong.zombie;

import kr.kro.iseong.zombie.listener.events;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Zombie extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Plugin Enabled");
        Bukkit.getPluginManager().registerEvents(new events(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("zombie")).setTabCompleter(new tabComplete());
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            provider.getProvider();
        }
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin Disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        if (label.equalsIgnoreCase("zombie")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");

                } else if (args[0].equalsIgnoreCase("disable")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return false;
                }
            } else {
                p.sendMessage("Try using '/help zombie'.");
            }
        }
        return true;
    }
}
