package com.iseong.zombie.listener;

import com.iseong.zombie.util.itemUtil;
import com.iseong.zombie.zombie;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class events implements Listener {

    private HashMap<UUID, Arrays[]> address = new HashMap<>();
    public HashMap<UUID, ItemStack[]> keepInv = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        World world = Bukkit.getWorld("world");
        Player p = e.getPlayer();
        Set<String> tag = p.getScoreboardTags();
        Location loc = p.getLocation();
        e.isCancelled();
        if (tag.contains("protect")) {
            p.playEffect(EntityEffect.TOTEM_RESURRECT);
            p.setRespawnLocation(loc, true);
            p.removeScoreboardTag("protect");
            return;
        }
        p.setRespawnLocation(loc, true);
        Zombie zombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
        zombie.setAdult();
        p.setGameMode(GameMode.SPECTATOR);
        String name = p.getName();
        zombie.addScoreboardTag(name);
        zombie.setCustomName(name);
        zombie.setCustomNameVisible(true);
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
        skull.setItemMeta(skullMeta);
        Objects.requireNonNull(((LivingEntity) zombie).getEquipment()).setHelmet(skull);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
//        ItemStack[] data = keepInv.get(e.getPlayer().getUniqueId());
        ItemStack[] data = (ItemStack[]) zombie.keepInvManager.getDataConfig().get("Users." + p.getUniqueId() + ".Items");
        Bukkit.broadcastMessage(Arrays.toString(Objects.requireNonNull(data)));
        inv.setStorageContents(data);
//        keepInv.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInvUpdate(PlayerInventorySlotChangeEvent e) {
        Player p = e.getPlayer();
        Set<String> tag = p.getScoreboardTags();
        PlayerInventory inv = p.getInventory();
        if (tag.contains("protect")) {
//            keepInv.put(p.getUniqueId(), inv.getStorageContents());
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            @NotNull Material item = p.getInventory().getItemInMainHand().getType();
            if (item == Material.POTION) {
                String meta = Objects.requireNonNull(Objects.requireNonNull(e.getItem()).getItemMeta().displayName()).toString();
                if (meta.contains("vaccine")) {
                    Entity target = p.getTargetEntity(5);
                    for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                        if (Objects.requireNonNull(target).getType() == EntityType.ZOMBIE && target.getScoreboardTags().contains(players.getName())) {
                            String targetName = target.getName();
                            if (!players.getName().contains(targetName)) break;
                            Player player = Bukkit.getPlayer(targetName);
                            if (!targetName.equals(Objects.requireNonNull(player).getName())) break;
                            Objects.requireNonNull(player).setGameMode(GameMode.SURVIVAL);
                            World world = Bukkit.getWorld("world");
                            UUID uuid = target.getUniqueId();
                            Entity entity = world.getEntity(uuid);
                            if (entity instanceof Zombie && entity.getCustomName() != null) {
                                ((Zombie) entity).setHealth(0);
                                player.setGameMode(GameMode.SURVIVAL);
                                player.teleport(target.getLocation());
                                p.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
                                p.getInventory().addItem(itemUtil.usedSyringe());
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        World world = Bukkit.getWorld("world");
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
                        Zombie zombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                        zombie.setAdult();
                        zombie.addScoreboardTag("sec");
                        Objects.requireNonNull(((LivingEntity) zombie).getEquipment()).setHelmet(new ItemStack(Material.STONE_BUTTON));
                    }
                } else if (tags.contains("sec")) {
                    for (int i = 0; i < 4; i++) {
                        Zombie zombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                        zombie.setAdult();
                        Objects.requireNonNull(((LivingEntity) zombie).getEquipment()).setHelmet(new ItemStack(Material.STONE_BUTTON));
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
                    ItemStack[] nowStorageStatus = p.getInventory().getStorageContents();
                    keepInv.put(p.getUniqueId(), nowStorageStatus);
                    FileConfiguration keepInvData = zombie.keepInvManager.getDataConfig();
                    keepInvData.set("Users." + p.getUniqueId() + ".Items", nowStorageStatus);
                    zombie.keepInvManager.saveConfig();
                } else {
                    p.sendMessage("관리자에게 문의하십시오.");
                }
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        Material type = e.getRecipe().getResult().getType();
        if (type == Material.MUSIC_DISC_FAR) {
//            Bukkit.getPluginManager().getPlugin("BanManger");
//            String reason = "123";
//            String command = "tempban " + e.getWhoClicked().getName() + " 1800 " + reason;
            e.setCancelled(true);
            e.getWhoClicked().sendMessage("A syringe once used cannot be reused.");
//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }
}
