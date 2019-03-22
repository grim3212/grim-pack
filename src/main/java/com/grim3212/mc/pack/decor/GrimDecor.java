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
import com.grim3212.mc.pack.decor.init.DecorSounds;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.network.MessageNeonChangeMode;
import com.grim3212.mc.pack.decor.network.MessageNeonOpen;
import com.grim3212.mc.pack.decor.network.MessageNeonUpdate;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.network.MessageSaveAlarm;
import com.grim3212.mc.pack.decor.proxy.DecorClientProxy;
import com.grim3212.mc.pack.decor.proxy.DecorServerProxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class GrimDecor extends GrimPart {

	public static GrimDecor INSTANCE = new GrimDecor();

	public static final String partId = "decor";
	public static final String partName = "Grim Decor";

	public static DecorServerProxy proxy = DistExecutor.runForDist(() -> DecorClientProxy::new, () -> DecorServerProxy::new);

	public GrimDecor() {
		super(GrimDecor.partId, GrimDecor.partName, new DecorConfig());
	}

	@Override
	public void setup(final FMLCommonSetupEvent event) {
		super.setup(event);

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
	public void clientSetup(final FMLClientSetupEvent event) {
		super.clientSetup(event);
		proxy.initColors();
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