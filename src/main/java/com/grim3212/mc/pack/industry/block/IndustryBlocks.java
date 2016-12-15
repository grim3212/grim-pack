package com.grim3212.mc.pack.industry.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.block.BlockElemental.ElementType;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.item.ItemSensor;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class IndustryBlocks implements IPartItems {

	public static Block togglerack;
	public static Block iron_workbench;
	public static Block diamond_workbench;
	public static Block ice_maker;
	public static Block fire_block;
	public static Block water_block;
	public static Block lava_block;
	public static Block spike;
	public static Block wooden_sensor;
	public static Block stone_sensor;
	public static Block iron_sensor;
	public static Block netherrack_sensor;
	public static Block attractor;
	public static Block repulsor;
	public static Block gravitor;
	public static Block direction_attractor;
	public static Block direction_repulsor;
	public static Block direction_gravitor;
	public static Block reactor;
	public static Block bomb_shell;
	public static Block c4;
	public static Block nuclear_bomb;
	public static Block uranium_ore;
	public static Block castle_gate;
	public static Block garage;
	public static Block halogen_light;
	public static Block halogen_torch;
	public static Block sidewalk;
	public static Block rway;
	public static Block rway_light_on;
	public static Block rway_light_off;
	public static Block rway_manhole;
	public static Block door_chain;
	public static Block door_glass;
	public static Block door_steel;
	public static Block concrete;
	public static Block modern_tile;
	public static Block tempered_glass;
	public static Block horizontal_siding;
	public static Block vertical_siding;
	public static Block fountain;
	public static Block camo_plate;
	public static Block chain_fence;
	public static Block aluminum_ladder;
	public static Block aluminum_ore;
	public static Block oil_ore;
	public static Block derrick;
	public static Block modern_furnace;
	public static Block steel_block;
	public static Block steel_pipe;
	public static Block refinery;
	public static Block steel_frame;
	public static Block fuel_tank;
	public static Block fan;
	public static Block specific_sensor;
	public static Block upgraded_specific_sensor;

	@Override
	public void initItems() {
		modern_tile = (new BlockManualPage(Material.ROCK, SoundType.STONE, "industry:metalworks.mfurnace_recipes")).setHardness(1.0F).setResistance(15.0F).setUnlocalizedName("modern_tile").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		tempered_glass = (new BlockTemperedGlass(Material.GLASS, false)).setHardness(1.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("tempered_glass").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		horizontal_siding = (new BlockSiding()).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("horizontal_siding").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		vertical_siding = (new BlockSiding()).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("vertical_siding").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		door_chain = (new BlockModernDoor(Material.CIRCUITS)).setHardness(0.5F).setResistance(5.0F).setUnlocalizedName("door_chain");
		door_glass = (new BlockModernDoor(Material.GLASS)).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("door_glass");
		door_steel = (new BlockModernDoor(Material.ANVIL)).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("door_steel");
		concrete = (new BlockManualPage(Material.ROCK, SoundType.STONE, "industry:moderntech.decoration")).setHardness(1.0F).setResistance(30.0F).setUnlocalizedName("concrete").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		togglerack = new BlockToggleRack().setUnlocalizedName("togglerack").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_workbench = (new BlockIronWorkbench()).setHardness(3F).setResistance(5F).setUnlocalizedName("iron_workbench").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		diamond_workbench = (new BlockDiamondWorkbench()).setHardness(5F).setResistance(5F).setUnlocalizedName("diamond_workbench").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		ice_maker = (new BlockIceMaker()).setHardness(1.0F).setUnlocalizedName("ice_maker").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fire_block = (new BlockElemental(ElementType.FIRE)).setHardness(2.0F).setUnlocalizedName("fire_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		water_block = (new BlockElemental(ElementType.WATER)).setHardness(2.0F).setUnlocalizedName("water_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		lava_block = (new BlockElemental(ElementType.LAVA)).setHardness(2.0F).setUnlocalizedName("lava_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		spike = (new BlockSpike()).setHardness(1.5F).setResistance(10F).setUnlocalizedName("spike").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		wooden_sensor = (new BlockSensor(0)).setHardness(0.5F).setResistance(10F).setUnlocalizedName("wooden_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		stone_sensor = (new BlockSensor(1)).setHardness(0.5F).setResistance(10F).setUnlocalizedName("stone_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		iron_sensor = (new BlockSensor(2)).setHardness(0.5F).setResistance(10F).setUnlocalizedName("iron_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		netherrack_sensor = (new BlockSensor(3)).setHardness(0.5F).setResistance(10F).setUnlocalizedName("netherrack_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		attractor = (new BlockGravity(-1)).setHardness(0.3F).setUnlocalizedName("attractor");
		repulsor = (new BlockGravity(1)).setHardness(0.3F).setUnlocalizedName("repulsor");
		gravitor = (new BlockGravity(0)).setHardness(0.3F).setUnlocalizedName("gravitor");
		direction_attractor = (new BlockDGravity(-1)).setHardness(0.3F).setUnlocalizedName("direction_attractor");
		direction_repulsor = (new BlockDGravity(1)).setHardness(0.3F).setUnlocalizedName("direction_repulsor");
		direction_gravitor = (new BlockDGravity(0)).setHardness(0.3F).setUnlocalizedName("direction_gravitor");
		nuclear_bomb = (new BlockNuclearBomb()).setHardness(1.0F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("nuclear_bomb");
		reactor = (new BlockNuclearReactor()).setHardness(1.0F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("reactor");
		bomb_shell = (new BlockManualPage(Material.IRON, SoundType.METAL, "industry:explosives.bomb_shell")).setHardness(1.0F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("bomb_shell");
		c4 = (new BlockC4()).setHardness(1.0F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("c4");
		uranium_ore = (new BlockUraniumOre()).setHardness(1.0F).setLightLevel(0.55F).setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab()).setUnlocalizedName("uranium_ore");
		castle_gate = new BlockGate().setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("castle_gate").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		garage = new BlockGate().setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("garage").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		halogen_light = (new BlockHLight()).setHardness(0.1F).setResistance(6.0F).setLightLevel(1.0F).setLightOpacity(0).setUnlocalizedName("halogen_light").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		halogen_torch = (new BlockHTorch()).setHardness(0.1F).setResistance(8.0F).setLightLevel(1.0F).setLightOpacity(0).setUnlocalizedName("halogen_torch").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		sidewalk = (new BlockSwalk()).setHardness(1.0F).setResistance(15.0F).setUnlocalizedName("sidewalk").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		rway = (new BlockRway()).setHardness(1.0F).setResistance(15.0F).setUnlocalizedName("rway").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		rway_light_on = (new BlockRwayLight()).setHardness(1.0F).setResistance(15.0F).setLightLevel(1.0F).setUnlocalizedName("rway_light_on");
		rway_light_off = (new BlockRwayLight()).setHardness(1.0F).setResistance(15.0F).setLightLevel(0.0F).setUnlocalizedName("rway_light_off").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		rway_manhole = (new BlockRwayManhole()).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("rway_manhole").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fountain = (new BlockFountain()).setHardness(1.5F).setResistance(10.0F).setUnlocalizedName("fountain").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		camo_plate = (new BlockCamoPlate()).setHardness(0.5F).setResistance(5.0F).setUnlocalizedName("camo_plate").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		chain_fence = (new BlockChainFence(Material.CIRCUITS)).setHardness(0.5F).setResistance(5.0F).setUnlocalizedName("chain_fence").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_ladder = (new BlockAluminumLadder()).setHardness(1.0F).setResistance(5.0F).setUnlocalizedName("aluminum_ladder").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		aluminum_ore = (new BlockManualPage(Material.ROCK, SoundType.STONE, "industry:metalworks.alumingot")).setHardness(1.0F).setResistance(5.0F).setUnlocalizedName("aluminum_ore").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		oil_ore = (new BlockOilOre()).setHardness(1.0F).setResistance(5.0F).setUnlocalizedName("oil_ore").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		derrick = (new BlockDerrick()).setHardness(1.0F).setResistance(10.0F).setLightLevel(0.5F).setUnlocalizedName("derrick").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		modern_furnace = (new BlockModernFurnace()).setHardness(10.0F).setResistance(2.0F).setUnlocalizedName("modern_furnace").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		refinery = (new BlockRefinery()).setHardness(1.0F).setResistance(10.0F).setLightLevel(0.0F).setUnlocalizedName("refinery").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_block = (new BlockManualPage(Material.IRON, SoundType.METAL, "industry:metalworks.steelstuff")).setHardness(1.0F).setResistance(20.0F).setUnlocalizedName("steel_block").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_pipe = (new BlockSteelPipe()).setHardness(1.0F).setResistance(10.0F).setUnlocalizedName("steel_pipe").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		steel_frame = (new BlockSteelFrame()).setHardness(1.0F).setResistance(10.0F).setLightOpacity(0).setUnlocalizedName("steel_frame").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fuel_tank = (new BlockFuel()).setHardness(1.0F).setResistance(5.0F).setUnlocalizedName("fuel_tank").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		fan = (new BlockFan()).setHardness(1.5F).setResistance(10F).setUnlocalizedName("fan").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		specific_sensor = (new BlockSpecificSensor()).setHardness(1.5F).setResistance(10F).setUnlocalizedName("specific_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());
		upgraded_specific_sensor = (new BlockSpecificSensor()).setHardness(3.0F).setResistance(12F).setUnlocalizedName("upgraded_specific_sensor").setCreativeTab(GrimIndustry.INSTANCE.getCreativeTab());

		Utils.registerBlock(upgraded_specific_sensor, "upgraded_specific_sensor");
		Utils.registerBlock(specific_sensor, "specific_sensor");
		Utils.registerBlock(fan, "fan");
		Utils.registerBlock(aluminum_ladder, "aluminum_ladder");
		Utils.registerBlock(aluminum_ore, "aluminum_ore");
		Utils.registerBlock(oil_ore, "oil_ore");
		Utils.registerBlock(derrick, "derrick");
		Utils.registerBlock(modern_furnace, "modern_furnace");
		Utils.registerBlock(refinery, "refinery");
		Utils.registerBlock(steel_block, "steel_block");
		Utils.registerBlock(steel_pipe, "steel_pipe");
		Utils.registerBlock(steel_frame, "steel_frame");
		Utils.registerBlock(fuel_tank, "fuel_tank");
		Utils.registerBlock(fountain, "fountain");
		Utils.registerBlock(camo_plate, "camo_plate");
		Utils.registerBlock(chain_fence, "chain_fence");
		Utils.registerBlock(modern_tile, "modern_tile");
		Utils.registerBlock(tempered_glass, "tempered_glass");
		Utils.registerBlock(horizontal_siding, "horizontal_siding");
		Utils.registerBlock(vertical_siding, "vertical_siding");
		Utils.registerBlock(concrete, "concrete");
		Utils.registerBlock(door_chain, "door_chain");
		Utils.registerBlock(door_glass, "door_glass");
		Utils.registerBlock(door_steel, "door_steel");
		Utils.registerBlock(sidewalk, "sidewalk");
		Utils.registerBlock(rway, "rway");
		Utils.registerBlock(rway_light_on, "rway_light_on");
		Utils.registerBlock(rway_light_off, "rway_light_off");
		Utils.registerBlock(rway_manhole, "rway_manhole");
		Utils.registerBlock(halogen_light, "halogen_light");
		Utils.registerBlock(halogen_torch, "halogen_torch");
		Utils.registerBlock(castle_gate, "castle_gate");
		Utils.registerBlock(garage, "garage");
		Utils.registerBlock(attractor, "attractor");
		Utils.registerBlock(repulsor, "repulsor");
		Utils.registerBlock(gravitor, "gravitor");
		Utils.registerBlock(direction_attractor, "direction_attractor");
		Utils.registerBlock(direction_repulsor, "direction_repulsor");
		Utils.registerBlock(direction_gravitor, "direction_gravitor");
		Utils.registerBlock(wooden_sensor, "wooden_sensor", new ItemSensor(wooden_sensor));
		Utils.registerBlock(stone_sensor, "stone_sensor", new ItemSensor(stone_sensor));
		Utils.registerBlock(iron_sensor, "iron_sensor", new ItemSensor(iron_sensor));
		Utils.registerBlock(netherrack_sensor, "netherrack_sensor", new ItemSensor(netherrack_sensor));
		Utils.registerBlock(spike, "spike");
		Utils.registerBlock(water_block, "water_block");
		Utils.registerBlock(lava_block, "lava_block");
		Utils.registerBlock(fire_block, "fire_block");
		Utils.registerBlock(iron_workbench, "iron_workbench");
		Utils.registerBlock(diamond_workbench, "diamond_workbench");
		Utils.registerBlock(togglerack, "togglerack");
		Utils.registerBlock(ice_maker, "ice_maker");
		Utils.registerBlock(uranium_ore, "uranium_ore");
		Utils.registerBlock(bomb_shell, "bomb_shell");
		Utils.registerBlock(c4, "c4");
		Utils.registerBlock(nuclear_bomb, "nuclear_bomb");
		Utils.registerBlock(reactor, "reactor");
	}

	public static List<IRecipe> sensors;
	public static List<IRecipe> workbenches;
	public static List<IRecipe> attracting;
	public static List<IRecipe> repulsing;
	public static List<IRecipe> gravitoring;
	public static List<IRecipe> htorches;
	public static List<IRecipe> rways;
	public static List<IRecipe> decoration;
	public static List<IRecipe> others;
	public static List<IRecipe> buckladd;
	public static List<IRecipe> steelstuff = new ArrayList<IRecipe>();

	@Override
	public void addRecipes() {
		OreDictionary.registerOre("oreUranium", uranium_ore);
		OreDictionary.registerOre("blockGlass", tempered_glass);
		OreDictionary.registerOre("blockSteel", steel_block);
		OreDictionary.registerOre("oreAluminum", aluminum_ore);

		GameRegistry.addSmelting(aluminum_ore, new ItemStack(IndustryItems.aluminum_ingot, 1), 0.45F);
		GameRegistry.addSmelting(uranium_ore, new ItemStack(IndustryItems.uranium_ingot), 0.7F);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(specific_sensor, 1), new Object[] { " Z ", "XYX", " Z ", 'X', Blocks.REDSTONE_LAMP, 'Y', IndustryItems.position_finder, 'Z', "blockRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgraded_specific_sensor, 1), new Object[] { "XXX", "XYX", "XXX", 'X', "ingotGold", 'Y', specific_sensor }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fan, 1), new Object[] { "X X", "XYX", "XXX", 'X', "plankWood", 'Y', "dustRedstone" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery, 1), new Object[] { "sss", "ara", "sss", 's', "ingotSteel", 'r', "dustRedstone", 'a', IndustryItems.aluminum_can }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(derrick, 1), new Object[] { "s s", "sRs", "sPs", 's', "ingotSteel", 'R', refinery, 'P', steel_pipe }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(modern_furnace, 1), new Object[] { "sss", "rFr", "sss", 's', "ingotSteel", 'r', "dustRedstone", 'F', Blocks.FURNACE }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fuel_tank, 1), new Object[] { "fff", "fff", "fff", 'f', IndustryItems.fuel }));
		IndustryItems.fuelstuff.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(aluminum_ladder, 4), new Object[] { "s s", "sss", "s s", 's', IndustryItems.aluminum_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.BUCKET, 1), new Object[] { "a a", " a ", 'a', "ingotAluminum" }));
		buckladd = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_pipe, 8), new Object[] { "s s", "s s", "s s", 's', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_block, 1), new Object[] { "iii", "iii", "iii", 'i', "ingotSteel" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steel_frame, 4), new Object[] { "i i", " i ", "i i", 'i', IndustryItems.steel_shaft }));
		steelstuff.addAll(RecipeHelper.getLatestIRecipes(3));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chain_fence, 32), new Object[] { "sss", "sss", 's', IndustryItems.aluminum_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fountain, 1), new Object[] { "CPC", "CaC", "C C", 'C', "cobblestone", 'P', steel_pipe, 'a', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(camo_plate, 2), new Object[] { "rr", "ii", 'r', "dustRedstone", 'i', "ingotGold" }));
		others = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(concrete, 8), new Object[] { "SSS", "SsS", "SSS", 'S', "stone", 's', IndustryItems.steel_shaft }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(horizontal_siding, 4), new Object[] { "L", "t", "a", 'L', "logWood", 't', IndustryItems.tarball, 'a', "ingotAluminum" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(vertical_siding, 4), new Object[] { "C", "t", "a", 'C', "cobblestone", 't', IndustryItems.tarball, 'a', "ingotAluminum" }));
		decoration = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sidewalk, 8), new Object[] { "SSS", "SSS", 'S', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rway, 1), new Object[] { "A", "S", 'A', IndustryItems.asphalt, 'S', "stone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rway_light_off, 1), new Object[] { "L", "R", 'L', halogen_light, 'R', rway }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rway_manhole, 1), new Object[] { "i", "R", 'i', "ingotIron", 'R', rway }));
		rways = RecipeHelper.getLatestIRecipes(3);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(halogen_light, 1), new Object[] { "G", "R", 'G', "blockGlass", 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(halogen_torch, 4), new Object[] { "G", "R", "S", 'G', "blockGlass", 'R', "dustRedstone", 'S', "stickWood" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(halogen_torch, 4), new Object[] { "H", "S", 'H', halogen_light, 'S', "stickWood" }));
		htorches = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(castle_gate, 1), new Object[] { "G", "G", "G", 'G', IndustryItems.gate_grating }));
		IndustryItems.gates.add(RecipeHelper.getLatestIRecipe());
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(garage, 1), new Object[] { "p", "p", "p", 'p', IndustryItems.garage_panel }));
		IndustryItems.garages.add(RecipeHelper.getLatestIRecipe());

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(togglerack, 4), new Object[] { "QQQ", "QAQ", "QQQ", 'A', "dustRedstone", 'Q', Blocks.NETHERRACK }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockIron", '=', Blocks.CRAFTING_TABLE }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockDiamond", '=', Blocks.CRAFTING_TABLE }));
		workbenches = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ice_maker, 1), new Object[] { "X X", "I I", "XXX", 'X', "cobblestone", 'I', Blocks.SNOW }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fire_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.FLINT_AND_STEEL }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(water_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.WATER_BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(lava_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.LAVA_BUCKET }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spike, 8), new Object[] { "X X", " X ", "XXX", 'X', "stickIron" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wooden_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "plankWood", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "stone", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_sensor), new Object[] { "XGX", "XYX", "XXX", 'X', "ingotIron", 'Y', "dustRedstone", 'G', "blockGlass" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(netherrack_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', Blocks.NETHERRACK, 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" }));
		sensors = RecipeHelper.getLatestIRecipes(4);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(attractor, 1), new Object[] { "SZS", "ZRZ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_attractor, 1), new Object[] { "SZS", "ZRZ", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" }));
		attracting = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(repulsor, 1), new Object[] { "ZSZ", "SRS", "ZSZ", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.COMPASS }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_repulsor, 1), new Object[] { "ZSZ", "SRS", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.COMPASS }));
		repulsing = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gravitor, 1), new Object[] { "S S", " R ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(direction_gravitor, 1), new Object[] { " R ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" }));
		gravitoring = RecipeHelper.getLatestIRecipes(2);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(uranium_ore, 1), new Object[] { "RDR", "DGD", "RDR", 'R', "dustRedstone", 'G', "glowstone", 'D', "gemDiamond" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(reactor, 1), new Object[] { "GMG", "CRC", "GMG", 'G', IndustryItems.graphite, 'M', IndustryItems.iron_parts, 'C', IndustryItems.reactor_core_case, 'R', IndustryItems.reactor_core }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bomb_shell, 1), new Object[] { "A A", " A ", "A A", 'A', "ingotAluminum" }));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(c4, 1), new Object[] { "GGG", "RRR", "GGG", 'G', Items.GUNPOWDER, 'R', "dustRedstone" }));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(nuclear_bomb, 1), new Object[] { "BCB", "CRC", "BCB", 'B', bomb_shell, 'C', c4, 'R', reactor }));
	}

}
