package com.grim3212.mc.pack.util.init;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.util.grave.BlockGrave;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class UtilBlocks {

	public static final Block grave = new BlockGrave();

	@SubscribeEvent
	public static void initBlocks(RegistryEvent.Register<Block> evt) {
		evt.getRegistry().register(grave);
	}

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		evt.getRegistry().register(new ItemManualBlock(grave).setRegistryName(grave.getRegistryName()));
	}

}
