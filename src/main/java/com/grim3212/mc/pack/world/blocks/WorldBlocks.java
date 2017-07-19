package com.grim3212.mc.pack.world.blocks;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.world.items.ItemFungus;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class WorldBlocks {

	public static final Block randomite = new BlockRandomite();
	public static final Block corruption_block = new BlockCorruption();
	public static final BlockGunpowderReed gunpowder_reed_block = new BlockGunpowderReed();
	public static final Block glowstone_seeds = new BlockGlowstoneSeed();
	public static final Block fungus_growing = new BlockFungusGrowing();
	public static final Block fungus_building = new BlockFungusBuilding();
	public static final Block fungus_layer_building = new BlockFungusLayer();
	public static final Block fungus_ore_building = new BlockFungusOre();
	public static final Block fungus_killing = new BlockFungusKilling();
	public static final Block fungus_maze = new BlockFungusMaze();

	@SubscribeEvent
	public static void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		r.register(randomite);
		r.register(corruption_block);
		r.register(gunpowder_reed_block);
		r.register(glowstone_seeds);
		r.register(fungus_growing);
		r.register(fungus_building);
		r.register(fungus_layer_building);
		r.register(fungus_ore_building);
		r.register(fungus_killing);
		r.register(fungus_maze);
	}

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(new ItemManualBlock(corruption_block).setRegistryName(corruption_block.getRegistryName()));
		r.register(new ItemManualBlock(glowstone_seeds).setRegistryName(glowstone_seeds.getRegistryName()));
		r.register(new ItemManualBlock(gunpowder_reed_block).setRegistryName(gunpowder_reed_block.getRegistryName()));
		r.register(new ItemManualBlock(randomite).setRegistryName(randomite.getRegistryName()));
		r.register(new ItemFungus(fungus_growing).setRegistryName(fungus_growing.getRegistryName()));
		r.register(new ItemFungus(fungus_building).setRegistryName(fungus_building.getRegistryName()));
		r.register(new ItemFungus(fungus_ore_building).setRegistryName(fungus_ore_building.getRegistryName()));
		r.register(new ItemFungus(fungus_layer_building).setRegistryName(fungus_layer_building.getRegistryName()));
		r.register(new ItemFungus(fungus_killing).setRegistryName(fungus_killing.getRegistryName()));
		r.register(new ItemFungus(fungus_maze).setRegistryName(fungus_maze.getRegistryName()));

		initOreDict();
	}

	private static void initOreDict() {
		OreDictionary.registerOre("blockFungus", fungus_ore_building);
		OreDictionary.registerOre("blockFungus", fungus_building);
		OreDictionary.registerOre("blockFungus", fungus_growing);
		OreDictionary.registerOre("blockFungus", fungus_killing);
		OreDictionary.registerOre("blockFungus", fungus_layer_building);
		OreDictionary.registerOre("blockFungus", fungus_maze);
		OreDictionary.registerOre("oreRandomite", randomite);
	}

	// public static IRecipe mazeFungusRecipe;

	public static List<IRecipe> greenFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> coloredFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> buildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> orebuildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> layerbuildingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> acidFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> breakingFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> vertFungus = new ArrayList<IRecipe>();
	public static List<IRecipe> mazeFungus = new ArrayList<IRecipe>();

	public static void addRecipes() {
		CreateRecipes.addShapedRecipe(new ItemStack(glowstone_seeds), new Object[] { "GSG", " G ", 'G', "dustGlowstone", 'S', Blocks.SOUL_SAND });

		// ====================BASIC==================== //
		if (Loader.isModLoaded("TConstruct")) {
			CreateRecipes.addShapedRecipe(new ItemStack(fungus_growing, 2, 0), new Object[] { "XXX", "X X", "XXX", 'X', "slimeball" });
		} else {
			CreateRecipes.addShapedRecipe(new ItemStack(fungus_growing, 1, 0), new Object[] { "XX", "XX", 'X', "slimeball" });
		}
		// greenFungus.add(RecipeHelper.getLatestIRecipe());

		// Colored
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 1), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeYellow" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 2), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeRed" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 3), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeBlue" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 4), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyePurple" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 5), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeOrange" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 6), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyePink" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 7), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeCyan" });
		// coloredFungus.addAll(RecipeHelper.getLatestIRecipes(7));

		// rapid
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_growing, 1, 8), new Object[] { "XXX", "XXX", "XXX", 'X', "slimeball" });
		// greenFungus.add(RecipeHelper.getLatestIRecipe());
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 9), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeYellow" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 10), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeRed" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 11), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeBlue" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 12), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyePurple" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 13), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeOrange" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 14), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyePink" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_growing, 1, 15), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeCyan" });
		// coloredFungus.addAll(RecipeHelper.getLatestIRecipes(7));
		// ====================END-BASIC==================== //

		// ====================BUILDERS==================== //
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 0), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Items.WATER_BUCKET });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 1), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "dirt" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 2), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 3), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "sand" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 4), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "netherrack" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 5), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "cobblestone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 6), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 7), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "sandstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 8), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Items.LAVA_BUCKET });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 9), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.GLOWSTONE });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 10), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', Blocks.CLAY });

		// buildingFungus.addAll(RecipeHelper.getLatestIRecipes(11));

		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 1, 11), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', WorldBlocks.randomite });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 3, 12), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.LEAVES });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 3, 13), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.GRAVEL });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 3, 14), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SNOW });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_building, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.END_STONE });

		// Ore building
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_ore_building, 1), new Object[] { "DRQ", "EFG", "ILC", 'F', new ItemStack(fungus_building, 1, 11), 'Q', Blocks.QUARTZ_ORE, 'I', Blocks.IRON_ORE, 'C', Blocks.COAL_ORE, 'E', Blocks.EMERALD_ORE, 'R', Blocks.REDSTONE_ORE, 'D', Blocks.DIAMOND_ORE, 'L', Blocks.LAPIS_ORE, 'G', Blocks.GOLD_ORE });

		// glass builder
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_killing, 1, 7), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "blockGlass" });
		// glass builder layer
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_killing, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "blockGlass" });
		// buildingFungus.addAll(RecipeHelper.getLatestIRecipes(8));

		// ====================END-BUILDERS==================== //

		// ====================LAYER BUILDERS==================== //
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 0), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.MOSSY_COBBLESTONE });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 1), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "dirt" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 2), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 3), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "sand" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 4), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "netherrack" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 5), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "cobblestone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 6), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 7), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', "sandstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 8), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.GRAVEL });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 9), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.OBSIDIAN });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 10), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SNOW });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 11), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.SOUL_SAND });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 12), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PRISMARINE });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 13), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.MAGMA });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 14), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PLANKS });
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_layer_building, 3, 15), new Object[] { "XXX", "aaa", "XXX", 'X', "slimeball", 'a', Blocks.PACKED_ICE });

		// layerbuildingFungus.addAll(RecipeHelper.getLatestIRecipes(16));

		// Maze
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_maze, 1, 0), new Object[] { "XXX", "ara", "XXX", 'X', "slimeball", 'a', Blocks.STONEBRICK, 'r', "dustRedstone" });
		// mazeFungus.addAll(RecipeHelper.getLatestIRecipes(1));

		// ====================EATERS==================== //
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 0), new Object[] { new ItemStack(fungus_growing, 1, 0), "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 1), new Object[] { new ItemStack(fungus_growing, 1, 8), "dyeBlack" });

		// alternate killer recipe
		CreateRecipes.addShapedRecipe(new ItemStack(fungus_killing, 1, 1), new Object[] { "XXX", "XaX", "XXX", 'X', "slimeball", 'a', "dyeBlack" });
		// acidFungus.addAll(RecipeHelper.getLatestIRecipes(3));

		// block eaters
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 2), new Object[] { new ItemStack(fungus_killing, 1, 0), "dirt" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 3), new Object[] { new ItemStack(fungus_killing, 1, 0), "stone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 4), new Object[] { new ItemStack(fungus_killing, 1, 0), "treeLeaves" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 4), new Object[] { new ItemStack(fungus_killing, 1, 0), Items.WATER_BUCKET });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 5), new Object[] { new ItemStack(fungus_killing, 1, 0), "sand" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 5), new Object[] { new ItemStack(fungus_killing, 1, 0), "gravel" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "cobblestone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), Blocks.STONEBRICK });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "netherrack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 6), new Object[] { new ItemStack(fungus_killing, 1, 0), "sandstone" });

		// alt block eater
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 2), new Object[] { new ItemStack(fungus_killing, 1, 1), "dirt" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 3), new Object[] { new ItemStack(fungus_killing, 1, 1), "stone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 4), new Object[] { new ItemStack(fungus_killing, 1, 1), "treeLeaves" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 4), new Object[] { new ItemStack(fungus_killing, 1, 1), Items.WATER_BUCKET });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 5), new Object[] { new ItemStack(fungus_killing, 1, 1), "sand" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 5), new Object[] { new ItemStack(fungus_killing, 1, 1), "gravel" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "cobblestone" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), Blocks.STONEBRICK });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "netherrack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 6), new Object[] { new ItemStack(fungus_killing, 1, 1), "sandstone" });

		// forest killing blob
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 14), new Object[] { new ItemStack(fungus_killing, 1, 0), "treeSapling" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 14), new Object[] { new ItemStack(fungus_killing, 1, 1), "treeSapling" });
		// breakingFungus.addAll(RecipeHelper.getLatestIRecipes(22));

		// killer up
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 8), new Object[] { new ItemStack(fungus_killing, 1, 2), "slimeball" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 9), new Object[] { new ItemStack(fungus_killing, 1, 3), "slimeball" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 10), new Object[] { new ItemStack(fungus_killing, 1, 4), "slimeball" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 11), new Object[] { new ItemStack(fungus_killing, 1, 5), "slimeball" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 12), new Object[] { new ItemStack(fungus_killing, 1, 6), "slimeball" });

		// all eater up
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 1, 13), new Object[] { new ItemStack(fungus_killing, 1, 0), "dyeBlack" });
		CreateRecipes.addShapelessRecipe(new ItemStack(fungus_killing, 2, 13), new Object[] { new ItemStack(fungus_killing, 1, 1), "dyeBlack" });
		// vertFungus.addAll(RecipeHelper.getLatestIRecipes(7));
		// ====================END-EATERS==================== //
	}

}
