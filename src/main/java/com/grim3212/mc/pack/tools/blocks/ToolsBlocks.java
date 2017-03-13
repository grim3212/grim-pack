package com.grim3212.mc.pack.tools.blocks;

import java.util.List;

import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolsBlocks implements IPartItems {

	public static Block black_diamond_block;
	public static Block black_diamond_ore;
	public static Block element_115_ore;

	@Override
	public void initItems() {
		black_diamond_ore = (new BlockBlackDiamond(Material.ROCK, SoundType.STONE)).setHardness(2.0F).setResistance(5F).setUnlocalizedName("black_diamond_ore").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		black_diamond_block = (new BlockBlackDiamond(Material.IRON, SoundType.METAL)).setHardness(2.0F).setResistance(5F).setUnlocalizedName("black_diamond_block").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
		element_115_ore = (new BlockElement()).setHardness(2.5F).setResistance(30.0F).setLightLevel(0.4F).setUnlocalizedName("element_115_ore").setCreativeTab(GrimTools.INSTANCE.getCreativeTab());

		Utils.registerBlock(element_115_ore, "element_115_ore");
		Utils.registerBlock(black_diamond_ore, "black_diamond_ore");
		Utils.registerBlock(black_diamond_block, "black_diamond_block");
		
		OreDictionary.registerOre("oreBlackDiamond", black_diamond_ore);
		OreDictionary.registerOre("blockBlackDiamond", black_diamond_block);
		OreDictionary.registerOre("oreElement115", element_115_ore);
	}

	public static List<IRecipe> black;

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(black_diamond_block, 1), new Object[] { "###", "###", "###", '#', ToolsItems.black_diamond }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ToolsItems.black_diamond, 9), new Object[] { "#", '#', black_diamond_block }));
		black = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addSmelting(black_diamond_ore, new ItemStack(ToolsItems.black_diamond, 1), 1.0F);
	}

}
