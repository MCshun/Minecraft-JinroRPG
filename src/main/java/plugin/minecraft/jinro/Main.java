package plugin.minecraft.jinro;

import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Main extends JavaPlugin {
    private static boolean isGameStarted = false;
    private static String jinroRPGMessage = ChatColor.RED + "[人狼RPG]" + ChatColor.GREEN + ": " + ChatColor.AQUA.toString();

    private static List<Player> joinedInGamePlayer = new ArrayList<>();
    private static List<Player> joinedPlayers = new ArrayList<>();
    private static List<ArmorStand> ArmorStands = new ArrayList<>();
    public static List<Player> Jinro = new ArrayList<>();
    public static List<Player> Innocent = new ArrayList<>();
    private static List<Player> Knights = new ArrayList<>();
    private static List<Player> Spell = new ArrayList<>();
    public static List<Sign> Signs = new ArrayList<>();
    private static Player Vampire;
    private static int listSize = 0;
    private static int sz = 0;
    private static int s = 0;
    private static Player Accomplice;
    private static Player Robbery;
    private static List<Player> DeathPlayerList = new ArrayList<>();
    public static boolean isStolen = false;
    public static boolean isVampireDead = false;
    public static boolean isDay = true;
    private static int TimeScheduler = 0;
    public static BossBar bar;
    public int day = 1;
    private static String StolenRole = "";
    public static Plugin plugin;
    public static Location StartPoint;


    @Override
    public void onEnable(){
        System.out.println("Plugin Enabled.");
        plugin = this;
        getServer().getPluginManager().registerEvents(new Events(), this);
        bar = Bukkit.createBossBar(ChatColor.RED + "人狼RPG", BarColor.GREEN, BarStyle.SOLID);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if(isStarted()) {
                if (!isDay) {
                    for(int c = 0;c <= s;c++) {
                        Collections.shuffle(ArmorStands);
                        ArmorStand armsta = ArmorStands.get(1);
                        Entity mobskeleton = armsta.getWorld().spawnEntity(armsta.getLocation(), EntityType.SKELETON);
                        LivingEntity skeleton = (LivingEntity) mobskeleton;
                        EntityEquipment equipment = skeleton.getEquipment();
                        equipment.clear();
                        equipment.getItemInMainHand().setType(Material.AIR);
                        skeleton.setHealth(2.0);
                    }
                    if(Knights.size() > 0) {
                        for(Player player:Knights) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 127, true), true);
                        }
                    }
                }
                bar.setProgress(bar.getProgress() - 0.005);
            }
        }, 0L, 10L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String command = cmd.getName();
        switch (command) {
            case "start":
                if (!(sender instanceof Player)) {
                    joinedInGamePlayer.addAll(Bukkit.getServer().getOnlinePlayers());
                    joinedPlayers.addAll(Bukkit.getServer().getOnlinePlayers());
                    if (isStarted()) {
                        end("");
                    }
                } else {
                    Player Sender = (Player) sender;
                    StartPoint = Sender.getLocation();
                    joinedInGamePlayer.addAll(Bukkit.getServer().getOnlinePlayers());
                    joinedPlayers.addAll(Bukkit.getServer().getOnlinePlayers());
                    if (isStarted()) {
                        end("");
                    }
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (!player.getName().equals(Sender.getName())) {
                            player.teleport((Entity) sender);
                        }
                    }
                }
                start(joinedInGamePlayer);
                return true;
            case "shopkeeper":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("このコマンドはゲーム内から実行してください。");
                } else {
                    Player player = (Player) sender;
                    ItemStack spawnegg = new ItemStack(Material.MONSTER_EGG);
                    SpawnEggMeta meta = (SpawnEggMeta) spawnegg.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "ShopKeeper");
                    meta.setSpawnedType(EntityType.VILLAGER);
                    spawnegg.setItemMeta(meta);
                    player.getInventory().addItem(spawnegg);
                }
                return true;
            case "gamesign":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("このコマンドはゲーム内から実行してください。");
                } else {
                    Player player = (Player) sender;
                    ItemStack signStack = new ItemStack(Material.SIGN);
                    ItemMeta itemMeta = signStack.getItemMeta();
                    if (itemMeta instanceof BlockStateMeta) {
                        BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
                        BlockState blockState = blockStateMeta.getBlockState();
                        if (blockState instanceof Sign) {
                            Sign sign = (Sign) blockState;
                            sign.setLine(1, "右クリックで役職記録");
                            sign.update();
                            blockStateMeta.setBlockState(sign);
                            signStack.setItemMeta(blockStateMeta);
                        }
                    }
                    player.getInventory().addItem(signStack);
                }
                return true;
            case "end":
                if (isStarted()) end("");
                return true;
            case "list":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("このコマンドはゲーム内から実行してください。");
                } else {
                    CheckPlayerGUI.OpenGUI((Player) sender);
                }
                return true;
            case "shop":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("このコマンドはゲーム内から実行してください。");
                } else {
                    Player player = (Player) sender;
                    if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "オンラインショップ")) {
                        TradeMenu.openGUI(player);
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
                        player.getInventory().removeItem(result);
                    } else {
                        player.sendMessage(getJinroMessage() + "オンラインショップを手にもって実行してください!");
                    }
                    return true;
                }
            case "banhammer":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("このコマンドはゲーム内から実行してください。");
                } else {
                    Player player = (Player) sender;
                    BanHammer.openBanGUI(player);
                }
                return true;

        }
        return false;
    }


    public static boolean isStarted() {
        return isGameStarted;
    }

    public static String getJinroMessage() {
        return jinroRPGMessage;
    }

    public static List<Player> getJoinedPlayer() {
        return joinedPlayers;
    }

    public static List<Player> getDeathPlayerList() {
        return DeathPlayerList;
    }

    public static boolean isUsingSpell(Player player) {
        if(Spell.size() == 0) { return false; }
        int s = 0;
        for(Player p:Spell) {
            if(p.getName().equals(player.getName())) {
                Spell.remove(s);
                return true;
            }
        }
        return false;
    }

    public static void addSpell(Player player) {
        Spell.add(player);
    }


    public void start(List<Player> list) {
        if(joinedInGamePlayer.size() <= 4) {
            for(Player player: Bukkit.getServer().getOnlinePlayers()) {
                player.sendMessage(getJinroMessage() + "人数が不足していたためスタートできませんでした");
            }
            return;
        }
        bar = Bukkit.createBossBar(ChatColor.RED + "人狼RPG", BarColor.GREEN, BarStyle.SOLID);
        bar.setColor(BarColor.YELLOW);
        bar.setTitle(ChatColor.YELLOW + "DAY TIME");
        bar.setProgress(1);
        listSize = list.size();
        for(World world:Bukkit.getServer().getWorlds()) {
            try {
                for (Entity entity : world.getEntitiesByClass(ArmorStand.class)) {
                    ArmorStand stand = (ArmorStand) entity;
                    ArmorStands.add(stand);
                    stand.setVisible(false);
                    stand.setSmall(true);
                    stand.setGravity(false);
                }
            } catch(Exception ignored) {}
        }
        double jinrosize = list.size();
        int jinrocount = (int)Math.ceil(jinrosize / 5);
        for (int count = 0; count < jinrocount; count++){
            Collections.shuffle(list);
            Jinro.add(list.get(count));
            list.remove(count);
        }
        Collections.shuffle(list);
        Vampire = list.get(1);
        list.remove(1);
        Collections.shuffle(list);
        Accomplice = list.get(1);
        list.remove(1);
        Robbery = list.get(1);
        list.remove(1);
        Collections.shuffle(list);
        Innocent.addAll(list);

        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            bar.addPlayer(player);
            player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 128000, 4, true), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 127, true), true);
            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.setPlayerListName("");
            player.getWorld().setDifficulty(Difficulty.EASY);
            player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 3));
            if(isJinro(player)) {
                player.sendTitle("あなたの役職 : " + ChatColor.RED + "人狼", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                StringBuilder OtherJinro = new StringBuilder();
                for(Player p: Jinro) {
                    OtherJinro.append(p.getName()).append(" ");
                }
                player.sendMessage(jinroRPGMessage + "あなたの役職 : " + ChatColor.RED + "人狼 (" + OtherJinro + ")");
            } else if(isVampire(player)) {
                player.sendTitle("あなたの役職 : " + ChatColor.DARK_PURPLE + "吸血鬼", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                player.sendMessage(jinroRPGMessage + "あなたの役職 : " + ChatColor.DARK_PURPLE + "吸血鬼");
            } else if(isAccomplice(player)) {
                player.sendTitle("あなたの役職 : " + ChatColor.GRAY + "共犯者", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                player.sendMessage(jinroRPGMessage + "あなたの役職 : " + ChatColor.GRAY + "共犯者");
            } else if(isRobbery(player)) {
                player.sendTitle("あなたの役職 : " + ChatColor.BLUE + "強盗", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                player.sendMessage(jinroRPGMessage + "あなたの役職 : " + ChatColor.BLUE + "強盗");
            } else {
                player.sendTitle("あなたの役職 : " + ChatColor.GREEN + "村人", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME START", 1, 50, 5);
                player.sendMessage(jinroRPGMessage + "あなたの役職 : " + ChatColor.GREEN + "村人");
            }
        }
        isGameStarted = true;
        day = 1;
        Day();
    }

    public static boolean isVampire(Player player) {
        if(isRobbery(player)) {
            if(StolenRole.equals("Vampire")) {
                return true;
            }
        }
        return Vampire.getName().equals(player.getName());
    }

    public static boolean isJinro(Player player) {
        for (Player value : Jinro) { if (value.getName().equals(player.getName())) return true; }
        return false;
    }

    public static boolean isAccomplice(Player player) {
        if(isRobbery(player)) {
            if(StolenRole.equals("Accomplice")) {
                return true;
            }
        }
        return Accomplice.getName().equals(player.getName());
    }
    public static boolean isRobbery(Player player) {
        return Robbery.getName().equals(player.getName());
    }

    public static boolean isDead(Player player) {
        for(Player p: DeathPlayerList) {
            if(p.getName().equals(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void addKnights(Player player) {
        Knights.add(player);
    }

    public static boolean isUsingKnights(Player player) {
        for(Player p:Knights) {
            if(player.getName().equals(p.getName()))  {
                return true;
            }
        }
        return false;
    }

    public static boolean isJinroAllDead() {
        int deathcount = 0;
        for(Player player: DeathPlayerList) {
            for (Player WereWolf : Jinro) {
                if (WereWolf.getName().equals(player.getName())) {
                    deathcount++;
                }
            }
        }
        return deathcount == Jinro.size();
    }

    public static boolean isInnocentAllDead() {
        int deathcount = 0;
        for(Player player: DeathPlayerList) {
            for (Player innocent : Innocent) {
                if (innocent.getName().equals(player.getName())) {
                    deathcount++;
                }
            }
        }
        return deathcount == Innocent.size();
    }

    public static void death(Player player) {
        DeathPlayerList.add(player);
        sz = DeathPlayerList.size();
        s = listSize - sz;
        if(s<=0) {
            s = 1;
        }
        s = (int)Math.ceil(s / 5);
        if(isVampire(player) && !StolenRole.equals("Vampire")) {
            isVampireDead = true;
        }
        if(isJinroAllDead()) {
            if(isVampireDead) {
                end("Innocent");
            } else {
                end("Vampire");
            }
        } else if(isInnocentAllDead()) {
            if(isVampireDead) {
                end("WereWolf");
            } else {
                end("Vampire");
            }
        }
    }
    public static void setStolen(String e) {
        StolenRole = e;
    }

    public static String getRandomWereWolfName() {
        List<Player> list = Jinro;
        Collections.shuffle(list);
        Player RandomJinro = list.get(0);
        return RandomJinro.getName();
    }

    public static void end(String Winner) {
        stopDayCycle();
        bar.removeAll();
        for(ArmorStand armorstand: ArmorStands) {
            armorstand.setVisible(true);
            armorstand.setSmall(false);
            armorstand.setGravity(true);
        }
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.getWorld().setDifficulty(Difficulty.PEACEFUL);
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        switch (Winner) {
            case "Innocent":
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendTitle("村人の勝利", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME END", 1, 50, 5);
                }
                break;
            case "Vampire":
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendTitle(ChatColor.RED + "吸血鬼の勝利", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME END", 1, 50, 5);
                }
                break;
            case "WereWolf":
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendTitle(ChatColor.RED + "人狼の勝利", ChatColor.GOLD.toString() + ChatColor.UNDERLINE + "GAME END", 1, 50, 5);
                }
                break;
        }
        StringBuilder JinroName = new StringBuilder();
        for(Player p:Jinro) {
            if(isRobbery(p)) {
                JinroName.append(p.getName()).append("(強盗) ");
            } else {
                JinroName.append(p.getName()).append(" ");
            }
        }
        StringBuilder InnocentName = new StringBuilder();
        for(Player p:Innocent) {
            if(isRobbery(p)) {
                InnocentName.append(p.getName()).append("(強盗) ");
            } else {
                InnocentName.append(p.getName()).append(" ");
            }
        }
        String AccompliceName = Accomplice.getName() + " ";
        String VampireName = Vampire.getName() + " ";
        if(isAccomplice(Robbery)) {
            AccompliceName = AccompliceName + Robbery.getName() + "(強盗)";
        } else if(isVampire(Robbery)) {
            VampireName = VampireName + Robbery.getName() + "(強盗)";
        }
        for(Player player: Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(ChatColor.GREEN + "-----=====" + ChatColor.UNDERLINE + "今回の役職" + ChatColor.RESET.toString() + ChatColor.GREEN + "=====-----");
            player.sendMessage(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "人狼");
            player.sendMessage(ChatColor.DARK_RED.toString() + JinroName);
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.BOLD + "共犯者");
            player.sendMessage(ChatColor.GRAY.toString() + AccompliceName);
            player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "吸血鬼");
            player.sendMessage(ChatColor.RED.toString() + VampireName);
            player.sendMessage(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "村人");
            player.sendMessage(ChatColor.DARK_GREEN.toString() + InnocentName);
            if(!isStolen) {
                player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "強盗");
                player.sendMessage(ChatColor.BLUE.toString() + Robbery.getName());
            }
            player.sendMessage(ChatColor.GREEN.toString() + ChatColor.STRIKETHROUGH + "==============================");
        }
        for(Sign sign:Signs) {
            sign.setLine(1,"右クリックで役職記録");
        }
        isGameStarted = false;
        joinedInGamePlayer.clear();
        Jinro.clear();
        Innocent.clear();
        Vampire = null;
        Accomplice = null;
        DeathPlayerList.clear();
        isVampireDead = false;
        isDay = true;
        isStolen = false;
        StolenRole = "";
        Knights.clear();
        Spell.clear();
        Signs.clear();
    }

    public static void stopDayCycle() {
        Bukkit.getScheduler().cancelTask(TimeScheduler);
        for (World world : Bukkit.getServer().getWorlds()) {
            world.setTime(6000);
        }
    }

    public void Day() {
        if(isStarted()) {
            Knights = new ArrayList<>();
            for(World world:Bukkit.getServer().getWorlds()) {
                try {
                    for (Entity entity : world.getEntitiesByClass(Skeleton.class)) {
                        entity.remove();
                    }
                } catch(Exception ignored) {}
            }
            isDay = true;
            for (World world : Bukkit.getServer().getWorlds()) {
                world.setTime(6000);
            }
            TimeScheduler = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if(isVampire(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000, 127, true), true);
                    }
                    player.sendTitle(ChatColor.BLUE + "夜になりました", "", 1, 25, 5);
                }
                bar.setColor(BarColor.BLUE);
                bar.setTitle(ChatColor.BLUE + "NIGHT TIME");
                bar.setProgress(1);
                Night();
            }, 2000L);
        }
    }

    public void Night() {
        if(isStarted()) {

            isDay = false;
            for (World world : Bukkit.getServer().getWorlds()) {
                world.setTime(18000);
            }
            TimeScheduler = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                day += 1;
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.sendTitle(ChatColor.YELLOW + "昼になりました", day + "日目", 1, 25, 5);
                }
                bar.setColor(BarColor.YELLOW);
                bar.setTitle(ChatColor.YELLOW + "DAY TIME");
                bar.setProgress(1);
                Day();
            }, 2000L);
        }
    }
}