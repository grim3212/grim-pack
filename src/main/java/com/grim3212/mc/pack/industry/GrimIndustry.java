package com.grim3212.mc.pack.industry;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.entity.IndustryEntities;
import com.grim3212.mc.pack.industry.network.MessageExtruderDirection;
import com.grim3212.mc.pack.industry.network.MessageSaveFan;
import com.grim3212.mc.pack.industry.network.MessageSensorChangeMode;
import com.grim3212.mc.pack.industry.network.MessageSensorSetBox;
import com.grim3212.mc.pack.industry.network.MessageSensorSetEntity;
import com.grim3212.mc.pack.industry.network.MessageSensorSetItem;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPlayer;
import com.grim3212.mc.pack.industry.network.MessageSensorSetPos;
import com.grim3212.mc.pack.industry.network.MessageSensorSetRender;
import com.grim3212.mc.pack.industry.network.MessageSetLock;
import com.grim3212.mc.pack.industry.world.IndustryGenerate;

import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimIndustry extends GrimPart {

	public static GrimIndustry INSTANCE = new GrimIndustry();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.industry.client.IndustryClientProxy", serverSide = "com.grim3212.mc.pack.industry.IndustryCommonProxy")
	public static IndustryCommonProxy proxy;

	public static final String partId = "industry";
	public static final String partName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.partId, GrimIndustry.partName, new IndustryConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		PacketDispatcher.registerMessage(MessageSetLock.class);
		PacketDispatcher.registerMessage(MessageSensorChangeMode.class);
		PacketDispatcher.registerMessage(MessageSensorSetEntity.class);
		PacketDispatcher.registerMessage(MessageSensorSetItem.class);
		PacketDispatcher.registerMessage(MessageSensorSetPlayer.class);
		PacketDispatcher.registerMessage(MessageSensorSetPos.class);
		PacketDispatcher.registerMessage(MessageSensorSetRender.class);
		PacketDispatcher.registerMessage(MessageSensorSetBox.class);
		PacketDispatcher.registerMessage(MessageSaveFan.class);
		PacketDispatcher.registerMessage(MessageExtruderDirection.class);
		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);
		IndustryEntities.initEntities();
		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualIndustry.INSTANCE;
	}
}