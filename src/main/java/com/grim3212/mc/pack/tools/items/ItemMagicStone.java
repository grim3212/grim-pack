package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMagicStone extends ItemManual {

	public ItemMagicStone() {
		super("magic_stone");
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			// Stone type
			for (int i = 0; i < EnumCrystalType.values.length; ++i) {
				// Core type
				for (int j = 0; j < EnumCrystalType.values.length; ++j) {

					// Calculate metadata using Cantor pairing function
					ItemStack stone = new ItemStack(this, 1, EnumCrystalType.values.length * i + j);
					items.add(stone);
				}
			}
		}
	}

	public static class StoneType {
		public final EnumCrystalType mainType;
		public final EnumCrystalType subType;

		public StoneType(EnumCrystalType mainType, EnumCrystalType subType) {
			this.mainType = mainType;
			this.subType = subType;
		}

		public static StoneType getStoneFromMeta(int meta) {
			EnumCrystalType mainType = EnumCrystalType.values[(meta / EnumCrystalType.values.length)];
			EnumCrystalType subType = EnumCrystalType.values[(meta % EnumCrystalType.values.length)];

			return new StoneType(mainType, subType);
		}

		public static int getMetaFromStoneType(StoneType type) {
			return EnumCrystalType.values.length * type.mainType.ordinal() + type.subType.ordinal();
		}

		public int getMeta() {
			return EnumCrystalType.values.length * this.mainType.ordinal() + this.subType.ordinal();
		}

		@Override
		public boolean equals(Object obj) {
			StoneType type = (StoneType) obj;

			return this.mainType == type.mainType && this.subType == type.subType;
		}
		
		@Override
		public int hashCode() {
			return EnumCrystalType.values.length * this.mainType.ordinal() + this.subType.ordinal();
		}

		@Override
		public String toString() {
			return "Main: [" + this.mainType.getUnlocalized() + "], Sub: [" + this.subType.getUnlocalized() + "]";
		}
	}
}
