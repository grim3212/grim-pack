package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
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
import com.grim3212.mc.pack.industry.init.IndustryNames;
import com.grim3212.mc.pack.industry.item.ItemBridgeControl;
import com.grim3212.mc.pack.industry.item.ItemGoldSafe;
import com.grim3212.mc.pack.industry.item.ItemModernDoor;
import com.grim3212.mc.pack.industry.item.ItemSensor;
import com.grim3212.mc.pack.industry.item.ItemShapedCharge;
import com.grim3212.mc.pack.industry.tile.TileEntityBridge;
import com.grim3212.mc.pack.industry.tile.TileEntityBridgeControl;
import com.grim3212.mc.pack.industry.tile.TileEntityBridgeGravity;
import com.grim3212.mc.pack.industry.tile.TileEntityCamo;
import com.grim3212.mc.pack.industry.tile.TileEntityChunkLoader;
import com.grim3212.mc.pack.industry.tile.TileEntityDGravity;
import com.grim3212.mc.pack.industry.tile.TileEntityFan;
import com.grim3212.mc.pack.industry.tile.TileEntityFireSensor;
import com.grim3212.mc.pack.industry.tile.TileEntityFlipFlopTorch;
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
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class IndustryBlocks {

	@ObjectHolder(IndustryNames.TOGGLE_RACK)
	public static Block togglerack;
	@ObjectHolder(IndustryNames.IRON_WORKBENCH)
	public static Block iron_workbench;
	@ObjectHolder(IndustryNames.DIAMOND_WORKBENCH)
	public static Block diamond_workbench;
	@ObjectHolder(IndustryNames.ICE_MAKER)
	public static Block ice_maker;
	@ObjectHolder(IndustryNames.ELEMENTAL_FIRE)
	public static Block elemental_fire;
	@ObjectHolder(IndustryNames.ELEMENTAL_WATER)
	public static Block elemental_water;
	@ObjectHolder(IndustryNames.ELEMENTAL_LAVA)
	public static Block elemental_lava;
	@ObjectHolder(IndustryNames.SPIKE)
	public static Block spike;
	@ObjectHolder(IndustryNames.WOODEN_SENSOR)
	public static Block wooden_sensor;
	@ObjectHolder(IndustryNames.STONE_SENSOR)
	public static Block stone_sensor;
	@ObjectHolder(IndustryNames.IRON_SENSOR)
	public static Block iron_sensor;
	@ObjectHolder(IndustryNames.NETHERRACK_SENSOR)
	public static Block netherrack_sensor;
	@ObjectHolder(IndustryNames.ATTRACTOR)
	public static Block attractor;
	@ObjectHolder(IndustryNames.REPULSOR)
	public static Block repulsor;
	@ObjectHolder(IndustryNames.GRAVITOR)
	public static Block gravitor;
	@ObjectHolder(IndustryNames.DIRECTION_ATTRACTOR)
	public static Block direction_attractor;
	@ObjectHolder(IndustryNames.DIRECTION_REPULSOR)
	public static Block direction_repulsor;
	@ObjectHolder(IndustryNames.DIRECTION_GRAVITOR)
	public static Block direction_gravitor;
	@ObjectHolder(IndustryNames.REACTOR)
	public static Block reactor;
	@ObjectHolder(IndustryNames.BOMB_SHELL)
	public static Block bomb_shell;
	@ObjectHolder(IndustryNames.C4)
	public static Block c4;
	@ObjectHolder(IndustryNames.NUCLEAR_BOMB)
	public static Block nuclear_bomb;
	@ObjectHolder(IndustryNames.URANIUM_ORE)
	public static Block uranium_ore;
	@ObjectHolder(IndustryNames.CASTLE_GATE)
	public static Block castle_gate;
	@ObjectHolder(IndustryNames.GARAGE)
	public static Block garage;
	@ObjectHolder(IndustryNames.HALOGEN_LIGHT)
	public static Block halogen_light;
	@ObjectHolder(IndustryNames.HALOGEN_TORCH)
	public static Block halogen_torch;
	@ObjectHolder(IndustryNames.SIDEWALK)
	public static Block sidewalk;
	@ObjectHolder(IndustryNames.RWAY)
	public static Block rway;
	@ObjectHolder(IndustryNames.RWAY_LIGHT_ON)
	public static Block rway_light_on;
	@ObjectHolder(IndustryNames.RWAY_LIGHT_OFF)
	public static Block rway_light_off;
	@ObjectHolder(IndustryNames.RWAY_MANHOLE)
	public static Block rway_manhole;
	@ObjectHolder(IndustryNames.DOOR_CHAIN)
	public static Block door_chain;
	@ObjectHolder(IndustryNames.DOOR_GLASS)
	public static Block door_glass;
	@ObjectHolder(IndustryNames.DOOR_STEEL)
	public static Block door_steel;
	@ObjectHolder(IndustryNames.CONCRETE)
	public static Block concrete;
	@ObjectHolder(IndustryNames.MODERN_TILE)
	public static Block modern_tile;
	@ObjectHolder(IndustryNames.TEMPERED_GLASS)
	public static Block tempered_glass;
	@ObjectHolder(IndustryNames.HORIZONTAL_SIDING)
	public static Block horizontal_siding;
	@ObjectHolder(IndustryNames.VERTICAL_SIDING)
	public static Block vertical_siding;
	@ObjectHolder(IndustryNames.FOUNTAIN)
	public static Block fountain;
	@ObjectHolder(IndustryNames.CAMO_PLATE)
	public static Block camo_plate;
	@ObjectHolder(IndustryNames.CHAIN_FENCE)
	public static Block chain_fence;
	@ObjectHolder(IndustryNames.ALUMINUM_LADDER)
	public static Block aluminum_ladder;
	@ObjectHolder(IndustryNames.OIL_ORE)
	public static Block oil_ore;
	@ObjectHolder(IndustryNames.DERRICK)
	public static Block derrick;
	@ObjectHolder(IndustryNames.MODERN_FURNACE)
	public static Block modern_furnace;
	@ObjectHolder(IndustryNames.STEEL_PIPE)
	public static Block steel_pipe;
	@ObjectHolder(IndustryNames.REFINERY)
	public static Block refinery;
	@ObjectHolder(IndustryNames.STEEL_FRAME)
	public static Block steel_frame;
	@ObjectHolder(IndustryNames.FUEL_TANK)
	public static Block fuel_tank;
	@ObjectHolder(IndustryNames.FAN)
	public static Block fan;
	@ObjectHolder(IndustryNames.SPECIFIC_SENSOR)
	public static Block specific_sensor;
	@ObjectHolder(IndustryNames.UPGRADED_SPECIFIC_SENSOR)
	public static Block upgraded_specific_sensor;
	@ObjectHolder(IndustryNames.ARROW_SENSOR)
	public static Block arrow_sensor;
	@ObjectHolder(IndustryNames.METAL_MESH)
	public static Block metal_mesh;
	@ObjectHolder(IndustryNames.FIRE_SENSOR)
	public static Block fire_sensor;
	@ObjectHolder(IndustryNames.CONVEYOR_BELT)
	public static Block conveyor_belt;
	@ObjectHolder(IndustryNames.DRILL)
	public static Block drill;
	@ObjectHolder(IndustryNames.DRILL_HEAD)
	public static Block drill_head;
	@ObjectHolder(IndustryNames.LOCKSMITH_WORKBENCH)
	public static Block locksmith_workbench;
	@ObjectHolder(IndustryNames.WOOD_CABINET)
	public static Block wood_cabinet;
	@ObjectHolder(IndustryNames.GLASS_CABINET)
	public static Block glass_cabinet;
	@ObjectHolder(IndustryNames.WAREHOUSE_CRATE)
	public static Block warehouse_crate;
	@ObjectHolder(IndustryNames.OBSIDIAN_SAFE)
	public static Block obsidian_safe;
	@ObjectHolder(IndustryNames.GOLD_SAFE)
	public static Block gold_safe;
	@ObjectHolder(IndustryNames.LOCKER)
	public static Block locker;
	@ObjectHolder(IndustryNames.ITEM_TOWER)
	public static Block item_tower;
	@ObjectHolder(IndustryNames.TANK)
	public static Block tank;
	@ObjectHolder(IndustryNames.SHAPED_CHARGE)
	public static Block shaped_charge;
	@ObjectHolder(IndustryNames.FLIP_FLOP_TORCH)
	public static Block flip_flop_torch;
	@ObjectHolder(IndustryNames.GLOWSTONE_TORCH)
	public static Block glowstone_torch;
	@ObjectHolder(IndustryNames.CHUNK_LOADER)
	public static Block chunk_loader;

	// Bridges
	@ObjectHolder(IndustryNames.BRIDGE_CONTROL)
	public static Block bridge_control;
	@ObjectHolder(IndustryNames.BRIDGE)
	public static Block bridge;

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (IndustryConfig.subpartDoors.get()) {
			r.register(new BlockModernDoor(Material.MISCELLANEOUS));
			r.register(new BlockModernDoor(Material.GLASS));
			r.register(new BlockModernDoor(Material.ANVIL));
		}

		if (IndustryConfig.subpartShapedCharges.get()) {
			r.register(new BlockShapedCharge());
		}

		if (IndustryConfig.subpartWorkbenchUpgrades.get()) {
			r.register(new BlockIronWorkbench());
			r.register(new BlockDiamondWorkbench());
		}

		if (IndustryConfig.subpartIceMaker.get())
			r.register(new BlockIceMaker());

		if (IndustryConfig.subpartElementalBlocks.get()) {
			r.register(new BlockElemental(ElementType.FIRE));
			r.register(new BlockElemental(ElementType.WATER));
			r.register(new BlockElemental(ElementType.LAVA));
			r.register(new BlockToggleRack());
		}

		if (IndustryConfig.subpartSpikes.get())
			r.register(new BlockSpike());

		if (IndustryConfig.subpartGravity.get()) {
			r.register(new BlockGravity(IndustryNames.ATTRACTOR, -1));
			r.register(new BlockGravity(IndustryNames.REPULSOR, 1));
			r.register(new BlockGravity(IndustryNames.GRAVITOR, 0));
			r.register(new BlockDGravity(IndustryNames.DIRECTION_ATTRACTOR, -1));
			r.register(new BlockDGravity(IndustryNames.DIRECTION_REPULSOR, 1));
			r.register(new BlockDGravity(IndustryNames.DIRECTION_GRAVITOR, 0));
		}

		if (IndustryConfig.subpartNuclear.get()) {
			r.register(new BlockNuclearBomb());
			r.register(new BlockNuclearReactor());
			r.register(new BlockManualPage(IndustryNames.BOMB_SHELL, Material.IRON, SoundType.METAL, "industry:explosives.bomb_shell").setHardness(1.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new BlockC4());
			r.register(new BlockUraniumOre());
		}

		if (IndustryConfig.subpartGates.get()) {
			r.register(new BlockGate(IndustryNames.CASTLE_GATE));
			r.register(new BlockGate(IndustryNames.GARAGE));
		}

		if (IndustryConfig.subpartHLights.get()) {
			r.register(new BlockHTorch());
			r.register(new BlockHLight());
		}

		if (IndustryConfig.subpartRWays.get()) {
			r.register(new BlockSwalk());
			r.register(new BlockRway());
			r.register(new BlockRwayLight(true));
			r.register(new BlockRwayLight(false));
			r.register(new BlockRwayManhole());
		}

		if (IndustryConfig.subpartDecoration.get()) {
			r.register(new BlockFountain());
			r.register(new BlockCamoPlate());
			r.register(new BlockChainFence());
			r.register(new BlockSiding(IndustryNames.HORIZONTAL_SIDING));
			r.register(new BlockSiding(IndustryNames.VERTICAL_SIDING));
			r.register(new BlockManualPage(IndustryNames.CONCRETE, Material.ROCK, SoundType.STONE, "industry:moderntech.decoration").setHardness(1.0F).setResistance(30.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
		}

		if (IndustryConfig.subpartFans.get())
			r.register(new BlockFan());

		if (IndustryConfig.subpartSensors.get()) {
			r.register(new BlockSensor(IndustryNames.WOODEN_SENSOR, 0));
			r.register(new BlockSensor(IndustryNames.STONE_SENSOR, 1));
			r.register(new BlockSensor(IndustryNames.IRON_SENSOR, 2));
			r.register(new BlockSensor(IndustryNames.NETHERRACK_SENSOR, 3));
			r.register(new BlockSpecificSensor(false));
			r.register(new BlockSpecificSensor(true));
			r.register(new BlockSensorArrow());
			r.register(new BlockFireSensor());
		}

		if (IndustryConfig.subpartMetalWorks.get()) {
			r.register(new BlockMetalMesh());
			r.register(new BlockAluminumLadder());
		}

		if (IndustryConfig.subpartSteel.get()) {
			r.register(new BlockSteelPipe());
			r.register(new BlockSteelFrame());
		}

		if (IndustryConfig.subpartConveyor.get()) {
			r.register(new BlockConveyorBelt());
		}

		if (IndustryConfig.subpartMachines.get()) {
			r.register(new BlockOilOre());
			r.register(new BlockDerrick());
			r.register(new BlockModernFurnace());
			r.register(new BlockRefinery());
			r.register(new BlockFuel());
			r.register(new BlockDrill());
			r.register(new BlockDrillHead());
			r.register(new BlockManualPage(IndustryNames.MODERN_TILE, Material.ROCK, SoundType.STONE, "industry:metalworks.mfurnace_recipes").setHardness(1.0F).setResistance(15.0F).setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY));
			r.register(new BlockTemperedGlass());
		}

		if (IndustryConfig.subpartStorage.get()) {
			r.register(new BlockWarehouseCrate());
			r.register(new BlockWoodCabinet());
			r.register(new BlockGlassCabinet());
			r.register(new BlockObsidianSafe());
			r.register(new BlockGoldSafe());
			r.register(new BlockLocker());
			r.register(new BlockItemTower());
			r.register(new BlockTank());
			r.register(new BlockLocksmithWorkbench());
		}

		if (IndustryConfig.subpartTorches.get()) {
			r.register(new BlockFlipFlopTorch());
			r.register(new BlockGlowstoneTorch());
		}

		if (IndustryConfig.subpartBridges.get()) {
			r.register(new BlockBridge());
			r.register(new BlockBridgeControl());
		}

		if (IndustryConfig.subpartChunkLoader.get()) {
			r.register(new BlockChunkLoader());
		}

		initTileEntities();
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (IndustryConfig.subpartTorches.get()) {
			r.register(new ItemManualBlock(flip_flop_torch).setRegistryName(flip_flop_torch.getRegistryName()));
			r.register(new ItemManualBlock(glowstone_torch).setRegistryName(glowstone_torch.getRegistryName()));
		}

		if (IndustryConfig.subpartStorage.get()) {
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

		if (IndustryConfig.subpartShapedCharges.get())
			r.register(new ItemShapedCharge(shaped_charge).setRegistryName(shaped_charge.getRegistryName()));

		if (IndustryConfig.subpartFans.get())
			r.register(new ItemManualBlock(fan).setRegistryName(fan.getRegistryName()));

		if (IndustryConfig.subpartMetalWorks.get()) {
			r.register(new ItemManualBlock(metal_mesh).setRegistryName(metal_mesh.getRegistryName()));
			r.register(new ItemManualBlock(aluminum_ladder).setRegistryName(aluminum_ladder.getRegistryName()));
		}

		if (IndustryConfig.subpartSteel.get()) {
			r.register(new ItemManualBlock(steel_pipe).setRegistryName(steel_pipe.getRegistryName()));
			r.register(new ItemManualBlock(steel_frame).setRegistryName(steel_frame.getRegistryName()));
		}

		if (IndustryConfig.subpartMachines.get()) {
			r.register(new ItemManualBlock(fuel_tank).setRegistryName(fuel_tank.getRegistryName()));
			r.register(new ItemManualBlock(modern_tile).setRegistryName(modern_tile.getRegistryName()));
			r.register(new ItemManualBlock(tempered_glass).setRegistryName(tempered_glass.getRegistryName()));
			r.register(new ItemManualBlock(oil_ore).setRegistryName(oil_ore.getRegistryName()));
			r.register(new ItemManualBlock(derrick).setRegistryName(derrick.getRegistryName()));
			r.register(new ItemManualBlock(modern_furnace).setRegistryName(modern_furnace.getRegistryName()));
			r.register(new ItemManualBlock(refinery).setRegistryName(refinery.getRegistryName()));
			r.register(new ItemManualBlock(drill).setRegistryName(drill.getRegistryName()));
		}

		if (IndustryConfig.subpartConveyor.get()) {
			r.register(new ItemManualBlock(conveyor_belt).setRegistryName(conveyor_belt.getRegistryName()));
		}

		if (IndustryConfig.subpartDecoration.get()) {
			r.register(new ItemManualBlock(horizontal_siding).setRegistryName(horizontal_siding.getRegistryName()));
			r.register(new ItemManualBlock(vertical_siding).setRegistryName(vertical_siding.getRegistryName()));
			r.register(new ItemManualBlock(concrete).setRegistryName(concrete.getRegistryName()));
			r.register(new ItemManualBlock(fountain).setRegistryName(fountain.getRegistryName()));
			r.register(new ItemManualBlock(camo_plate).setRegistryName(camo_plate.getRegistryName()));
			r.register(new ItemManualBlock(chain_fence).setRegistryName(chain_fence.getRegistryName()));
		}

		if (IndustryConfig.subpartDoors.get()) {
			r.register(new ItemModernDoor(door_chain).setRegistryName(door_chain.getRegistryName()));
			r.register(new ItemModernDoor(door_glass).setRegistryName(door_glass.getRegistryName()));
			r.register(new ItemModernDoor(door_steel).setRegistryName(door_steel.getRegistryName()));
		}

		if (IndustryConfig.subpartRWays.get()) {
			r.register(new ItemManualBlock(sidewalk).setRegistryName(sidewalk.getRegistryName()));
			r.register(new ItemManualBlock(rway).setRegistryName(rway.getRegistryName()));
			r.register(new ItemManualBlock(rway_light_off).setRegistryName(rway_light_off.getRegistryName()));
			r.register(new ItemManualBlock(rway_manhole).setRegistryName(rway_manhole.getRegistryName()));
		}

		if (IndustryConfig.subpartHLights.get()) {
			r.register(new ItemManualBlock(halogen_light).setRegistryName(halogen_light.getRegistryName()));
			r.register(new ItemManualBlock(halogen_torch).setRegistryName(halogen_torch.getRegistryName()));
		}

		if (IndustryConfig.subpartGates.get()) {
			r.register(new ItemManualBlock(castle_gate).setRegistryName(castle_gate.getRegistryName()));
			r.register(new ItemManualBlock(garage).setRegistryName(garage.getRegistryName()));
		}

		if (IndustryConfig.subpartGravity.get()) {
			r.register(new ItemManualBlock(attractor).setRegistryName(attractor.getRegistryName()));
			r.register(new ItemManualBlock(repulsor).setRegistryName(repulsor.getRegistryName()));
			r.register(new ItemManualBlock(gravitor).setRegistryName(gravitor.getRegistryName()));
			r.register(new ItemManualBlock(direction_attractor).setRegistryName(direction_attractor.getRegistryName()));
			r.register(new ItemManualBlock(direction_repulsor).setRegistryName(direction_repulsor.getRegistryName()));
			r.register(new ItemManualBlock(direction_gravitor).setRegistryName(direction_gravitor.getRegistryName()));
		}

		if (IndustryConfig.subpartSensors.get()) {
			r.register(new ItemSensor(wooden_sensor).setRegistryName(wooden_sensor.getRegistryName()));
			r.register(new ItemSensor(stone_sensor).setRegistryName(stone_sensor.getRegistryName()));
			r.register(new ItemSensor(iron_sensor).setRegistryName(iron_sensor.getRegistryName()));
			r.register(new ItemSensor(netherrack_sensor).setRegistryName(netherrack_sensor.getRegistryName()));
			r.register(new ItemManualBlock(upgraded_specific_sensor).setRegistryName(upgraded_specific_sensor.getRegistryName()));
			r.register(new ItemManualBlock(specific_sensor).setRegistryName(specific_sensor.getRegistryName()));
			r.register(new ItemManualBlock(arrow_sensor).setRegistryName(arrow_sensor.getRegistryName()));
			r.register(new ItemManualBlock(fire_sensor).setRegistryName(fire_sensor.getRegistryName()));
		}

		if (IndustryConfig.subpartSpikes.get())
			r.register(new ItemManualBlock(spike).setRegistryName(spike.getRegistryName()));

		if (IndustryConfig.subpartElementalBlocks.get()) {
			r.register(new ItemManualBlock(water_block).setRegistryName(water_block.getRegistryName()));
			r.register(new ItemManualBlock(lava_block).setRegistryName(lava_block.getRegistryName()));
			r.register(new ItemManualBlock(fire_block).setRegistryName(fire_block.getRegistryName()));
			r.register(new ItemManualBlock(togglerack).setRegistryName(togglerack.getRegistryName()));
		}

		if (IndustryConfig.subpartWorkbenchUpgrades.get()) {
			r.register(new ItemManualBlock(iron_workbench).setRegistryName(iron_workbench.getRegistryName()));
			r.register(new ItemManualBlock(diamond_workbench).setRegistryName(diamond_workbench.getRegistryName()));
		}

		if (IndustryConfig.subpartIceMaker.get())
			r.register(new ItemManualBlock(ice_maker).setRegistryName(ice_maker.getRegistryName()));

		if (IndustryConfig.subpartNuclear.get()) {
			r.register(new ItemManualBlock(uranium_ore).setRegistryName(uranium_ore.getRegistryName()));
			r.register(new ItemManualBlock(bomb_shell).setRegistryName(bomb_shell.getRegistryName()));
			r.register(new ItemManualBlock(c4).setRegistryName(c4.getRegistryName()));
			r.register(new ItemManualBlock(nuclear_bomb).setRegistryName(nuclear_bomb.getRegistryName()));
			r.register(new ItemManualBlock(reactor).setRegistryName(reactor.getRegistryName()));
		}

		if (IndustryConfig.subpartBridges.get()) {
			r.register(new ItemBridgeControl(bridge_control).setRegistryName(bridge_control.getRegistryName()));
		}

		if (IndustryConfig.subpartChunkLoader.get()) {
			r.register(new ItemManualBlock(chunk_loader).setRegistryName(chunk_loader.getRegistryName()));
		}
	}

	private void initTileEntities() {
		if (IndustryConfig.subpartGravity.get()) {
			GameRegistry.registerTileEntity(TileEntityGravity.class, new ResourceLocation(GrimPack.modID, "gravity"));
			GameRegistry.registerTileEntity(TileEntityDGravity.class, new ResourceLocation(GrimPack.modID, "directional_gravity"));
		}

		if (IndustryConfig.subpartDecoration.get())
			GameRegistry.registerTileEntity(TileEntityCamo.class, new ResourceLocation(GrimPack.modID, "camo_plate"));

		if (IndustryConfig.subpartMachines.get()) {
			GameRegistry.registerTileEntity(TileEntityMachine.class, new ResourceLocation(GrimPack.modID, "machine"));
			GameRegistry.registerTileEntity(TileEntityMFurnace.class, new ResourceLocation(GrimPack.modID, "modern_furnace"));
		}

		if (IndustryConfig.subpartFans.get())
			GameRegistry.registerTileEntity(TileEntityFan.class, new ResourceLocation(GrimPack.modID, "fan"));

		if (IndustryConfig.subpartSensors.get()) {
			GameRegistry.registerTileEntity(TileEntitySpecificSensor.class, new ResourceLocation(GrimPack.modID, "specific_sensor"));
			GameRegistry.registerTileEntity(TileEntityFireSensor.class, new ResourceLocation(GrimPack.modID, "fire_sensor"));
			GameRegistry.registerTileEntity(TileEntitySensor.class, new ResourceLocation(GrimPack.modID, "sensors"));
		}

		if (IndustryConfig.subpartStorage.get()) {
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

		if (IndustryConfig.subpartChunkLoader) {
			GameRegistry.registerTileEntity(TileEntityChunkLoader.class, new ResourceLocation(GrimPack.modID, "grimpack_chunk_loader"));
		}
	}
}