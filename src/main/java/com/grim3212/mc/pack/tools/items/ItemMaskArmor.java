package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemMaskArmor extends ItemArmor {

	public static final String[] types = new String[] { "empty", "blaze", "cave_spider", "chicken", "cow", "creeper", "enderman", "ghast", "magmacube", "mooshroom", "pig", "sheep", "skeleton", "slime", "spider", "squid", "villager", "wolf", "zombie_pigman", "zombie" };

	public ItemMaskArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
		setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		this.setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ToolsItems.mask.getUnlocalizedName() + "_" + types[stack.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal("mask." + types[stack.getItemDamage()]) + " " + StatCollector.translateToLocal(ToolsItems.mask.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		String maskType = types[stack.getItemDamage()];

		return GrimTools.modID + ":" + "textures/models/armor/" + maskType + "Mask_1.png";
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (int i = 0; i < types.length; i++) {
			ItemStack stack = new ItemStack(itemIn, 1, i);
			subItems.add(stack);
		}
	}
}