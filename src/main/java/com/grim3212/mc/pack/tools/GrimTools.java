package com.grim3212.mc.pack.tools;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.entity.ToolsEntities;
import com.grim3212.mc.pack.tools.event.MilkingEvent;
import com.grim3212.mc.pack.tools.init.ToolsRecipes;
import com.grim3212.mc.pack.tools.init.ToolsSounds;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.network.MessageDoubleJump;
import com.grim3212.mc.pack.tools.network.MessagePowerStaffSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageSlingshotSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageStaffKey;
import com.grim3212.mc.pack.tools.network.MessageWandKeys;
import com.grim3212.mc.pack.tools.world.ToolsGenerate;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

		MinecraftForge.EVENT_BUS.register(new ToolsBlocks());
		MinecraftForge.EVENT_BUS.register(new ToolsItems());
		MinecraftForge.EVENT_BUS.register(new ToolsRecipes());

		if (ToolsConfig.subpartRayGuns)
			MinecraftForge.EVENT_BUS.register(new ToolsSounds());

		if (ToolsConfig.subpartBuckets)
			MinecraftForge.EVENT_BUS.register(new MilkingEvent());

		if (ToolsConfig.subpartWands)
			PacketDispatcher.registerMessage(MessageWandKeys.class);

		if (ToolsConfig.subpartPowerstaff)
			PacketDispatcher.registerMessage(MessagePowerStaffSwitchModes.class);

		if (ToolsConfig.subpartSlingshots)
			PacketDispatcher.registerMessage(MessageSlingshotSwitchModes.class);

		if (ToolsConfig.subpartStaffs)
			PacketDispatcher.registerMessage(MessageStaffKey.class);

		if (ToolsConfig.subpartChickenSuit)
			PacketDispatcher.registerMessage(MessageDoubleJump.class);

		GameRegistry.registerWorldGenerator(new ToolsGenerate(), 25);
		ToolsEntities.initEntities();

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();

		ToolsRecipes.initRecipes();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);

		if (ToolsConfig.subpartBuckets)
			RecipeHelper.replaceRecipes(new ItemStack(Items.MILK_BUCKET), "bucketMilk", NonNullList.create());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualTools.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/tools_main.png");
	}
}