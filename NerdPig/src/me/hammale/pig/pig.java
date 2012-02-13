package me.hammale.pig;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.citizensnpcs.api.CitizensManager;
import net.citizensnpcs.resources.npclib.*;

import me.desmin88.mobdisguise.api.*;

public class pig extends JavaPlugin {
	
	Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[NerdPig] Version: " + pdfFile.getVersion() + " Enabled!");
	    PluginManager pm = getServer().getPluginManager();
	    Plugin test = pm.getPlugin("Citizens");
	    if (test != null) {
	        System.out.println("[NerdPig] hooked into Citizens!");
	    } else {
	        System.out.println("[NerdPig] Citizens isn't loaded.");
	        pm.disablePlugin(this);
	    }
	    Plugin test1 = pm.getPlugin("MobDisguise");
	    if (test1 != null) {
	        System.out.println("[NerdPig] hooked into MobDisguise!");
	    } else {
	        System.out.println("[NerdPig] MobDisguise isn't loaded.");
	        pm.disablePlugin(this);
	    }
	    fixNpc();
	}

	public void fixNpc(){
		readData(s);
	}
	
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[NerdPig] Version: " + pdfFile.getVersion() + " Disabled!");	
	}
	
	
	  public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args){
			if(cmd.getName().equalsIgnoreCase("npchide")){
					if(args.length >= 1){
						if(sender instanceof Player){
							Player p = (Player) sender;
							if(CitizensManager.validateSelected(p) == true){
								int i = CitizensManager.getSelected(p);
								HumanNPC npc = CitizensManager.getNPC(i);
								MobDisguiseAPI.undisguisePlayer(npc.getPlayer());
								MobDisguiseAPI.disguisePlayer(npc.getPlayer(), args[0]);
								addData(npc.getPlayer().getName(), args[0]);
								sender.sendMessage(ChatColor.GREEN + "Hiding " + npc.getPlayer().getName() + " as a " + args[0]);
								return true;
							}else{
								sender.sendMessage(ChatColor.RED + "Please select an NPC before hiding!");
							}
						}
					}
			return false;
			}
			return false;
	  }
	  
	  public void removeFile(String s) {
		  File f = new File("plugins/NerdPig/npcs/" + s + ".dat");
		  if(f.exists()){
			  boolean success = f.delete();
			  if (!success){
				  throw new IllegalArgumentException("[NerdPig] Deletion failed!");
			  }
		  }
	  }
	  
	  public void addData(String npc, String mob) {
		  try{
		  File file = new File("plugins/NerdPigs/npcs/" + npc + ".dat");
		          String str = null;		    
		          if (file.exists()) {
		        	  removeFile(npc);
		          }
		          str = (npc + "," + mob);	              
		          PrintWriter out = new PrintWriter(new FileWriter(file, true));	    
		          out.println(str);
		          out.close();
		  }catch (Exception e){
			  System.err.println("[NerdPig] Error: " + e.getMessage());
		  }
	}
	  
	  public String readData(String s){

		  try{
			  FileInputStream fstream = new FileInputStream("plugins/NerdPigs/npcs/" + s + ".dat");
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  while ((strLine = br.readLine()) != null){
				  return strLine;
			  }
			  in.close();
			  return null;
		  }catch (Exception e){
			  System.err.println("[NerdPig] Error: " + e.getMessage());
		  }
		  return null;
	  }
}
