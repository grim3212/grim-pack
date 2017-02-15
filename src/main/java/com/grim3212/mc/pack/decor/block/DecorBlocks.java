package com.grim3212.mc.pack.decor.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.item.ItemColorizer;
import com.grim3212.mc.pack.decor.item.ItemDecorStairs;
import com.grim3212.mc.pack.decor.item.ItemGrill;
import com.grim3212.mc.pack.decor.item.ItemLantern;
import com.grim3212.mc.pack.decor.item.ItemSloped;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class DecorBlocks implements IPartItems {

	public static Block calendar;
	public static Block wall_clock;
	public static Block light_bulb;
	public static Block pot;
	public static Block cage;
	public static Block chain;
	public static Block road;
	public static Block lantern;
	public static Block fancy_stone;
	public static Block craft_clay;
	public static Block craft_bone;
	public static Block counter;
	public static Block stool;
	public static Block chair;
	public static Block wall;
	public static Block table;
	public static Block fence;
	public static Block fence_gate;
	public static Block lamp_post_bottom;
	public static Block lamp_post_middle;
	public static Block lamp_post_top;
	public static Block fireplace;
	public static Block firering;
	public static Block firepit;
	public static Block chimney;
	public static Block stove;
	public static Block grill;
	public static Block hardened_wood;
	public static Block colorizer;
	public static Block colorizer_light;
	public static Block burning_wood;
	// Super slope shapes
	public static Block corner;
	public static Block slope;
	public static Block sloped_angle;
	public static Block slanted_corner;
	public static Block oblique_slope;
	public static Block sloped_intersection;
	public static Block pyramid;
	public static Block full_pyramid;
	public static Block sloped_post;
	public static Block decor_stairs;
	public static Block decor_door;
	public static Block pillar;

	@Override
	public void initItems() {
		calendar = (new BlockCalendar()).setHardness(1.0F).setUnlocalizedName("calendar").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		wall_clock = new BlockWallClock().setHardness(0.75F).setUnlocalizedName("wall_clock").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		light_bulb = (new BlockLightBulb()).setHardness(0.1F).setUnlocalizedName("light_bulb");
		lantern = (new BlockLantern().setHardness(0.1F).setLightLevel(0.9375F).setUnlocalizedName("lantern")).setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		road = (new BlockRoad()).setHardness(0.5F).setResistance(10F).setUnlocalizedName("road").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		fancy_stone = (new BlockFancyStone()).setHardness(0.5F).setResistance(10F).setUnlocalizedName("fancy_stone").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		chain = (new BlockDecoration(Material.CIRCUITS, false)).setUnlocalizedName("chain").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		cage = (new BlockDecoration(Material.IRON, true)).setHardness(0.8F).setResistance(5F).setUnlocalizedName("cage").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		pot = (new BlockPot()).setHardness(0.5F).setResistance(10F).setUnlocalizedName("pot").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		craft_clay = (new BlockCraftClay()).setUnlocalizedName("craft_clay").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		craft_bone = (new BlockCraftBone()).setUnlocalizedName("craft_bone").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		counter = (new BlockCounter()).setUnlocalizedName("counter");
		stool = (new BlockStool()).setUnlocalizedName("stool");
		chair = (new BlockChair()).setUnlocalizedName("chair");
		wall = (new BlockWall()).setUnlocalizedName("wall");
		fence = (new BlockFence()).setUnlocalizedName("fence");
		fence_gate = (new BlockFenceGate()).setUnlocalizedName("fence_gate");
		table = (new BlockTable()).setUnlocalizedName("table");
		lamp_post_bottom = (new BlockLampPost(false)).setUnlocalizedName("lamp_post_bottom");
		lamp_post_middle = (new BlockLampPost(false)).setUnlocalizedName("lamp_post_middle");
		lamp_post_top = new BlockLampPost(true).setUnlocalizedName("lamp_post_top");
		fireplace = new BlockFireplace().setUnlocalizedName("fireplace");
		firering = new BlockFirering().setUnlocalizedName("firering");
		firepit = new BlockFirepit().setUnlocalizedName("firepit");
		chimney = new BlockChimney().setUnlocalizedName("chimney");
		stove = new BlockStove().setUnlocalizedName("stove");
		grill = new BlockGrill().setUnlocalizedName("grill");
		hardened_wood = (new BlockHardenedWood()).setHardness(1.5F).setResistance(12F).setUnlocalizedName("hardened_wood").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		colorizer = (new BlockColorizer()).setUnlocalizedName("colorizer");
		colorizer_light = (new BlockColorizerLight()).setUnlocalizedName("colorizer_light");
		burning_wood = (new BlockBurningWood()).setHardness(0.8F).setResistance(5F).setUnlocalizedName("burning_wood").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		corner = (new BlockSlopedRotate(SlopeType.Corner)).setUnlocalizedName("corner");
		pyramid = (new BlockHedge(SlopeType.Pyramid)).setUnlocalizedName("pyramid");
		full_pyramid = (new BlockHedge(SlopeType.FullPyramid)).setUnlocalizedName("full_pyramid");
		slope = (new BlockSlopedRotate(SlopeType.Slope)).setUnlocalizedName("slope");
		sloped_angle = (new BlockSlopedRotate(SlopeType.SlopedAngle)).setUnlocalizedName("sloped_angle");
		slanted_corner = (new BlockSlopedRotate(SlopeType.SlantedCorner)).setUnlocalizedName("slanted_corner");
		oblique_slope = (new BlockSlopedRotate(SlopeType.ObliqueSlope)).setUnlocalizedName("oblique_slope");
		sloped_intersection = (new BlockSlopedRotate(SlopeType.SlopedIntersection)).setUnlocalizedName("sloped_intersection");
		sloped_post = (new BlockHedge(SlopeType.SlopedPost)).setUnlocalizedName("sloped_post");
		decor_stairs = (new BlockDecorStairs()).setUnlocalizedName("decor_stairs");
		decor_door = (new BlockDecorDoor()).setUnlocalizedName("decor_door");
		pillar = (new BlockDecorFacing()).setUnlocalizedName("pillar");

		Utils.registerBlock(calendar, "calendar");
		Utils.registerBlock(wall_clock, "wall_clock");
		Utils.registerBlock(light_bulb, "light_bulb");
		Utils.registerBlock(lamp_post_bottom, "lamp_post_bottom");
		Utils.registerBlock(lamp_post_middle, "lamp_post_middle");
		Utils.registerBlock(lamp_post_top, "lamp_post_top");
		Utils.registerBlock(road, "road");
		Utils.registerBlock(fancy_stone, "fancy_stone");
		Utils.registerBlock(chain, "chain");
		Utils.registerBlock(cage, "cage");
		Utils.registerBlock(pot, "pot");
		Utils.registerBlock(craft_clay, "craft_clay");
		Utils.registerBlock(craft_bone, "craft_bone");
		Utils.registerBlock(hardened_wood, "hardened_wood");
		Utils.registerBlock(burning_wood, "burning_wood");
		Utils.registerBlock(lantern, "lantern", new ItemLantern(lantern));
		Utils.registerBlock(colorizer, "colorizer", new ItemColorizer(colorizer));
		Utils.registerBlock(colorizer_light, "colorizer_light", new ItemColorizer(colorizer_light));
		Utils.registerBlock(grill, "grill", new ItemGrill(grill));
		Utils.registerBlock(fireplace, "fireplace", new ItemColorizer(fireplace));
		Utils.registerBlock(firering, "firering", new ItemColorizer(firering));
		Utils.registerBlock(firepit, "firepit", new ItemColorizer(firepit));
		Utils.registerBlock(chimney, "chimney", new ItemColorizer(chimney));
		Utils.registerBlock(stove, "stove", new ItemColorizer(stove));
		Utils.registerBlock(table, "table", new ItemColorizer(table));
		Utils.registerBlock(counter, "counter", new ItemColorizer(counter));
		Utils.registerBlock(stool, "stool", new ItemColorizer(stool));
		Utils.registerBlock(chair, "chair", new ItemColorizer(chair));
		Utils.registerBlock(wall, "wall", new ItemColorizer(wall));
		Utils.registerBlock(fence, "fence", new ItemColorizer(fence));
		Utils.registerBlock(fence_gate, "fence_gate", new ItemColorizer(fence_gate));
		Utils.registerBlock(corner, "corner", new ItemSloped(corner));
		Utils.registerBlock(slope, "slope", new ItemSloped(slope, true));
		Utils.registerBlock(sloped_angle, "sloped_angle", new ItemSloped(sloped_angle));
		Utils.registerBlock(slanted_corner, "slanted_corner", new ItemSloped(slanted_corner));
		Utils.registerBlock(oblique_slope, "oblique_slope", new ItemSloped(oblique_slope));
		Utils.registerBlock(sloped_intersection, "sloped_intersection", new ItemSloped(sloped_intersection));
		Utils.registerBlock(pyramid, "pyramid", new ItemSloped(pyramid));
		Utils.registerBlock(full_pyramid, "full_pyramid", new ItemSloped(full_pyramid));
		Utils.registerBlock(sloped_post, "sloped_post", new ItemSloped(sloped_post));
		Utils.registerBlock(pillar, "pillar", new ItemSloped(pillar));
		Utils.registerBlock(decor_stairs, "decor_stairs", new ItemDecorStairs(decor_stairs));
		Utils.registerBlock(decor_door, "decor_door");
	}

	public static IRecipe mossy;
	public static List<IRecipe> stone;
	public static List<IRecipe> chains;
	public static List<IRecipe> crafts = new ArrayList<IRecipe>();
	public static List<IRecipe> clocks;
	public static List<IRecipe> colorizers;
	public static List<IRecipe> lights = new ArrayList<IRecipe>();

	@Override
	public void addRecipes() {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(calendar, 1), new Object[] { "##", "##", "##", '#', Items.PAPER }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wall_clock, 1), new Object[] { "XIX", "IRI", "XIX", 'X', "plankWood", 'I', "ingotGold", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wall_clock, 1), new Object[] { "XXX", "XRX", "XXX", 'X', "plankWood", 'R', Items.CLOCK }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.CLOCK, 1), new Object[] { wall_clock }));
		clocks = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(light_bulb, 1, 0), new Object[] { "###", "#$#", " ! ", '#', "blockGlass", '$', Blocks.REDSTONE_TORCH, '!', "ingotIron" }));
		lights.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(craft_bone, 1), new Object[] { " # ", "###", "###", '#', Items.BONE }));
		crafts.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lantern, 1, 0), new Object[] { " # ", "#X#", '#', Items.PAPER, 'X', "dustGlowstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lantern, 1, 1), new Object[] { " # ", "#X#", '#', Items.BONE, 'X', "dustGlowstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lantern, 1, 2), new Object[] { " # ", "#X#", '#', "ingotIron", 'X', "dustGlowstone" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.MOSSY_COBBLESTONE, 8), new Object[] { "###", "#X#", "###", '#', Blocks.COBBLESTONE, 'X', Items.WATER_BUCKET }));
		mossy = RecipeHelper.getLatestIRecipe();

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fancy_stone, 1), new Object[] { "###", "# #", "###", '#', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.STONE, 8), new Object[] { "#", '#', fancy_stone }));
		stone = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cage, 2), new Object[] { "###", "# #", "###", '#', "stickIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chain, 2), new Object[] { "#", "#", '#', "stickIron" }));
		chains = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addSmelting(Blocks.GRAVEL, new ItemStack(road, 1), 0.15F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hardened_wood, 9), new Object[] { "###", "#W#", "###", '#', "stone", 'W', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(colorizer, 2), new Object[] { " # ", "#W#", " # ", '#', "dye", 'W', hardened_wood }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(colorizer_light, 4), new Object[] { " # ", "#W#", " # ", '#', colorizer, 'W', Items.GLOWSTONE_DUST }));
		colorizers = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(burning_wood, 5), new Object[] { " # ", "#W#", " # ", '#', "netherrack", 'W', "plankWood" }));

		addColorizerRecipe(corner, 4, "###", "## ", "#  ");
		addColorizerRecipe(slope, 4, "#  ", "## ", "###");
		addColorizerRecipe(sloped_angle, 5, " ##", "###");
		addColorizerRecipe(slanted_corner, 5, "  #", "  #", "###");
		addColorizerRecipe(oblique_slope, 4, "  #", "###");
		addColorizerRecipe(sloped_post, 6, "# ", "##", "##");
		addColorizerRecipe(sloped_intersection, 4, "## ", "# #");
		addColorizerRecipe(pyramid, 4, " # ", "###");
		addColorizerRecipe(full_pyramid, 4, " # ", " # ", "###");
		addColorizerRecipe(decor_stairs, 4, "  #", " ##", "###");
		addColorizerRecipe(stool, 4, "###", "S S");
		addColorizerRecipe(counter, 4, "###", " S ");
		addColorizerRecipe(table, 4, "###", "S S", "S S");
		addColorizerRecipe(chair, 4, "#  ", "###", "S S");
		addColorizerRecipe(wall, 4, "#", "#", "#");
		addColorizerRecipe(fence, 4, "###", "#S#");
		addColorizerRecipe(fence_gate, 4, "S#S", "SSS");
		addColorizerRecipe(DecorItems.lamp_item, 2, "#G#", "###", " # ");
		addColorizerRecipe(fireplace, 2, "###", "#P#", "###");
		addColorizerRecipe(chimney, 6, "# #", "# #", "#I#");
		addColorizerRecipe(firepit, 2, " I ", "#P#", "###");
		addColorizerRecipe(firering, 2, " # ", "#P#", " # ");
		addColorizerRecipe(stove, 2, "###", "BPB", "###");
		addColorizerRecipe(grill, 2, "#C#", "#P#", " # ");
		addColorizerRecipe(grill, 2, "#H#", "#P#", " # ");
		addColorizerRecipe(pillar, 2, "#", "S", "#");
		addColorizerRecipe(DecorItems.decor_door_item, 3, "##", "##", "##");
	}

	private void addColorizerRecipe(Block furnType, int amount, String... pattern) {
		addColorizerRecipe(Item.getItemFromBlock(furnType), amount, pattern);
	}

	private void addColorizerRecipe(Item furnType, int amount, String... pattern) {
		ItemStack itemstack = new ItemStack(furnType, amount);
		NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
		NBTHelper.setInteger(itemstack, "meta", 0);

		GameRegistry.addRecipe(new ShapedOreRecipe(itemstack, new Object[] { pattern, '#', colorizer, 'S', "stickWood", 'G', "glowstone", 'P', burning_wood, 'I', "ingotIron", 'C', Items.COAL, 'H', new ItemStack(Items.COAL, 1, 1), 'B', Blocks.IRON_BARS }));
	}
}
