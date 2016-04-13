package com.grim3212.mc.industry.item;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.industry.GrimIndustry;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IndustryItems implements IPartItems {

	public static Item water_bowl;

	public static List<IRecipe> ice = new ArrayList<IRecipe>();

	@Override
	public void initItems() {
		water_bowl = (new Item()).setUnlocalizedName("water_bowl").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setMaxStackSize(1);

		GameRegistry.registerItem(water_bowl, "water_bowl");
	}

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(water_bowl, 1), new Object[] { "X", "M", 'X', Items.water_bucket, 'M', Items.bowl }));
		ice.add(RecipeHelper.getLatestIRecipe());
	}

}
