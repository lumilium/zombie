package com.iseong.zombie;

import com.iseong.zombie.listener.events;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Zombie extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new events(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("zombie")).setTabCompleter(new tabComplete());
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            provider.getProvider();
        }
    }

    @Override
    public void onDisable() {}



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
                } else if (args[0].equalsIgnoreCase("speed")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 6));
                } else if (args[0].equalsIgnoreCase("boom")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
                    livingEntity.addScoreboardTag("boom");
                } else if (args[0].equalsIgnoreCase("split")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
                    livingEntity.addScoreboardTag("split");
                } else if (args[0].equalsIgnoreCase("slow")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
                    AttributeInstance healthAttribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    assert healthAttribute != null;
                    healthAttribute.setBaseValue(100);
                    livingEntity.setHealth(100);
                    entity.addScoreboardTag("slow");
                } else if (args[0].equalsIgnoreCase("vaccine")) {
                    ItemStack vaccine = new ItemStack(Material.POTION);
                    ItemMeta meta = vaccine.getItemMeta();
                    PotionMeta potionMeta = (PotionMeta) meta;
                    potionMeta.setColor(Color.fromRGB(68, 147, 5));
                    meta.setDisplayName("vaccine");
                    vaccine.setItemMeta(meta);
                    p.getInventory().addItem(vaccine);
                } else if (args[0].equalsIgnoreCase("invisible")) {
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                    Objects.requireNonNull(((LivingEntity) entity).getEquipment()).setHelmet(new ItemStack(Material.STONE_BUTTON));
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Usages: /zombie help");
            }
        }
        return true;
    }
}
