package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetPos extends AbstractServerMessage<MessageSensorSetPos> {

	private BlockPos sensePos;
	private BlockPos pos;

	public MessageSensorSetPos() {
	}

	public MessageSensorSetPos(BlockPos pos, BlockPos sensePos) {
		this.pos = pos;
		this.sensePos = sensePos;
	}

	@Override
	protected MessageSensorSetPos read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetPos(buffer.readBlockPos(), buffer.readBlockPos());
	}

	@Override
	protected void write(MessageSensorSetPos msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeBlockPos(msg.sensePos);
	}

	@Override
	public void process(MessageSensorSetPos msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setSensorPos(msg.sensePos);
		}
	}

}
