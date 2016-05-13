package com.grim3212.mc.pack.industry;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.client.gui.IndustryGuiHandler;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.tile.IndustryTileEntities;
import com.grim3212.mc.pack.industry.world.IndustryGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimIndustry.modID, name = GrimIndustry.modName, version = GrimCore.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.pack.industry.config.ConfigGuiFactory")
public class GrimIndustry extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.pack.industry.client.IndustryClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimIndustry.modID)
	public static GrimIndustry INSTANCE;

	public static final String modID = "grimindustry";
	public static final String modName = "Grim Industry";

	public GrimIndustry() {
		super(GrimIndustry.modID, GrimIndustry.modName, GrimCore.modVersion);
		addItem(new IndustryBlocks());
		addItem(new IndustryItems());
		addTileEntity(new IndustryTileEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Industry lets the player mess be a bit more technical.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-industry/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new IndustryGuiHandler());

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
		return Item.getItemFromBlock(IndustryBlocks.togglerack);
	}

	@Override
	protected GrimConfig setConfig() {
		return new IndustryConfig();
	}
}