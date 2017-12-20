package com.grim3212.mc.pack.industry.chunkloading;

import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ChunkLoaderEvents {

	@SubscribeEvent
	public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.player.world.isRemote) {
			ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(event.player.world);
			if (storage != null) {
				storage.updateLoginTime(event.player.getUniqueID().toString(), System.currentTimeMillis());

				// Enable all of the chunk loaders this player owns
				for (ChunkLoaderData loaderPos : storage.getChunkLoaders()) {
					TileEntityChunkLoader tileEntity = (TileEntityChunkLoader) event.player.world.getTileEntity(loaderPos.pos);
					if (tileEntity != null) {
						tileEntity.enableChunkLoader();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent event) {
		if (!event.player.world.isRemote) {
			ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(event.player.world);
			if (storage != null) {
				storage.updateLoginTime(event.player.getUniqueID().toString(), System.currentTimeMillis());
			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		long timeout = IndustryConfig.chunkLoaderTimeout;
		if (timeout == 0) {
			return;
		}
		// Base off of hours
		timeout = timeout * 60 * 60 * 1000;

		if (!event.world.isRemote && event.world.getTotalWorldTime() % 1200 == 0) {
			ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(event.world);

			if (storage != null) {
				for (ChunkLoaderData data : storage.getChunkLoaders()) {
					if (System.currentTimeMillis() - data.ownerLastLogin >= timeout && !isPlayerOnline(event.world, data.ownerId)) {
						TileEntityChunkLoader tileEntity = (TileEntityChunkLoader) event.world.getTileEntity(data.pos);
						if (tileEntity != null) {
							tileEntity.disableChunkLoader();
						}
					}
				}
			}
		}
	}

	public boolean isPlayerOnline(World world, String ownerId) {
		for (EntityPlayer playerEntity : world.playerEntities) {
			if (playerEntity.getUniqueID().toString().equals(ownerId)) {
				return true;
			}
		}
		return false;
	}

}
