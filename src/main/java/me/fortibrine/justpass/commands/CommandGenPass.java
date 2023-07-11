package me.fortibrine.justpass.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.fortibrine.justpass.JustPass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;
import java.util.function.UnaryOperator;

public class CommandGenPass implements CommandExecutor {

    private JustPass plugin;
    public CommandGenPass(JustPass plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        FileConfiguration config = plugin.getConfig();

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);

        BookMeta meta = (BookMeta) item.getItemMeta();

        OfflinePlayer offlinePlayer;
        if (args.length > 0) {
            OfflinePlayer offlinePlayerInCommand = Bukkit.getOfflinePlayer(args[0]);
            if (!offlinePlayerInCommand.isOnline()) {
                player.sendMessage(config.getString("messages.offline"));
                return true;
            }
            offlinePlayer = offlinePlayerInCommand;
        } else {
            offlinePlayer = (OfflinePlayer) sender;
        }

        Player onlinePlayer = (Player) offlinePlayer;

        meta.setAuthor(config.getString("author"));
        meta.setTitle(PlaceholderAPI.setPlaceholders(offlinePlayer, config.getString("title")));

        for (String page : config.getStringList("book")) {

            page = PlaceholderAPI.setPlaceholders(offlinePlayer, page);
            meta.addPage(page);
        }

        item.setItemMeta(meta);

        onlinePlayer.getInventory().addItem(item);

        return true;
    }
}
