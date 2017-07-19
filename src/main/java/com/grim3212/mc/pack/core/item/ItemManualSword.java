package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ResourceLocation;

public class ItemManualSword extends ItemSword implements IManualItem {

	private String link;

	public ItemManualSword(String name, ToolMaterial material, String link) {
		super(material);
		this.link = link;
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualRegistry.getPageFromString(link);
	}
}
