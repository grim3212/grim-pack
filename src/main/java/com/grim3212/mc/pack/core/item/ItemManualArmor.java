package com.grim3212.mc.pack.core.item;

import org.apache.commons.lang3.StringUtils;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemManualArmor extends ItemArmor implements IManualItem {

	private String link;

	public ItemManualArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String link) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.link = link;
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	public ItemManualArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		this(name, materialIn, renderIndexIn, equipmentSlotIn, "");
	}

	@Override
	public Page getPage(ItemStack stack) {
		return StringUtils.isEmpty(link) ? null : ManualRegistry.getPageFromString(link);
	}
}
