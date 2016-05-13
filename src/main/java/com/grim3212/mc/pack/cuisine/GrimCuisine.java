package com.grim3212.mc.pack.cuisine;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.events.DropEvent;
import com.grim3212.mc.pack.cuisine.events.OnBonemealEvent;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CuisineGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrimCuisine extends GrimPart {

	public static GrimCuisine INSTANCE = new GrimCuisine();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.cuisine.client.CuisineClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "grimcuisine";
	public static final String partName = "Grim Cuisine";

	public GrimCuisine() {
		super(GrimCuisine.partId, GrimCuisine.partName);
		addItem(new CuisineBlocks());
		addItem(new CuisineItems());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new DropEvent());
		MinecraftForge.EVENT_BUS.register(new OnBonemealEvent());
		GameRegistry.registerWorldGenerator(new CuisineGenerate(), 25);

		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.registerManual(getModSection());
	}

	@Override
	protected Item getCreativeTabIcon() {
		return CuisineItems.cheese;
	}

	@Override
	public GrimConfig setConfig() {
		return new CuisineConfig();
	}
}