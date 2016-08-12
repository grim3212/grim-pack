package com.grim3212.mc.pack.industry;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.IndustryTileEntities;
import com.grim3212.mc.pack.industry.world.IndustryGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimIndustry extends GrimPart {

	public static GrimIndustry INSTANCE = new GrimIndustry();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.industry.client.IndustryClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partId = "industry";
	public static final String partName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.partId, GrimIndustry.partName, new IndustryConfig());
		addItem(new IndustryBlocks());
		addItem(new IndustryItems());
		addTileEntity(new IndustryTileEntities());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);
		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();
	}

	@Override
	protected Item getCreativeTabIcon() {
		return Item.getItemFromBlock(IndustryBlocks.togglerack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualIndustry.INSTANCE;
	}
}