package com.grim3212.mc.pack.industry;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.IndustryTileEntities;
import com.grim3212.mc.pack.industry.world.IndustryGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GrimIndustry extends GrimPart {

	public static GrimIndustry INSTANCE = new GrimIndustry();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.industry.client.IndustryClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "grimindustry";
	public static final String partName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.partId, GrimIndustry.partName);
		addItem(new IndustryBlocks());
		addItem(new IndustryItems());
		addTileEntity(new IndustryTileEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);
		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.registerManual(getModSection());
		proxy.initColors();
	}

	@Override
	protected Item getCreativeTabIcon() {
		return Item.getItemFromBlock(IndustryBlocks.togglerack);
	}

	@Override
	public GrimConfig setConfig() {
		return new IndustryConfig();
	}
}