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
	}

}
