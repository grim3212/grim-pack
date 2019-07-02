package com.grim3212.mc.pack.decor.inventory;

import com.grim3212.mc.pack.decor.client.gui.GuiCage;
import com.grim3212.mc.pack.decor.client.gui.GuiGrill;
import com.grim3212.mc.pack.decor.init.DecorNames;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;

public class DecorContainers {

	@ObjectHolder(DecorNames.CONTAINER_GRILL)
	public static ContainerType<ContainerGrill> GRILL_TYPE;
	@ObjectHolder(DecorNames.CONTAINER_CAGE)
	public static ContainerType<ContainerCage> CAGE_TYPE;

	@SubscribeEvent
	public void initContainers(RegistryEvent.Register<ContainerType<?>> evt) {
		evt.getRegistry().register(new ContainerType<ContainerGrill>(ContainerGrill::new).setRegistryName(DecorNames.CONTAINER_GRILL));
		evt.getRegistry().register(new ContainerType<ContainerCage>(ContainerCage::new).setRegistryName(DecorNames.CONTAINER_CAGE));
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerScreenFactories() {
		ScreenManager.registerFactory(GRILL_TYPE, GuiGrill::new);
		ScreenManager.registerFactory(CAGE_TYPE, GuiCage::new);
	}
}
