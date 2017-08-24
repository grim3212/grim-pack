package com.grim3212.mc.pack.util.init;

import com.grim3212.mc.pack.util.grave.BlockGrave;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UtilBlocks {

	public static final Block grave = new BlockGrave();

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		evt.getRegistry().register(grave);
	}

}
