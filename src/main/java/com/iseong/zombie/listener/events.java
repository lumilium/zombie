package com.iseong.zombie.listener;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class events implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.isCancelled();
        Player p = e.getPlayer();
        Location pLoc = p.getLocation();
        p.setRespawnLocation(pLoc, true);
        Entity PlayerZombie = e.getEntity().getWorld().spawnEntity(Objects.requireNonNull(pLoc), EntityType.ZOMBIE);
        p.setGameMode(GameMode.SPECTATOR);
        String name = p.getName();
        PlayerZombie.addScoreboardTag(name);
        LivingEntity livingEntity = (LivingEntity) PlayerZombie;
        livingEntity.setCustomName(name);
        livingEntity.setCustomNameVisible(true);
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        skull.setItemMeta(skullMeta);
        Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(skull);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();

        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            @NotNull Material item = p.getInventory().getItemInMainHand().getType();
            if (item == Material.DEBUG_STICK) {
                Entity target = p.getTargetEntity(5);
                for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                    if (Objects.requireNonNull(target).getType() == EntityType.ZOMBIE && target.getScoreboardTags().contains(players.getName())) {
                        String targetName = target.getName();
                        Player player = Bukkit.getPlayer(targetName);
                        Objects.requireNonNull(player).setGameMode(GameMode.SURVIVAL);
                        for (World worlds: Bukkit.getWorlds()) {
                            for (Entity entity : worlds.getEntities()) {
                                if (entity instanceof Zombie && entity.getCustomName() != null) {
                                    if (!targetName.equals(player.getName())) return;
                                    ((Zombie) entity).setHealth(0);
                                    player.setGameMode(GameMode.SURVIVAL);
                                    player.teleport(target.getLocation());
                                    p.getInventory().removeItem(new ItemStack(Material.DEBUG_STICK, 1));
                                } else {
                                    System.out.println("커스텀 이름이 없습니다.");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        Set<String> tags = entity.getScoreboardTags();
        Location loc = entity.getLocation();
        if (!tags.isEmpty()) {
            if (entity.getType() == EntityType.ZOMBIE) {
                if (tags.contains("boom")) {
                    Entity tnt = e.getEntity().getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
                    TNTPrimed primed = (TNTPrimed) tnt;
                    primed.setFuseTicks(0);
                } else if (tags.contains("split")) {
                    for (int i = 0; i < 4; i++) {
                        Entity split1 = e.getEntity().getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                        split1.addScoreboardTag("sec");
                        Objects.requireNonNull(((LivingEntity) split1).getEquipment()).setHelmet(new ItemStack(Material.STONE_BUTTON));
                    }
                } else if (tags.contains("sec")) {
                    for (int i = 0; i < 4; i++) {
                        Entity split1 = e.getEntity().getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                        Objects.requireNonNull(((LivingEntity) split1).getEquipment()).setHelmet(new ItemStack(Material.STONE_BUTTON));
                    }
                }
            }
        }
    }
}
