package de.Jan.Varus.Objects;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum Erfolge {
	STONED("Stoned", "�7Prima, du hast einen �8Stein �7abgebaut! �eHerzlichen Gl�ckwuscnh!", 5, Material.STONE, ErfolgeType.BLOCKBREAK),
	NATURBURSCHE("Naturbursche", "�aSehr sch�n du k�mmerst dich um die Natur! �2Weiter so!", 15, Material.GRASS_BLOCK, ErfolgeType.BLOCKPLACE), 
	ANTIVEGI("Anti-Vegetarier", "�dAiaiai du magst wohl sehr gerne Fleisch!", 10, EntityType.PIG, ErfolgeType.ENTITYKILL),
	HOLZKOPF("Holz-Kopf", "�6Aiaiai du holz kopf!", 10, Material.OAK_LOG, ErfolgeType.BLOCKBREAK),
	G�RTNER("G�rtner", "�aDu hast dolle Garten arbeit!", 25, Material.PINK_TULIP, ErfolgeType.BLOCKPLACE),
	B�CKER("B�cker", "�eDu bist nun Krasser B�cker! �aMachst du mir meine Hochzeitstorte?", 45, Material.CAKE, ErfolgeType.BLOCKPLACE),
	CAT("M�rder", "�6Du hast was getan ... �7uff okay bitte sch�n", 15, EntityType.CAT, ErfolgeType.ENTITYKILL),
	GOLDDIGGER("GoldDigger", "�6Gold ... sehr sch�n du bist ein echter gr�ber!", 74, Material.GOLD_ORE, ErfolgeType.BLOCKBREAK),
	MUSIKER("Mu�asi�eker", "�dDu bist ein echter Musiker!", 105, Material.JUKEBOX, ErfolgeType.BLOCKPLACE),
	DJ("DJ", "�9Baut mir nen Krassen beat!", 25, Material.NOTE_BLOCK, ErfolgeType.BLOCKPLACE),
	K�PFTE("K�pfte-spie�", "�6Jetzt so nen RICHTIG sch�nen K�pfte-spie�!", 15, EntityType.COW, ErfolgeType.ENTITYKILL),
	DIA("Richkid", "�dDu hast einen Diamanten abgebaut!", 45, Material.DIAMOND_ORE, ErfolgeType.BLOCKBREAK),
	VEGITARIER("Vegetarier", "�aDu ern�rst dich ohne Fleisch! Sehr nice!", 15, Material.MELON, ErfolgeType.BLOCKBREAK),
	MONEYBOY("MONEYBOY", "�dOHHHH du hast Dias ohne ende!", 100, Material.DIAMOND_BLOCK, ErfolgeType.BLOCKPLACE),
	DRANGONLORD("Drachenlord", "�5Respekt�8, �5du hast den Drachen erlegt!", 15000, EntityType.ENDER_DRAGON, ErfolgeType.ENTITYKILL); 
	private String name; 
	private String message; 
	private int coins; 
	private ErfolgeType type; 
	private Material material; 
	private EntityType entityType; 
	Erfolge(String name, String message, int coins, EntityType entityType, ErfolgeType type) {
		this.name = name; 
		this.message = message; 
		this.coins = coins; 
		this.entityType = entityType; 
		this.type = type; 
	}
	Erfolge(String name, String message, int coins, Material material, ErfolgeType type) {
		this.name = name; 
		this.message = message; 
		this.coins = coins; 
		this.material = material; 
		this.type = type; 
		
	}
	public String getName() {
		return name;
	}
	public String getMessage() {
		return message;
	}
	public int getCoins() {
		return coins;
	}
	public ErfolgeType getType() {
		return type;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	public Material getMaterial() {
		return material;
	} 
	
	
}
