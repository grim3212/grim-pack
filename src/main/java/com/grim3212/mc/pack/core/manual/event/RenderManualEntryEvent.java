package com.grim3212.mc.pack.core.manual.event;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.init.CoreInit;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderManualEntryEvent {

	private final ResourceLocation ICONS_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/icons.png");
	private Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public void onEvent(TickEvent.RenderTickEvent event) {
		if (event.phase == Phase.END && CoreConfig.showCheckmark.get()) {
			if (mc.player != null && mc.world != null && !mc.isGamePaused()) {

				boolean flag = false;
				PlayerEntity player = mc.player;
				World world = mc.player.world;
				RayTraceResult pos = ClientUtil.getMouseOver();

				if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == CoreInit.instruction_manual) {
					if (pos != null) {
						switch (pos.getType()) {
						case BLOCK:
							BlockRayTraceResult blockTrace = (BlockRayTraceResult) pos;
							BlockState state = world.getBlockState(blockTrace.getPos());

							if (state.getBlock() instanceof IManualBlock)
								flag = true;
							break;
						case ENTITY:
							EntityRayTraceResult entTrace = (EntityRayTraceResult) pos;

							if (entTrace.getEntity() != null) {
								if (entTrace.getEntity() instanceof IManualEntity) {
									flag = true;
								} else if (entTrace.getEntity() instanceof ItemEntity) {
									ItemEntity item = (ItemEntity) entTrace.getEntity();
									if (item.getItem().getItem() instanceof IManualItem) {
										flag = true;
									}
								}
							}

							break;
						default:
							break;
						}
					}
				}

				if (flag && mc.currentScreen == null) {
					MainWindow scaled = mc.getMainWindow();
					mc.getTextureManager().bindTexture(ICONS_LOCATION);
					RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
					AbstractGui.blit((scaled.getScaledWidth() / 2) + 8, (scaled.getScaledHeight() / 2) - 4, 8, 8, 256, 256, 8, 8, 128, 128);
				}
			}
		}
	}
}
