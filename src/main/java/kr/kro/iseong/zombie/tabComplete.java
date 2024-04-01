package kr.kro.iseong.zombie;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class tabComplete implements TabCompleter {

    static List<String> arguments = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return zombie();
    }

    public static List<String> zombie() {
        if (arguments.isEmpty()) {
            arguments.add("start");
            arguments.add("disable");
        }
        return arguments;
    }
}
