package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBrush extends ItemManual {

	public ItemBrush() {
		super("brush");
		this.setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("grimpack.decor.brush_info"));
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualDecor.brush_page;
	}
}
