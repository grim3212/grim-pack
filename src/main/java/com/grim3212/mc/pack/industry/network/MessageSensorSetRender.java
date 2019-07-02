package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetRender extends AbstractServerMessage<MessageSensorSetRender> {

	private boolean shouldRender;
	private BlockPos pos;

	public MessageSensorSetRender() {
	}

	public MessageSensorSetRender(BlockPos pos, boolean shouldRender) {
		this.pos = pos;
		this.shouldRender = shouldRender;
	}

	@Override
	protected MessageSensorSetRender read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetRender(buffer.readBlockPos(), buffer.readBoolean());
	}

	@Override
	protected void write(MessageSensorSetRender msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeBoolean(msg.shouldRender);
	}

	@Override
	public void process(MessageSensorSetRender msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).setRenderSensorPos(msg.shouldRender);
		}
	}
}