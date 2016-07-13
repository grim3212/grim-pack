package com.grim3212.mc.pack.core;

import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.item.CoreItems;
import com.grim3212.mc.pack.core.manual.event.LoginEvent;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class GrimCore extends GrimPart {

	public static GrimCore INSTANCE = new GrimCore();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.core.client.CoreClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "grimcore";
	public static final String partName = "Grim Core";

	public GrimCore() {
		super(GrimCore.partId, GrimCore.partName);
		addItem(new CoreItems());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		// Register LoginEvent for receiving the Instruction Manual
		MinecraftForge.EVENT_BUS.register(new LoginEvent());

		proxy.registerModels();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.registerManual(getModSection());
	}

	@Override
	protected Item getCreativeTabIcon() {
		return CoreItems.instruction_manual;
	}

	@Override
	public GrimConfig setConfig() {
		return new CoreConfig();
	}
}