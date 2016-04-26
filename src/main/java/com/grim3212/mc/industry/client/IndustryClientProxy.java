package com.grim3212.mc.industry.client;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.industry.block.IndustryBlocks;
import com.grim3212.mc.industry.item.IndustryItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class IndustryClientProxy extends ClientProxy {

	@Override
	protected void registerModels() {
		// ITEMS
		RenderHelper.renderItem(IndustryItems.water_bowl);
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
	}

	@Override
	protected void registerManual(ModSection modSection) {
		ManualRegistry.addSection("toggle", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryBlocks.togglerack)));
		ManualRegistry.addSection("benches", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.workbenches, 25));
		ManualRegistry.addSection("ice", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryItems.ice, 25));
		ManualRegistry.addSection("elemental", modSection).addSubSectionPages(new PageCrafting("fire", new ItemStack(IndustryBlocks.fire_block)), new PageCrafting("water", new ItemStack(IndustryBlocks.water_block)), new PageCrafting("lava", new ItemStack(IndustryBlocks.lava_block)));
		ManualRegistry.addSection("spikes", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryBlocks.spike)));
		ManualRegistry.addSection("sensors", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.sensors, 20));
		ManualRegistry.addSection("attract", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.attracting, 25));
		ManualRegistry.addSection("repulse", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.repulsing, 25));
		ManualRegistry.addSection("gravitor", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.gravitoring, 25));
		ManualRegistry.addSection("boots", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryItems.gravity_boots)));
		ManualRegistry.addSection("refining", modSection).addSubSectionPages(new PageCrafting("uranium", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("armor", IndustryItems.armor, 20), new PageFurnace("uranium_smelt", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("refined_uranium", new ItemStack(IndustryItems.refined_uranium)), new PageCrafting("plutonium", new ItemStack(IndustryItems.plutonium_ingot)), new PageCrafting("refined_plutonium", new ItemStack(IndustryItems.refined_plutonium)),
				new PageCrafting("reactor_core", new ItemStack(IndustryItems.reactor_core)));
		ManualRegistry.addSection("reactor", modSection).addSubSectionPages(new PageFurnace("graphite", new ItemStack(Items.flint)), new PageCrafting("graphite_rod", new ItemStack(IndustryItems.graphite_rod)), new PageCrafting("reactor_case", new ItemStack(IndustryItems.reactor_core_case)), new PageCrafting("iron_parts", new ItemStack(IndustryItems.iron_parts)), new PageCrafting("reactor", new ItemStack(IndustryBlocks.reactor)));
		ManualRegistry.addSection("explosives", modSection).addSubSectionPages(new PageFurnace("aluminium", new ItemStack(Items.iron_ingot)), new PageCrafting("bomb_shell", new ItemStack(IndustryBlocks.bomb_shell)), new PageCrafting("c4", new ItemStack(IndustryBlocks.c4)), new PageCrafting("nuclear_bomb", new ItemStack(IndustryBlocks.nuclear_bomb)));
		ManualRegistry.addSection("gravity", modSection).addSubSectionPages(new PageCrafting("control", IndustryItems.control, 25));
	}

}
