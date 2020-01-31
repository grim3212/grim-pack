package com.grim3212.mc.pack.core.client;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.gui.GuiUtils;

public class TooltipHelper {

	public static void renderToolTip(ItemStack itemstack, int x, int y) {
		Minecraft mc = Minecraft.getInstance();

		List<ITextComponent> list = itemstack.getTooltip(mc.player, mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

		if (NBTHelper.hasTag(itemstack, "customTooltip")) {
			list.add(new StringTextComponent(""));
			list.add(new StringTextComponent(NBTHelper.getString(itemstack, "customTooltip")));
		}

		for (int k = 0; k < list.size(); ++k) {
			if (k == 0) {
				list.set(k, new StringTextComponent(itemstack.getRarity().color + list.get(k).getFormattedText()));
			} else {
				list.set(k, new StringTextComponent(TextFormatting.GRAY + list.get(k).getFormattedText()));
			}
		}

		List<String> tooltip = Lists.newArrayList();
		list.forEach((s) -> tooltip.add(s.getFormattedText()));

		FontRenderer font = itemstack.getItem().getFontRenderer(itemstack);
		GuiUtils.drawHoveringText(tooltip, x, y, mc.currentScreen.width, mc.currentScreen.height, -1, (font == null ? mc.fontRenderer : font));
	}
}
