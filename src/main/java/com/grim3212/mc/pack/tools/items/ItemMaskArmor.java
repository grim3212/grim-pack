package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;

@SuppressWarnings("deprecation")
public class ItemMaskArmor extends ItemManualArmor {

	// TODO: Add in new mob masks
	public static final String[] types = new String[] { "empty", "blaze", "cave_spider", "chicken", "cow", "creeper", "enderman", "ghast", "magmacube", "mooshroom", "pig", "sheep", "skeleton", "slime", "spider", "squid", "villager", "wolf", "zombie_pigman", "zombie" };

	public ItemMaskArmor(ArmorMaterial armorMaterial, int renderIndex) {
		super("mask", armorMaterial, renderIndex, EntityEquipmentSlot.HEAD);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
		this.setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getMetadata() == 0)
			return ManualTools.emptyMask_page;

		return ManualTools.mobMask_page;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ToolsItems.mask.getUnlocalizedName() + "_" + types[stack.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.translateToLocal("mask." + types[stack.getItemDamage()]) + " " + I18n.translateToLocal(ToolsItems.mask.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		String maskType = types[stack.getItemDamage()];

		return GrimPack.modID + ":" + "textures/models/armor/" + maskType + "mask_1.png";
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab))
			for (int i = 0; i < types.length; i++) {
				ItemStack stack = new ItemStack(this, 1, i);
				items.add(stack);
			}
	}
}