package de.Jan.Varus.Objects;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface SmartInventroy {
	Inventory getInventory(String title, int size, InventoryHolder holder); 
}
