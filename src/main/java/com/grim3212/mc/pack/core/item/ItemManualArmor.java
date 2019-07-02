package com.grim3212.mc.pack.core.item;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import org.apache.commons.lang3.StringUtils;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemManualArmor extends ArmorItem implements IManualItem {

	private String link;

	public ItemManualArmor(String name, IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, String link, Item.Properties props) {
		super(materialIn, equipmentSlotIn, props);
		this.link = link;
		this.setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	public ItemManualArmor(String name, IArmorMaterial materialIn, EquipmentSlotType equipmentSlotIn, Item.Properties props) {
		this(name, materialIn, equipmentSlotIn, "", props);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return StringUtils.isEmpty(link) ? null : ManualRegistry.getPageFromString(link);
	}
}
