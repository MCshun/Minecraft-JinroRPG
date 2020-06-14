package plugin.minecraft.jinro;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BanHammer {
    public static void openBanGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Ban");
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            skullMeta.setOwningPlayer(p);
            skullMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.AQUA + p.getName());
            head.setItemMeta(skullMeta);
            inv.addItem(head);
        }
        player.openInventory(inv);
    }
}
