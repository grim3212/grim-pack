package com.grim3212.mc.pack.util.client;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.util.GrimUtil;
import com.grim3212.mc.pack.util.client.event.RenderTickHandler;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.network.MessageFusRoDah;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindHelper {

	private KeyBinding fusrodah = new KeyBinding("FusRoDah", Keyboard.KEY_Z, GrimUtil.partName);
	private KeyBinding timekey = new KeyBinding("Time Toggle", Keyboard.KEY_G, GrimUtil.partName);
	private KeyBinding autotorch = new KeyBinding("AutoTorch", Keyboard.KEY_B, GrimUtil.partName);
	private static long lastPress = 0L;

	public KeyBindHelper() {
		if (UtilConfig.subpartFusRoDah)
			ClientRegistry.registerKeyBinding(fusrodah);
		if (UtilConfig.subpartTime)
			ClientRegistry.registerKeyBinding(timekey);
	}

	@SubscribeEvent
	public void tick(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		if (UtilConfig.subpartAutoTorch && autotorch.isPressed()) {
			if (UtilConfig.atEnabled.getBoolean()) {
				mc.ingameGUI.addChatMessage(ChatType.GAME_INFO, new TextComponentTranslation("grimpack.util.autotorch.off"));
			} else {
				mc.ingameGUI.addChatMessage(ChatType.GAME_INFO, new TextComponentTranslation("grimpack.util.autotorch.on"));
			}

			// Enable / Disable
			UtilConfig.atEnabled.set(!UtilConfig.atEnabled.getBoolean());
			// Save config file
			GrimUtil.INSTANCE.getConfig().save();
		}

		if (UtilConfig.subpartTime && timekey.isPressed()) {
			if (mc.inGameHasFocus) {
				if ((RenderTickHandler.enabled = !RenderTickHandler.enabled)) {
					RenderTickHandler.retracting = false;
				} else {
					RenderTickHandler.retracting = true;
				}
			}
		}

		if (UtilConfig.subpartFusRoDah && fusrodah.isPressed()) {
			if (mc.inGameHasFocus) {
				World world = mc.world;
				EntityPlayerSP player = mc.player;
				float yaw = player.rotationYaw * 0.01745329F;
				double xPower = (double) (-MathHelper.sin(yaw)) * UtilConfig.frd_power;
				double zPower = (double) MathHelper.cos(yaw) * UtilConfig.frd_power;
				// Every thousand is a second
				if (lastPress + (UtilConfig.fusrodahCooldown * 1000) <= System.currentTimeMillis()) {
					PacketDispatcher.sendToServer(new MessageFusRoDah());
					lastPress = System.currentTimeMillis();

					double pitch = Math.sin(-player.rotationPitch * 0.01745329F);

					for (int count = 0; count <= 2; ++count)
						world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, player.posX + (double) count * xPower, (player.posY + 1) + (double) count * pitch, player.posZ + (double) count * zPower, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
