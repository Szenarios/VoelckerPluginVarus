package de.Jan.Varus.Events.pets;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.Jan.Varus.Objects.Pet;
import de.Jan.Varus.Objects.Tamer;

public class playerMoveEvent implements Listener {
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer(); 
		Tamer tamer = new Tamer(player); 
		if(tamer.getIds() != null) {
			for(String id : tamer.getIds()) {
				if(Bukkit.getEntity(UUID.fromString(id)) != null) {
					Entity entity = Bukkit.getEntity(UUID.fromString(id)); 
					Pet pet = new Pet(entity); 
					if(pet.isFollow()) {
						follow(player, entity);
					}
				}
			}
		}
	}
	public void follow(Player player, Entity entity) {
		Creature creature = (Creature) entity;
		Location location = player.getLocation();
		location.distance(creature.getLocation());
		int rdm = (int) Math.random() * 6 + 1; 
		switch (rdm) {
		case 1:
			location.add(0, 0, 2);
			break;
		case 2:
			location.add(2, 0, 2);
			break;
		case 3:
			location.add(0, 0, 2);
			break;
		case 4:
			location.subtract(0, 0, 2);
			break;
		case 5: 
			location.subtract(2, 0, 0);
			break; 
		default:
			break;
		}
		double distance = location.distance(creature.getLocation()); 
		if(distance >= 20) {
			if(creature.isOnGround()) {
				creature.teleport(player); 
			}
		} else {
			if(distance <= 5) {
				((CraftCreature) creature).getHandle().getNavigation().a(location.getX(), location.getY(),location.getZ(), 1.5);
			} else {
				((CraftCreature) creature).getHandle().getNavigation().a(location.getX(), location.getY(),location.getZ(), 1.7);
			}
		}
	}
}
