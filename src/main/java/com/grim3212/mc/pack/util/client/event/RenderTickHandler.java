package com.grim3212.mc.pack.util.client.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.opengl.GL11;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.ClientUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class RenderTickHandler {

	public static float zLevel = 0.0F;
	private int timeWidth;
	public static boolean enabled = false;
	public int scrollMax = -200;
	public int scrollHeight = scrollMax;
	public int scrollMin = -177;
	public static boolean retracting = false;
	public Minecraft game = Minecraft.getMinecraft();

	private static final ResourceLocation resourceLocation = new ResourceLocation(GrimPack.modID, "textures/gui/carcass.png");

	@SubscribeEvent
	public void tick(RenderTickEvent event) {
		if (!enabled && !retracting)
			return;

		timeWidth = game.displayWidth;
		ScaledResolution scaledresolution = new ScaledResolution(game);
		timeWidth = scaledresolution.getScaledWidth();
		FontRenderer fontrenderer = game.fontRenderer;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		game.renderEngine.bindTexture(resourceLocation);
		ClientUtil.drawTexturedModalRect(timeWidth - 245, scrollHeight, 0, 0, 176 - 43, 200, zLevel);
		ClientUtil.drawTexturedModalRect(timeWidth - 325, scrollHeight, 0, 0, 176 - 50, 200, zLevel);
		Date today;
		String result;
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("MMMM d - HH:mm:ss - z");
		today = new Date();
		result = formatter.format(today);
		fontrenderer.drawStringWithShadow((new StringBuilder()).append("").append(result).toString(), timeWidth - 277, scrollHeight + 180, 0xc6c6c6);
		if (enabled) {
			if (scrollHeight < scrollMin)
				scrollHeight++;
		} else {
			if (scrollHeight > scrollMax)
				scrollHeight--;
			if (scrollHeight == scrollMax)
				retracting = false;
		}
		return;
	}
}
