package fr.theome.bingo.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemCreator {

    private ItemStack item;
    private int slot;
    private ArrayList<Pattern> patterns;
    public ItemCreator(final Material material, final int byteID) {
        item = new ItemStack(material, 1, (byte) byteID);
    }

    public ItemCreator(final ItemStack item) {
        setMaterial(item.getType());
        setAmount(item.getAmount());
        setDurability(item.getDurability());
        setName(item.getItemMeta().getDisplayName());
        setEnchantments(item.getItemMeta().getEnchants());
        setLores(item.getItemMeta().getLore());
    }

    public ItemCreator(final ItemCreator itemcreator) {
        this.item = itemcreator.getItem();
        this.slot = itemcreator.slot;
        this.patterns = itemcreator.patterns;
    }

    public ItemStack getItem() {
        return item;
    }

    public Material getMaterial() {
        return item.getType();
    }

    public ItemCreator setMaterial(final Material material) {
        if (item == null) {
            item = new ItemStack(material);
        } else {
            item.setType(material);
        }
        return this;
    }

    public ItemCreator setUnbreakable(final Boolean unbreakable) {
        final ItemMeta meta = item.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return this;
    }

    public Integer getAmount() {
        return item.getAmount();
    }

    public ItemCreator setAmount(final Integer amount) {
        item.setAmount(amount);
        return this;
    }

    public Short getDurability() {
        return item.getDurability();
    }

    public ItemCreator setDurability(final short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemCreator setDurability(final int durability) {
        final short shortdurability = ((short) durability);
        item.setDurability(shortdurability);
        return this;
    }

    public int getDurabilityInteger() {
        return item.getDurability();
    }

    public ItemMeta getMeta() {
        return item.getItemMeta();
    }

    public ItemCreator setMeta(final ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    public String getName() {
        return item.getItemMeta().getDisplayName();
    }

    public ItemCreator setName(final String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ArrayList<String> getLores() {
        return (ArrayList<String>) item.getItemMeta().getLore();
    }

    public ItemCreator setLores(final List<String> list) {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(list);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearLores() {
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(new ArrayList<>());
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator insertLores(final String lore, final Integer position) {
        final ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }
        lores.add(position, lore);
        meta.setLore(lores);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addLore(final String lore) {
        final ItemMeta meta = item.getItemMeta();
        ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores == null) {
            lores = new ArrayList<>();
        }
        if (lore != null) {
            lores.add(lore);
        } else {
            lores.add(" ");
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeLore(final String lore) {
        final ItemMeta meta = item.getItemMeta();
        final ArrayList<String> lores = (ArrayList<String>) meta.getLore();
        if (lores != null) {
            if (lores.contains(lore)) {
                lores.remove(lore);
                meta.setLore(lores);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public String[] getTableauLores() {
        if (item.getItemMeta().getLore() != null) {
            return item.getItemMeta().getLore().toArray(new String[0]);
        } else {
            return null;
        }
    }

    public ItemCreator setTableauLores(final String[] lores) {
        final ArrayList<String> tableaulores = new ArrayList<>(Arrays.asList(lores));
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(tableaulores);
        item.setItemMeta(meta);
        return this;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return new HashMap<>(item.getItemMeta().getEnchants());
    }

    public ItemCreator setEnchantments(final Map<Enchantment, Integer> map) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        for (final Map.Entry<Enchantment, Integer> e : map.entrySet()) {
            meta.addEnchant(e.getKey(), e.getValue(), true);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearEnchantments() {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addEnchantment(final Enchantment enchantment, final Integer lvl) {
        final ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, lvl, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeEnchantment(final Enchantment enchantment) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            if (meta.getEnchants().containsKey(enchantment)) {
                meta.removeEnchant(enchantment);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public ItemCreator setTableauEnchantments(final Enchantment[] enchantments, final Integer[] enchantmentslvl) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getEnchants() != null) {
            final ArrayList<Enchantment> cloneenchantments = new ArrayList<>(meta.getEnchants().keySet());
            for (final Enchantment enchantment : cloneenchantments) {
                meta.removeEnchant(enchantment);
            }
        }
        for (int i = 0; i < enchantments.length && i < enchantmentslvl.length; i++) {
            meta.addEnchant(enchantments[i], enchantmentslvl[i], true);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ArrayList<ItemFlag> getItemFlags() {
        final ArrayList<ItemFlag> itemflags = new ArrayList<>();
        if (item.getItemMeta().getItemFlags() != null) {
            itemflags.addAll(item.getItemMeta().getItemFlags());
        }
        return itemflags;
    }

    public ItemCreator setItemFlags(final ArrayList<ItemFlag> itemflags) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<>(meta.getItemFlags());
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
        }
        for (final ItemFlag itemflag : itemflags) {
            meta.addItemFlags(itemflag);
        }
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator clearItemFlags() {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            final ArrayList<ItemFlag> cloneitemflags = new ArrayList<>(meta.getItemFlags());
            for (final ItemFlag itemflag : cloneitemflags) {
                meta.removeItemFlags(itemflag);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(itemflag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator removeItemFlags(final ItemFlag itemflag) {
        final ItemMeta meta = item.getItemMeta();
        if (meta.getItemFlags() != null) {
            if (meta.getItemFlags().contains(itemflag)) {
                meta.removeItemFlags(itemflag);
                item.setItemMeta(meta);
            }
        }
        return this;
    }

    public SkullMeta getSkullMeta() {
        if (item.getType().equals(Material.SKULL_ITEM)) {
            return (SkullMeta) item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setSkullMeta(final SkullMeta skullmeta) {
        if (item.getType().equals(Material.SKULL_ITEM)) {
            item.setItemMeta(skullmeta);
        }
        return this;
    }

    public BannerMeta getBannerMeta() {
        if (item.getType().equals(Material.BANNER)) {
            return (BannerMeta) item.getItemMeta();
        }
        return null;
    }

    public ItemCreator setBannerMeta(final BannerMeta bannermeta) {
        if (item.getType().equals(Material.BANNER)) {
            item.setItemMeta(bannermeta);
        }
        return this;
    }

    public DyeColor getBasecolor() {
        if (item.getType().equals(Material.BANNER)) {
            return ((BannerMeta) item.getItemMeta()).getBaseColor();
        }
        return null;
    }

    public ItemCreator setBasecolor(final DyeColor basecolor) {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.setBaseColor(basecolor);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator setColor(final Color basecolor) {
        if (item.getType().equals(Material.LEATHER_BOOTS) || item.getType().equals(Material.LEATHER_CHESTPLATE) || item.getType().equals(Material.LEATHER_HELMET) || item.getType().equals(Material.LEATHER_LEGGINGS)) {
            final LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(basecolor);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ArrayList<Pattern> getPatterns() {
        if (item.getType().equals(Material.BANNER)) {
            return (ArrayList<Pattern>) ((BannerMeta) item.getItemMeta()).getPatterns();
        }
        return null;
    }

    public ItemCreator setPatterns(final ArrayList<Pattern> petterns) {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.setPatterns(petterns);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator clearPatterns() {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.setPatterns(new ArrayList<>());
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addPattern(final Pattern pattern) {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            meta.addPattern(pattern);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator removePattern(final Pattern pattern) {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            final ArrayList<Pattern> patterns = (ArrayList<Pattern>) meta.getPatterns();
            if (patterns != null) {
                if (patterns.contains(pattern)) {
                    patterns.remove(pattern);
                    meta.setPatterns(patterns);
                    item.setItemMeta(meta);
                }
            }
        }
        return this;
    }

    public ItemCreator setTableauPatterns(final Pattern[] patterns) {
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            if (meta.getPatterns() != null) {
                meta.setPatterns(new ArrayList<>());
            }
            for (final Pattern pattern : patterns) {
                meta.addPattern(pattern);
            }
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemCreator addallItemsflags() {
        final ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCreator addBannerPreset(final int ID, final DyeColor patterncolor) {
        switch (ID) {
            case 1:
                addBannerPreset(BannerPreset.barre, patterncolor);
                break;
            case 2:
                addBannerPreset(BannerPreset.precedent, patterncolor);
                break;
            case 3:
                addBannerPreset(BannerPreset.suivant, patterncolor);
                break;
            case 4:
                addBannerPreset(BannerPreset.coeur, patterncolor);
                break;
            case 5:
                addBannerPreset(BannerPreset.cercleEtoile, patterncolor);
                break;
            case 6:
                addBannerPreset(BannerPreset.croix, patterncolor);
                break;
            case 7:
                addBannerPreset(BannerPreset.yinYang, patterncolor);
                break;
            case 8:
                addBannerPreset(BannerPreset.losange, patterncolor);
                break;
            case 9:
                addBannerPreset(BannerPreset.moin, patterncolor);
                break;
            case 10:
                addBannerPreset(BannerPreset.plus, patterncolor);
                break;
            default:
                break;
        }
        return this;
    }

    public ItemCreator addBannerPreset(final BannerPreset type, final DyeColor patterncolor) {
        if (type == null)
            return this;
        if (item.getType().equals(Material.BANNER)) {
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            final DyeColor basecolor = meta.getBaseColor();
            switch (type) {
                // rien, barre, precedent, suivant, Coeur, cercleEtoile, croix,
                // yinYang, Losange, Moin, Plus;
                case barre:
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_DOWNRIGHT), true);
                    break;
                case precedent:
                    // precedent
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), true);
                    break;
                case suivant:
                    // suivant
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_BOTTOM_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_LEFT), true);
                    break;
                case coeur:
                    // Coeur
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_TOP), true);
                    break;
                case cercleEtoile:
                    // cercleEtoile
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.FLOWER), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.CIRCLE_MIDDLE), true);
                    break;
                case croix:
                    // croix
                    addasyncronePattern(new Pattern(patterncolor, PatternType.CROSS), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.CURLY_BORDER), true);
                    break;
                case yinYang:
                    // yinYang
                    addasyncronePattern(new Pattern(patterncolor, PatternType.SQUARE_BOTTOM_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.DIAGONAL_LEFT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.SQUARE_TOP_LEFT), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.TRIANGLE_TOP), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_RIGHT), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.TRIANGLE_BOTTOM), false);
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_LEFT), true);
                    break;
                case losange:
                    // Losange
                    addasyncronePattern(new Pattern(patterncolor, PatternType.RHOMBUS_MIDDLE), true);
                    break;
                case moin:
                    // Moin
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRIPE_MIDDLE), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
                    break;
                case plus:
                    // Plus
                    addasyncronePattern(new Pattern(patterncolor, PatternType.STRAIGHT_CROSS), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_TOP), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.STRIPE_BOTTOM), false);
                    addasyncronePattern(new Pattern(basecolor, PatternType.BORDER), true);
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    private void addasyncronePattern(final Pattern pattern, final Boolean calcul) {
        if (calcul) {
            patterns.add(pattern);
            final BannerMeta meta = (BannerMeta) item.getItemMeta();
            for (final Pattern currentpattern : patterns) {
                meta.addPattern(currentpattern);
            }
            patterns.clear();
            item.setItemMeta(meta);

        } else {
            if (patterns == null) {
                patterns = new ArrayList<>();
            }
            patterns.add(pattern);
        }
    }

    public enum BannerPreset {
        barre, precedent, suivant, coeur, cercleEtoile, croix, yinYang, losange, moin, plus
    }
}
