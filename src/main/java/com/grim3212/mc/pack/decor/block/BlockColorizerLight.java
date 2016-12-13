package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;

public class BlockColorizerLight extends BlockColorizer implements IManualBlock, IColorizer {

	public BlockColorizerLight() {
		this.setLightLevel(1.0F);
	}
}
