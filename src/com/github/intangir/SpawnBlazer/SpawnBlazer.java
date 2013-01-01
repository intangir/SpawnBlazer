package com.github.intangir.SpawnBlazer;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnBlazer extends JavaPlugin implements Listener
{
    public Logger log;
    public PluginDescriptionFile pdfFile;
    
	public void onEnable()
	{
		log = this.getLogger();
		pdfFile = this.getDescription();

		Bukkit.getPluginManager().registerEvents(this, this);
		
		log.info("v" + pdfFile.getVersion() + " enabled!");
	}
	
	public void onDisable()
	{
		log.info("v" + pdfFile.getVersion() + " disabled.");
	}

	@EventHandler(ignoreCancelled=true)
	public void onChunkLoad(ChunkLoadEvent e)
	{
		ProcessChunk(e.getChunk());
	}

	@EventHandler(ignoreCancelled=true)
	public void onChunkPopulate(ChunkPopulateEvent e)
	{
		ProcessChunk(e.getChunk());
	}

	public void ProcessChunk(Chunk c)
	{
		for (BlockState state: c.getTileEntities())
		{
			if(state instanceof CreatureSpawner)
			{
				if(state.getBlock().getBiome() == Biome.HELL)
				{
					EntityType et = ((CreatureSpawner) state).getSpawnedType();
					if(et == EntityType.ZOMBIE || et == EntityType.SKELETON)
					{
						((CreatureSpawner) state).setSpawnedType(EntityType.BLAZE);
						log.info("Changed spawner to blaze at " + state.getBlock().getX() + " " +  state.getBlock().getY() + " " + state.getBlock().getZ());
					}
				}
			}
		}
	}
}

