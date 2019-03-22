package com.grim3212.mc.pack.cuisine;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.cuisine.block.CuisineBlocks;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.cuisine.crafting.CuisineConditionFactory;
import com.grim3212.mc.pack.cuisine.event.DropEvent;
import com.grim3212.mc.pack.cuisine.event.OnBonemealEvent;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.CuisineWorldGen;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class GrimCuisine extends GrimPart {

	public static GrimCuisine INSTANCE = new GrimCuisine();

	public static final String partId = "cuisine";
	public static final String partName = "Grim Cuisine";

	public GrimCuisine() {
		super(GrimCuisine.partId, GrimCuisine.partName, new CuisineConfig());

		FMLJavaModLoadingContext.get().getModEventBus().register(new CuisineBlocks());
		FMLJavaModLoadingContext.get().getModEventBus().register(new CuisineItems());
	}

	@Override
	public void setup(final FMLCommonSetupEvent event) {
		super.setup(event);

		CraftingHelper.register(new ResourceLocation(GrimPack.modID, "cuisine_subpart_enabled"), new CuisineConditionFactory());

		if (CuisineConfig.subpartDragonFruit.get())
			MinecraftForge.EVENT_BUS.register(new DropEvent());
		if (CuisineConfig.subpartChocolate.get()) {
			MinecraftForge.EVENT_BUS.register(new OnBonemealEvent());
			CuisineWorldGen.initWorldGen();
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public IManualPart getManual() {
		return ManualCuisine.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/cuisine_main.png");
	}
}