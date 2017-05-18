package com.grim3212.mc.pack.util.frozen;

import java.io.IOException;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractClientMessage;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;

public class MessageFrozen extends AbstractClientMessage<MessageFrozen> {

	private int id;
	private boolean freeze;
	private int duration;

	public MessageFrozen() {
	}

	public MessageFrozen(int id, boolean freeze, int duration) {
		this.id = id;
		this.freeze = freeze;
		this.duration = duration;
	}

	@Override
	protected void read(PacketBuffer buffer) throws IOException {
		this.id = buffer.readInt();
		this.freeze = buffer.readBoolean();
		this.duration = buffer.readInt();
	}

	@Override
	protected void write(PacketBuffer buffer) throws IOException {
		buffer.writeInt(id);
		buffer.writeBoolean(freeze);
		buffer.writeInt(duration);
	}

	@Override
	public void process(EntityPlayer player, Side side) {
		FrozenCapability.frozen((EntityLivingBase) player.world.getEntityByID(id), freeze, duration);
	}

}
