package com.grim3212.mc.pack.cuisine.item;

import com.grim3212.mc.pack.core.item.ItemManualFood;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class ItemRawPie extends ItemManualFood {

	public ItemRawPie(String name) {
		super(name, 2, 0.3f, false, "cuisine:pie.craft");
		setPotionEffect(new PotionEffect(MobEffects.HUNGER, 300, 0), 0.1F);
		setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	}

}
