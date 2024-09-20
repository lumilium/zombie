package com.iseong.zombie.tabComplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class team  implements TabCompleter {

    static List<String> arguments = new ArrayList<>();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            return length1();
        } else if (strings.length == 2) {
            return length1();
        }
        return null;
    }

    private @Nullable List<String> length1() {
        if (arguments.isEmpty()) {
            arguments.add("help");
            arguments.add("invite");
            arguments.add("create");
            arguments.add("remove");
        }
        return arguments;
    }

//    private @Nullable List<String> length2() {
//        if (arguments.isEmpty()) {
//            arguments.add("invite");
//            arguments.add("create");
//            arguments.add("remove");
//        }
//        return arguments;
//    }
}
