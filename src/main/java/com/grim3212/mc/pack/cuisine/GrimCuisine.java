package com.grim3212.mc.pack.cuisine;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.event.CuisineAchievements;
import com.grim3212.mc.pack.cuisine.event.DropEvent;
import com.grim3212.mc.pack.cuisine.event.OnBonemealEvent;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CuisineGenerate;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
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
		addItem(new CuisineBlocks());
		addItem(new CuisineItems());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new DropEvent());
		MinecraftForge.EVENT_BUS.register(new OnBonemealEvent());
		GameRegistry.registerWorldGenerator(new CuisineGenerate(), 25);
		CuisineAchievements.init();

		proxy.preInit();
	}

	@Override
	protected ItemStack getCreativeTabIcon() {
		return new ItemStack(CuisineItems.cheese);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualCuisine.INSTANCE;
	}
}