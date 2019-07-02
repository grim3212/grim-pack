package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFan.FanMode;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSaveFan extends AbstractServerMessage<MessageSaveFan> {

	private int range;
	private FanMode mode;
	private BlockPos pos;

	public MessageSaveFan() {
	}

	public MessageSaveFan(int range, FanMode mode, BlockPos pos) {
		this.range = range;
		this.mode = mode;
		this.pos = pos;
	}

	@Override
	protected MessageSaveFan read(PacketBuffer buffer) throws IOException {
		return new MessageSaveFan(buffer.readInt(), buffer.readEnumValue(FanMode.class), buffer.readBlockPos());
	}

	@Override
	protected void write(MessageSaveFan msg, PacketBuffer buffer) throws IOException {
		buffer.writeInt(msg.range);
		buffer.writeEnumValue(msg.mode);
		buffer.writeBlockPos(msg.pos);
	}

	@Override
	public void process(MessageSaveFan msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntityFan) {
			if (((TileEntityFan) te).getMode() != FanMode.OFF)
				((TileEntityFan) te).setMode(msg.mode);
			((TileEntityFan) te).setOldMode(msg.mode);
			((TileEntityFan) te).setRange(msg.range);
			ctx.get().getSender().world.markForRerender(msg.pos);
		}
	}

}
