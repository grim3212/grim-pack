package com.grim3212.mc.pack.industry.network;

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.network.AbstractMessage.AbstractServerMessage;
import com.grim3212.mc.pack.industry.inventory.ContainerLocksmithWorkbench;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSetLock extends AbstractServerMessage<MessageSetLock> {

	private String lock;

	public MessageSetLock() {
	}

	public MessageSetLock(String lock) {
		this.lock = lock;
	}

	@Override
	protected MessageSetLock read(PacketBuffer buffer) throws IOException {
		return new MessageSetLock(buffer.readString(11));
	}

	@Override
	protected void write(MessageSetLock msg, PacketBuffer buffer) throws IOException {
		buffer.writeString(msg.lock, 11);
	}

	@Override
	public void process(MessageSetLock msg, Supplier<Context> ctx) {
		if (ctx.get().getSender().openContainer instanceof ContainerLocksmithWorkbench) {
			((ContainerLocksmithWorkbench) ctx.get().getSender().openContainer).updateLock(msg.lock);
		}
	}

}