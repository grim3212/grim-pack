package com.grim3212.mc.industry;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.industry.block.IndustryBlocks;
import com.grim3212.mc.industry.item.IndustryItems;

public class IndustryClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
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

}
