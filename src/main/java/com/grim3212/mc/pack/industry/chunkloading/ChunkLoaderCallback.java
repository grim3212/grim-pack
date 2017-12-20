package com.grim3212.mc.pack.industry.chunkloading;

import java.util.List;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.GrimIndustry;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class ChunkLoaderCallback implements LoadingCallback {

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
		
		GrimLog.info(GrimIndustry.partName, tickets.size() + " chunk loaders found!");
		
		for(Ticket ticket : tickets) {
			BlockPos pos = NBTHelper.getBlockPos(ticket.getModData(), "Pos");

			Block block = world.getBlockState(pos).getBlock();
			if (block == IndustryBlocks.chunk_loader) {
                TileEntityChunkLoader tileEntity = (TileEntityChunkLoader) world.getTileEntity(pos);
				if(tileEntity != null)
                    tileEntity.chunkLoadNewTicket(ticket);
			}
		}
		
	}

}
