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


public class HolzfällerAxtEvent implements Listener  {
	@EventHandler 
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if(event.getPlayer().getItemInHand() != null) {
			if(event.getPlayer().getItemInHand().hasItemMeta()) {
				if(event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
					if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(getAxt().getItemMeta().getDisplayName())) {
						Player player = event.getPlayer(); 
						player.getItemInHand().setDurability((short) (player.getItemInHand().getDurability() + 30));
						if(player.getItemInHand().getDurability() >= 1561) {
							player.getInventory().remove(player.getItemInHand());
						}
						ceckBlock(event.getBlock(), player);
					}
				}
			}
		}
	}
	public static ItemStack getAxt() {
		ItemStack stack = new ItemStack(Material.DIAMOND_AXE);
		volkCommand.ChangeMeta(stack, "Holzfäller Axt", "§7Fälle einen ganzen baum!"); 
		return stack; 
	}
	
	public static void ceckBlock(Block block, Player player) {
		if(isLog(block.getType())) {
			block.breakNaturally(player.getItemInHand());
			Location upBlock =  new Location(block.getWorld(), block.getX(), block.getY()+1, block.getZ()); 			
			if(isLog(upBlock.getBlock().getType())) {
				ceckBlock(upBlock.getBlock(), player);
			}
			Location leftBlock = new Location(block.getWorld(), block.getX()-1, block.getY(), block.getZ()); 
			if(isLog(leftBlock.getBlock().getType())) {
				ceckBlock(leftBlock.getBlock(), player);
			}
			Location rightBlock = new Location(block.getWorld(), block.getX()+1, block.getY(), block.getZ()); 
			if(isLog(rightBlock.getBlock().getType())) {
				ceckBlock(rightBlock.getBlock(), player);
			}
			Location frontBlock = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()+1);
			if(isLog(frontBlock.getBlock().getType())) {
				ceckBlock(frontBlock.getBlock(), player);
			}
			Location backBlock = new Location(block.getWorld(), block.getX(), block.getY(), block.getZ()-1);
			if(isLog(backBlock.getBlock().getType())) {
				ceckBlock(backBlock.getBlock(), player);
			}
		}
	}
	private static boolean isLog(Material mat) {
		switch (mat) {
		case ACACIA_LOG:
			return true; 
		case BIRCH_LOG: 
			return true; 
		case DARK_OAK_LOG:
			return true; 
		case OAK_LOG: 
			return true; 
		case SPRUCE_LOG: 
			return true; 
		case JUNGLE_LOG:
			return true; 
		default:
			return false; 
		}
	}
}
