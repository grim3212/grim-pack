package com.grim3212.mc.pack.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.network.NetworkEvent;

public class ServerProxy {

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(NetworkEvent.Context ctx) {
		return ctx.getSender();
	}

	/**
	 * Returns the current thread based on side during message handling, used for
	 * ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(NetworkEvent.Context ctx) {
		return ctx.getSender().getServer();
	}

	public void preInit() {
	}

	public void initColors() {
	}

	public void displayDismountMessage(EntityPlayer player) {
	}
}
