package de.Jan.Varus.Objects;

public enum V�lker {
	FEE("�d"), KOBOLD("�2"), WIKINGER("�6"), MENSCH("�f"), VAMPIR("�0"), ZWERG("�8"); 
	String colorPrefix; 
	public String getPrefix() {
		return colorPrefix; 
	}
	V�lker(String colorPrefix) {
		this.colorPrefix = colorPrefix; 
	}
}
