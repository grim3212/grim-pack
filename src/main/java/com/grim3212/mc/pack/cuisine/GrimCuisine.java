package com.grim3212.mc.pack.cuisine;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.events.DropEvent;
import com.grim3212.mc.pack.cuisine.events.OnBonemealEvent;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CuisineGenerate;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
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

	public static Achievement BAKE_CHOCOLATE_CAKE;
	public static Achievement COCOA;
	public static Achievement CHOCOLATE_MOULD;
	public static Achievement CHOCOLATE_BAR;
	public static Achievement HOT_CHOCOLATE;
	public static Achievement SODA;
	public static Achievement PIE_PAN;
	public static Achievement PIE;
	public static Achievement CUISINE_START;

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

		CUISINE_START = Utils.addAchievement("achievement.cuisine_start", "cuisine_start", 5, 5, new ItemStack(CuisineItems.cheese), GrimCore.OPEN_MANUAL);
		SODA = Utils.addAchievement("achievement.soda", "soda", 5, 3, new ItemStack(CuisineItems.soda), CUISINE_START);
		PIE_PAN = Utils.addAchievement("achievement.pie_pan", "pie_pan", 5, 7, new ItemStack(CuisineItems.pan), CUISINE_START);
		PIE = Utils.addAchievement("achievement.pie", "pie", 5, 9, new ItemStack(CuisineBlocks.apple_pie), PIE_PAN);
		COCOA = Utils.addAchievement("achievement.cocoa", "cocoa", 7, 5, new ItemStack(CuisineItems.cocoa_dust), CUISINE_START);
		CHOCOLATE_MOULD = Utils.addAchievement("achievement.chocolate_mould", "chocolate_mould", 9, 3, new ItemStack(CuisineBlocks.chocolate_bar_mould), COCOA);
		CHOCOLATE_BAR = Utils.addAchievement("achievement.chocolate_bar", "chocolate_bar", 9, 5, new ItemStack(CuisineItems.chocolate_bar_wrapped), CHOCOLATE_MOULD);
		HOT_CHOCOLATE = Utils.addAchievement("achievement.hot_chocolate", "hot_chocolate", 7, 7, new ItemStack(CuisineItems.chocolate_bowl_hot), COCOA);
		BAKE_CHOCOLATE_CAKE = Utils.addAchievement("achievement.bake_chocolate_cake", "bake_chocolate_cake", 7, 9, new ItemStack(CuisineBlocks.chocolate_cake), HOT_CHOCOLATE);

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