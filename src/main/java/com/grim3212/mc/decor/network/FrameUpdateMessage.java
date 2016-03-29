package com.grim3212.mc.decor.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.decor.entity.EntityFrame;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class FrameUpdateMessage extends AbstractServerMessage<FrameUpdateMessage> {

	private int entityID;
	private int id;

	public FrameUpdateMessage() {
	}

	public FrameUpdateMessage(int entityID, int id) {
		this.entityID = entityID;
		this.id = id;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		entityID = buffer.readInt();
		id = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(entityID);
		buffer.writeInt(id);
	}

	public int getEntityID() {
		return entityID;
	}

	public int getId() {
		return id;
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		Entity entity = player.worldObj.getEntityByID(getEntityID());

		if ((entity != null) && ((entity instanceof EntityFrame))) {
			((EntityFrame) entity).updateFrame(getId());
		}
	}

}
