package de.Jan.Varus.Objects;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface SmartInventoryRunnable {
	void run(Inventory inv, InventoryClickEvent event); 
}
