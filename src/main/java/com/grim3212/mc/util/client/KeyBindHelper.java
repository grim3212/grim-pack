package com.grim3212.mc.util.client;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.util.GrimUtil;
import com.grim3212.mc.util.client.event.RenderTickHandler;
import com.grim3212.mc.util.config.UtilConfig;
import com.grim3212.mc.util.network.MessageFusRoDah;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindHelper {

	private KeyBinding fusrodah = new KeyBinding("FusRoDah", Keyboard.KEY_Z, GrimUtil.modName);
	private KeyBinding timekey = new KeyBinding("Time Toggle", Keyboard.KEY_G, GrimUtil.modName);

	public KeyBindHelper() {
		ClientRegistry.registerKeyBinding(fusrodah);
		ClientRegistry.registerKeyBinding(timekey);
	}

	@SubscribeEvent
	public void tick(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (timekey.isPressed()) {
			if (Minecraft.getMinecraft().inGameHasFocus) {
				if ((RenderTickHandler.enabled = !RenderTickHandler.enabled)) {
					RenderTickHandler.retracting = false;
				} else {
					RenderTickHandler.retracting = true;
				}
			}
		}

		if (fusrodah.isPressed()) {
			if (mc.inGameHasFocus) {
				World world = mc.theWorld;
				EntityPlayerSP player = mc.thePlayer;
				float yaw = player.rotationYaw * 0.01745329F;
				double xPower = (double) (-MathHelper.sin(yaw)) * UtilConfig.frd_power;
				double zPower = (double) MathHelper.cos(yaw) * UtilConfig.frd_power;
				PacketDispatcher.sendToServer(new MessageFusRoDah());

				double pitch = Math.sin(-player.rotationPitch * 0.01745329F);

				for (int count = 0; count <= 2; ++count)
					world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, player.posX + (double) count * xPower, (player.posY + 1) + (double) count * pitch, player.posZ + (double) count * zPower, 0.0D, 0.0D, 0.0D);

				if (UtilConfig.soundEnabled) {
					if (UtilConfig.useOldSound)
						player.worldObj.playSound(player.posX, player.posY, player.posZ, GrimUtil.modID + ":fusrodah-old", 1.0F, 1.0F, false);
					else
						player.worldObj.playSound(player.posX, player.posY, player.posZ, GrimUtil.modID + ":fusrodah", 1.0F, 1.0F, false);
				}

			}
		}
	}
}
