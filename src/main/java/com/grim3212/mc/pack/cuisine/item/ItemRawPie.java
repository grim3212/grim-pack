package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManualFood;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.item.Item;

public class ItemRawPie extends ItemManualFood {

	public ItemRawPie(String name) {
		super(name, "cuisine:pie.craft", new Item.Properties().group(GrimItemGroups.GRIM_CUISINE).food(CuisineFoods.RAW_PIE));
	}

}
