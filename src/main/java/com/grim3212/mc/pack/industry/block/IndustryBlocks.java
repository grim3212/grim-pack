package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.block.BlockElemental.ElementType;
import com.grim3212.mc.pack.industry.block.storage.BlockGlassCabinet;
import com.grim3212.mc.pack.industry.block.storage.BlockGoldSafe;
import com.grim3212.mc.pack.industry.block.storage.BlockItemTower;
import com.grim3212.mc.pack.industry.block.storage.BlockLocker;
import com.grim3212.mc.pack.industry.block.storage.BlockLocksmithWorkbench;
import com.grim3212.mc.pack.industry.block.storage.BlockObsidianSafe;
import com.grim3212.mc.pack.industry.block.storage.BlockTank;
import com.grim3212.mc.pack.industry.block.storage.BlockWarehouseCrate;
import com.grim3212.mc.pack.industry.block.storage.BlockWoodCabinet;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.item.ItemBridgeControl;
import com.grim3212.mc.pack.industry.item.ItemGoldSafe;
import com.grim3212.mc.pack.industry.item.ItemModernDoor;
import com.grim3212.mc.pack.industry.item.ItemSensor;
import com.grim3212.mc.pack.industry.item.ItemShapedCharge;
import com.grim3212.mc.pack.industry.tile.*;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

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
	public static final Block oil_ore = new BlockOilOre();
	public static final Block derrick = new BlockDerrick();
	public static final Block modern_furnace = new BlockModernFurnace();
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
	public static final Block shaped_charge = new BlockShapedCharge();
	public static final Block flip_flop_torch = new BlockFlipFlopTorch();
	public static final Block glowstone_torch = new BlockGlowstoneTorch();
	public static final Block chunk_loader = new BlockChunkLoader();

	// Bridges
	public static final Block bridge_control = new BlockBridgeControl();
	public static final Block bridge = new BlockBridge();

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (IndustryConfig.subpartDoors) {
			r.register(door_chain);
			r.register(door_glass);
			r.register(door_steel);
		}

		if (IndustryConfig.subpartShapedCharges) {
			r.register(shaped_charge);
		}

		if (IndustryConfig.subpartWorkbenchUpgrades) {
			r.register(iron_workbench);
			r.register(diamond_workbench);
		}

		if (IndustryConfig.subpartIceMaker)
			r.register(ice_maker);

		if (IndustryConfig.subpartElementalBlocks) {
			r.register(fire_block);
			r.register(water_block);
			r.register(lava_block);
			r.register(togglerack);
		}

		if (IndustryConfig.subpartSpikes)
			r.register(spike);

		if (IndustryConfig.subpartGravity) {
			r.register(attractor);
			r.register(repulsor);
			r.register(gravitor);
			r.register(direction_attractor);
			r.register(direction_repulsor);
			r.register(direction_gravitor);
		}

		if (IndustryConfig.subpartNuclear) {
			r.register(nuclear_bomb);
			r.register(reactor);
			r.register(bomb_shell);
			r.register(c4);
			r.register(uranium_ore);
		}

		if (IndustryConfig.subpartGates) {
			r.register(castle_gate);
			r.register(garage);
		}

		if (IndustryConfig.subpartHLights) {
			r.register(halogen_light);
			r.register(halogen_torch);
		}

		if (IndustryConfig.subpartRWays) {
			r.register(sidewalk);
			r.register(rway);
			r.register(rway_light_on);
			r.register(rway_light_off);
			r.register(rway_manhole);
		}

		if (IndustryConfig.subpartDecoration) {
			r.register(fountain);
			r.register(camo_plate);
			r.register(chain_fence);
			r.register(horizontal_siding);
			r.register(vertical_siding);
			r.register(concrete);
		}

		if (IndustryConfig.subpartFans)
			r.register(fan);

		if (IndustryConfig.subpartSensors) {
			r.register(wooden_sensor);
			r.register(stone_sensor);
			r.register(iron_sensor);
			r.register(netherrack_sensor);
			r.register(specific_sensor);
			r.register(upgraded_specific_sensor);
			r.register(arrow_sensor);
			r.register(fire_sensor);
		}

		if (IndustryConfig.subpartMetalWorks) {
			r.register(metal_mesh);
			r.register(aluminum_ladder);
		}

		if (IndustryConfig.subpartSteel) {
			r.register(steel_pipe);
			r.register(steel_frame);
		}

		if (IndustryConfig.subpartConveyor) {
			r.register(conveyor_belt);
		}

		if (IndustryConfig.subpartMachines) {
			r.register(oil_ore);
			r.register(derrick);
			r.register(modern_furnace);
			r.register(refinery);
			r.register(fuel_tank);
			r.register(drill);
			r.register(drill_head);
			r.register(modern_tile);
			r.register(tempered_glass);
		}

		if (IndustryConfig.subpartStorage) {
			r.register(warehouse_crate);
			r.register(wood_cabinet);
			r.register(glass_cabinet);
			r.register(obsidian_safe);
			r.register(gold_safe);
			r.register(locker);
			r.register(item_tower);
			r.register(tank);
			r.register(locksmith_workbench);
		}

		if (IndustryConfig.subpartTorches) {
			r.register(flip_flop_torch);
			r.register(glowstone_torch);
		}

		if (IndustryConfig.subpartBridges) {
			r.register(bridge);
			r.register(bridge_control);
		}

		if(IndustryConfig.subpartChunkLoader){
			r.register(chunk_loader);
		}

		initTileEntities();
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (IndustryConfig.subpartTorches) {
			r.register(new ItemManualBlock(flip_flop_torch).setRegistryName(flip_flop_torch.getRegistryName()));
			r.register(new ItemManualBlock(glowstone_torch).setRegistryName(glowstone_torch.getRegistryName()));
		}

		if (IndustryConfig.subpartStorage) {
			r.register(new ItemGoldSafe(gold_safe).setRegistryName(gold_safe.getRegistryName()));
			r.register(new ItemManualBlock(tank).setRegistryName(tank.getRegistryName()));
			r.register(new ItemManualBlock(item_tower).setRegistryName(item_tower.getRegistryName()));
			r.register(new ItemManualBlock(locker).setRegistryName(locker.getRegistryName()));
			r.register(new ItemManualBlock(obsidian_safe).setRegistryName(obsidian_safe.getRegistryName()));
			r.register(new ItemManualBlock(locksmith_workbench).setRegistryName(locksmith_workbench.getRegistryName()));
			r.register(new ItemManualBlock(wood_cabinet).setRegistryName(wood_cabinet.getRegistryName()));
			r.register(new ItemManualBlock(glass_cabinet).setRegistryName(glass_cabinet.getRegistryName()));
			r.register(new ItemManualBlock(warehouse_crate).setRegistryName(warehouse_crate.getRegistryName()));
		}

		if (IndustryConfig.subpartShapedCharges)
			r.register(new ItemShapedCharge(shaped_charge).setRegistryName(shaped_charge.getRegistryName()));

		if (IndustryConfig.subpartFans)
			r.register(new ItemManualBlock(fan).setRegistryName(fan.getRegistryName()));

		if (IndustryConfig.subpartMetalWorks) {
			r.register(new ItemManualBlock(metal_mesh).setRegistryName(metal_mesh.getRegistryName()));
			r.register(new ItemManualBlock(aluminum_ladder).setRegistryName(aluminum_ladder.getRegistryName()));
		}

		if (IndustryConfig.subpartSteel) {
			r.register(new ItemManualBlock(steel_pipe).setRegistryName(steel_pipe.getRegistryName()));
			r.register(new ItemManualBlock(steel_frame).setRegistryName(steel_frame.getRegistryName()));
		}

		if (IndustryConfig.subpartMachines) {
			r.register(new ItemManualBlock(fuel_tank).setRegistryName(fuel_tank.getRegistryName()));
			r.register(new ItemManualBlock(modern_tile).setRegistryName(modern_tile.getRegistryName()));
			r.register(new ItemManualBlock(tempered_glass).setRegistryName(tempered_glass.getRegistryName()));
			r.register(new ItemManualBlock(oil_ore).setRegistryName(oil_ore.getRegistryName()));
			r.register(new ItemManualBlock(derrick).setRegistryName(derrick.getRegistryName()));
			r.register(new ItemManualBlock(modern_furnace).setRegistryName(modern_furnace.getRegistryName()));
			r.register(new ItemManualBlock(refinery).setRegistryName(refinery.getRegistryName()));
			r.register(new ItemManualBlock(drill).setRegistryName(drill.getRegistryName()));
		}

		if (IndustryConfig.subpartConveyor) {
			r.register(new ItemManualBlock(conveyor_belt).setRegistryName(conveyor_belt.getRegistryName()));
		}

		if (IndustryConfig.subpartDecoration) {
			r.register(new ItemManualBlock(horizontal_siding).setRegistryName(horizontal_siding.getRegistryName()));
			r.register(new ItemManualBlock(vertical_siding).setRegistryName(vertical_siding.getRegistryName()));
			r.register(new ItemManualBlock(concrete).setRegistryName(concrete.getRegistryName()));
			r.register(new ItemManualBlock(fountain).setRegistryName(fountain.getRegistryName()));
			r.register(new ItemManualBlock(camo_plate).setRegistryName(camo_plate.getRegistryName()));
			r.register(new ItemManualBlock(chain_fence).setRegistryName(chain_fence.getRegistryName()));
		}

		if (IndustryConfig.subpartDoors) {
			r.register(new ItemModernDoor(door_chain).setRegistryName(door_chain.getRegistryName()));
			r.register(new ItemModernDoor(door_glass).setRegistryName(door_glass.getRegistryName()));
			r.register(new ItemModernDoor(door_steel).setRegistryName(door_steel.getRegistryName()));
		}

		if (IndustryConfig.subpartRWays) {
			r.register(new ItemManualBlock(sidewalk).setRegistryName(sidewalk.getRegistryName()));
			r.register(new ItemManualBlock(rway).setRegistryName(rway.getRegistryName()));
			r.register(new ItemManualBlock(rway_light_off).setRegistryName(rway_light_off.getRegistryName()));
			r.register(new ItemManualBlock(rway_manhole).setRegistryName(rway_manhole.getRegistryName()));
		}

		if (IndustryConfig.subpartHLights) {
			r.register(new ItemManualBlock(halogen_light).setRegistryName(halogen_light.getRegistryName()));
			r.register(new ItemManualBlock(halogen_torch).setRegistryName(halogen_torch.getRegistryName()));
		}

		if (IndustryConfig.subpartGates) {
			r.register(new ItemManualBlock(castle_gate).setRegistryName(castle_gate.getRegistryName()));
			r.register(new ItemManualBlock(garage).setRegistryName(garage.getRegistryName()));
		}

		if (IndustryConfig.subpartGravity) {
			r.register(new ItemManualBlock(attractor).setRegistryName(attractor.getRegistryName()));
			r.register(new ItemManualBlock(repulsor).setRegistryName(repulsor.getRegistryName()));
			r.register(new ItemManualBlock(gravitor).setRegistryName(gravitor.getRegistryName()));
			r.register(new ItemManualBlock(direction_attractor).setRegistryName(direction_attractor.getRegistryName()));
			r.register(new ItemManualBlock(direction_repulsor).setRegistryName(direction_repulsor.getRegistryName()));
			r.register(new ItemManualBlock(direction_gravitor).setRegistryName(direction_gravitor.getRegistryName()));
		}

		if (IndustryConfig.subpartSensors) {
			r.register(new ItemSensor(wooden_sensor).setRegistryName(wooden_sensor.getRegistryName()));
			r.register(new ItemSensor(stone_sensor).setRegistryName(stone_sensor.getRegistryName()));
			r.register(new ItemSensor(iron_sensor).setRegistryName(iron_sensor.getRegistryName()));
			r.register(new ItemSensor(netherrack_sensor).setRegistryName(netherrack_sensor.getRegistryName()));
			r.register(new ItemManualBlock(upgraded_specific_sensor).setRegistryName(upgraded_specific_sensor.getRegistryName()));
			r.register(new ItemManualBlock(specific_sensor).setRegistryName(specific_sensor.getRegistryName()));
			r.register(new ItemManualBlock(arrow_sensor).setRegistryName(arrow_sensor.getRegistryName()));
			r.register(new ItemManualBlock(fire_sensor).setRegistryName(fire_sensor.getRegistryName()));
		}

		if (IndustryConfig.subpartSpikes)
			r.register(new ItemManualBlock(spike).setRegistryName(spike.getRegistryName()));

		if (IndustryConfig.subpartElementalBlocks) {
			r.register(new ItemManualBlock(water_block).setRegistryName(water_block.getRegistryName()));
			r.register(new ItemManualBlock(lava_block).setRegistryName(lava_block.getRegistryName()));
			r.register(new ItemManualBlock(fire_block).setRegistryName(fire_block.getRegistryName()));
			r.register(new ItemManualBlock(togglerack).setRegistryName(togglerack.getRegistryName()));
		}

		if (IndustryConfig.subpartWorkbenchUpgrades) {
			r.register(new ItemManualBlock(iron_workbench).setRegistryName(iron_workbench.getRegistryName()));
			r.register(new ItemManualBlock(diamond_workbench).setRegistryName(diamond_workbench.getRegistryName()));
		}

		if (IndustryConfig.subpartIceMaker)
			r.register(new ItemManualBlock(ice_maker).setRegistryName(ice_maker.getRegistryName()));

		if (IndustryConfig.subpartNuclear) {
			r.register(new ItemManualBlock(uranium_ore).setRegistryName(uranium_ore.getRegistryName()));
			r.register(new ItemManualBlock(bomb_shell).setRegistryName(bomb_shell.getRegistryName()));
			r.register(new ItemManualBlock(c4).setRegistryName(c4.getRegistryName()));
			r.register(new ItemManualBlock(nuclear_bomb).setRegistryName(nuclear_bomb.getRegistryName()));
			r.register(new ItemManualBlock(reactor).setRegistryName(reactor.getRegistryName()));
		}

		if (IndustryConfig.subpartBridges) {
			r.register(new ItemBridgeControl(bridge_control).setRegistryName(bridge_control.getRegistryName()));
		}

		if(IndustryConfig.subpartChunkLoader){
			r.register(new ItemManualBlock(chunk_loader).setRegistryName(chunk_loader.getRegistryName()));
		}

		initOreDict();
	}

	private void initTileEntities() {
		if (IndustryConfig.subpartGravity) {
			GameRegistry.registerTileEntity(TileEntityGravity.class, new ResourceLocation(GrimPack.modID, "gravity"));
			GameRegistry.registerTileEntity(TileEntityDGravity.class, new ResourceLocation(GrimPack.modID, "directional_gravity"));
		}

		if (IndustryConfig.subpartDecoration)
			GameRegistry.registerTileEntity(TileEntityCamo.class, new ResourceLocation(GrimPack.modID, "camo_plate"));

		if (IndustryConfig.subpartMachines) {
			GameRegistry.registerTileEntity(TileEntityMachine.class, new ResourceLocation(GrimPack.modID, "machine"));
			GameRegistry.registerTileEntity(TileEntityMFurnace.class, new ResourceLocation(GrimPack.modID, "modern_furnace"));
		}

		if (IndustryConfig.subpartFans)
			GameRegistry.registerTileEntity(TileEntityFan.class, new ResourceLocation(GrimPack.modID, "fan"));

		if (IndustryConfig.subpartSensors) {
			GameRegistry.registerTileEntity(TileEntitySpecificSensor.class, new ResourceLocation(GrimPack.modID, "specific_sensor"));
			GameRegistry.registerTileEntity(TileEntityFireSensor.class, new ResourceLocation(GrimPack.modID, "fire_sensor"));
			GameRegistry.registerTileEntity(TileEntitySensor.class, new ResourceLocation(GrimPack.modID, "sensors"));
		}

		if (IndustryConfig.subpartStorage) {
			GameRegistry.registerTileEntity(TileEntityWarehouseCrate.class, new ResourceLocation(GrimPack.modID, "warehouse_crate"));
			GameRegistry.registerTileEntity(TileEntityGlassCabinet.class, new ResourceLocation(GrimPack.modID, "glass_cabinet"));
			GameRegistry.registerTileEntity(TileEntityWoodCabinet.class, new ResourceLocation(GrimPack.modID, "wood_cabinet"));
			GameRegistry.registerTileEntity(TileEntityObsidianSafe.class, new ResourceLocation(GrimPack.modID, "obsidian_safe"));
			GameRegistry.registerTileEntity(TileEntityGoldSafe.class, new ResourceLocation(GrimPack.modID, "gold_safe"));
			GameRegistry.registerTileEntity(TileEntityLocker.class, new ResourceLocation(GrimPack.modID, "locker"));
			GameRegistry.registerTileEntity(TileEntityItemTower.class, new ResourceLocation(GrimPack.modID, "item_tower"));
			GameRegistry.registerTileEntity(TileEntityTank.class, new ResourceLocation(GrimPack.modID, "tank"));
		}

		if (IndustryConfig.subpartTorches) {
			GameRegistry.registerTileEntity(TileEntityFlipFlopTorch.class, new ResourceLocation(GrimPack.modID, "flip_flop_torch"));
		}

		if (IndustryConfig.subpartBridges) {
			GameRegistry.registerTileEntity(TileEntityBridge.class, new ResourceLocation(GrimPack.modID, "grimpack_bridge"));
			GameRegistry.registerTileEntity(TileEntityBridgeGravity.class, new ResourceLocation(GrimPack.modID, "grimpack_gravity_bridge"));
			GameRegistry.registerTileEntity(TileEntityBridgeControl.class, new ResourceLocation(GrimPack.modID, "grimpack_bridge_control"));
		}

		if(IndustryConfig.subpartChunkLoader){
			GameRegistry.registerTileEntity(TileEntityChunkLoader.class, new ResourceLocation(GrimPack.modID, "grimpack_chunk_loader"));
		}
	}

	private void initOreDict() {
		if (IndustryConfig.subpartNuclear)
			OreDictionary.registerOre("oreUranium", uranium_ore);

		if (IndustryConfig.subpartMachines) {
			OreDictionary.registerOre("blockGlass", tempered_glass);
			OreDictionary.registerOre("oreOil", oil_ore);
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (IndustryConfig.subpartNuclear)
			GameRegistry.addSmelting(uranium_ore, new ItemStack(IndustryItems.uranium_ingot), 0.7F);
	}
}