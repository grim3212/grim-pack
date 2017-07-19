package com.grim3212.mc.pack.industry.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemModernDoor extends ItemDoor implements IManualItem {

	public ItemModernDoor(String name, Block block) {
		super(block);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
		this.setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.doors_page;
	}

}
