package com.grim3212.mc.pack.decor.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.client.ManualDecor;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrush extends ItemManual {

	public ItemBrush() {
		this.setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
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
