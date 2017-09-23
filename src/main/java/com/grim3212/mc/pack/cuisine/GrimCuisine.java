package com.grim3212.mc.pack.cuisine;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.event.DropEvent;
import com.grim3212.mc.pack.cuisine.event.OnBonemealEvent;
import com.grim3212.mc.pack.cuisine.init.CuisineRecipes;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CuisineGenerate;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimCuisine extends GrimPart {

	public static GrimCuisine INSTANCE = new GrimCuisine();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.cuisine.client.CuisineClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "cuisine";
	public static final String partName = "Grim Cuisine";

	public GrimCuisine() {
		super(GrimCuisine.partId, GrimCuisine.partName, new CuisineConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new CuisineBlocks());
		MinecraftForge.EVENT_BUS.register(new CuisineItems());
		if (CuisineConfig.subpartDragonFruit)
			MinecraftForge.EVENT_BUS.register(new DropEvent());
		if (CuisineConfig.subpartChocolate) {
			MinecraftForge.EVENT_BUS.register(new OnBonemealEvent());
			GameRegistry.registerWorldGenerator(new CuisineGenerate(), 25);
		}

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		CuisineRecipes.initRecipes();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualCuisine.INSTANCE;
	}
	
	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/cuisine_main.png");
	}
}