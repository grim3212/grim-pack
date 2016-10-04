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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderManualEntryEvent {

	private final ResourceLocation ICONS_LOCATION = new ResourceLocation(GrimPack.modID, "textures/gui/icons.png");
	private Minecraft mc = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onEvent(RenderTickEvent event) {
		if (event.phase == Phase.END && CoreConfig.showCheckmark) {
			if (mc.thePlayer != null && mc.theWorld != null && !mc.isGamePaused()) {

				boolean flag = false;
				EntityPlayer player = mc.thePlayer;
				World world = mc.thePlayer.worldObj;
				RayTraceResult pos = ClientUtil.getMouseOver();

				if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() == CoreItems.instruction_manual) {
					if (pos != null) {
						switch (pos.typeOfHit) {
						case BLOCK:
							IBlockState state = world.getBlockState(pos.getBlockPos());
							if (state.getBlock() instanceof IManualBlock)
								flag = true;
							break;
						case ENTITY:
							if (pos.entityHit != null) {
								if (pos.entityHit instanceof IManualEntity) {
									flag = true;
								} else if (pos.entityHit instanceof EntityItem) {
									EntityItem item = (EntityItem) pos.entityHit;
									if (item.getEntityItem().getItem() instanceof IManualItem) {
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
					ScaledResolution scaled = new ScaledResolution(mc);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(ICONS_LOCATION);
					Gui.drawScaledCustomSizeModalRect((scaled.getScaledWidth() / 2) + 8, (scaled.getScaledHeight() / 2) - 4, 0, 0, 16, 16, 16, 16, 128, 128);
				}
			}
		}
	}
}
