package de.Jan.Varus;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

public class Test {
	private static File varusConfigFile = new File("plugins\\VarusVoelker\\config.yml"); 
	public static FileConfiguration varusConfig; 
	
	private void createCustomConfig() {
		if(!varusConfigFile.exists()) {
			varusConfigFile.getParentFile().mkdirs(); 
			try {
				varusConfigFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			Bukkit.getConsoleSender().sendMessage("§eConfig wurde exstern geladen!"); 
		}
		varusConfig = new YamlConfiguration(); 
		try {
			varusConfig.load(varusConfigFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void SaveDrof() {
		try {
			varusConfig.save(varusConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
