package com.grim3212.mc.pack.decor.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.decor.tile.TileEntityNeonSign;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

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
	protected MessageNeonChangeMode read(PacketBuffer buffer) throws IOException {
		return new MessageNeonChangeMode(buffer.readInt(), buffer.readBlockPos());
	}

	@Override
	protected void write(MessageNeonChangeMode msg, PacketBuffer buffer) throws IOException {
		buffer.writeInt(msg.mode);
		buffer.writeBlockPos(msg.pos);
	}

	@Override
	public void process(MessageNeonChangeMode msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().getEntityWorld().getTileEntity(msg.pos);

		if (te instanceof TileEntityNeonSign) {
			((TileEntityNeonSign) te).mode = msg.mode;
		}
	}
}
