package com.iseong.zombie;

import com.iseong.zombie.data.dataManager;
import com.iseong.zombie.listener.events;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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

    public dataManager data;
    private final Random random = new Random();

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(this, this::spawnRandomCustomZombie, 0L, 1L);
        Bukkit.getPluginManager().registerEvents(new events(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("zombie")).setTabCompleter(new tabComplete());
        this.data = new dataManager(this);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            provider.getProvider();
        }

        recipe();

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
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    ItemStack vaccine = new ItemStack(Material.POTION);
                    ItemMeta meta = vaccine.getItemMeta();
                    PotionMeta potionMeta = (PotionMeta) meta;
                    potionMeta.setColor(Color.fromRGB(68, 147, 5));
                    meta.setDisplayName("vaccine");
                    meta.setCustomModelData(1);
                    vaccine.setItemMeta(meta);
                    p.getInventory().addItem(vaccine);
                } else if (args[0].equalsIgnoreCase("invisible")) {
                    if (!user.getPrimaryGroup().equals("admin")) p.sendMessage("당신은 관리자가 아닙니다.");
                    Location loc = p.getLocation();
                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
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
                    Random random = new Random();
                    World world = getServer().getWorld("world");
//                    int y = random.nextInt(383) - 63;
                    int x = random.nextInt(45) - 22;
                    int z = random.nextInt(45) - 22;
                    int y = world.getHighestBlockYAt(x, z);
                    Location loc = new Location(world, x, y, z);
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


//                    Location loc = new Location(world, x, y, z);
//                    Entity entity = Objects.requireNonNull(p.getPlayer()).getWorld().spawnEntity(loc, EntityType.ZOMBIE);
//                    LivingEntity livingEntity = (LivingEntity) entity;
//                    ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
//                    Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
//                    livingEntity.addScoreboardTag("split");
//                    p.sendMessage(String.valueOf(loc));
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "Usages: /zombie help");
            }
        }
        return true;
    }

    private void recipe() {
        ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta vaccineMeta = bottle.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) vaccineMeta;
        potionMeta.setColor(Color.fromRGB(68, 147, 5));
        vaccineMeta.setDisplayName("vaccine");
        vaccineMeta.setCustomModelData(1);
        bottle.setItemMeta(vaccineMeta);
        ItemMeta syringeMeta = netheriteScrap.getItemMeta();
        syringeMeta.setDisplayName("syringe");
        netheriteScrap.setItemMeta(syringeMeta);
        ShapedRecipe vaccine = new ShapedRecipe(bottle);
        ShapedRecipe syringe = new ShapedRecipe(netheriteScrap);
        syringe.shape(
                "GPG",
                "G G",
                "GIG"
        );

//        vaccine.shape(
//                " Z ",
//                "KTG",
//                " N "
//        );

        vaccine.shape(
                " Z ",
                "KNG",
                " B "
        );

        syringe.setIngredient('G', Material.GLASS);
        syringe.setIngredient('P', Material.PISTON);
        syringe.setIngredient('I', Material.IRON_BLOCK);

        vaccine.setIngredient('Z', Material.ZOMBIE_HEAD);
        vaccine.setIngredient('K', Material.ROTTEN_FLESH);
//        vaccine.setIngredient('T', Material.TOTEM_OF_UNDYING);
        vaccine.setIngredient('G', Material.GOLDEN_APPLE);
        vaccine.setIngredient('N', Material.NETHERITE_SCRAP);
        vaccine.setIngredient('B', Material.GLASS_BOTTLE);

        getServer().addRecipe(vaccine);
        getServer().addRecipe(syringe);
    }

    private void spawnRandomCustomZombie() {
        int type = random.nextInt(5);
        World world = Bukkit.getWorld("world"); // Replace with your world name
        if (world == null) return;
        Location randomLocation = getRandomLocation(world);

        Zombie customZombie = (Zombie) world.spawnEntity(randomLocation, EntityType.ZOMBIE);
        if (type == 0) {
            customZombie.setCustomName("speed");
            customZombie.setAdult();
            LivingEntity livingEntity = customZombie;
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 6));
        } else if (type == 1) {
            customZombie.setCustomName("boom");
            customZombie.setAdult();
            LivingEntity livingEntity = customZombie;
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
            livingEntity.addScoreboardTag("boom");
        } else if (type == 2) {
            customZombie.setCustomName("split");
            customZombie.setAdult();
            LivingEntity livingEntity = customZombie;
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
            livingEntity.addScoreboardTag("split");
        } else if (type == 3) {
            customZombie.setCustomName("slow");
            customZombie.setAdult();
            LivingEntity livingEntity = customZombie;
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
            AttributeInstance healthAttribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert healthAttribute != null;
            healthAttribute.setBaseValue(100);
            livingEntity.setHealth(100);
            customZombie.addScoreboardTag("slow");
        } else {
            customZombie.setCustomName("invisible");
            customZombie.setAdult();
            LivingEntity livingEntity = customZombie;
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
            ItemStack helmet = new ItemStack(Material.STONE_BUTTON);
            Objects.requireNonNull(livingEntity.getEquipment()).setHelmet(helmet);
        }
//        customZombie.setCustomName("Custom Zombie");
//        customZombie.setCustomNameVisible(true);
//        customZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10000);
//        customZombie.setHealth(10.0);
//        customZombie.getEquipment().setHelmet(new ItemStack(Material.STONE_BUTTON));

        // Additional customization (like potion effects, AI goals, etc.) can be done here
    }

    private Location getRandomLocation(World world) {
        int x = random.nextInt(91) - 45; // Random X coordinate within a range
        int z = random.nextInt(91) - 45; // Random Z coordinate within a range
        int y = world.getHighestBlockYAt(x, z); // Gets the highest Y at that X, Z to avoid underground spawns
        return new Location(world, x, y+1, z);
    }
}