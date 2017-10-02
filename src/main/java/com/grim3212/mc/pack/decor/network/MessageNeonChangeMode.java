package com.grim3212.mc.pack.decor.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

public class MessageNeonChangeMode extends AbstractServerMessage<MessageNeonChangeMode> {

	private int mode;
	private BlockPos pos;

	public MessageNeonChangeMode() {
	}

	public MessageNeonChangeMode(int mode, BlockPos pos) {
		this.mode = mode;
		this.pos = pos;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.mode = buffer.readInt();
		this.pos = buffer.readBlockPos();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(mode);
		buffer.writeBlockPos(pos);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		TileEntity te = player.world.getTileEntity(pos);

		if (te instanceof TileEntityNeonSign) {
			((TileEntityNeonSign) te).mode = this.mode;
		}
	}
}
