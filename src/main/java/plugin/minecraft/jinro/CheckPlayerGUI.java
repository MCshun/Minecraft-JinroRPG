package plugin.minecraft.jinro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class CheckPlayerGUI {
    public static void OpenGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Joined Players");
        if(Main.isStarted()) {
            if(Main.isDead(player)) {
                for (Player p : Main.getJoinedPlayer()) {
                    List<String> lore = new ArrayList<>();
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    skullMeta.setOwningPlayer(p);
                    skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
                    if (Main.isJinro(p)) {
                        if(Main.isRobbery(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.RED + "人狼(強盗)");
                        } else {
                            lore.add(ChatColor.RESET.toString() + ChatColor.RED + "人狼");
                        }
                    } else if (Main.isVampire(p)) {
                        if(Main.isRobbery(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "吸血鬼(強盗)");
                        } else {
                            lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "吸血鬼");
                        }
                    } else if (Main.isAccomplice(p)) {
                        if(Main.isRobbery(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "共犯者(強盗)");
                        } else {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "共犯者");
                        }
                    } else if(!Main.isStolen && Main.isRobbery(p)) {
                        lore.add(ChatColor.RESET.toString() + ChatColor.BLUE + "強盗");
                    } else {
                        if(Main.isRobbery(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GREEN + "村人(強盗)");
                        } else {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GREEN + "村人");
                        }
                    }
                    if(Main.isDead(p)) {
                        lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "死亡");
                    } else {
                        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "生存");
                    }
                    if(Main.isUsingKnights(p)) {
                        lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW + "騎士の祈り使用中");
                    } else {
                        lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "騎士の祈りは使用していません");
                    }
                    lore.add(ChatColor.RESET.toString());
                    if(player.getGameMode().equals(GameMode.SPECTATOR)) {
                        lore.add(ChatColor.YELLOW + "クリックでテレポート");
                    }
                    skullMeta.setLore(lore);
                    head.setItemMeta(skullMeta);
                    inv.addItem(head);
                }
            } else {
                for (Player p : Main.getJoinedPlayer()) {
                    List<String> lore = new ArrayList<>();
                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                    SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                    skullMeta.setOwningPlayer(p);
                    skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
                    if(Main.isJinro(p)) {
                        if(Main.isJinro(player)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.RED + "人狼");
                        }
                    }
                    if(p.getName().equals(player.getName())) {
                        if (Main.isVampire(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.DARK_PURPLE + "吸血鬼");
                        } else if (Main.isAccomplice(p)) {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GRAY + "共犯者");
                        } else {
                            lore.add(ChatColor.RESET.toString() + ChatColor.GREEN + "村人");
                        }
                    }
                    skullMeta.setLore(lore);
                    head.setItemMeta(skullMeta);
                    inv.addItem(head);
                }
            }
        } else {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                skullMeta.setOwningPlayer(p);
                skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
                head.setItemMeta(skullMeta);
                inv.addItem(head);
            }
        }
        player.openInventory(inv);
    }
}
