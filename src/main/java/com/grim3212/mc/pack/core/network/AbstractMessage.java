package com.grim3212.mc.pack.core.network;

/**
 * Pretty much all network stuff comes from coolAlias
 * https://github.com/coolAlias/Tutorial-Demo/tree/master/src/main/java/tutorial/network
 */

import java.io.IOException;
import java.util.function.Supplier;

import com.grim3212.mc.pack.core.GrimCore;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class AbstractMessage<T extends AbstractMessage<T>> {
	/**
	 * Some PacketBuffer methods throw IOException - default handling propagates the
	 * exception. if an IOException is expected but should not be fatal, handle it
	 * within this method.
	 */
	protected abstract T read(PacketBuffer buffer) throws IOException;

	/**
	 * Some PacketBuffer methods throw IOException - default handling propagates the
	 * exception. if an IOException is expected but should not be fatal, handle it
	 * within this method.
	 */
	protected abstract void write(PacketBuffer buffer) throws IOException;

	/**
	 * Called on whichever side the message is received; for bidirectional packets,
	 * be sure to check side If {@link #requiresMainThread()} returns true, this
	 * method is guaranteed to be called on the main Minecraft thread for this side.
	 */
	public abstract void process(EntityPlayer player, Supplier<NetworkEvent.Context> ctx);

	/**
	 * If message is sent to the wrong side, an exception will be thrown during
	 * handling
	 * 
	 * @return True if the message is allowed to be handled on the given side
	 */
	protected boolean isValidOnSide(NetworkDirection dir) {
		return true;
	}

	/**
	 * Whether this message requires the main thread to be processed (i.e. it
	 * requires that the world, player, and other objects are in a valid state).
	 */
	protected boolean requiresMainThread() {
		return true;
	}

	public T fromBytes(ByteBuf buffer) {
		try {
			return read(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void toBytes(T msg, ByteBuf buffer) {
		try {
			write(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// =====================================================================//
	/*
	 * Make the implementation final so child classes don't need to bother with it,
	 * since the message class shouldn't have anything to do with the handler. This
	 * is simply to avoid having to have:
	 * 
	 * public static class Handler extends GenericMessageHandler<OpenGuiMessage> {}
	 * 
	 * in every single message class for the sole purpose of registration.
	 */
	public final void onMessage(AbstractMessage<T> msg, Supplier<NetworkEvent.Context> ctx) {
		if (!isValidOnSide(ctx.get().getDirection())) {
			throw new RuntimeException("Invalid side " + ctx.get().getDirection().name() + " for " + this.getClass().getSimpleName());
		} else {
			ctx.get().enqueueWork(() -> {
				this.process(GrimCore.proxy.getPlayerEntity(ctx.get()), ctx);
			});
			ctx.get().setPacketHandled(true);
		}
	}

	/**
	 * Messages that can only be sent from the server to the client should use this
	 * class
	 */
	public static abstract class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(NetworkDirection dist) {
			return dist.getReceptionSide() == LogicalSide.CLIENT;
		}
	}

	/**
	 * Messages that can only be sent from the client to the server should use this
	 * class
	 */
	public static abstract class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(NetworkDirection dist) {
			return dist.getReceptionSide() == LogicalSide.SERVER;
		}
	}
}
