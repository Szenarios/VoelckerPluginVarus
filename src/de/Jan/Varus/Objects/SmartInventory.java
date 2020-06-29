package de.Jan.Varus.Objects;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SmartInventory implements Listener {
	private Inventory inv; 
	private String title; 
	private int size; 
	private InventoryHolder holder; 
	private SmartInventoryRunnable runnable; 
	public SmartInventory(String title, int size, InventoryHolder holder, SmartInventroy smartInventroy, SmartInventoryRunnable runnable) {
		this.title = title; 
		this.size = size; 
		this.holder = holder; 
		this.runnable = runnable; 
		this.inv = smartInventroy.getInventory(title, size, holder); 
		
	}
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase(title)) {
			runnable.run(inv, event); 
		}
	}
	public Inventory getInv() {
		return inv;
	}
	public String getTitle() {
		return title;
	}
	public int getSize() {
		return size;
	}
}
