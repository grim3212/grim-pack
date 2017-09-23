package com.grim3212.mc.pack.core.util.generator;

import org.lwjgl.input.Keyboard;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.generator.renderers.RendererHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class GenerateRendererHandler {

	private KeyBinding reset = new KeyBinding("keybind", Keyboard.KEY_MINUS, GrimPack.modName);

	public GenerateRendererHandler() {
		ClientRegistry.registerKeyBinding(reset);
	}

	@SubscribeEvent
	public void keyInput(KeyInputEvent event) {
		// Reset tick so we can retrigger the save
		if (reset.isKeyDown()) {
			tickNumber = 0;
		}
	}

	int tickNumber = 100;
	boolean gameStarted = false;

	@SubscribeEvent
	public void tick(TickEvent.RenderTickEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.world != null) {
			tickNumber++;
		}
		// for now it is 1, it seems to work even in the first frame
		if (tickNumber == 10) {
			Generator.images("F:/Documentation");
		}
	}

	private boolean resize = false;

	@SubscribeEvent
	public void tick(TickEvent.ClientTickEvent event) {
		if (event.phase == Phase.START) {
			if (!gameStarted) {
				gameStarted = true;

				if (resize)
					// Used to get proper scaling for images
					RendererHelper.resizeWindow(512, 512, false);
			}
		}
	}
}
