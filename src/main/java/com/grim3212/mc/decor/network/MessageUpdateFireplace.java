package com.grim3212.mc.decor.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractClientMessage;
import com.grim3212.mc.decor.tile.TileEntityFireplace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageUpdateFireplace extends AbstractClientMessage<MessageUpdateFireplace> {

	private BlockPos pos;
	private boolean active;

	public MessageUpdateFireplace() {
	}

	public MessageUpdateFireplace(BlockPos pos, boolean active) {
		this.pos = pos;
		this.active = active;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.pos = buffer.readBlockPos();
		this.active = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(pos);
		buffer.writeBoolean(active);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.worldObj.getTileEntity(pos);
		if (te instanceof TileEntityFireplace) {
			TileEntityFireplace tef = (TileEntityFireplace) te;
			tef.setActive(active);
		}
	}

}
