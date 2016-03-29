package com.grim3212.mc.decor.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageFrameDye implements IMessage {

	private int entityID;
	private int color;
	private boolean burn;

	public MessageFrameDye() {
	}

	public MessageFrameDye(int entityID, int color, boolean burn) {
		this.entityID = entityID;
		this.color = color;
		this.burn = burn;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.entityID = buf.readInt();
		this.color = buf.readInt();
		this.burn = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
		buf.writeInt(color);
		buf.writeBoolean(burn);
	}

	public int getEntityID() {
		return entityID;
	}

	public int getColor() {
		return color;
	}

	public boolean isBurn() {
		return burn;
	}

}
