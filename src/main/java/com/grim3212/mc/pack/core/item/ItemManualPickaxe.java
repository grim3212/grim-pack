package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemManualPickaxe extends ItemPickaxe implements IManualItem {

	private String page;

	public ItemManualPickaxe(String name, ToolMaterial toolMaterial, String page) {
		super(toolMaterial);
		this.page = page;
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(this.page);
	}
}
