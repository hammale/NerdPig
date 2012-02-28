package me.hammale.pig;

import me.desmin88.mobdisguise.api.MobDisguiseAPI;
import net.citizensnpcs.api.CitizensManager;
import net.citizensnpcs.resources.npclib.HumanNPC;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class pigEntity implements Listener {

	public pig plugin;
	
    public pigEntity(pig instance) {
    	plugin = instance;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
    	
    	EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
        if(event.getDamager() instanceof Entity){
	        Entity ent = (Entity) event.getDamager();
	        if(net.citizensnpcs.api.CitizensManager.isNPC(ent)){
		    	final HumanNPC npc = CitizensManager.get(ent);
				MobDisguiseAPI.undisguisePlayer(npc.getPlayer());
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				    public void run() {
				    	MobDisguiseAPI.disguisePlayer(npc.getPlayer(), plugin.readData(npc.getName()));
				    }
				}, 5L);			
	        }
        }
    }
}