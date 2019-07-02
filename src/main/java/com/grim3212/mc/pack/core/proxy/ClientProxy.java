package com.grim3212.mc.pack.core.proxy;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class ClientProxy extends ServerProxy {

	@Override
	public PlayerEntity getPlayerEntity(NetworkEvent.Context ctx) {
		return (ctx.getDirection() == NetworkDirection.PLAY_TO_CLIENT ? Minecraft.getInstance().player : super.getPlayerEntity(ctx));
	}

	@Override
	public abstract void preInit();

	@Override
	public void displayDismountMessage(PlayerEntity player) {
		if (player == Minecraft.getInstance().player) {
			Minecraft.getInstance().ingameGUI.setOverlayMessage(I18n.format("mount.onboard", Minecraft.getInstance().gameSettings.keyBindSneak.getLocalizedName()), false);
		}
	}

	@Override
	public void openManual() {
		Minecraft.getInstance().displayGuiScreen(GuiManualIndex.activeManualPage);
	}
}
