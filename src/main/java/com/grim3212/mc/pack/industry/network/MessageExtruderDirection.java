package com.grim3212.mc.pack.industry.network;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.entity.EntityExtruder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;

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
	protected void read(PacketBuffer buffer) throws IOException {
		this.facing = buffer.readByte();
		this.extruderId = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeByte(this.facing);
		buffer.writeInt(this.extruderId);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		Entity entity = player.getEntityWorld().getEntityByID(this.extruderId);
		if (entity != null && entity instanceof EntityExtruder) {
			((EntityExtruder) entity).setFacing(EnumFacing.getFront(this.facing));
		}
	}

}
