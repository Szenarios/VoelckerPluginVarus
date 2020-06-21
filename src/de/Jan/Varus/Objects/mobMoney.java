package de.Jan.Varus.Objects;

import org.bukkit.entity.EntityType;

public enum mobMoney {
	FELDERMAUS(50, EntityType.BAT),
	HUHN(3, EntityType.CHICKEN), 
	KABELJAU(3, EntityType.COD), 
	KUH(5, EntityType.COW), 
	CREEPER(15, EntityType.CREEPER),
	DROWNED(10, EntityType.DROWNED), 
	ELDERGUARDIEN(75, EntityType.ELDER_GUARDIAN),
	ENDERMITE(30, EntityType.ENDERMITE),
	EVOLKER(75, EntityType.EVOKER), 
	PILLAGER(15, EntityType.PILLAGER), 
	KUGELFISCH(5, EntityType.PUFFERFISH),
	PIG(3, EntityType.PIG), 
	PHANTOM(20, EntityType.PHANTOM),
	MUHROMCOW(40, EntityType.MUSHROOM_COW), 
	MAGMA(5, EntityType.MAGMA_CUBE), 
	GUARDIEN(10, EntityType.GUARDIAN), 
	GHAST(20, EntityType.GHAST),
	HUSK(10, EntityType.HUSK),
	STRAY(15, EntityType.STRAY), 
	SQUID(10, EntityType.SQUID), 
	SLIME(3, EntityType.SLIME),
	SHEEP(3, EntityType.SHEEP), 
	SALOM(3, EntityType.SALMON), 
	RABBIT(3, EntityType.RABBIT), 
	TROPICALFISH(3, EntityType.TROPICAL_FISH), 
	TUHRTLE(3, EntityType.TURTLE),
	WITCH(30, EntityType.WITCH), 
	WITHERSKLETT(25, EntityType.WITHER_SKELETON), 
	PIGMAN(1, EntityType.PIG_ZOMBIE),
	PLAYER(115, EntityType.PLAYER),
	ENDERDRACHE(1000, EntityType.ENDER_DRAGON),
	WITHERBOSS(666, EntityType.WITHER); 
	
	private int coins; 
	private EntityType entityType; 
	mobMoney(int coins, EntityType entityType){
		this.coins = coins; 
		this.entityType = entityType; 
	}
	public int getCoins() {
		return coins;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	
}
