package de.Jan.Varus.Objects;

public enum Völker {
	FEE("§d"), KOBOLD("§2"), WIKINGER("§6"), MENSCH("§f"), VAMPIR("§0"), ZWERG("§8"); 
	String colorPrefix; 
	public String getPrefix() {
		return colorPrefix; 
	}
	Völker(String colorPrefix) {
		this.colorPrefix = colorPrefix; 
	}
}
