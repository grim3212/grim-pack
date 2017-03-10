package com.grim3212.mc.pack.util.init;

import com.grim3212.mc.pack.core.part.IPartItems;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.util.grave.BlockGrave;

import net.minecraft.block.Block;

public class UtilBlocks implements IPartItems {

	public static Block grave;

	@Override
	public void initItems() {
		grave = (new BlockGrave()).setHardness(1.0F).setResistance(99.9F).setUnlocalizedName("grave");

		Utils.registerBlock(grave, "grave");
	}

	@Override
	public void addRecipes() {
		// No-op
	}

}
