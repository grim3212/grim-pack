package com.grim3212.mc.industry;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.industry.block.IndustryBlocks;

public class IndustryClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
		// ITEMS

		// BLOCKS
		RenderHelper.renderBlock(IndustryBlocks.togglerack);
	}

}
