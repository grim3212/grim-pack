package com.grim3212.mc.pack.industry.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.CreateRecipes;
import com.grim3212.mc.pack.industry.block.BlockElemental.ElementType;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.item.ItemGoldSafe;
import com.grim3212.mc.pack.industry.item.ItemSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityCamo;
import com.grim3212.mc.pack.industry.tile.TileEntityDGravity;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFireSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityGlassCabinet;
import com.grim3212.mc.pack.industry.tile.TileEntityGoldSafe;
import com.grim3212.mc.pack.industry.tile.TileEntityGravity;
import com.grim3212.mc.pack.industry.tile.TileEntityItemTower;
import com.grim3212.mc.pack.industry.tile.TileEntityLocker;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.industry.tile.TileEntityObsidianSafe;
import com.grim3212.mc.pack.industry.tile.TileEntitySensor;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityTank;
import com.grim3212.mc.pack.industry.tile.TileEntityWarehouseCrate;
import com.grim3212.mc.pack.industry.tile.TileEntityWoodCabinet;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class IndustryBlocks {

	public static final Block togglerack = new BlockToggleRack();
	public static final Block iron_workbench = new BlockIronWorkbench();
	public static final Block diamond_workbench = new BlockDiamondWorkbench();
	public static final Block ice_maker = new BlockIceMaker();
	public static final Block fire_block = new BlockElemental(ElementType.FIRE);
	public static final Block water_block = new BlockElemental(ElementType.WATER);
	public static final Block lava_block = new BlockElemental(ElementType.LAVA);
	public static final Block spike = new BlockSpike();
	public static final Block wooden_sensor = new BlockSensor("wooden_sensor", 0);
	public static final Block stone_sensor = new BlockSensor("stone_sensor", 1);
	public static final Block iron_sensor = new BlockSensor("iron_sensor", 2);
	public static final Block netherrack_sensor = new BlockSensor("netherrack_sensor", 3);
	public static final Block attractor = new BlockGravity("attractor", -1);
	public static final Block repulsor = new BlockGravity("repulsor", 1);
	public static final Block gravitor = new BlockGravity("gravitor", 0);
	public static final Block direction_attractor = new BlockDGravity("direction_attractor", -1);
	public static final Block direction_repulsor = new BlockDGravity("direction_repulsor", 1);
	public static final Block direction_gravitor = new BlockDGravity("direction_gravitor", 0);
	public static final Block reactor = new BlockNuclearReactor();
	public static final Block bomb_shell = (new BlockManualPage("bomb_shell", Material.IRON, SoundType.METAL, "industry:explosives.bomb_shell")).setHardness(1.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Block c4 = new BlockC4();
	public static final Block nuclear_bomb = new BlockNuclearBomb();
	public static final Block uranium_ore = new BlockUraniumOre();
	public static final Block castle_gate = new BlockGate("castle_gate");
	public static final Block garage = new BlockGate("garage");
	public static final Block halogen_light = new BlockHLight();
	public static final Block halogen_torch = new BlockHTorch();
	public static final Block sidewalk = new BlockSwalk();
	public static final Block rway = new BlockRway();
	public static final Block rway_light_on = new BlockRwayLight(true);
	public static final Block rway_light_off = new BlockRwayLight(false);
	public static final Block rway_manhole = new BlockRwayManhole();
	public static final Block door_chain = new BlockModernDoor(Material.CIRCUITS);
	public static final Block door_glass = new BlockModernDoor(Material.GLASS);
	public static final Block door_steel = new BlockModernDoor(Material.ANVIL);
	public static final Block concrete = (new BlockManualPage("concrete", Material.ROCK, SoundType.STONE, "industry:moderntech.decoration")).setHardness(1.0F).setResistance(30.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Block modern_tile = (new BlockManualPage("modern_tile", Material.ROCK, SoundType.STONE, "industry:metalworks.mfurnace_recipes")).setHardness(1.0F).setResistance(15.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Block tempered_glass = new BlockTemperedGlass();
	public static final Block horizontal_siding = new BlockSiding("horizontal_siding");
	public static final Block vertical_siding = new BlockSiding("vertical_siding");
	public static final Block fountain = new BlockFountain();
	public static final Block camo_plate = new BlockCamoPlate();
	public static final Block chain_fence = new BlockChainFence(Material.CIRCUITS);
	public static final Block aluminum_ladder = new BlockAluminumLadder();
	public static final Block aluminum_ore = (new BlockManualPage("aluminum_ore", Material.ROCK, SoundType.STONE, "industry:metalworks.alumingot")).setHardness(1.0F).setResistance(5.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Block oil_ore = new BlockOilOre();
	public static final Block derrick = new BlockDerrick();
	public static final Block modern_furnace = new BlockModernFurnace();
	public static final Block steel_block = (new BlockManualPage("steel_block", Material.IRON, SoundType.METAL, "industry:metalworks.steelstuff")).setHardness(1.0F).setResistance(20.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	public static final Block steel_pipe = new BlockSteelPipe();
	public static final Block refinery = new BlockRefinery();
	public static final Block steel_frame = new BlockSteelFrame();
	public static final Block fuel_tank = new BlockFuel();
	public static final Block fan = new BlockFan();
	public static final Block specific_sensor = new BlockSpecificSensor(false);
	public static final Block upgraded_specific_sensor = new BlockSpecificSensor(true);
	public static final Block arrow_sensor = new BlockSensorArrow();
	public static final Block metal_mesh = new BlockMetalMesh();
	public static final Block fire_sensor = new BlockFireSensor();
	public static final Block conveyor_belt = new BlockConveyorBelt();
	public static final Block drill = new BlockDrill();
	public static final Block drill_head = new BlockDrillHead();
	public static final Block locksmith_workbench = new BlockLocksmithWorkbench();
	public static final Block wood_cabinet = new BlockWoodCabinet();
	public static final Block glass_cabinet = new BlockGlassCabinet();
	public static final Block warehouse_crate = new BlockWarehouseCrate();
	public static final Block obsidian_safe = new BlockObsidianSafe();
	public static final Block gold_safe = new BlockGoldSafe();
	public static final Block locker = new BlockLocker();
	public static final Block item_tower = new BlockItemTower();
	public static final Block tank = new BlockTank();

	@SubscribeEvent
	public static void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		r.register(modern_tile);
		r.register(tempered_glass);
		r.register(horizontal_siding);
		r.register(vertical_siding);
		r.register(concrete);
		r.register(door_chain);
		r.register(door_glass);
		r.register(door_steel);
		r.register(togglerack);
		r.register(iron_workbench);
		r.register(diamond_workbench);
		r.register(ice_maker);
		r.register(fire_block);
		r.register(water_block);
		r.register(lava_block);
		r.register(spike);
		r.register(wooden_sensor);
		r.register(stone_sensor);
		r.register(iron_sensor);
		r.register(netherrack_sensor);
		r.register(attractor);
		r.register(repulsor);
		r.register(gravitor);
		r.register(direction_attractor);
		r.register(direction_repulsor);
		r.register(direction_gravitor);
		r.register(nuclear_bomb);
		r.register(reactor);
		r.register(bomb_shell);
		r.register(c4);
		r.register(uranium_ore);
		r.register(castle_gate);
		r.register(garage);
		r.register(halogen_light);
		r.register(halogen_torch);
		r.register(sidewalk);
		r.register(rway);
		r.register(rway_light_on);
		r.register(rway_light_off);
		r.register(rway_manhole);
		r.register(fountain);
		r.register(camo_plate);
		r.register(chain_fence);
		r.register(aluminum_ladder);
		r.register(aluminum_ore);
		r.register(oil_ore);
		r.register(derrick);
		r.register(modern_furnace);
		r.register(refinery);
		r.register(steel_block);
		r.register(steel_pipe);
		r.register(steel_frame);
		r.register(fuel_tank);
		r.register(fan);
		r.register(specific_sensor);
		r.register(upgraded_specific_sensor);
		r.register(arrow_sensor);
		r.register(metal_mesh);
		r.register(fire_sensor);
		r.register(conveyor_belt);
		r.register(drill);
		r.register(drill_head);
		r.register(warehouse_crate);
		r.register(wood_cabinet);
		r.register(glass_cabinet);
		r.register(obsidian_safe);
		r.register(gold_safe);
		r.register(locker);
		r.register(item_tower);
		r.register(tank);
		r.register(locksmith_workbench);

		initTileEntities();
	}

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(new ItemGoldSafe(gold_safe).setRegistryName(gold_safe.getRegistryName()));
		r.register(new ItemManualBlock(tank).setRegistryName(tank.getRegistryName()));
		r.register(new ItemManualBlock(item_tower).setRegistryName(item_tower.getRegistryName()));
		r.register(new ItemManualBlock(locker).setRegistryName(locker.getRegistryName()));
		r.register(new ItemManualBlock(obsidian_safe).setRegistryName(obsidian_safe.getRegistryName()));
		r.register(new ItemManualBlock(locksmith_workbench).setRegistryName(locksmith_workbench.getRegistryName()));
		r.register(new ItemManualBlock(wood_cabinet).setRegistryName(wood_cabinet.getRegistryName()));
		r.register(new ItemManualBlock(glass_cabinet).setRegistryName(glass_cabinet.getRegistryName()));
		r.register(new ItemManualBlock(warehouse_crate).setRegistryName(warehouse_crate.getRegistryName()));
		r.register(new ItemManualBlock(drill).setRegistryName(drill.getRegistryName()));
		r.register(new ItemManualBlock(drill_head).setRegistryName(drill_head.getRegistryName()));
		r.register(new ItemManualBlock(conveyor_belt).setRegistryName(conveyor_belt.getRegistryName()));
		r.register(new ItemManualBlock(fire_sensor).setRegistryName(fire_sensor.getRegistryName()));
		r.register(new ItemManualBlock(metal_mesh).setRegistryName(metal_mesh.getRegistryName()));
		r.register(new ItemManualBlock(arrow_sensor).setRegistryName(arrow_sensor.getRegistryName()));
		r.register(new ItemManualBlock(upgraded_specific_sensor).setRegistryName(upgraded_specific_sensor.getRegistryName()));
		r.register(new ItemManualBlock(specific_sensor).setRegistryName(specific_sensor.getRegistryName()));
		r.register(new ItemManualBlock(fan).setRegistryName(fan.getRegistryName()));
		r.register(new ItemManualBlock(aluminum_ladder).setRegistryName(aluminum_ladder.getRegistryName()));
		r.register(new ItemManualBlock(aluminum_ore).setRegistryName(aluminum_ore.getRegistryName()));
		r.register(new ItemManualBlock(oil_ore).setRegistryName(oil_ore.getRegistryName()));
		r.register(new ItemManualBlock(derrick).setRegistryName(derrick.getRegistryName()));
		r.register(new ItemManualBlock(modern_furnace).setRegistryName(modern_furnace.getRegistryName()));
		r.register(new ItemManualBlock(refinery).setRegistryName(refinery.getRegistryName()));
		r.register(new ItemManualBlock(steel_block).setRegistryName(steel_block.getRegistryName()));
		r.register(new ItemManualBlock(steel_pipe).setRegistryName(steel_pipe.getRegistryName()));
		r.register(new ItemManualBlock(steel_frame).setRegistryName(steel_frame.getRegistryName()));
		r.register(new ItemManualBlock(fuel_tank).setRegistryName(fuel_tank.getRegistryName()));
		r.register(new ItemManualBlock(fountain).setRegistryName(fountain.getRegistryName()));
		r.register(new ItemManualBlock(camo_plate).setRegistryName(camo_plate.getRegistryName()));
		r.register(new ItemManualBlock(chain_fence).setRegistryName(chain_fence.getRegistryName()));
		r.register(new ItemManualBlock(modern_tile).setRegistryName(modern_tile.getRegistryName()));
		r.register(new ItemManualBlock(tempered_glass).setRegistryName(tempered_glass.getRegistryName()));
		r.register(new ItemManualBlock(horizontal_siding).setRegistryName(horizontal_siding.getRegistryName()));
		r.register(new ItemManualBlock(vertical_siding).setRegistryName(vertical_siding.getRegistryName()));
		r.register(new ItemManualBlock(concrete).setRegistryName(concrete.getRegistryName()));
		r.register(new ItemManualBlock(door_chain).setRegistryName(door_chain.getRegistryName()));
		r.register(new ItemManualBlock(door_glass).setRegistryName(door_glass.getRegistryName()));
		r.register(new ItemManualBlock(door_steel).setRegistryName(door_steel.getRegistryName()));
		r.register(new ItemManualBlock(sidewalk).setRegistryName(sidewalk.getRegistryName()));
		r.register(new ItemManualBlock(rway).setRegistryName(rway.getRegistryName()));
		r.register(new ItemManualBlock(rway_light_on).setRegistryName(rway_light_on.getRegistryName()));
		r.register(new ItemManualBlock(rway_light_off).setRegistryName(rway_light_off.getRegistryName()));
		r.register(new ItemManualBlock(rway_manhole).setRegistryName(rway_manhole.getRegistryName()));
		r.register(new ItemManualBlock(halogen_light).setRegistryName(halogen_light.getRegistryName()));
		r.register(new ItemManualBlock(halogen_torch).setRegistryName(halogen_torch.getRegistryName()));
		r.register(new ItemManualBlock(castle_gate).setRegistryName(castle_gate.getRegistryName()));
		r.register(new ItemManualBlock(garage).setRegistryName(garage.getRegistryName()));
		r.register(new ItemManualBlock(attractor).setRegistryName(attractor.getRegistryName()));
		r.register(new ItemManualBlock(repulsor).setRegistryName(repulsor.getRegistryName()));
		r.register(new ItemManualBlock(gravitor).setRegistryName(gravitor.getRegistryName()));
		r.register(new ItemManualBlock(direction_attractor).setRegistryName(direction_attractor.getRegistryName()));
		r.register(new ItemManualBlock(direction_repulsor).setRegistryName(direction_repulsor.getRegistryName()));
		r.register(new ItemManualBlock(direction_gravitor).setRegistryName(direction_gravitor.getRegistryName()));
		r.register(new ItemSensor(wooden_sensor).setRegistryName(wooden_sensor.getRegistryName()));
		r.register(new ItemSensor(stone_sensor).setRegistryName(stone_sensor.getRegistryName()));
		r.register(new ItemSensor(iron_sensor).setRegistryName(iron_sensor.getRegistryName()));
		r.register(new ItemSensor(netherrack_sensor).setRegistryName(netherrack_sensor.getRegistryName()));
		r.register(new ItemManualBlock(spike).setRegistryName(spike.getRegistryName()));
		r.register(new ItemManualBlock(water_block).setRegistryName(water_block.getRegistryName()));
		r.register(new ItemManualBlock(lava_block).setRegistryName(lava_block.getRegistryName()));
		r.register(new ItemManualBlock(fire_block).setRegistryName(fire_block.getRegistryName()));
		r.register(new ItemManualBlock(iron_workbench).setRegistryName(iron_workbench.getRegistryName()));
		r.register(new ItemManualBlock(diamond_workbench).setRegistryName(diamond_workbench.getRegistryName()));
		r.register(new ItemManualBlock(togglerack).setRegistryName(togglerack.getRegistryName()));
		r.register(new ItemManualBlock(ice_maker).setRegistryName(ice_maker.getRegistryName()));
		r.register(new ItemManualBlock(uranium_ore).setRegistryName(uranium_ore.getRegistryName()));
		r.register(new ItemManualBlock(bomb_shell).setRegistryName(bomb_shell.getRegistryName()));
		r.register(new ItemManualBlock(c4).setRegistryName(c4.getRegistryName()));
		r.register(new ItemManualBlock(nuclear_bomb).setRegistryName(nuclear_bomb.getRegistryName()));
		r.register(new ItemManualBlock(reactor).setRegistryName(reactor.getRegistryName()));

		initOreDict();
	}

	private static void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySensor.class, "sensors");
		GameRegistry.registerTileEntity(TileEntityGravity.class, "gravity");
		GameRegistry.registerTileEntity(TileEntityCamo.class, "camo_plate");
		GameRegistry.registerTileEntity(TileEntityDGravity.class, "directional_gravity");
		GameRegistry.registerTileEntity(TileEntityMachine.class, "machine");
		GameRegistry.registerTileEntity(TileEntityMFurnace.class, "modern_furnace");
		GameRegistry.registerTileEntity(TileEntityFan.class, "fan");
		GameRegistry.registerTileEntity(TileEntitySpecificSensor.class, "specific_sensor");
		GameRegistry.registerTileEntity(TileEntityFireSensor.class, "fire_sensor");
		GameRegistry.registerTileEntity(TileEntityWarehouseCrate.class, "grimpack_warehouse_crate");
		GameRegistry.registerTileEntity(TileEntityGlassCabinet.class, "grimpack_glass_cabinet");
		GameRegistry.registerTileEntity(TileEntityWoodCabinet.class, "grimpack_wood_cabinet");
		GameRegistry.registerTileEntity(TileEntityObsidianSafe.class, "grimpack_obsidian_safe");
		GameRegistry.registerTileEntity(TileEntityGoldSafe.class, "grimpack_gold_safe");
		GameRegistry.registerTileEntity(TileEntityLocker.class, "grimpack_locker");
		GameRegistry.registerTileEntity(TileEntityItemTower.class, "grimpack_item_tower");
		GameRegistry.registerTileEntity(TileEntityTank.class, "grimpack_tank");
	}

	private static void initOreDict() {
		OreDictionary.registerOre("oreUranium", uranium_ore);
		OreDictionary.registerOre("blockGlass", tempered_glass);
		OreDictionary.registerOre("blockSteel", steel_block);
		OreDictionary.registerOre("oreAluminum", aluminum_ore);
		OreDictionary.registerOre("oreOil", oil_ore);
	}

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		GameRegistry.addSmelting(aluminum_ore, new ItemStack(IndustryItems.aluminum_ingot, 1), 0.45F);
		GameRegistry.addSmelting(uranium_ore, new ItemStack(IndustryItems.uranium_ingot), 0.7F);
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

	public static void addRecipes() {

		CreateRecipes.addShapedRecipe(new ItemStack(tank, 4), "XYX", "Y Y", "XYX", 'X', "ingotIron", 'Y', "blockGlass");
		CreateRecipes.addShapedRecipe(new ItemStack(specific_sensor, 1), new Object[] { " Z ", "XYX", " Z ", 'X', Blocks.REDSTONE_LAMP, 'Y', IndustryItems.position_finder, 'Z', "blockRedstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(upgraded_specific_sensor, 1), new Object[] { "XXX", "XYX", "XXX", 'X', "ingotGold", 'Y', specific_sensor });
		CreateRecipes.addShapedRecipe(new ItemStack(fan, 1), new Object[] { "X X", "XYX", "XXX", 'X', "plankWood", 'Y', "dustRedstone" });

		CreateRecipes.addShapedRecipe(new ItemStack(refinery, 1), new Object[] { "sss", "ara", "sss", 's', "ingotSteel", 'r', "dustRedstone", 'a', IndustryItems.aluminum_can });
		CreateRecipes.addShapedRecipe(new ItemStack(derrick, 1), new Object[] { "s s", "sRs", "sPs", 's', "ingotSteel", 'R', refinery, 'P', steel_pipe });
		CreateRecipes.addShapedRecipe(new ItemStack(modern_furnace, 1), new Object[] { "sss", "rFr", "sss", 's', "ingotSteel", 'r', "dustRedstone", 'F', Blocks.FURNACE });

		CreateRecipes.addShapedRecipe(new ItemStack(fuel_tank, 1), new Object[] { "fff", "fff", "fff", 'f', IndustryItems.fuel });
		// IndustryItems.fuelstuff.add(RecipeHelper.getLatestIRecipe());

		CreateRecipes.addShapedRecipe(new ItemStack(aluminum_ladder, 4), new Object[] { "s s", "sss", "s s", 's', IndustryItems.aluminum_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[] { "a a", " a ", 'a', "ingotAluminum" });
		// buckladd = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(steel_pipe, 8), new Object[] { "s s", "s s", "s s", 's', "ingotSteel" });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_block, 1), new Object[] { "iii", "iii", "iii", 'i', "ingotSteel" });
		CreateRecipes.addShapedRecipe(new ItemStack(steel_frame, 4), new Object[] { "i i", " i ", "i i", 'i', IndustryItems.steel_shaft });
		// steelstuff.addAll(RecipeHelper.getLatestIRecipes(3));

		CreateRecipes.addShapedRecipe(new ItemStack(chain_fence, 32), new Object[] { "sss", "sss", 's', IndustryItems.aluminum_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(fountain, 1), new Object[] { "CPC", "CaC", "C C", 'C', "cobblestone", 'P', steel_pipe, 'a', "ingotAluminum" });
		CreateRecipes.addShapedRecipe(new ItemStack(camo_plate, 2), new Object[] { "rr", "ii", 'r', "dustRedstone", 'i', "ingotGold" });
		// others = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(concrete, 8), new Object[] { "SSS", "SsS", "SSS", 'S', "stone", 's', IndustryItems.steel_shaft });
		CreateRecipes.addShapedRecipe(new ItemStack(horizontal_siding, 4), new Object[] { "L", "t", "a", 'L', "logWood", 't', IndustryItems.tarball, 'a', "ingotAluminum" });
		CreateRecipes.addShapedRecipe(new ItemStack(vertical_siding, 4), new Object[] { "C", "t", "a", 'C', "cobblestone", 't', IndustryItems.tarball, 'a', "ingotAluminum" });
		// decoration = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(sidewalk, 8), new Object[] { "SSS", "SSS", 'S', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(rway, 1), new Object[] { "A", "S", 'A', IndustryItems.asphalt, 'S', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(rway_light_off, 1), new Object[] { "L", "R", 'L', halogen_light, 'R', rway });
		CreateRecipes.addShapedRecipe(new ItemStack(rway_manhole, 1), new Object[] { "i", "R", 'i', "ingotIron", 'R', rway });
		// rways = RecipeHelper.getLatestIRecipes(3);

		CreateRecipes.addShapedRecipe(new ItemStack(halogen_light, 1), new Object[] { "G", "R", 'G', "blockGlass", 'R', "dustRedstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(halogen_torch, 4), new Object[] { "G", "R", "S", 'G', "blockGlass", 'R', "dustRedstone", 'S', "stickWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(halogen_torch, 4), new Object[] { "H", "S", 'H', halogen_light, 'S', "stickWood" });
		// htorches = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(castle_gate, 1), new Object[] { "G", "G", "G", 'G', IndustryItems.gate_grating });
		// IndustryItems.gates.add(RecipeHelper.getLatestIRecipe());
		CreateRecipes.addShapedRecipe(new ItemStack(garage, 1), new Object[] { "p", "p", "p", 'p', IndustryItems.garage_panel });
		// IndustryItems.garages.add(RecipeHelper.getLatestIRecipe());

		CreateRecipes.addShapedRecipe(new ItemStack(togglerack, 4), new Object[] { "QQQ", "QAQ", "QQQ", 'A', "dustRedstone", 'Q', "netherrack" });
		CreateRecipes.addShapedRecipe(new ItemStack(iron_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockIron", '=', "workbench" });
		CreateRecipes.addShapedRecipe(new ItemStack(diamond_workbench), new Object[] { " X ", "X=X", " X ", 'X', "blockDiamond", '=', "workbench" });
		// workbenches = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(ice_maker, 1), new Object[] { "X X", "I I", "XXX", 'X', "cobblestone", 'I', Blocks.SNOW });

		CreateRecipes.addShapedRecipe(new ItemStack(fire_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.FLINT_AND_STEEL });
		CreateRecipes.addShapedRecipe(new ItemStack(water_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.WATER_BUCKET });
		CreateRecipes.addShapedRecipe(new ItemStack(lava_block, 1), new Object[] { "###", "#!#", "###", '#', "blockIron", '!', Items.LAVA_BUCKET });
		CreateRecipes.addShapedRecipe(new ItemStack(spike, 8), new Object[] { "X X", " X ", "XXX", 'X', "stickIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(wooden_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "plankWood", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(stone_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "stone", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" });
		CreateRecipes.addShapedRecipe(new ItemStack(iron_sensor), new Object[] { "XGX", "XYX", "XXX", 'X', "ingotIron", 'Y', "dustRedstone", 'G', "blockGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(netherrack_sensor), new Object[] { "IGI", "XYX", "IXI", 'X', "netherrack", 'Y', "dustRedstone", 'G', "blockGlass", 'I', "ingotIron" });
		// sensors = RecipeHelper.getLatestIRecipes(4);

		CreateRecipes.addShapedRecipe(new ItemStack(attractor, 1), new Object[] { "SZS", "ZRZ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(direction_attractor, 1), new Object[] { "SZS", "ZRZ", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" });
		// attracting = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(repulsor, 1), new Object[] { "ZSZ", "SRS", "ZSZ", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.COMPASS });
		CreateRecipes.addShapedRecipe(new ItemStack(direction_repulsor, 1), new Object[] { "ZSZ", "SRS", 'Z', "dustRedstone", 'S', "ingotIron", 'R', Items.COMPASS });
		// repulsing = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(gravitor, 1), new Object[] { "S S", " R ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(direction_gravitor, 1), new Object[] { " R ", "SZS", 'S', "ingotIron", 'R', Items.COMPASS, 'Z', "dustRedstone" });
		// gravitoring = RecipeHelper.getLatestIRecipes(2);

		CreateRecipes.addShapedRecipe(new ItemStack(uranium_ore, 1), new Object[] { "RDR", "DGD", "RDR", 'R', "dustRedstone", 'G', "glowstone", 'D', "gemDiamond" });
		CreateRecipes.addShapedRecipe(new ItemStack(reactor, 1), new Object[] { "GMG", "CRC", "GMG", 'G', IndustryItems.graphite, 'M', IndustryItems.iron_parts, 'C', IndustryItems.reactor_core_case, 'R', IndustryItems.reactor_core });

		CreateRecipes.addShapedRecipe(new ItemStack(bomb_shell, 1), new Object[] { "A A", " A ", "A A", 'A', "ingotAluminum" });

		CreateRecipes.addShapedRecipe(new ItemStack(c4, 1), new Object[] { "GGG", "RRR", "GGG", 'G', "gunpowder", 'R', "dustRedstone" });
		CreateRecipes.addShapedRecipe(new ItemStack(nuclear_bomb, 1), new Object[] { "BCB", "CRC", "BCB", 'B', bomb_shell, 'C', c4, 'R', reactor });

		CreateRecipes.addShapedRecipe(new ItemStack(arrow_sensor, 1), new Object[] { "XXX", "S#S", "XXX", 'X', Blocks.WOOL, 'S', metal_mesh, '#', Items.ARROW });
		CreateRecipes.addShapedRecipe(new ItemStack(metal_mesh, 1), new Object[] { "XX", "XX", 'X', "stickIron" });

		CreateRecipes.addShapedRecipe(new ItemStack(fire_sensor, 1), new Object[] { "SSS", "SNS", "X#X", 'X', "stickIron", '#', "dustRedstone", 'N', "netherrack", 'S', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(fire_sensor, 1), new Object[] { "SSS", "SNS", "X#X", 'X', "stickIron", '#', "dustRedstone", 'N', Items.FLINT_AND_STEEL, 'S', "stone" });
		CreateRecipes.addShapedRecipe(new ItemStack(conveyor_belt, 6), new Object[] { "XXX", "S#S", "XXX", 'X', "stickIron", '#', "dustRedstone", 'S', "cobblestone" });

		CreateRecipes.addShapedRecipe(new ItemStack(drill, 1), new Object[] { "I I", "S#S", "XDX", 'I', "ingotIron", 'X', "plankWood", 'S', "dustRedstone", '#', "gemDiamond", 'D', IndustryItems.drill_head_item });

		CreateRecipes.addShapedRecipe(new ItemStack(locksmith_workbench, 1), new Object[] { "L", "K", "W", 'L', IndustryItems.locksmith_lock, 'K', IndustryItems.locksmith_key, 'W', "workbench" });
		CreateRecipes.addShapedRecipe(new ItemStack(locksmith_workbench, 1), new Object[] { "K", "L", "W", 'L', IndustryItems.locksmith_lock, 'K', IndustryItems.locksmith_key, 'W', "workbench" });
		CreateRecipes.addShapedRecipe(new ItemStack(wood_cabinet, 1), new Object[] { " X ", "XCX", " X ", 'X', "plankWood", 'C', "chest" });
		CreateRecipes.addShapedRecipe(new ItemStack(glass_cabinet, 1), new Object[] { " X ", "GCG", " X ", 'X', "plankWood", 'C', "chest", 'G', "blockGlass" });
		CreateRecipes.addShapedRecipe(new ItemStack(warehouse_crate, 1), new Object[] { "LLL", "P P", "PPP", 'P', "plankWood", 'L', "logWood" });
		CreateRecipes.addShapedRecipe(new ItemStack(obsidian_safe, 1), new Object[] { " X ", "XCX", " X ", 'X', "obsidian", 'C', "chest" });
		CreateRecipes.addShapedRecipe(new ItemStack(gold_safe, 1), new Object[] { " G ", "GIG", " G ", 'I', obsidian_safe, 'G', "ingotGold" });
		CreateRecipes.addShapedRecipe(new ItemStack(locker, 1), new Object[] { " X ", "XCX", " X ", 'X', "ingotIron", 'C', "chest" });
		CreateRecipes.addShapedRecipe(new ItemStack(item_tower, 4), new Object[] { "I I", "ICI", "I I", 'I', "ingotIron", 'C', "chest" });
	}

}
