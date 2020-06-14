package plugin.minecraft.jinro;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Events implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity().getPlayer();
        player.spigot().respawn();
        player.getInventory().clear();
        e.getDrops().clear();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(e.getEntity().getPlayer().getKiller().getName() + "->" + e.getEntity().getPlayer().getName());
                } catch (Exception ignored) {}
                if(Main.isStarted()) {
                    if(Main.StartPoint!=null) { player.teleport(Main.StartPoint); }
                    player.setGameMode(GameMode.SPECTATOR);
                    Main.death(player);
                }
            }
        }, 5L);
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity() instanceof Skeleton) {
            if(e.getEntity().getKiller() != null) {
                e.setDroppedExp(0);
                e.getDrops().clear();
                int en = (int) Math.ceil(Math.random() * 2);
                if(en == 1) {
                    e.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.EMERALD, 1));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(Main.getJinroMessage() +e.getPlayer().getName() + "さんがログインしました");
        e.getPlayer().setPlayerListName("");
        if(Main.isStarted()) {
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
            e.getPlayer().sendMessage(Main.getJinroMessage() + "すでにゲームが開始していたため、スペクテイターモードになりました。");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(Main.getJinroMessage() + e.getPlayer().getName() + "さんがログアウトしました");
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        if (Main.isStarted()) {
            if(!Main.isDead(e.getPlayer())) {
                Main.death(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        try {
            if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RESET + "プロビデンスの眼光")) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    if (e.getPlayer().getName().equals(player.getName())) {
                        player.sendMessage(Main.getJinroMessage() + "プロビデンスの眼光を使用しました");
                    } else {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 1, true));
                    }
                }
                e.getItemDrop().remove();
            } else if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RESET + "共犯者の目")) {
                if (Main.isStarted()) {
                    e.getPlayer().sendMessage(Main.getJinroMessage() + "人狼の一人は" + Main.getRandomWereWolfName() + "です。");
                }
                e.getItemDrop().remove();
            } else if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RESET + "騎士の祈り")) {
                if (Main.isStarted()) {
                    if (!Main.isDay) {
                        if(Main.isJinro(e.getPlayer())) {
                            e.getPlayer().sendMessage(Main.getJinroMessage() + "人狼は騎士の祈りを使用することはできません!");
                            return;
                        }
                        Main.addKnights(e.getPlayer());
                        e.getItemDrop().remove();
                        e.getPlayer().sendMessage(Main.getJinroMessage() + "騎士の祈りを使用しました。");
                    } else {
                        e.getPlayer().sendMessage(Main.getJinroMessage() + "昼の間は使用することができません!");
                        e.setCancelled(true);
                    }
                }
            } else if (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.RESET + "天恵の呪符")) {
                if (Main.isStarted()) {
                    if (!Main.isDay) {
                        e.getPlayer().sendMessage(Main.getJinroMessage() + "天恵の呪符を使用しました。");
                        Main.addSpell(e.getPlayer());
                        e.getItemDrop().remove();
                    } else {
                        e.getPlayer().sendMessage(Main.getJinroMessage() + "昼の間は使用することができません!");
                        e.setCancelled(true);
                    }
                }
            }
        } catch(Exception ignored) {}
    }

    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent e) {
        try {
            if (e.getDamager() instanceof Player) {
                if (e.getEntity() instanceof Player) {
                    Player damager = (Player) e.getDamager();
                    Player player = (Player) e.getEntity();
                    if (Main.isStarted()) {
                        if (damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "聖なる十字架")) {
                            if (Main.isVampire(player)) {
                                Main.death(player);
                                player.setGameMode(GameMode.SPECTATOR);
                                player.spigot().respawn();
                            }
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.AQUA + "吸血鬼を殴ると一撃で倒せる。");
                            lore.add(ChatColor.AQUA + "一度使うと消滅する。");
                            ItemStack result = new ItemStack(Material.NETHER_STAR, 1);
                            ItemMeta meta = result.getItemMeta();
                            meta.setDisplayName(ChatColor.RESET + "聖なる十字架");
                            meta.setLore(lore);
                            result.setItemMeta(meta);
                            damager.getInventory().removeItem(result);
                        } else if(Main.isRobbery(damager) && damager.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.RESET + "強盗の剣")) {
                            if (!Main.isStolen) {
                                if (Main.isJinro(player)) {
                                    damager.sendTitle("あなたの役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                    StringBuilder OtherJinro = new StringBuilder();
                                    for (Player pl : Main.Jinro) {
                                        OtherJinro.append(pl.getName()).append(" ");
                                        pl.sendMessage(Main.getJinroMessage() + damager.getName() + "の役職は" + ChatColor.RED + "人狼" + ChatColor.WHITE + "になりました");
                                    }
                                    damager.sendMessage(Main.getJinroMessage() + "あなたの役職 : " + ChatColor.RED + "人狼 (" + OtherJinro + ")");
                                    Main.Jinro.add(damager);
                                    player.damage(1000);
                                    Main.isStolen = true;
                                } else if (Main.isVampire(player)) {
                                    if(Main.isDay) {
                                        Main.setStolen("Vampire");
                                        damager.sendTitle("あなたの役職は" + ChatColor.DARK_PURPLE + "吸血鬼" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                        damager.sendMessage(Main.getJinroMessage() + "あなたの役職 : " + ChatColor.DARK_PURPLE + "吸血鬼");
                                        player.damage(1000);
                                        Main.isStolen = true;
                                    }
                                } else if (Main.isAccomplice(player)) {
                                    if(!Main.isUsingKnights(player)) {
                                        Main.setStolen("Accomplice");
                                        damager.sendTitle("あなたの役職は" + ChatColor.GRAY + "共犯者" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                        damager.sendMessage(Main.getJinroMessage() + "あなたの役職 : " + ChatColor.GRAY + "共犯者");
                                        player.damage(1000);
                                        Main.isStolen = true;
                                    }
                                } else {
                                    if(!Main.isUsingKnights(player)) {
                                        damager.sendTitle("あなたの役職は" + ChatColor.GREEN + "村人" + ChatColor.WHITE + "になりました", "", 1, 50, 5);
                                        damager.sendMessage(Main.getJinroMessage() + "あなたの役職 : " + ChatColor.GREEN + "村人");
                                        player.damage(1000);
                                        Main.Innocent.add(damager);
                                        Main.isStolen = true;
                                    }
                                }
                            }
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
                            damager.getInventory().removeItem(result);
                        }
                    }

                }
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {
        Entity entity = e.getEntity();
        e.setCancelled(true);
        entity.getWorld().createExplosion(entity.getLocation(), 0);
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (!(entity instanceof Villager))
            return;
        if (entity.getCustomName().equalsIgnoreCase(ChatColor.GREEN + "ShopKeeper")) {
            e.setCancelled(true);
            Player player = e.getPlayer();
            TradeMenu.openGUI(player);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        try {
            Block block = e.getClickedBlock();
            Player player = e.getPlayer();
            if(block.getState() instanceof Sign) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
                String l2 = lines[1];
                if (l2.equalsIgnoreCase("右クリックで役職記録")) {
                    if (Main.isStarted()) {
                        sign.setLine(1, player.getName());
                        Main.Signs.add(sign);
                        sign.update();
                    } else {
                        player.sendMessage(Main.getJinroMessage() + "まだゲームはスタートしていません!");
                    }
                } else {
                    if(Main.isStarted()) {
                        Player p = Bukkit.getServer().getPlayerExact(l2);
                        if (player.getInventory().getItemInMainHand().getType().equals(Material.QUARTZ)) {
                            for(Player pl:Bukkit.getServer().getOnlinePlayers()) {
                                pl.sendMessage(Main.getJinroMessage() + player.getName() + "さんが大魔導士の心を使用しました。");
                                if (Main.isJinro(p)) {
                                    pl.sendMessage(Main.getJinroMessage() + p.getName() + "は人狼です");
                                    pl.sendTitle(p.getName() + "の役職 : " + ChatColor.RED + "人狼", "", 1, 50, 5);
                                } else if (Main.isVampire(p)) {
                                    pl.sendMessage(Main.getJinroMessage() + p.getName() + "は吸血鬼です");
                                    pl.sendTitle(p.getName() + "の役職 : " + ChatColor.DARK_PURPLE + "吸血鬼", "", 1, 50, 5);
                                } else {
                                    pl.sendMessage(Main.getJinroMessage() + p.getName() + "は村人です");
                                    pl.sendTitle(p.getName() + "の役職 : " + ChatColor.GREEN + "村人", "", 1, 50, 5);
                                }
                                if (Main.isUsingSpell(p)) {
                                    p.sendMessage(Main.getJinroMessage() + player.getName() + "に占われました!");
                                }
                            }
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
                            player.getInventory().removeItem(result);
                        }
                        if (!Main.isDay) {
                            if (player.getInventory().getItemInMainHand().getType().equals(Material.STICK)) {
                                if (Main.isJinro(p)) {
                                    player.sendMessage(Main.getJinroMessage() + p.getName() + "は人狼です");
                                    player.sendTitle(p.getName() + "の役職 : " + ChatColor.RED + "人狼", "", 1, 50, 5);
                                } else if (Main.isVampire(p)) {
                                    player.sendMessage(Main.getJinroMessage() + p.getName() + "は吸血鬼です");
                                    player.sendTitle(p.getName() + "の役職 : " + ChatColor.DARK_PURPLE + "吸血鬼", "", 1, 50, 5);
                                } else {
                                    player.sendMessage(Main.getJinroMessage() + p.getName() + "は村人です");
                                    player.sendTitle(p.getName() + "の役職 : " + ChatColor.GREEN + "村人", "", 1, 50, 5);
                                }
                                if(Main.isUsingSpell(p)) {
                                    p.sendMessage(Main.getJinroMessage() + player.getName() + "に占われました!");
                                }
                                List<String> lore = new ArrayList<>();
                                lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
                                lore.add(ChatColor.AQUA + "その人の役職が分かる。");
                                lore.add(ChatColor.AQUA + "共犯者は村人と出る。");
                                ItemStack result = new ItemStack(Material.STICK, 1);
                                ItemMeta meta = result.getItemMeta();
                                meta.setDisplayName(ChatColor.RESET + "占い師の心");
                                meta.setLore(lore);
                                result.setItemMeta(meta);
                                player.getInventory().removeItem(result);
                            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.SKULL_ITEM)) {
                                if (Main.isDead(p)) {
                                    player.sendMessage(Main.getJinroMessage() + p.getName() + "は死亡しています。");
                                    player.sendTitle(p.getName() + " : " + ChatColor.GRAY + "死亡", "", 1, 50, 5);
                                } else {
                                    player.sendMessage(Main.getJinroMessage() + p.getName() + "は生存しています。");
                                    player.sendTitle(p.getName() + " : " + ChatColor.YELLOW + "生存", "", 1, 50, 5);
                                }
                                List<String> lore = new ArrayList<>();
                                lore.add(ChatColor.AQUA + "名前が書かれている看板を右クリックすると、");
                                lore.add(ChatColor.AQUA + "その人が死んでいるかどうかが分かる。");
                                ItemStack result = new ItemStack(Material.SKULL_ITEM, 1);
                                ItemMeta meta = result.getItemMeta();
                                meta.setDisplayName(ChatColor.RESET + "霊媒師の心");
                                meta.setLore(lore);
                                result.setItemMeta(meta);
                                player.getInventory().removeItem(result);
                            }
                        }
                    }
                }
            } else if (e.getClickedBlock().getType() == Material.CHEST && Main.isStarted()) {
                e.setCancelled(true);
                TradeMenu.openGUI(e.getPlayer());
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onPlayerPickUpArrow(PlayerPickupArrowEvent e) {
        try {
            e.getArrow().remove();
            e.setCancelled(true);
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void InventoryOnClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory open = e.getInventory();
        ItemStack item = e.getCurrentItem();

        if(open == null) {
            return;
        }

        if(open.getName().equals(ChatColor.GOLD + "TestItems")) {
            e.setCancelled(true);

            if(item != null) {
                player.getInventory().addItem(item);
            }
        } else if(open.getName().equals(ChatColor.GOLD + "Joined Players")) {
            if(item == null || !item.hasItemMeta()) {
                return;
            } else if(player.getGameMode().equals(GameMode.SPECTATOR) || item.getType().equals(Material.SKULL)) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                try {
                    Player p = Bukkit.getPlayerExact(meta.getOwningPlayer().getName());
                    player.teleport(p);
                } catch (Exception ignored) {}
            }
            e.setCancelled(true);
        } else if(open.getName().equals(ChatColor.GOLD + "Ban")) {
            if(item == null || !item.hasItemMeta()) {
                return;
            } else if(item.getType().equals(Material.SKULL)) {
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                try {
                    String command = "ban " + meta.getOwningPlayer().getName() + " 不正や荒らしをしている可能性があったため、一時的にBanしました。 eight_y_88またはMC_shunに異議申し立てを行ってください。";
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                } catch (Exception ignored) {}
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if(e.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) {
            for(Player player:Main.getDeathPlayerList()) {
                player.sendMessage(ChatColor.GRAY + "[霊界チャット]" + e.getPlayer().getName() + ": " + e.getMessage());
                e.setCancelled(true);
            }
        }
        if(!Main.isDay) e.setCancelled(true);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        Entity hitPlayer = e.getHitEntity();

        if ((e.getEntity() instanceof Snowball)) {
            if(hitPlayer instanceof Player) {
                Player player = (Player) hitPlayer;
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true), true);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 127, true), true);
            }
        }
    }

    @EventHandler
    public void onShot(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof Snowball) {
            e.getEntity().setVelocity(e.getEntity().getVelocity().multiply(2));
        }
    }

    @EventHandler
    public void onPlayerPickUpItem(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.AQUA + "人狼陣営専用アイテム");
            lore.add(ChatColor.AQUA + "人狼陣営以外が拾うと爆死する。");
            ItemStack result = new ItemStack(Material.BEACON, 1);
            ItemMeta meta = result.getItemMeta();
            meta.setDisplayName(ChatColor.RESET + "地雷");
            meta.setLore(lore);
            result.setItemMeta(meta);
            if(e.getItem().getItemStack().equals(result)) {
                for(Player p:Main.Innocent) {
                    if(player.getName().equals(p.getName())) {
                        player.damage(1000);
                        player.getWorld().createExplosion(player.getLocation(), 0);
                    }
                }
                if(Main.isVampire(player)) {
                    player.damage(1000);
                    player.getWorld().createExplosion(player.getLocation(), 0);
                    e.setCancelled(true);
                    e.getItem().remove();
                }
            }
        }
    }
}

