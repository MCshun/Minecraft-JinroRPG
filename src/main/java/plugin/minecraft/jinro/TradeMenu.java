package plugin.minecraft.jinro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class TradeMenu {
    private static MerchantRecipe JinroAxe() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "人狼専用の斧。プレイヤーを一撃で倒せる。");
        ItemStack result = new ItemStack(Material.STONE_AXE, 1, (short) 130);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "人狼の斧");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 3));
        return recipe;
    }

    private static MerchantRecipe AccompliceEye() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "共犯者専用のアイテム。");
        lore.add(ChatColor.AQUA + "Qキーで投げると人狼が一人わかる。");
        lore.add(ChatColor.AQUA + "共犯者しか買えないため、見られるとばれるが、");
        lore.add(ChatColor.AQUA + "人狼にアピールするためにも使える。");
        ItemStack result = new ItemStack(Material.END_CRYSTAL);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "共犯者の目");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }

    private static MerchantRecipe Bow() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "当てた相手を一撃で倒せる。");
        ItemStack result = new ItemStack(Material.BOW, 1, (short) 384);
        ItemMeta meta = result.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 32767, true);
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 2));
        return recipe;
    }

    private static MerchantRecipe Arrow() {
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.ARROW), 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 2));
        return recipe;
    }

    private static MerchantRecipe Stake() {
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.COOKED_BEEF, 5), 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 1));
        return recipe;
    }

    private static MerchantRecipe Sword() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "スケルトンをフルチャージ攻撃でワンパン出来る剣。");
        lore.add(ChatColor.AQUA + "30回くらい使えると思う。");
        ItemStack result = new ItemStack(Material.WOOD_SWORD, 1, (short) 29);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET +"スケ狩りの剣(30回くらい使えると思う)");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 4));
        return recipe;
    }

    private static MerchantRecipe InvPot() {
        ItemStack result = new ItemStack(Material.POTION, 1, (short) 29);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 1, true), true);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 4));
        return recipe;
    }

    private static MerchantRecipe SpdPot() {
        ItemStack result = new ItemStack(Material.POTION, 1, (short) 29);
        PotionMeta meta = (PotionMeta) result.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1, true), true);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 4));
        return recipe;
    }

    private static MerchantRecipe fortunetellersheart() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人の役職が分かる。");
        lore.add(ChatColor.AQUA + "共犯者は村人と出る。");
        ItemStack result = new ItemStack(Material.STICK, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "占い師の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }

    private static MerchantRecipe Mediumsheart() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人が死んでいるかどうかが分かる。");
        ItemStack result = new ItemStack(Material.SKULL_ITEM, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "霊媒師の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }

    private static MerchantRecipe superfortunetellersheart() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
        lore.add(ChatColor.AQUA + "その人の役職が分かる。");
        lore.add(ChatColor.AQUA + "かつ全員に通知される。");
        lore.add(ChatColor.AQUA + "昼でも夜でも使える。");
        lore.add(ChatColor.AQUA + "共犯者は村人と出る。");
        ItemStack result = new ItemStack(Material.QUARTZ, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "大魔道士の心");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 32));
        return recipe;
    }

    private static MerchantRecipe Stungrenade() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "当てた相手は五秒間動けなくなる。");
        ItemStack result = new ItemStack(Material.SNOW_BALL, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "スタングレネード");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 2));
        return recipe;
    }

    private static MerchantRecipe holycross() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "吸血鬼を殴ると一撃で倒せる。");
        lore.add(ChatColor.AQUA + "一度使うと消滅する。");
        ItemStack result = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "聖なる十字架");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 2));
        return recipe;
    }

    private static MerchantRecipe providenceeyes() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Qキーで投げると効果発動。");
        lore.add(ChatColor.AQUA + "自分以外の全員を光らせることができる。");
        ItemStack result = new ItemStack(Material.DOUBLE_PLANT, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "プロビデンスの眼光");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 2));
        return recipe;
    }

    private static MerchantRecipe Knights() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Qキーで投げると効果発動。");
        lore.add(ChatColor.AQUA + "使用した夜、すべての攻撃を無効化できる。人狼使用不可。");
        ItemStack result = new ItemStack(Material.ARMOR_STAND, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "騎士の祈り");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 4));
        return recipe;
    }

    private static MerchantRecipe Spell() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Qキーで投げると効果発動。");
        lore.add(ChatColor.AQUA + "使用した夜、いつ、誰に占われたかわかる。");
        lore.add(ChatColor.AQUA + "一回占われると効果が切れる。");
        ItemStack result = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "天恵の呪符");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 3));
        return recipe;
    }

    private static MerchantRecipe onlineShop() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "このアイテムを手に持った状態で");
        lore.add(ChatColor.AQUA + "/shopコマンドを使うと");
        lore.add(ChatColor.AQUA + "SHOPのGUIが開ける。");
        lore.add(ChatColor.AQUA + "1度使うと消える。");
        ItemStack result = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "オンラインショップ");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }

    private static MerchantRecipe Mine() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "人狼陣営専用アイテム");
        lore.add(ChatColor.AQUA + "人狼陣営以外が拾うと爆死する。");
        ItemStack result = new ItemStack(Material.BEACON, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "地雷");
        meta.setLore(lore);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }

    private static MerchantRecipe RobberySword() {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "強盗専用アイテム");
        lore.add(ChatColor.AQUA + "一回だけ殴った相手の役職を盗める。");
        lore.add(ChatColor.AQUA + "盗んだ相手は死ぬ。");
        ItemStack result = new ItemStack(Material.GOLD_SWORD, 1);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "強盗の剣");
        meta.setLore(lore);
        meta.setUnbreakable(true);
        result.setItemMeta(meta);
        MerchantRecipe recipe = new MerchantRecipe(result, 64);
        recipe.addIngredient(new ItemStack(Material.EMERALD, 5));
        return recipe;
    }


    public static void openGUI(Player player) {
        Merchant merchant = Bukkit.createMerchant(ChatColor.RESET + "店員");
        List<MerchantRecipe> recipes = new ArrayList<>();
        recipes.add(Bow());
        recipes.add(Arrow());
        recipes.add(Stake());
        recipes.add(Sword());
        recipes.add(InvPot());
        recipes.add(SpdPot());
        recipes.add(fortunetellersheart());
        recipes.add(superfortunetellersheart());
        recipes.add(Mediumsheart());
        recipes.add(Stungrenade());
        recipes.add(holycross());
        recipes.add(providenceeyes());
        recipes.add(Knights());
        recipes.add(Spell());
        recipes.add(onlineShop());
        if(Main.isStarted()) {
            if (Main.isJinro(player)) {
                recipes.add(JinroAxe());
                recipes.add(Mine());
            }
            if (Main.isAccomplice(player)) {
                recipes.add(AccompliceEye());
                recipes.add(Mine());
            }
            if(Main.isRobbery(player) && !Main.isStolen) {
                recipes.add(RobberySword());
            }
        }
        merchant.setRecipes(recipes);
        player.openMerchant(merchant, true);
    }
}
