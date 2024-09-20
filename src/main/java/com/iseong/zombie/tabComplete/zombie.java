package com.iseong.zombie.tabComplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zombie implements TabCompleter {

    static List<String> arguments = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return zombie();
        } else if (strings.length == 2) {
            return  team();
        }
        return null;
    }

    public static List<String> zombie() {
        if (arguments.isEmpty()) {
            arguments.add("start");
            arguments.add("disable");
            arguments.add("speed");
            arguments.add("boom");
            arguments.add("split");
            arguments.add("slow");
            arguments.add("vaccine");
            arguments.add("invisible");
            arguments.add("materials");
            arguments.add("summon");
        }
        return arguments;
    }

    public static List<String> team() {
        if (arguments.isEmpty()) {
            arguments.add("invite");
        }
        return arguments;
    }
}
