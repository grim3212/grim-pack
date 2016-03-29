package com.grim3212.mc.decor.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageFrame implements IMessage {

	private int entityID;
	private int id;

	public MessageFrame() {
	}

	public MessageFrame(int entityID, int id) {
		this.entityID = entityID;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(id);
	}

	public int getEntityID() {
		return entityID;
	}

	public int getId() {
		return id;
	}
}