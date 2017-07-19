package com.grim3212.mc.pack.tools;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.entity.ToolsEntities;
import com.grim3212.mc.pack.tools.event.MilkingEvent;
import com.grim3212.mc.pack.tools.network.MessagePowerStaffSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageSlingshotSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageWandKeys;
import com.grim3212.mc.pack.tools.world.ToolsGenerate;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimTools extends GrimPart {

	public static GrimTools INSTANCE = new GrimTools();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.tools.client.ToolsClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "tools";
	public static final String partName = "Grim Tools";

	public GrimTools() {
		super(GrimTools.partId, GrimTools.partName, new ToolsConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new MilkingEvent());
		PacketDispatcher.registerMessage(MessageWandKeys.class);
		PacketDispatcher.registerMessage(MessagePowerStaffSwitchModes.class);
		PacketDispatcher.registerMessage(MessageSlingshotSwitchModes.class);
		GameRegistry.registerWorldGenerator(new ToolsGenerate(), 25);
		ToolsEntities.initEntities();

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		// Replace milk buckets
		// OreDictionaryHelper.replaceRecipes(new ItemStack(Items.MILK_BUCKET),
		// "bucketMilk", null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualTools.INSTANCE;
	}
}