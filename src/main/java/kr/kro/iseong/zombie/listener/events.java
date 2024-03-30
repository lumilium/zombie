package kr.kro.iseong.zombie.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

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
}
