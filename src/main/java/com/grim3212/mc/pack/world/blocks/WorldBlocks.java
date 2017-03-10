package com.grim3212.mc.pack.world.blocks;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.world.GrimWorld;
import com.grim3212.mc.pack.world.items.ItemFungus;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class WorldBlocks implements IPartItems {

	public static Block randomite;
	public static Block corruption_block;
	public static BlockGunpowderReed gunpowder_reed_block;
	public static Block glowstone_seeds;
	public static Block fungus_growing;
	public static Block fungus_building;
	public static Block fungus_layer_building;
	public static Block fungus_ore_building;
	public static Block fungus_killing;
	public static Block fungus_maze;

	@Override
	public void initItems() {
		randomite = (new BlockRandomite()).setHardness(1.6F).setResistance(1.0F).setUnlocalizedName("randomite").setCreativeTab(GrimWorld.INSTANCE.getCreativeTab());
		gunpowder_reed_block = (BlockGunpowderReed) (new BlockGunpowderReed()).setHardness(0.0F).setLightLevel(0.5F).setResistance(200F).setUnlocalizedName("gunpowder_reed_block");
		glowstone_seeds = new BlockGlowstoneSeed().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("glowstone_seeds").setLightLevel(0.8F);
		corruption_block = (new BlockCorruption()).setHardness(1.0F).setUnlocalizedName("corruption_block");
		fungus_growing = new BlockFungusGrowing().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_growing").setLightLevel(0.8F).setLightOpacity(10);
		fungus_building = new BlockFungusBuilding().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_building").setLightLevel(0.8F).setLightOpacity(10);
		fungus_ore_building = new BlockFungusOre().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_ore_building").setLightLevel(0.8F).setLightOpacity(10);
		fungus_layer_building = new BlockFungusLayer().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_layer_building").setLightLevel(0.8F).setLightOpacity(10);
		fungus_killing = new BlockFungusKilling().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_killing").setLightLevel(0.8F).setLightOpacity(10);
		fungus_maze = new BlockFungusMaze().setHardness(0.0F).setResistance(0.0F).setUnlocalizedName("fungus_maze").setLightLevel(0.8F).setLightOpacity(10);

		Utils.registerBlock(fungus_growing, "fungus_growing", new ItemFungus(fungus_growing));
		Utils.registerBlock(fungus_building, "fungus_building", new ItemFungus(fungus_building));
		Utils.registerBlock(fungus_ore_building, "fungus_ore_building", new ItemFungus(fungus_ore_building));
		Utils.registerBlock(fungus_layer_building, "fungus_layer_building", new ItemFungus(fungus_layer_building));
		Utils.registerBlock(fungus_killing, "fungus_killing", new ItemFungus(fungus_killing));
		Utils.registerBlock(fungus_maze, "fungus_maze", new ItemFungus(fungus_maze));
		Utils.registerBlock(corruption_block, "corruption_block");
		Utils.registerBlock(glowstone_seeds, "glowstone_seeds");
		Utils.registerBlock(gunpowder_reed_block, "gunpowder_reed_block");
		Utils.registerBlock(randomite, "randomite");
	}

	//public static IRecipe mazeFungusRecipe;

	public static List<IRecipe> greenFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> coloredFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> buildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> orebuildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> layerbuildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> acidFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> breakingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> vertFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> mazeFungus = new ArrayList<IRecipe>();

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("blockFungus", fungus_ore_building);
		OreDictionary.registerOre("blockFungus", fungus_building);
		OreDictionary.registerOre("blockFungus", fungus_growing);
		OreDictionary.registerOre("blockFungus", fungus_killing);
		OreDictionary.registerOre("blockFungus", fungus_layer_building);
		OreDictionary.registerOre("blockFungus", fungus_maze);
		OreDictionary.registerOre("oreRandomite", randomite);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(glowstone_seeds), new Object[] { "GSG", " G ", 'G', "dustGlowstone", 'S', Blocks.SOUL_SAND }));

		// ====================BASIC==================== //
		if (Loader.isModLoaded("TConstruct")) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_growing, 2, 0), new Object[] { "XXX", "X X", "XXX", 'X', "slimeball" }));
		} else {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_growing, 1, 0), new Object[] { "XX", "XX", 'X', "slimeball" }));
		}
		greenFungus.add(RecipeHelper.getLatestIRecipe());

		// Colored
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 1), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeYellow" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 2), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeRed" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 3), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 4), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyePurple" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 5), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeOrange" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 6), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyePink" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 7), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeCyan" }));
		coloredFungus.addAll(RecipeHelper.getLatestIRecipes(7));

		// rapid
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_growing, 1, 8), new Object[] { "XXX", "XXX", "XXX", 'X', "slimeball" }));
		greenFungus.add(RecipeHelper.getLatestIRecipe());
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 9), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeYellow" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 10), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeRed" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 11), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeBlue" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 12), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyePurple" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 13), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeOrange" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 14), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyePink" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_growing, 1, 15), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeCyan" }));
		coloredFungus.addAll(RecipeHelper.getLatestIRecipes(7));
		// ====================END-BASIC==================== //

		// ====================BUILDERS==================== //
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 0), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 1), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "dirt" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 2), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 3), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "sand" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 4), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "netherrack" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 5), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 6), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 7), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "sandstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 8), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Items.LAVA_BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 9), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.GLOWSTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 10), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.CLAY }));

		buildingFungus.addAll(RecipeHelper.getLatestIRecipes(11));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 1, 11), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', WorldBlocks.randomite }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 3, 12), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.LEAVES }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 3, 13), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.GRAVEL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 3, 14), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SNOW }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_building, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.END_STONE }));
		
		//Ore building
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_ore_building, 1), new Object[] { "DRQ", "EFG", "ILC", 'F', new ItemStack(fungus_building, 1, 11), 'Q', Blocks.QUARTZ_ORE, 'I', Blocks.IRON_ORE, 'C', Blocks.COAL_ORE, 'E', Blocks.EMERALD_ORE, 'R', Blocks.REDSTONE_ORE, 'D', Blocks.DIAMOND_ORE, 'L', Blocks.LAPIS_ORE, 'G', Blocks.GOLD_ORE }));
		
		// glass builder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_killing, 1, 7), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "blockGlass" }));
		// glass builder layer
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_killing, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "blockGlass" }));
		buildingFungus.addAll(RecipeHelper.getLatestIRecipes(8));
		
		// ====================END-BUILDERS==================== //
		
		// ====================LAYER BUILDERS==================== //
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 0), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.MOSSY_COBBLESTONE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 1), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "dirt" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 2), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 3), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "sand" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 4), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "netherrack" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 5), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "cobblestone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 6), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 7), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "sandstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 8), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.GRAVEL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 9), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.OBSIDIAN }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 10), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SNOW }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 11), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SOUL_SAND }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 12), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PRISMARINE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 13), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.MAGMA }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 14), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PLANKS }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_layer_building, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PACKED_ICE }));

		layerbuildingFungus.addAll(RecipeHelper.getLatestIRecipes(16));
		
		//Maze
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_maze, 1, 0), new Object[] { "XXX", "ara", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK, 'r', "dustRedstone" }));
		mazeFungus.addAll(RecipeHelper.getLatestIRecipes(1));

		// ====================EATERS==================== //
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 0), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 1), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeBlack" }));

		// alternate killer recipe
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fungus_killing, 1, 1), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "dyeBlack" }));
		acidFungus.addAll(RecipeHelper.getLatestIRecipes(3));

		// block eaters
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 2), new Object[] { new ItemStack(fungus_killing, 1, 0), "dirt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 3), new Object[] { new ItemStack(fungus_killing, 1, 0), "stone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 4), new Object[] { new ItemStack(fungus_killing, 1, 0), "treeLeaves" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 4), new Object[] { new ItemStack(fungus_killing, 1, 0), Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 5), new Object[] { new ItemStack(fungus_killing, 1, 0), "sand" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 5), new Object[] { new ItemStack(fungus_killing, 1, 0), "gravel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "cobblestone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), Blocks.STONEBRICK }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "netherrack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "sandstone" }));

		// alt block eater
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 2), new Object[] { new ItemStack(fungus_killing, 1, 1), "dirt" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 3), new Object[] { new ItemStack(fungus_killing, 1, 1), "stone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 4), new Object[] { new ItemStack(fungus_killing, 1, 1), "treeLeaves" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 4), new Object[] { new ItemStack(fungus_killing, 1, 1), Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 5), new Object[] { new ItemStack(fungus_killing, 1, 1), "sand" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 5), new Object[] { new ItemStack(fungus_killing, 1, 1), "gravel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "cobblestone" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), Blocks.STONEBRICK }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "netherrack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "sandstone" }));

		// forest killing blob
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 14), new Object[] { new ItemStack(fungus_killing, 1, 0), "treeSapling" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 14), new Object[] { new ItemStack(fungus_killing, 1, 1), "treeSapling" }));
		breakingFungus.addAll(RecipeHelper.getLatestIRecipes(22));

		// killer up
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 8), new Object[] { new ItemStack(fungus_killing, 1, 2), "slimeball" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 9), new Object[] { new ItemStack(fungus_killing, 1, 3), "slimeball" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 10), new Object[] { new ItemStack(fungus_killing, 1, 4), "slimeball" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 11), new Object[] { new ItemStack(fungus_killing, 1, 5), "slimeball" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 12), new Object[] { new ItemStack(fungus_killing, 1, 6), "slimeball" }));

		// all eater up
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 1, 13), new Object[] { new ItemStack(fungus_killing, 1, 0), "dyeBlack" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fungus_killing, 2, 13), new Object[] { new ItemStack(fungus_killing, 1, 1), "dyeBlack" }));
		vertFungus.addAll(RecipeHelper.getLatestIRecipes(7));
		// ====================END-EATERS==================== //
	}

}
