package de.Jan.Varus.Events.SpezialItem;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import de.Jan.Varus.Commands.volkCommand;

public class MegaPickeEvent implements Listener {
	@EventHandler 
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(event.getPlayer().getItemInHand() != null) {
			if(event.getPlayer().getItemInHand().hasItemMeta()) {
				if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
					if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getPicke().getItemMeta().getDisplayName())) {
						Player player = event.getPlayer(); 
						player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 20));
						if(player.getItemInHand().getDurability() >= 1561) {
							player.getInventory().remove(player.getItemInHand());
						}
						BreakBlock(event.getBlock(), player);
					}
				}
			}
		}
	}
	public static ItemStack getPicke() {
		ItemStack stack = new ItemStack(Material.DIAMOND_PICKAXE); 
		volkCommand.ChangeMeta(stack, "Picke des Arbeiters", "§7Hiermit kannst du 3x3x1 blöcke abbauen!");
		return stack; 
	}
	public void BreakBlock(Block block, Player player) {
		switch (player.getFacing()) {
		case EAST:
			breakBlockEastOrWest(block, player);
			break; 
		case NORTH: 	
			breakBlockNorthOrSouth(block, player);
			break; 
		case SOUTH: 
			breakBlockNorthOrSouth(block, player);
			break;
		case WEST: 
			breakBlockEastOrWest(block, player);
			break;
		default:
			break;
		}
	}
	public void breakBlockUpOrDown(Block mainblock, Player player) {
		for (int x = mainblock.getX()-1; x < mainblock.getX()+2; x++) {
			for (int z = mainblock.getZ()-1; z < mainblock.getZ()+2; z++) {
				Block block = mainblock.getWorld().getBlockAt(new Location(mainblock.getWorld(), x, mainblock.getY(), z)); 
				block.breakNaturally(player.getItemInHand()); 
			}
		}
	}
	public void breakBlockNorthOrSouth(Block mainblock, Player player) {
		for (int x = mainblock.getX()-1; x < mainblock.getX()+2; x++) {
			for (int y = mainblock.getY()-1; y < mainblock.getY()+2; y++) {
				Block block = mainblock.getWorld().getBlockAt(new Location(mainblock.getWorld(), x, y, mainblock.getZ())); 
				block.breakNaturally(player.getItemInHand());
			}
		}
	}
	public void breakBlockEastOrWest(Block mainblock, Player player) {
		for (int z = mainblock.getZ()-1; z < mainblock.getZ()+2; z++) {
			for (int y = mainblock.getY()-1; y < mainblock.getY()+2; y++) {
				Block block = mainblock.getWorld().getBlockAt(new Location(mainblock.getWorld(), mainblock.getX(), y, z)); 
				block.breakNaturally(player.getItemInHand());
			}
		}
	}
}
