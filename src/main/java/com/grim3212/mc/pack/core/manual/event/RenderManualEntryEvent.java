package com.grim3212.mc.pack.core.manual.event;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderManualEntryEvent {

	private final ResourceLocation ICONS_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/icons.png");
	private Minecraft mc = Minecraft.getInstance();

	@SubscribeEvent
	public void onEvent(RenderTickEvent event) {
		if (event.phase == Phase.END && CoreConfig.showCheckmark.get()) {
			if (mc.player != null && mc.world != null && !mc.isGamePaused()) {

				boolean flag = false;
				EntityPlayer player = mc.player;
				World world = mc.player.world;
				RayTraceResult pos = ClientUtil.getMouseOver();

				if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == CoreItems.instruction_manual) {
					if (pos != null) {
						switch (pos.type) {
						case BLOCK:
							IBlockState state = world.getBlockState(pos.getBlockPos());
							if (state.getBlock() instanceof IManualBlock)
								flag = true;
							break;
						case ENTITY:
							if (pos.entity != null) {
								if (pos.entity instanceof IManualEntity) {
									flag = true;
								} else if (pos.entity instanceof EntityItem) {
									EntityItem item = (EntityItem) pos.entity;
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
					MainWindow scaled = mc.mainWindow;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.textureManager.bindTexture(ICONS_LOCATION);
					Gui.drawScaledCustomSizeModalRect((scaled.getScaledWidth() / 2) + 8, (scaled.getScaledHeight() / 2) - 4, 0, 0, 8, 8, 8, 8, 128, 128);
				}
			}
		}
	}
}
