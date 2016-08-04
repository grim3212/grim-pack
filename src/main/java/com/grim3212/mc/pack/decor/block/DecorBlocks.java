package com.grim3212.mc.pack.decor.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.block.BlockSound;
import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.item.ItemColorizer;
import com.grim3212.mc.pack.decor.item.ItemGrill;
import com.grim3212.mc.pack.decor.item.ItemLantern;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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

	@Override
	public void initItems() {
		calendar = (new BlockCalendar()).setHardness(1.0F).setUnlocalizedName("calendar").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		wall_clock = new BlockWallClock().setHardness(0.75F).setUnlocalizedName("wall_clock").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		light_bulb = (new BlockLightBulb()).setHardness(0.1F).setUnlocalizedName("light_bulb");
		lantern = (new BlockLantern().setHardness(0.1F).setLightLevel(0.9375F).setUnlocalizedName("lantern")).setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		road = (new BlockRoad()).setHardness(0.5F).setResistance(10F).setUnlocalizedName("road").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		fancy_stone = (new BlockSound(Material.ROCK, SoundType.STONE)).setHardness(0.5F).setResistance(10F).setUnlocalizedName("fancy_stone").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
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
		hardened_wood = (new BlockSound(Material.ROCK, SoundType.STONE)).setHardness(1.5F).setResistance(12F).setUnlocalizedName("hardened_wood").setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		colorizer = (new BlockColorizer()).setUnlocalizedName("colorizer");

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
		Utils.registerBlock(colorizer, "colorizer", new ItemColorizer(colorizer));
		Utils.registerBlock(grill, "grill", new ItemGrill(grill));
		Utils.registerBlock(lantern, "lantern", new ItemLantern(lantern));
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
	}

	public static IRecipe mossy;
	public static List<IRecipe> stone;
	public static List<IRecipe> chains;
	public static List<IRecipe> crafts = new ArrayList<IRecipe>();
	public static List<IRecipe> clocks;
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

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cage, 2), new Object[] { "###", "# #", "###", '#', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chain, 2), new Object[] { "#", "#", '#', "ingotIron" }));
		chains = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addSmelting(Blocks.GRAVEL, new ItemStack(road, 1), 0.15F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hardened_wood, 9), new Object[] { "###", "#W#", "###", '#', "stone", 'W', "plankWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(colorizer, 4), new Object[] { " # ", "#W#", " # ", '#', "dye", 'W', hardened_wood }));

		addFurnitureRecipe(stool, 4, "###", "S S");
		addFurnitureRecipe(counter, 4, "###", " S ");
		addFurnitureRecipe(table, 4, "###", "S S", "S S");
		addFurnitureRecipe(chair, 4, "#  ", "###", "S S");
		addFurnitureRecipe(wall, 4, "#", "#", "#");
		addFurnitureRecipe(fence, 4, "###", "#S#");
		addFurnitureRecipe(fence_gate, 4, "S#S", "SSS");
		addFurnitureRecipe(DecorItems.lamp_item, 1, "#G#", "###", " # ");
		addFurnitureRecipe(fireplace, 1, "###", "#P#", "###");
		addFurnitureRecipe(chimney, 6, "# #", "# #", "#I#");
		addFurnitureRecipe(firepit, 1, " I ", "#P#", "###");
		addFurnitureRecipe(firering, 1, " # ", "#P#", " # ");
		addFurnitureRecipe(stove, 1, "###", "BPB", "###");
		addFurnitureRecipe(grill, 1, "#C#", "###", " # ");
		addFurnitureRecipe(grill, 1, "#H#", "###", " # ");
	}

	private void addFurnitureRecipe(Block furnType, int amount, String... pattern) {
		addFurnitureRecipe(Item.getItemFromBlock(furnType), amount, pattern);
	}

	private void addFurnitureRecipe(Item furnType, int amount, String... pattern) {
		GameRegistry.addRecipe(new ShapedOreRecipe(furnType, new Object[] { pattern, '#', colorizer, 'S', "stickWood", 'G', "glowstone", 'P', "plankWood", 'I', "ingotIron", 'C', Items.COAL, 'H', new ItemStack(Items.COAL, 1, 1), 'B', Blocks.IRON_BARS }));
	}
}
