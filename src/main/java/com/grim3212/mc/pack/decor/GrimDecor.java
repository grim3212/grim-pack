package com.grim3212.mc.pack.decor;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.entity.DecorEntities;
import com.grim3212.mc.pack.decor.init.DecorRecipes;
import com.grim3212.mc.pack.decor.init.DecorSounds;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.network.MessageNeonChangeMode;
import com.grim3212.mc.pack.decor.network.MessageNeonOpen;
import com.grim3212.mc.pack.decor.network.MessageNeonUpdate;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.network.MessageSaveAlarm;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimDecor extends GrimPart {

	public static GrimDecor INSTANCE = new GrimDecor();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.decor.client.DecorClientProxy", serverSide = "com.grim3212.mc.pack.decor.DecorCommonProxy")
	public static DecorCommonProxy proxy;

	public static final String partId = "decor";
	public static final String partName = "Grim Decor";

	public GrimDecor() {
		super(GrimDecor.partId, GrimDecor.partName, new DecorConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new DecorBlocks());
		MinecraftForge.EVENT_BUS.register(new DecorItems());
		MinecraftForge.EVENT_BUS.register(new DecorSounds());

		if (DecorConfig.subpartColorizer && DecorConfig.subpartFireplaces)
			PacketDispatcher.registerMessage(MessageParticles.class);

		if (DecorConfig.subpartAlarm)
			PacketDispatcher.registerMessage(MessageSaveAlarm.class);

		if (DecorConfig.subpartNeonSign) {
			PacketDispatcher.registerMessage(MessageNeonOpen.class);
			PacketDispatcher.registerMessage(MessageNeonChangeMode.class);
			PacketDispatcher.registerMessage(MessageNeonUpdate.class);
		}

		DecorEntities.initEntities();
		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();

		DecorRecipes.initRecipes();
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IManualPart getManual() {
		return ManualDecor.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/decor_main.png");
	}
}