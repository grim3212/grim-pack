package com.grim3212.mc.pack.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class ClientProxy extends ServerProxy {

	@Override
	public EntityPlayer getPlayerEntity(NetworkEvent.Context ctx) {
		return (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT ? Minecraft.getInstance().player : super.getPlayerEntity(ctx));
	}

	@Override
	public IThreadListener getThreadFromContext(NetworkEvent.Context ctx) {
		return (ctx.getDirection().getLogicalSide() == LogicalSide.CLIENT ? Minecraft.getInstance() : super.getThreadFromContext(ctx));
	}

	@Override
	public abstract void preInit();

	@Override
	public void displayDismountMessage(EntityPlayer player) {
		if (player == Minecraft.getInstance().player) {
			Minecraft.getInstance().ingameGUI.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getInstance().gameSettings.keyBindSneak.func_197978_k()), false);
		}
	}
}
