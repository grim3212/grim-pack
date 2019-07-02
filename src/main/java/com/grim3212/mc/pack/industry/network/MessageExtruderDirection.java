package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageExtruderDirection extends AbstractServerMessage<MessageExtruderDirection> {

	private byte facing;
	private int extruderId;

	public MessageExtruderDirection() {
	}

	public MessageExtruderDirection(byte facing, int extruderId) {
		this.facing = facing;
		this.extruderId = extruderId;
	}

	@Override
	protected MessageExtruderDirection read(PacketBuffer buffer) throws IOException {
		return new MessageExtruderDirection(buffer.readByte(), buffer.readInt());
	}

	@Override
	protected void write(MessageExtruderDirection msg, PacketBuffer buffer) throws IOException {
		buffer.writeByte(msg.facing);
		buffer.writeInt(msg.extruderId);
	}

	@Override
	public void process(MessageExtruderDirection msg, Supplier<Context> ctx) {
		Entity entity = ctx.get().getSender().getEntityWorld().getEntityByID(msg.extruderId);
		if (entity != null && entity instanceof EntityExtruder) {
			((EntityExtruder) entity).setFacing(Direction.byIndex(msg.facing));
		}
	}

}
