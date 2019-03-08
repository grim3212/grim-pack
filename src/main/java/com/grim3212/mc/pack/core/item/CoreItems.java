package com.grim3212.mc.pack.core.item;

import com.grim3212.mc.pack.core.manual.ItemInstructionManual;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CoreItems {

	public static final Item instruction_manual = new ItemInstructionManual();

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		evt.getRegistry().register(instruction_manual);
	}
}