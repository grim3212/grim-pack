package com.grim3212.mc.pack.industry.client;

import com.google.common.collect.ImmutableList;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.init.CoreNames;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageCrafting;
import com.grim3212.mc.pack.core.manual.pages.PageFurnace;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.client.pages.PageMachine;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.init.IndustryNames;
import com.grim3212.mc.pack.industry.util.MachineRecipes;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraftforge.oredict.OreDictionary;

public class ManualIndustry implements IManualPart {

	public static ManualIndustry INSTANCE = new ManualIndustry();

	public static Page workbench_page;
	public static Page iceMaker_page;
	public static Page togglerack_page;
	public static Page fireBlock_page;
	public static Page waterBlock_page;
	public static Page lavaBlock_page;
	public static Page spike_page;
	public static Page sensor_page;
	public static Page gravityBoots_page;
	public static Page controller_page;
	public static Page attract_page;
	public static Page repulse_page;
	public static Page mob_repulsor_page;
	public static Page gravitor_page;
	public static Page uranium_page;
	public static Page radiationSuit_page;
	public static Page uraniumSmelt_page;
	public static Page refinedUranium_page;
	public static Page plutonium_page;
	public static Page refinedPlutonium_page;
	public static Page reactorCore_page;
	public static Page reactorCase_page;
	public static Page ironParts_page;
	public static Page reactor_page;
	public static Page bombShell_page;
	public static Page c4_page;
	public static Page nuclearBomb_page;
	public static Page gate_page;
	public static Page gateTrumpet_page;
	public static Page garage_page;
	public static Page garageRemote_page;
	public static Page hLight_page;
	public static Page hTorch_page;
	public static Page sidewalk_page;
	public static Page tarball_page;
	public static Page asphalt_page;
	public static Page rways_page;
	public static Page paint_page;
	public static Page doors_page;
	public static Page others_page;
	public static Page decoration_page;
	public static Page paintTech_page;
	public static Page buckLadd_page;
	public static Page coalIron_page;
	public static Page steelIngot_page;
	public static Page steelStuff_page;
	public static Page steelTools_page;
	public static Page fuel_page;
	public static Page machineInfo_page;
	public static Page refinery_page;
	public static Page refineryRecipes_page;
	public static Page derrick_page;
	public static Page derrickRecipes_page;
	public static Page modernFurnace_page;
	public static Page modernFurnaceRecipes_page;
	public static Page extruder_page;
	public static Page extruderInfo_page;
	public static Page fan_page;
	public static Page portableUpgrade_page;
	public static Page specificSensor_page;
	public static Page upgradedSpecificSensor_page;
	public static Page positionFinder_page;
	public static Page specificSensorInfo_page;
	public static Page specificSensorInfo2_page;
	public static Page arrowSensor_page;
	public static Page fireSensor_page;
	public static Page metalMesh_page;
	public static Page conveyorBelt_page;
	public static Page drill_page;
	public static Page combination_page;
	public static Page locksmithWorkbench_page;
	public static Page warehouseCrate_page;
	public static Page safes_page;
	public static Page locker_page;
	public static Page cabinets_page;
	public static Page itemTower_page;
	public static Page tank_page;
	public static Page shapedChargeBase_page;
	public static Page shapedCharge_page;
	public static Page flipFlopTorch_page;
	public static Page glowstoneTorch_page;
	public static Page bridgeLaser_page;
	public static Page bridgeAccel_page;
	public static Page bridgeTrick_page;
	public static Page bridgeDeath_page;
	public static Page gravLift_page;
	public static Page chunkLoader_page;

