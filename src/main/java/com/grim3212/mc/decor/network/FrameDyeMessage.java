package com.grim3212.mc.decor.network;

import java.io.IOException;

import com.grim3212.mc.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.decor.entity.EntityFrame;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class FrameDyeMessage extends AbstractServerMessage<FrameDyeMessage> {

	private int entityID;
	private int color;
	private boolean burn;

	public FrameDyeMessage() {
	}

	public FrameDyeMessage(int entityID, int color, boolean burn) {
		this.entityID = entityID;
		this.color = color;
		this.burn = burn;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.entityID = buffer.readInt();
		this.color = buffer.readInt();
		this.burn = buffer.readBoolean();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(entityID);
		buffer.writeInt(color);
		buffer.writeBoolean(burn);
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

	@Override
	public void process(EntityPlayer player, Side side) {
		Entity entity = player.worldObj.getEntityByID(getEntityID());

		if ((entity != null) && ((entity instanceof EntityFrame))) {
			((EntityFrame) entity).dyeFrame(getColor(), isBurn());
		}
	}
}
