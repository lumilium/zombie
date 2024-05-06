package com.iseong.zombie.listener;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

public class events implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        Set<String> tag = p.getScoreboardTags();
        Location pLoc = p.getLocation();
        if (tag.contains("protect")) {
            e.isCancelled();
            p.playEffect(EntityEffect.TOTEM_RESURRECT);
            p.setRespawnLocation(pLoc, true);
            p.removeScoreboardTag("protect");
            return;
        }
        e.isCancelled();
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
            int slot = p.getInventory().getHeldItemSlot();
            if (item == Material.POTION) {
                String meta = Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta().displayName()).toString();
                if (meta.contains("vaccine")) {
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
                                        p.getInventory().setItem(slot, new ItemStack(Material.GLASS_BOTTLE));
                                    }
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

    @EventHandler
    public void onDrinkPotion(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (e.getItem().hasItemMeta()) {
            if (e.getItem().getItemMeta() instanceof PotionMeta) {
                String meta = Objects.requireNonNull(e.getItem().getItemMeta().displayName()).toString();
                if (Objects.requireNonNull(meta).contains("vaccine")) {
                    p.addScoreboardTag("protect");
                } else {
                    p.sendMessage("관리자에게 문의하십시오.");
                }
            }
        }
    }
}
