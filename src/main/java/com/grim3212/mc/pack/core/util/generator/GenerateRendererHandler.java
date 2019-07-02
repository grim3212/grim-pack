package com.grim3212.mc.pack.core.util.generator;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.generator.renderers.RendererHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardKeyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class GenerateRendererHandler {

	// Subtract key
	private KeyBinding reset = new KeyBinding("keybind", 109, GrimPack.modName);

	public GenerateRendererHandler() {
		ClientRegistry.registerKeyBinding(reset);
	}

	@SubscribeEvent
	public void keyInput(KeyboardKeyEvent event) {
		// Reset tick so we can retrigger the save
		if (reset.isKeyDown()) {
			tickNumber = 0;
		}
	}

	int tickNumber = 100;
	boolean gameStarted = false;

	@SubscribeEvent
	public void tick(TickEvent.RenderTickEvent event) {
		Minecraft mc = Minecraft.getInstance();

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
