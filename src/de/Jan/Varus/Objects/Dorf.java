package de.Jan.Varus.Objects;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import de.Jan.Varus.Main;

public class Dorf {
	private String name; 
	private Völker Volk; 
	private Location ort; 
	private UUID anführer; 
	private UUID coAnführer; 
	private List<String> player; 
	private int level;	
	private boolean menschen; 
	private boolean fee; 
	private boolean kobold; 
	private boolean wikinger; 
	private boolean vampir; 
	private boolean zwerg; 
	public Dorf(String name) {
		if(Main.dorf.getString(name) != null) {
			this.name = name; 
			this.Volk = Völker.valueOf(Main.dorf.getString(name + ".volk").toUpperCase());
			this.ort = Main.dorf.getLocation(name + ".ort");
			this.anführer = UUID.fromString(Main.dorf.getString(name + ".anführer"));
			this.coAnführer = Main.dorf.getString(name + ".coanführer").equalsIgnoreCase("null") ? null : UUID.fromString(Main.dorf.getString(name + ".coanführer"));
			this.player = (List<String>) Main.dorf.get(name + ".player");
			this.level = Main.dorf.getInt(name + ".level");
			this.menschen = Main.dorf.getBoolean(name + ".mensch");
			this.fee = Main.dorf.getBoolean(name + ".fee");
			this.kobold = Main.dorf.getBoolean(name + ".kobold");
			this.wikinger = Main.dorf.getBoolean(name + ".wikinger");
			this.zwerg = Main.dorf.getBoolean(name + ".zwerg");
			this.vampir = Main.dorf.getBoolean(name + ".vampir");
		} else {
			this.name = name; 
			this.Volk = null; 
			this.ort = null; 
			this.anführer = null; 
			this.coAnführer = null; 
			this.player = null; 
			this.level = 1; 
			this.menschen = false;
			this.fee = false; 
			this.zwerg = false; 
			this.vampir = false; 
			this.kobold = false;
			this.wikinger = false; 
		}
	}
	public void save() { 
		Main.dorf.set(name + ".volk", Volk.toString().toLowerCase());
		Main.dorf.set(name + ".ort", ort);
		Main.dorf.set(name + ".anführer", anführer.toString());
		Main.dorf.set(name + ".coanführer", (coAnführer != null ? coAnführer.toString() : "null"));
		Main.dorf.set(name + ".level", level);
		Main.dorf.set(name + ".player", player);
		Main.dorf.set(name + ".mensch", menschen);
		Main.dorf.set(name + ".fee", fee);
		Main.dorf.set(name + ".kobold", kobold);
		Main.dorf.set(name + ".wikinger", wikinger);
		Main.dorf.set(name + ".zwerg", zwerg);
		Main.dorf.set(name + ".vampir", vampir);
		Main.SaveDrof();
	}
		
	public Völker getVolk() {
		return Volk;
	}
	public void setVolk(Völker volk) {
		Volk = volk;
	}
	public Location getOrt() {
		return ort;
	}
	public void setOrt(Location ort) {
		this.ort = ort;
	}
	public UUID getAnführer() {
		return anführer;
	}
	public void setAnführer(UUID anführer) {
		this.anführer = anführer;
	}
	public UUID getCoAnführer() {
		return coAnführer;
	}
	public boolean isMenschen() {
		return menschen;
	}
	public void setMenschen(boolean menschen) {
		this.menschen = menschen;
	}
	public boolean isFee() {
		return fee;
	}
	public void setFee(boolean fee) {
		this.fee = fee;
	}
	public boolean isKobold() {
		return kobold;
	}
	public void setKobold(boolean kobold) {
		this.kobold = kobold;
	}
	public boolean isWikinger() {
		return wikinger;
	}
	public void setWikinger(boolean wikinger) {
		this.wikinger = wikinger;
	}
	public void setCoAnführer(UUID coAnführer) {
		this.coAnführer = coAnführer;
	}
	public List<String> getPlayer() {
		return player;
	}
	public void setPlayer(List<String> player) {
		this.player = player;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isVampir() {
		return vampir;
	}
	public void setVampir(boolean vampir) {
		this.vampir = vampir;
	}
	public boolean isZwerg() {
		return zwerg;
	}
	public void setZwerg(boolean zwerg) {
		this.zwerg = zwerg;
	}
}