package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor.SensorMode;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorChangeMode extends AbstractServerMessage<MessageSensorChangeMode> {

	private SensorMode mode;
	private BlockPos pos;

	public MessageSensorChangeMode() {
	}

	public MessageSensorChangeMode(BlockPos pos, SensorMode mode) {
		this.pos = pos;
		this.mode = mode;
	}

	@Override
	protected MessageSensorChangeMode read(PacketBuffer buffer) throws IOException {
		return new MessageSensorChangeMode(buffer.readBlockPos(), SensorMode.valueOf(buffer.readString(64)));
	}

	@Override
	protected void write(MessageSensorChangeMode msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeString(msg.mode.name());
	}

	@Override
	public void process(MessageSensorChangeMode msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setMode(msg.mode);
		}
	}

}
