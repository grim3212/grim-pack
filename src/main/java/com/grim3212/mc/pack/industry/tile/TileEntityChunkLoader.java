package com.grim3212.mc.pack.industry.tile;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.tile.TileEntityGrim;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderData;
import com.grim3212.mc.pack.industry.chunkloading.ChunkLoaderStorage;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.ForgeChunkManager;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TileEntityChunkLoader extends TileEntityGrim {

    /**
     * Ticket that this tile entity handles
     */
    private ForgeChunkManager.Ticket ticket;

    private UUID owner = null;
    private boolean enabled = true;

    public void setOwner(UUID ownerId)
    {
        this.owner = ownerId;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        owner = compound.getUniqueId("Owner");
        enabled = compound.getBoolean("Enabled");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound tag = super.writeToNBT(compound);
        tag.setUniqueId("Owner", owner);
        tag.setBoolean("Enabled", enabled);

        return tag;
    }

    public boolean getEnabled()
    {
        return enabled;
    }

    public void enableChunkLoader()
    {
        if(!enabled)
        {
            // Force chunk loading
            enabled = true;
            chunkLoad();
        }
    }

    public void disableChunkLoader()
    {
        if(enabled)
        {
            // Unforce chunk, release ticket
            unChunkLoad();

            if(ticket != null)
            {
                ForgeChunkManager.releaseTicket(ticket);
                ticket = null;
            }

            enabled = false;
        }
    }

    public void chunkLoad()
    {
        if(!enabled)
        {
            return;
        }

        if(ticket == null)
        {
            ticket = ForgeChunkManager.requestTicket(GrimPack.INSTANCE, world, ForgeChunkManager.Type.NORMAL);
        }

        if(ticket == null)
        {
            GrimLog.error(GrimIndustry.partName, "Ticket request failed!");
            return;
        }

        // Position with ticket
        NBTHelper.setBlockPos(ticket.getModData(), "Pos", getPos());

        ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(world);
        storage.addChunkLoader(new ChunkLoaderData(owner.toString(), world.provider.getDimension(), getPos(), System.currentTimeMillis()));

        int size = IndustryConfig.chunkLoaderSize;
        if(size % 2 != 0 && size > 1)
        {
            size--;
        }
        int dist = size / 2;

        //int chunkCount = 0;
        ChunkPos centerPos = new ChunkPos(pos.getX() / 16, pos.getZ() / 16);
        for(int x = centerPos.x - dist; x <= centerPos.x + dist; x++)
        {
            for(int z = centerPos.z - dist; z <= centerPos.z + dist; z++)
            {
                ChunkPos chunkPos = new ChunkPos(x, z);
                ForgeChunkManager.forceChunk(ticket, chunkPos);
                //chunkCount++;
            }
        }

        //GrimLog.info(GrimIndustry.partName, "chunkLoading at pos: " + pos.toString() + " " + chunkCount + " chunks loaded.");
    }

    public void chunkLoadNewTicket(ForgeChunkManager.Ticket ticket)
    {
        if(this.ticket != null && this.ticket != ticket)
        {
            ForgeChunkManager.releaseTicket(this.ticket);
        }
        this.ticket = ticket;
        chunkLoad();
    }

    @Override
    public void invalidate() {
        if(ticket != null)
        {
            ForgeChunkManager.releaseTicket(ticket);
            ticket = null;
        }

        // Remove loader from world data
        ChunkLoaderStorage storage = ChunkLoaderStorage.getStorage(world);
        if(world.isRemote || storage == null)
        {
            return;
        }

        if(world.provider != null)
        {
            storage.removeChunkLoader(new ChunkLoaderData(owner.toString(), world.provider.getDimension(), getPos(), 0));
        }

        super.invalidate();
    }

    public void unChunkLoad()
    {
        int size = IndustryConfig.chunkLoaderSize;
        if(size % 2 != 0 && size > 1)
        {
            size--;
        }
        int dist = size / 2;

        ChunkPos centerPos = new ChunkPos(pos.getX() / 16, pos.getZ() / 16);
        for(int x = centerPos.x - dist; x <= centerPos.x + dist; x++) {
            for (int z = centerPos.z - dist; z <= centerPos.z + dist; z++) {
                ChunkPos chunkPos = new ChunkPos(x, z);
                ForgeChunkManager.unforceChunk(ticket, chunkPos);

                //GrimLog.info(GrimIndustry.partName, "Unchunkloading at chunk pos: " + chunkPos);
            }
        }
    }

}
