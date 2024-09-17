package com.iseong.zombie;

import com.iseong.zombie.data.dataManager;
import com.iseong.zombie.listener.events;
import com.iseong.zombie.util.itemUtil;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Zombie;

import java.util.Objects;
import java.util.Random;

public final class zombie extends JavaPlugin {

    private dataManager data;
    private final Random random = new Random();

    @Override
    public void onEnable() {
        this.data = new dataManager(this);
        FileConfiguration configData = this.data.getDataConfig();

        if (configData != null) {
            long sS = configData.getLong("summoningSpeed");
            Bukkit.getScheduler().runTaskTimer(this, this::spawnRandomCustomZombie, 0L, sS);
        }

        Bukkit.getPluginManager().registerEvents(new events(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("zombie")).setTabCompleter(new tabComplete());

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            provider.getProvider();
        }

        recipe();

//                .getString("development");;

    }

    @Override
    public void onDisable() {}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player) sender;
        User user = LuckPermsProvider.get().getPlayerAdapter(Player.class).getUser(p);
        if (label.equalsIgnoreCase("zombie")) {
            if (args.length == 1) {
                Location loc = p.getLocation();
                World world = Bukkit.getWorld("world");
                if (args[0].equalsIgnoreCase("start")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");

                } else if (args[0].equalsIgnoreCase("disable")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return false;
                } else if (args[0].equalsIgnoreCase("speed")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Zombie customZombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                    customZombie.setAdult();
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
                    customZombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 6));
                } else if (args[0].equalsIgnoreCase("boom")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Zombie customZombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                    customZombie.setAdult();
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
                    customZombie.addScoreboardTag("boom");
                } else if (args[0].equalsIgnoreCase("split")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Zombie customZombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                    customZombie.setAdult();
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
                    customZombie.addScoreboardTag("split");
                } else if (args[0].equalsIgnoreCase("slow")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Zombie customZombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                    customZombie.setAdult();
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
                    customZombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
                    customZombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
                    AttributeInstance healthAttribute = customZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                    assert healthAttribute != null;
                    healthAttribute.setBaseValue(100);
                    customZombie.setHealth(100);
                    customZombie.addScoreboardTag("slow");
                } else if (args[0].equalsIgnoreCase("vaccine")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    ItemStack vaccine = new ItemStack(Material.POTION);
                    ItemMeta meta = vaccine.getItemMeta();
                    PotionMeta potionMeta = (PotionMeta) meta;
                    potionMeta.setColor(Color.fromRGB(68, 147, 5));
                    meta.setDisplayName("vaccine");
//                    meta.setCustomModelData(1);
                    vaccine.setItemMeta(meta);
                    p.getInventory().addItem(vaccine);
                } else if (args[0].equalsIgnoreCase("invisible")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Zombie customZombie = (Zombie) world.spawnEntity(loc, EntityType.ZOMBIE);
                    customZombie.setAdult();
                    customZombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
                } else if (args[0].equalsIgnoreCase("materials")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    p.getInventory().addItem(new ItemStack(Material.GLASS, 6));
                    p.getInventory().addItem(new ItemStack(Material.PISTON));
                    p.getInventory().addItem(new ItemStack(Material.IRON_BLOCK));
                    p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    p.getInventory().addItem(new ItemStack(Material.ROTTEN_FLESH));
                    p.getInventory().addItem(new ItemStack(Material.ZOMBIE_HEAD));
                    p.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
                } else if (args[0].equalsIgnoreCase("summon")) {

//                    Random random = new Random();
//                    World world = getServer().getWorld("world");
////                    int y = random.nextInt(383) - 63;
//                    int x = random.nextInt(45) - 22;
//                    int z = random.nextInt(45) - 22;
//                    int y = world.getHighestBlockYAt(x, z);
//                    Location loc = new Location(world, x, y, z);
//                    Random random = new Random();
//                    World world = getServer().getWorld("world");
//                    boolean isAir = false;
//                    while (!isAir) {
//                        int y = random.nextInt(383) - 63;
//                        int x = random.nextInt(45) - 22;
//                        int z = random.nextInt(45) - 22;
//                        Location loc = new Location(world, x, y, z);
//                        boolean isBlock = BlockPosition.BLOCK_ZERO.isBlock();
////                        if () {
////
////                        }
//                    }
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Usages: /zombie help");
            }
        }
        return true;
    }

    private void recipe() {
        getServer().addRecipe(itemUtil.vaccineBottle());
        getServer().addRecipe(itemUtil.syringe());
        getServer().addRecipe(itemUtil.vaccine());
        getServer().addRecipe(itemUtil.fakeVaccine());
    }

    private void spawnRandomCustomZombie() {
        int type = random.nextInt(5);
        FileConfiguration configData = this.data.getDataConfig();
        Location randomLocation = getRandomLocation();
        World world = Bukkit.getWorld("world");
        if (type == 0 && configData.getBoolean("types.speed")) {
            Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);
            customZombie.setCustomName("speed");
            customZombie.setAdult();
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
            customZombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 6));
        } else if (type == 1 && configData.getBoolean("types.boom")) {
            Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);
            customZombie.setCustomName("boom");
            customZombie.setAdult();
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
            customZombie.addScoreboardTag("boom");
        } else if (type == 2 && configData.getBoolean("types.split")) {
            Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);
            customZombie.setCustomName("split");
            customZombie.setAdult();
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
            customZombie.addScoreboardTag("split");
        } else if (type == 3 && configData.getBoolean("types.slow")) {
            Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);
            customZombie.setCustomName("slow");
            customZombie.setAdult();
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
            customZombie.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
            customZombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
            AttributeInstance healthAttribute = customZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert healthAttribute != null;
            healthAttribute.setBaseValue(100);
            customZombie.setHealth(100);
            customZombie.addScoreboardTag("slow");
        } else if (configData.getBoolean("types.invisible")) {
            Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);

            customZombie.setCustomName("invisible");
            customZombie.setAdult();
            customZombie.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(((LivingEntity) customZombie).getEquipment()).setHelmet(helmet);
        } else {
            Bukkit.getPluginManager().disablePlugin(this);
        }
//        customZombie.setCustomName("Custom Zombie");
//        customZombie.setCustomNameVisible(true);
//        customZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10000);
//        customZombie.setHealth(10.0);
//        customZombie.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON));

        // Additional customization (like potion effects, AI goals, etc.) can be done here
    }

    private Location getRandomLocation() {
        World world = Bukkit.getWorld("world");
        if (world == null) return null;
        FileConfiguration configData = this.data.getDataConfig();
        int worldBorderSize = configData.getInt("worldBorderSize")-1;
        int x = random.nextInt(worldBorderSize) - worldBorderSize/2; // Random X coordinate within a range
        int z = random.nextInt(worldBorderSize) - worldBorderSize/2; // Random Z coordinate within a range
        int y = world.getHighestBlockYAt(x, z)+1; // Gets the highest Y at that X, Z to avoid underground spawns
        return new Location(world, x, y, z);
    }
}