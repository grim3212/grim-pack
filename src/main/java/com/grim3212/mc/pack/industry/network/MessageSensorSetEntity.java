package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSensorSetEntity extends AbstractServerMessage<MessageSensorSetEntity> {

	private String entityName;
	private BlockPos pos;

	public MessageSensorSetEntity() {
	}

	public MessageSensorSetEntity(BlockPos pos, String entityName) {
		this.pos = pos;
		this.entityName = entityName;
	}

	@Override
	protected MessageSensorSetEntity read(PacketBuffer buffer) throws IOException {
		return new MessageSensorSetEntity(buffer.readBlockPos(), buffer.readString(64));
	}

	@Override
	protected void write(MessageSensorSetEntity msg, PacketBuffer buffer) throws IOException {
		buffer.writeBlockPos(msg.pos);
		buffer.writeString(msg.entityName);
	}

	@Override
	public void process(MessageSensorSetEntity msg, Supplier<Context> ctx) {
		TileEntity te = ctx.get().getSender().world.getTileEntity(msg.pos);

		if (te instanceof TileEntitySpecificSensor) {
			((TileEntitySpecificSensor) te).getSpecific().setEntitySpecific(msg.entityName);
		}
	}

}
