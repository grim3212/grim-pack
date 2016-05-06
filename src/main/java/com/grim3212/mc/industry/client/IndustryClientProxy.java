package com.grim3212.mc.industry.client;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.industry.block.BlockFountain;
import com.grim3212.mc.industry.block.BlockModernDoor;
import com.grim3212.mc.industry.block.BlockSiding;
import com.grim3212.mc.industry.block.IndustryBlocks;
import com.grim3212.mc.industry.client.event.ModelEvent;
import com.grim3212.mc.industry.item.IndustryItems;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class IndustryClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		ModelLoader.setCustomStateMapper(IndustryBlocks.door_chain, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.door_glass, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.door_steel, new StateMap.Builder().ignore(BlockModernDoor.POWERED).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.fountain, new StateMap.Builder().ignore(BlockFountain.ACTIVE).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.horizontal_siding, new StateMap.Builder().ignore(BlockSiding.COLOR).build());
		ModelLoader.setCustomStateMapper(IndustryBlocks.vertical_siding, new StateMap.Builder().ignore(BlockSiding.COLOR).build());

		MinecraftForge.EVENT_BUS.register(new ModelEvent());

		ModelEvent.renderCustomModel(IndustryBlocks.camo_plate, "powered=true", "powered=false");

		// ITEMS
		RenderHelper.renderItem(IndustryItems.gravity_boots);
		RenderHelper.renderItem(IndustryItems.graphite);
		RenderHelper.renderItem(IndustryItems.graphite_rod);
		RenderHelper.renderItem(IndustryItems.anti_radiation_boots);
		RenderHelper.renderItem(IndustryItems.anti_radiation_legs);
		RenderHelper.renderItem(IndustryItems.anti_radiation_chest);
		RenderHelper.renderItem(IndustryItems.anti_radiation_helmet);
		RenderHelper.renderItem(IndustryItems.iron_parts);
		RenderHelper.renderItem(IndustryItems.plutonium_ingot);
		RenderHelper.renderItem(IndustryItems.reactor_core);
		RenderHelper.renderItem(IndustryItems.reactor_core_case);
		RenderHelper.renderItem(IndustryItems.refined_plutonium);
		RenderHelper.renderItem(IndustryItems.refined_uranium);
		RenderHelper.renderItem(IndustryItems.uranium_ingot);
		RenderHelper.renderItem(IndustryItems.low_gravity_controller);
		RenderHelper.renderItem(IndustryItems.gravity_controller);
		RenderHelper.renderItem(IndustryItems.aluminum_ingot);
		RenderHelper.renderItem(IndustryItems.garage_panel);
		RenderHelper.renderItem(IndustryItems.garage_remote);
		RenderHelper.renderItem(IndustryItems.gate_grating);
		RenderHelper.renderItem(IndustryItems.gate_trumpet);
		RenderHelper.renderItem(IndustryItems.paint_roller);
		RenderHelper.renderItem(IndustryItems.paint_roller_white);
		RenderHelper.renderItem(IndustryItems.paint_roller_red);
		RenderHelper.renderItem(IndustryItems.paint_roller_green);
		RenderHelper.renderItem(IndustryItems.paint_roller_blue);
		RenderHelper.renderItem(IndustryItems.tarball);
		RenderHelper.renderItem(IndustryItems.asphalt);
		RenderHelper.renderItem(IndustryItems.door_chain_item);
		RenderHelper.renderItem(IndustryItems.door_glass_item);
		RenderHelper.renderItem(IndustryItems.door_steel_item);
		RenderHelper.renderItem(IndustryItems.rubber);
		RenderHelper.renderItem(IndustryItems.aluminum_can);
		RenderHelper.renderItem(IndustryItems.aluminum_shaft);
		RenderHelper.renderItem(IndustryItems.oily_chunk);
		RenderHelper.renderItem(IndustryItems.coal_dust);
		RenderHelper.renderItem(IndustryItems.coal_iron_ingot);
		RenderHelper.renderItem(IndustryItems.crude_oil);
		RenderHelper.renderItem(IndustryItems.fuel);
		RenderHelper.renderItem(IndustryItems.super_crude_oil);
		RenderHelper.renderItem(IndustryItems.steel_ingot);
		RenderHelper.renderItem(IndustryItems.steel_shaft);
		RenderHelper.renderItem(IndustryItems.steel_axe);
		RenderHelper.renderItem(IndustryItems.steel_hoe);
		RenderHelper.renderItem(IndustryItems.steel_pickaxe);
		RenderHelper.renderItem(IndustryItems.steel_shovel);
		RenderHelper.renderItem(IndustryItems.steel_sword);

		// BLOCKS
		RenderHelper.renderBlock(IndustryBlocks.togglerack);
		RenderHelper.renderBlock(IndustryBlocks.iron_workbench);
		RenderHelper.renderBlock(IndustryBlocks.diamond_workbench);
		RenderHelper.renderBlock(IndustryBlocks.ice_maker);
		RenderHelper.renderBlock(IndustryBlocks.fire_block);
		RenderHelper.renderBlock(IndustryBlocks.lava_block);
		RenderHelper.renderBlock(IndustryBlocks.water_block);
		RenderHelper.renderBlock(IndustryBlocks.spike);
		RenderHelper.renderBlock(IndustryBlocks.wooden_sensor);
		RenderHelper.renderBlock(IndustryBlocks.stone_sensor);
		RenderHelper.renderBlock(IndustryBlocks.iron_sensor);
		RenderHelper.renderBlock(IndustryBlocks.netherrack_sensor);
		RenderHelper.renderBlock(IndustryBlocks.attractor);
		RenderHelper.renderBlock(IndustryBlocks.repulsor);
		RenderHelper.renderBlock(IndustryBlocks.gravitor);
		RenderHelper.renderBlock(IndustryBlocks.direction_attractor);
		RenderHelper.renderBlock(IndustryBlocks.direction_repulsor);
		RenderHelper.renderBlock(IndustryBlocks.direction_gravitor);
		RenderHelper.renderBlock(IndustryBlocks.bomb_shell);
		RenderHelper.renderBlock(IndustryBlocks.c4);
		RenderHelper.renderBlock(IndustryBlocks.reactor);
		RenderHelper.renderBlock(IndustryBlocks.nuclear_bomb);
		RenderHelper.renderBlock(IndustryBlocks.uranium_ore);
		RenderHelper.renderBlock(IndustryBlocks.castle_gate);
		RenderHelper.renderBlock(IndustryBlocks.garage);
		RenderHelper.renderBlock(IndustryBlocks.halogen_light);
		RenderHelper.renderBlock(IndustryBlocks.halogen_torch);
		RenderHelper.renderBlock(IndustryBlocks.rway);
		RenderHelper.renderBlock(IndustryBlocks.rway_light_off);
		RenderHelper.renderBlock(IndustryBlocks.rway_light_on);
		RenderHelper.renderBlock(IndustryBlocks.rway_manhole);
		RenderHelper.renderBlock(IndustryBlocks.sidewalk);
		RenderHelper.renderBlock(IndustryBlocks.door_chain);
		RenderHelper.renderBlock(IndustryBlocks.door_glass);
		RenderHelper.renderBlock(IndustryBlocks.door_steel);
		RenderHelper.renderBlock(IndustryBlocks.concrete);
		RenderHelper.renderBlock(IndustryBlocks.modern_tile);
		RenderHelper.renderBlock(IndustryBlocks.tempered_glass);
		RenderHelper.renderBlock(IndustryBlocks.horizontal_siding);
		RenderHelper.renderBlock(IndustryBlocks.vertical_siding);
		RenderHelper.renderBlock(IndustryBlocks.chain_fence);
		RenderHelper.renderBlock(IndustryBlocks.fountain);
		RenderHelper.renderBlock(IndustryBlocks.fuel_tank);
		RenderHelper.renderBlock(IndustryBlocks.aluminum_ore);
		RenderHelper.renderBlock(IndustryBlocks.aluminum_ladder);
		RenderHelper.renderBlock(IndustryBlocks.oil_ore);
		RenderHelper.renderBlock(IndustryBlocks.steel_block);
		RenderHelper.renderBlock(IndustryBlocks.steel_frame);
		RenderHelper.renderBlock(IndustryBlocks.steel_pipe);
		RenderHelper.renderBlock(IndustryBlocks.derrick);
		RenderHelper.renderBlock(IndustryBlocks.refinery);
		RenderHelper.renderBlock(IndustryBlocks.modern_furnace);
	}

	@Override
	public void registerManual(ModSection modSection) {
		ManualRegistry.addSection("benches", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.workbenches, 25));
		ManualRegistry.addSection("ice", modSection).addSubSectionPages(new PageCrafting("recipes", new ItemStack(IndustryBlocks.ice_maker)));
		ManualRegistry.addSection("elemental", modSection).addSubSectionPages(new PageCrafting("toggle", new ItemStack(IndustryBlocks.togglerack)), new PageCrafting("fire", new ItemStack(IndustryBlocks.fire_block)), new PageCrafting("water", new ItemStack(IndustryBlocks.water_block)), new PageCrafting("lava", new ItemStack(IndustryBlocks.lava_block)));
		ManualRegistry.addSection("spikes", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryBlocks.spike)));
		ManualRegistry.addSection("sensors", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.sensors, 20));
		ManualRegistry.addSection("gravity", modSection).addSubSectionPages(new PageCrafting("boots", new ItemStack(IndustryItems.gravity_boots)), new PageCrafting("control", IndustryItems.control, 25), new PageCrafting("attract", IndustryBlocks.attracting, 25), new PageCrafting("repulse", IndustryBlocks.repulsing, 25), new PageCrafting("gravitor", IndustryBlocks.gravitoring, 25));
		ManualRegistry.addSection("refining", modSection).addSubSectionPages(new PageCrafting("uranium", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("armor", IndustryItems.armor, 20), new PageFurnace("uranium_smelt", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("refined_uranium", new ItemStack(IndustryItems.refined_uranium)), new PageCrafting("plutonium", new ItemStack(IndustryItems.plutonium_ingot)), new PageCrafting("refined_plutonium", new ItemStack(IndustryItems.refined_plutonium)),
				new PageCrafting("reactor_core", new ItemStack(IndustryItems.reactor_core)));
		ManualRegistry.addSection("reactor", modSection).addSubSectionPages(new PageFurnace("graphite", new ItemStack(Items.flint)), new PageCrafting("graphite_rod", new ItemStack(IndustryItems.graphite_rod)), new PageCrafting("reactor_case", new ItemStack(IndustryItems.reactor_core_case)), new PageCrafting("iron_parts", new ItemStack(IndustryItems.iron_parts)), new PageCrafting("reactor", new ItemStack(IndustryBlocks.reactor)));
		ManualRegistry.addSection("explosives", modSection).addSubSectionPages(new PageFurnace("aluminium", new ItemStack(IndustryBlocks.aluminum_ore)), new PageCrafting("bomb_shell", new ItemStack(IndustryBlocks.bomb_shell)), new PageCrafting("c4", new ItemStack(IndustryBlocks.c4)), new PageCrafting("nuclear_bomb", new ItemStack(IndustryBlocks.nuclear_bomb)));
		ManualRegistry.addSection("gates", modSection).addSubSectionPages(new PageCrafting("gate", IndustryItems.gates, 25), new PageCrafting("trumpet", new ItemStack(IndustryItems.gate_trumpet)), new PageCrafting("garage", IndustryItems.garages, 25), new PageCrafting("remote", new ItemStack(IndustryItems.garage_remote)));
		ManualRegistry.addSection("hlights", modSection).addSubSectionPages(new PageCrafting("hlight", new ItemStack(IndustryBlocks.halogen_light)), new PageCrafting("htorch", IndustryBlocks.htorches, 25));
		ManualRegistry.addSection("rways", modSection).addSubSectionPages(new PageCrafting("swalk", new ItemStack(IndustryBlocks.sidewalk)), new PageCrafting("tarball", new ItemStack(IndustryItems.tarball)), new PageFurnace("asphalt", new ItemStack(IndustryItems.tarball)), new PageCrafting("rways", IndustryBlocks.rways, 20), new PageCrafting("paint", new ItemStack(IndustryItems.paint_roller)));
		ManualRegistry.addSection("moderntech", modSection).addSubSectionPages(new PageCrafting("doors", IndustryItems.doors, 25), new PageCrafting("others", IndustryBlocks.others, 25), new PageCrafting("decoration", IndustryBlocks.decoration, 25), new PageCrafting("paint", IndustryItems.paint, 25));
		ManualRegistry.addSection("metalworks", modSection).addSubSectionPages(new PageFurnace("alumingot", new ItemStack(IndustryBlocks.aluminum_ore)), new PageCrafting("alumstuff", IndustryItems.alumstuff, 25), new PageCrafting("buckladd", IndustryBlocks.buckladd, 25), new PageCrafting("coaliron", IndustryItems.coaliron, 25), new PageFurnace("steelingot", new ItemStack(IndustryItems.coal_iron_ingot)), new PageCrafting("steelstuff", IndustryBlocks.steelstuff, 20), new PageCrafting("steeltools", IndustryItems.steeltools, 20), new PageCrafting("fuel", IndustryItems.fuelstuff, 25),
				new PageCrafting("refinery", new ItemStack(IndustryBlocks.refinery)), new PageCrafting("derrick", new ItemStack(IndustryBlocks.derrick)), new PageCrafting("mfurnace", new ItemStack(IndustryBlocks.modern_furnace)));
	}
}
