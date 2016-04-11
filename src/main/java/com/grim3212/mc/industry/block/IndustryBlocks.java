package com.grim3212.mc.industry.block;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.industry.GrimIndustry;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IndustryBlocks implements IPartItems {

	public static Block togglerack;

	@Override
	public void initItems() {
		togglerack = new BlockToggleRack().setUnlocalizedName("togglerack").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());

		GameRegistry.registerBlock(togglerack, "togglerack");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(togglerack, 4), new Object[] { "QQQ", "QAQ", "QQQ", 'A', "dustRedstone", 'Q', Blocks.netherrack }));
	}

}
