package de.Jan.Varus.Events.essentials;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class chatEvent implements Listener{
	@EventHandler 
	public void ChatEvent(PlayerChatEvent event) {
		event.setFormat(event.getPlayer().getDisplayName() + " §8| §r" + (event.getPlayer().isOp() ? event.getMessage().replace("&", "§") : "§7" + event.getMessage()).replace("%", "Prozent"));
	}
}