	@Override
	public void initPages() {
		if (IndustryConfig.subpartStorage.get()) {
			tank_page = new PageCrafting("tank", RecipeHelper.getRecipePath(IndustryNames.TANK));
			combination_page = new PageCrafting("combination", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.LOCKSMITH_KEY), RecipeHelper.getRecipePath(IndustryNames.LOCKSMITH_LOCK)), 25);
			locksmithWorkbench_page = new PageCrafting("locksmith_workbench", RecipeHelper.getRecipePath(IndustryNames.LOCKSMITH_WORKBENCH));
			warehouseCrate_page = new PageCrafting("warehouse_crate", RecipeHelper.getRecipePath(IndustryNames.WAREHOUSE_CRATE));
			cabinets_page = new PageCrafting("cabinets", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.WOOD_CABINET), RecipeHelper.getRecipePath(IndustryNames.GLASS_CABINET)), 25);
			safes_page = new PageCrafting("safes", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.OBSIDIAN_SAFE), RecipeHelper.getRecipePath(IndustryNames.GOLD_SAFE)), 25);
			locker_page = new PageCrafting("locker", RecipeHelper.getRecipePath(IndustryNames.LOCKER));
			itemTower_page = new PageCrafting("item_tower", RecipeHelper.getRecipePath(IndustryNames.ITEM_TOWER));
		}

		if (IndustryConfig.subpartShapedCharges.get()) {
			shapedChargeBase_page = new PageCrafting("shaped_charge", RecipeHelper.createPath("shaped_charge"));
			shapedCharge_page = new PageCrafting("shaped_charge_radius", RecipeHelper.getAllPaths("shaped_charge_"), 20);
		}

		if (IndustryConfig.subpartSensors.get()) {
			specificSensorInfo_page = new PageInfo("specific_sensor_info");
			specificSensorInfo2_page = new PageInfo("specific_sensor_info2");
			positionFinder_page = new PageCrafting("position_finder", RecipeHelper.getRecipePath(IndustryNames.POSITION_FINDER)).appendImageUrl("gps.png");
			specificSensor_page = new PageCrafting("specific_sensor", RecipeHelper.getRecipePath(IndustryNames.SPECIFIC_SENSOR)).appendImageUrl("specific_sensor.png");
			upgradedSpecificSensor_page = new PageCrafting("upgraded_specific_sensor", RecipeHelper.getRecipePath(IndustryNames.UPGRADED_SPECIFIC_SENSOR));
			sensor_page = new PageCrafting("recipes", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.WOODEN_SENSOR), RecipeHelper.getRecipePath(IndustryNames.STONE_SENSOR), RecipeHelper.getRecipePath(IndustryNames.IRON_SENSOR), RecipeHelper.getRecipePath(IndustryNames.NETHERRACK_SENSOR)), 20);
			arrowSensor_page = new PageCrafting("arrow_sensor", RecipeHelper.getRecipePath(IndustryNames.ARROW_SENSOR));
			fireSensor_page = new PageCrafting("fire_sensor", RecipeHelper.getRecipePath(IndustryNames.FIRE_SENSOR));
		}

		if (IndustryConfig.subpartFans.get())
			fan_page = new PageCrafting("fan", RecipeHelper.getRecipePath(IndustryNames.FAN));

		if (IndustryConfig.subpartExtruder.get()) {
			extruder_page = new PageCrafting("extruder", RecipeHelper.getRecipePath(IndustryNames.EXTRUDER));
			extruderInfo_page = new PageInfo("info");
		}

		if (IndustryConfig.subpartWorkbenchUpgrades.get()) {
			workbench_page = new PageCrafting("recipes", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.IRON_WORKBENCH), RecipeHelper.getRecipePath(IndustryNames.DIAMOND_WORKBENCH)), 25);
			portableUpgrade_page = new PageCrafting("portable_upgrade", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.PORTABLE_IRON_WORKBENCH), RecipeHelper.getRecipePath(IndustryNames.PORTABLE_DIAMOND_WORKBENCH)), 25);
		}

		if (IndustryConfig.subpartIceMaker.get())
			iceMaker_page = new PageCrafting("recipes", RecipeHelper.getRecipePath(IndustryNames.ICE_MAKER));

		if (IndustryConfig.subpartElementalBlocks.get()) {
			togglerack_page = new PageCrafting("toggle", RecipeHelper.getRecipePath(IndustryNames.TOGGLE_RACK));
			fireBlock_page = new PageCrafting("fire", RecipeHelper.getRecipePath(IndustryNames.ELEMENTAL_FIRE));
			waterBlock_page = new PageCrafting("water", RecipeHelper.getRecipePath(IndustryNames.ELEMENTAL_WATER));
			lavaBlock_page = new PageCrafting("lava", RecipeHelper.getRecipePath(IndustryNames.ELEMENTAL_LAVA));
		}

		if (IndustryConfig.subpartSpikes.get())
			spike_page = new PageCrafting("recipe", RecipeHelper.getRecipePath(IndustryNames.SPIKE));

		if (IndustryConfig.subpartGravity.get()) {
			gravityBoots_page = new PageCrafting("boots", RecipeHelper.getRecipePath(IndustryNames.GRAVITY_BOOTS));
			controller_page = new PageCrafting("control", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.GRAVITY_CONTROLLER), RecipeHelper.getRecipePath(IndustryNames.LOW_GRAVITY_CONTROLLER)), 25);
			attract_page = new PageCrafting("attract", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.ATTRACTOR), RecipeHelper.getRecipePath(IndustryNames.DIRECTION_ATTRACTOR)), 25);
			repulse_page = new PageCrafting("repulse", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.REPULSOR), RecipeHelper.getRecipePath(IndustryNames.REPULSOR)), 25);
			mob_repulsor_page = new PageCrafting("mob_repulsor", RecipeHelper.getRecipePath(IndustryNames.MOB_REPULSOR));
			gravitor_page = new PageCrafting("gravitor", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.GRAVITOR), RecipeHelper.getRecipePath(IndustryNames.DIRECTION_GRAVITOR)), 25);
		}

		if (IndustryConfig.subpartNuclear.get()) {
			uranium_page = new PageCrafting("uranium", RecipeHelper.getRecipePath(IndustryNames.URANIUM_ORE));
			radiationSuit_page = new PageCrafting("armor", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.ANTI_RADIATION_HELMET), RecipeHelper.getRecipePath(IndustryNames.ANTI_RADIATION_CHEST), RecipeHelper.getRecipePath(IndustryNames.ANTI_RADIATION_LEGS), RecipeHelper.getRecipePath(IndustryNames.ANTI_RADIATION_BOOTS)), 20);
			uraniumSmelt_page = new PageFurnace("uranium_smelt", RecipeHelper.getRecipePath(IndustryNames.URANIUM_INGOT));
			refinedUranium_page = new PageCrafting("refined_uranium", RecipeHelper.getRecipePath(IndustryNames.REFINED_URANIUM));
			plutonium_page = new PageCrafting("plutonium", RecipeHelper.getRecipePath(IndustryNames.PLUTONIUM_INGOT));
			refinedPlutonium_page = new PageCrafting("refined_plutonium", RecipeHelper.getRecipePath(IndustryNames.REFINED_PLUTONIUM));
			reactorCore_page = new PageCrafting("reactor_core", RecipeHelper.getRecipePath(IndustryNames.REACTOR_CORE));
			reactorCase_page = new PageCrafting("reactor_case", RecipeHelper.getRecipePath(IndustryNames.REACTOR_CORE_CASE));
			ironParts_page = new PageCrafting("iron_parts", RecipeHelper.getRecipePath(IndustryNames.IRON_PARTS));
			reactor_page = new PageCrafting("reactor", RecipeHelper.getRecipePath(IndustryNames.REACTOR));
			bombShell_page = new PageCrafting("bomb_shell", RecipeHelper.getRecipePath(IndustryNames.BOMB_SHELL));
			c4_page = new PageCrafting("c4", RecipeHelper.getRecipePath(IndustryNames.C4));
			nuclearBomb_page = new PageCrafting("nuclear_bomb", RecipeHelper.getRecipePath(IndustryNames.NUCLEAR_BOMB));
		}

		if (IndustryConfig.subpartGates.get()) {
			gate_page = new PageCrafting("gate", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.GATE_GRATING), RecipeHelper.getRecipePath(IndustryNames.CASTLE_GATE)), 25);
			gateTrumpet_page = new PageCrafting("trumpet", RecipeHelper.getRecipePath(IndustryNames.GATE_TRUMPET));
			garage_page = new PageCrafting("garage", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.GARAGE_PANEL), RecipeHelper.getRecipePath(IndustryNames.GARAGE)), 25);
			garageRemote_page = new PageCrafting("remote", RecipeHelper.getRecipePath(IndustryNames.GARAGE_REMOTE));
		}

		if (IndustryConfig.subpartHLights.get()) {
			hLight_page = new PageCrafting("hlight", RecipeHelper.getRecipePath(IndustryNames.HALOGEN_LIGHT));
			hTorch_page = new PageCrafting("htorch", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.HALOGEN_TORCH), RecipeHelper.getRecipePath(IndustryNames.HALOGEN_TORCH + "_alt")), 25);
		}

		if (IndustryConfig.subpartRWays.get()) {
			sidewalk_page = new PageCrafting("swalk", RecipeHelper.getRecipePath(IndustryNames.SIDEWALK));
			tarball_page = new PageCrafting("tarball", RecipeHelper.getRecipePath(IndustryNames.TARBALL));
			asphalt_page = new PageFurnace("asphalt", RecipeHelper.getRecipePath(IndustryNames.ASPHALT));
			rways_page = new PageCrafting("rways", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.RWAY), RecipeHelper.getRecipePath(IndustryNames.RWAY_LIGHT_OFF), RecipeHelper.getRecipePath(IndustryNames.RWAY_MANHOLE)), 20).appendImageUrl("rway.png");
		}

		if (IndustryConfig.subpartDoors.get())
			doors_page = new PageCrafting("doors", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.DOOR_CHAIN), RecipeHelper.getRecipePath(IndustryNames.DOOR_GLASS), RecipeHelper.getRecipePath(IndustryNames.DOOR_STEEL)), 25);

		if (IndustryConfig.subpartDecoration.get()) {
			others_page = new PageCrafting("others", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.CHAIN_FENCE), RecipeHelper.getRecipePath(IndustryNames.FOUNTAIN), RecipeHelper.getRecipePath(IndustryNames.CAMO_PLATE)), 25);
			decoration_page = new PageCrafting("decoration", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.CONCRETE), RecipeHelper.getRecipePath(IndustryNames.HORIZONTAL_SIDING), RecipeHelper.getRecipePath(IndustryNames.VERTICAL_SIDING)), 25);
			paint_page = new PageCrafting("paint", RecipeHelper.getRecipePath(IndustryNames.PAINT_ROLLER));
			paintTech_page = new PageCrafting("paint", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.PAINT_ROLLER_WHITE), RecipeHelper.getRecipePath(IndustryNames.PAINT_ROLLER_RED), RecipeHelper.getRecipePath(IndustryNames.PAINT_ROLLER_GREEN), RecipeHelper.getRecipePath(IndustryNames.PAINT_ROLLER_BLUE)), 25);
		}

		if (IndustryConfig.subpartMetalWorks.get()) {
			metalMesh_page = new PageCrafting("metal_mesh", RecipeHelper.getRecipePath(IndustryNames.METAL_MESH));
			buckLadd_page = new PageCrafting("buckladd", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.ALUMINUM_LADDER), RecipeHelper.getRecipePath(GrimIndustry.partId, "bucket")), 25);
		}

		if (IndustryConfig.subpartSteel.get()) {
			coalIron_page = new PageCrafting("coaliron", ImmutableList.of(RecipeHelper.getRecipePath(CoreNames.COAL_DUST), RecipeHelper.getRecipePath(IndustryNames.COAL_IRON_INGOT)), 25);
			steelIngot_page = new PageFurnace("steelingot", RecipeHelper.getRecipePath(IndustryNames.COAL_IRON_INGOT));
			steelStuff_page = new PageCrafting("steelstuff", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.STEEL_PIPE), RecipeHelper.getRecipePath(IndustryNames.STEEL_FRAME), RecipeHelper.getRecipePath(CoreNames.STEEL_BLOCK), RecipeHelper.getRecipePath(CoreNames.STEEL_INGOT), RecipeHelper.getRecipePath(CoreNames.STEEL_SHAFT)), 20);
			steelTools_page = new PageCrafting("steeltools", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.STEEL_SWORD), RecipeHelper.getRecipePath(IndustryNames.STEEL_PICKAXE), RecipeHelper.getRecipePath(IndustryNames.STEEL_SHOVEL), RecipeHelper.getRecipePath(IndustryNames.STEEL_AXE), RecipeHelper.getRecipePath(IndustryNames.STEEL_HOE)), 20);
		}

		if (IndustryConfig.subpartConveyor.get()) {
			conveyorBelt_page = new PageCrafting("conveyor_belt", RecipeHelper.getRecipePath(IndustryNames.CONVEYOR_BELT));
		}

		if (IndustryConfig.subpartMachines.get()) {
			drill_page = new PageCrafting("drill", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.DRILL), RecipeHelper.getRecipePath(IndustryNames.DRILL_HEAD_ITEM)), 25);
			fuel_page = new PageCrafting("fuel", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.SUPER_CRUDE_OIL), RecipeHelper.getRecipePath(IndustryNames.FUEL), RecipeHelper.getRecipePath(IndustryNames.FUEL_TANK)), 25);
			machineInfo_page = new PageInfo("info");
			refinery_page = new PageCrafting("refinery", RecipeHelper.getRecipePath(IndustryNames.REFINERY));
			refineryRecipes_page = new PageMachine("refinery_recipes", MachineRecipes.INSTANCE.getInputs(MachineType.REFINERY), 35, MachineType.REFINERY);

			if (CoreConfig.subpartAluminum.get())
				derrick_page = new PageCrafting("derrick", ImmutableList.of(RecipeHelper.getRecipePath(IndustryNames.DERRICK), RecipeHelper.getRecipePath(CoreNames.ALUMINUM_CAN)), 25);

			if (OreDictionary.doesOreNameExist("can"))
				derrickRecipes_page = new PageMachine("derrick_recipes", "can", MachineType.DERRICK);

			modernFurnace_page = new PageCrafting("mfurnace", RecipeHelper.getRecipePath(IndustryNames.MODERN_FURNACE));
			modernFurnaceRecipes_page = new PageMachine("mfurnace_recipes", MachineRecipes.INSTANCE.getInputs(MachineType.MODERN_FURNACE), 35, MachineType.MODERN_FURNACE);
		}

		if (IndustryConfig.subpartTorches.get()) {
			flipFlopTorch_page = new PageCrafting("flipflop", RecipeHelper.getRecipePath(IndustryNames.FLIP_FLOP_TORCH));
			glowstoneTorch_page = new PageCrafting("glowstone", RecipeHelper.getRecipePath(IndustryNames.GLOWSTONE_TORCH));
		}

		if (IndustryConfig.subpartBridges.get()) {
			bridgeLaser_page = new PageCrafting("laser", RecipeHelper.createPath("bridge_laser"));
			bridgeAccel_page = new PageCrafting("accel", RecipeHelper.createPath("bridge_accel"));
			bridgeTrick_page = new PageCrafting("trick", RecipeHelper.createPath("bridge_trick"));
			bridgeDeath_page = new PageCrafting("death", RecipeHelper.createPath("bridge_death"));
			gravLift_page = new PageCrafting("gravity", RecipeHelper.createPath("bridge_gravity"));
		}

		if (IndustryConfig.subpartChunkLoader.get()) {
			chunkLoader_page = new PageCrafting("chunk_loader", RecipeHelper.getRecipePath(IndustryNames.CHUNK_LOADER));
		}
	}

	@Override
	public void registerChapters(ManualPart part) {
		if (IndustryConfig.subpartWorkbenchUpgrades.get())
			ManualRegistry.addChapter("benches", part).addPages(workbench_page, portableUpgrade_page);

		if (IndustryConfig.subpartStorage.get())
			ManualRegistry.addChapter("storage", part).addPages(warehouseCrate_page, cabinets_page, safes_page, locker_page, itemTower_page, combination_page, locksmithWorkbench_page, tank_page);

		if (IndustryConfig.subpartElementalBlocks.get())
			ManualRegistry.addChapter("elemental", part).addPages(togglerack_page, fireBlock_page, waterBlock_page, lavaBlock_page);

		if (IndustryConfig.subpartSpikes.get())
			ManualRegistry.addChapter("spikes", part).addPages(spike_page);

		if (IndustryConfig.subpartFans.get())
			ManualRegistry.addChapter("fan", part).addPages(fan_page).appendImageUrl("fans.png");

		if (IndustryConfig.subpartExtruder.get())
			ManualRegistry.addChapter("extruders", part).addPages(extruder_page, extruderInfo_page).appendImageUrl("extruder.png");

		if (IndustryConfig.subpartSensors.get())
			ManualRegistry.addChapter("sensors", part).addPages(sensor_page, positionFinder_page, specificSensorInfo_page, specificSensorInfo2_page, specificSensor_page, upgradedSpecificSensor_page, arrowSensor_page, fireSensor_page);

		if (IndustryConfig.subpartGravity.get())
			ManualRegistry.addChapter("gravity", part).addPages(gravityBoots_page, controller_page, attract_page, gravitor_page, repulse_page, mob_repulsor_page);

		if (IndustryConfig.subpartNuclear.get()) {
			ManualRegistry.addChapter("refining", part).addPages(uranium_page, radiationSuit_page, uraniumSmelt_page, refinedUranium_page, plutonium_page, refinedPlutonium_page, reactorCore_page);
			ManualRegistry.addChapter("reactor", part).addPages(reactorCase_page, ironParts_page, reactor_page);
		}

		if (IndustryConfig.subpartNuclear.get() || IndustryConfig.subpartShapedCharges.get()) {
			ManualRegistry.addChapter("explosives", part).addPages(bombShell_page, c4_page, nuclearBomb_page, shapedChargeBase_page, shapedCharge_page);
		}

		if (IndustryConfig.subpartGates.get())
			ManualRegistry.addChapter("gates", part).addPages(gate_page, gateTrumpet_page, garage_page, garageRemote_page);

		if (IndustryConfig.subpartHLights.get())
			ManualRegistry.addChapter("hlights", part).addPages(hLight_page, hTorch_page);

		if (IndustryConfig.subpartRWays.get())
			ManualRegistry.addChapter("rways", part).addPages(sidewalk_page, tarball_page, asphalt_page, rways_page);

		if (IndustryConfig.subpartDoors.get() || IndustryConfig.subpartDecoration.get())
			ManualRegistry.addChapter("moderntech", part).addPages(doors_page, others_page, decoration_page, paint_page, paintTech_page);

		if (IndustryConfig.subpartMetalWorks.get() || IndustryConfig.subpartSteel.get())
			ManualRegistry.addChapter("metalworks", part).addPages(buckLadd_page, coalIron_page, steelIngot_page, steelStuff_page, steelTools_page, metalMesh_page);

		if (IndustryConfig.subpartMachines.get() || IndustryConfig.subpartConveyor.get())
			ManualRegistry.addChapter("machines", part).addPages(machineInfo_page, refinery_page, refineryRecipes_page, derrick_page, derrickRecipes_page, fuel_page, modernFurnace_page, modernFurnaceRecipes_page, drill_page, conveyorBelt_page);

		if (IndustryConfig.subpartIceMaker.get())
			ManualRegistry.addChapter("ice", part).addPages(iceMaker_page).appendImageUrl("icemaker.png");

		if (IndustryConfig.subpartTorches.get())
			ManualRegistry.addChapter("torches", part).addPages(flipFlopTorch_page, glowstoneTorch_page);

		if (IndustryConfig.subpartBridges.get())
			ManualRegistry.addChapter("bridges", part).addPages(bridgeLaser_page, bridgeAccel_page, bridgeTrick_page, bridgeDeath_page, gravLift_page);

		if (IndustryConfig.subpartChunkLoader.get())
			ManualRegistry.addChapter("chunk_loader", part).addPages(chunkLoader_page);
	}

}
