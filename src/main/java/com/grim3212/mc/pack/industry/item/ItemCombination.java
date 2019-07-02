package com.grim3212.mc.pack.industry.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemCombination extends ItemManual {

	public ItemCombination(String name) {
		super(name);
		this.maxStackSize = 16;
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	public void addInformation(ItemStack itemstack, PlayerEntity entityplayer, List<String> list, boolean flag) {
		if ((itemstack.hasTagCompound()) && (itemstack.getTagCompound().hasKey("Lock")) && (!itemstack.getTagCompound().getString("Lock").isEmpty())) {
			list.add(I18n.format("grimpack.industry.combo") + itemstack.getTagCompound().getString("Lock"));
		}
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.combination_page;
	}
}