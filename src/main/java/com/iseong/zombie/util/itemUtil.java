package com.iseong.zombie.util;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class itemUtil {

    public static ShapedRecipe vaccineBottle() {
//        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemStack disc = new ItemStack(Material.MUSIC_DISC_BLOCKS, 1);
        ItemMeta vaccineMeta = disc.getItemMeta();
//        PotionMeta potionMeta = (PotionMeta) vaccineMeta;
//        potionMeta.setColor(Color.fromRGB(68, 147, 5));
        vaccineMeta.setDisplayName("bottle containing vaccine");
        vaccineMeta.setCustomModelData(1);
        disc.setItemMeta(vaccineMeta);
        ShapedRecipe vaccineBottle = new ShapedRecipe(disc);
        vaccineBottle.shape(
                "   ",
                "KZG",
                " B "
        );
        vaccineBottle.setIngredient('Z', Material.ZOMBIE_HEAD);
        vaccineBottle.setIngredient('K', Material.ROTTEN_FLESH);
        vaccineBottle.setIngredient('G', Material.GOLDEN_APPLE);
        vaccineBottle.setIngredient('B', Material.GLASS_BOTTLE);
        return vaccineBottle;
    }

    public static ShapedRecipe syringe() {
        ItemStack netheriteScrap = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta syringeMeta = netheriteScrap.getItemMeta();
        syringeMeta.setDisplayName("syringe");
        netheriteScrap.setItemMeta(syringeMeta);
        ShapedRecipe syringe = new ShapedRecipe(netheriteScrap);
        syringe.shape(
                "GPG",
                "G G",
                "GIG"
        );
        syringe.setIngredient('G', Material.GLASS);
        syringe.setIngredient('P', Material.PISTON);
        syringe.setIngredient('I', Material.IRON_BLOCK);
        return syringe;
    }

    public static ShapedRecipe vaccine() {
        ItemStack potion = new ItemStack(Material.POTION, 1);
        ItemMeta vaccineMeta = potion.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) vaccineMeta;
        potionMeta.setColor(Color.fromRGB(68, 147, 5));
        vaccineMeta.setDisplayName("vaccine");
        vaccineMeta.setCustomModelData(1);
        potion.setItemMeta(vaccineMeta);
        ShapedRecipe vaccine = new ShapedRecipe(potion);
        vaccine.shape(
                "S",
                "B"
        );
        vaccine.setIngredient('S', Material.NETHERITE_SCRAP);
        vaccine.setIngredient('B', Material.MUSIC_DISC_BLOCKS);
        return vaccine;
    }

    public static ShapedRecipe fakeVaccine() {
        ItemStack fakeVaccine = new ItemStack(Material.MUSIC_DISC_FAR);
        ItemMeta vaccineMeta = fakeVaccine.getItemMeta();
        vaccineMeta.setDisplayName("vaccine");
        vaccineMeta.setCustomModelData(1);
        fakeVaccine.setItemMeta(vaccineMeta);
        ShapedRecipe vaccine = new ShapedRecipe(fakeVaccine);
        vaccine.shape(
                "S",
                "B"
        );
        vaccine.setIngredient('S', Material.MUSIC_DISC_CHIRP);
        vaccine.setIngredient('B', Material.MUSIC_DISC_BLOCKS);
        return vaccine;
    }

    public static ItemStack usedSyringe() {
        ItemStack syringe = new ItemStack(Material.MUSIC_DISC_CHIRP);
        ItemMeta syringeMeta = syringe.getItemMeta();
        syringeMeta.setCustomModelData(1);
        syringeMeta.setDisplayName("used syringe");
        syringe.setItemMeta(syringeMeta);
        return syringe;
    }
}
