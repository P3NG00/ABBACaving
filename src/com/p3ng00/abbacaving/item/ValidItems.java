package com.p3ng00.abbacaving.item;

import org.bukkit.Material;
import org.bukkit.entity.Item;

import java.util.ArrayList;

public class ValidItems {

    private final ArrayList<ValidItem> VALID_ITEMS;

    public ValidItems() {
        VALID_ITEMS = new ArrayList<>();
        VALID_ITEMS.add(new ValidItem("Iron Ore", 1, Material.IRON_ORE));
        VALID_ITEMS.add(new ValidItem("Gold Ore", 2, Material.GOLD_ORE));
        VALID_ITEMS.add(new ValidItem("Lapis Ore", 2, Material.LAPIS_ORE));
        VALID_ITEMS.add(new ValidItem("Redstone Ore", 3, Material.REDSTONE_ORE));
        VALID_ITEMS.add(new ValidItem("Diamond Ore", 5, Material.DIAMOND_ORE));
        VALID_ITEMS.add(new ValidItem("Emerald Ore", 5, Material.EMERALD_ORE));
        VALID_ITEMS.add(new ValidItem("Name Tag", 3, Material.NAME_TAG));
        VALID_ITEMS.add(new ValidItem("Music Disc", 2, Material.MUSIC_DISC_13, Material.MUSIC_DISC_CAT, Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CHIRP, Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_STAL, Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_11, Material.MUSIC_DISC_WAIT));
        VALID_ITEMS.add(new ValidItem("Golden Apple", 6, Material.GOLDEN_APPLE));
        VALID_ITEMS.add(new ValidItem("God Apple", 20, Material.ENCHANTED_GOLDEN_APPLE));
        VALID_ITEMS.add(new ValidItem("Spawner", 20, Material.SPAWNER));
    }

    public ArrayList<ValidItem> getValidItems() {
        return VALID_ITEMS;
    }
}
