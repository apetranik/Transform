package com.javaminecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Transform extends JavaPlugin implements Listener {

    public static final Logger log = Logger.getLogger("Minecraft");

    private boolean isTransforming = false;
    Player me;
    World world;
    Location spot;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        me = (Player) sender;
        world = me.getWorld();
        spot = me.getLocation();

        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("transformon")) {
                isTransforming = true;
                log.info("[Transformer] on");
                return true;
            }
            if (label.equalsIgnoreCase("transformoff")) {
                isTransforming = false;
                log.info("[Transforming] off");
                return true;
            }
        }
        return false;
    }

    @Override
    public void onEnable() {
        // this is an event listener class
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(this, this);

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        if (player != me) {
            return;
            // Don't care about some other player
        }
        if (!isTransforming) {
            // transforming is off
            return;
        }
        log.info("[Transformer] Player moved to (" + from.getBlockX() + ", " + from.getBlockY() + ", " + from.getBlockZ() + ")");
        // get block player is in
        Block block = from.getBlock();
        
        Block down = block.getRelative(BlockFace.DOWN);
        Material below = down.getType();
        // turn block to stone
        log.info("[Transforming to stone]");
        down.setType(Material.STONE);

    }

}
