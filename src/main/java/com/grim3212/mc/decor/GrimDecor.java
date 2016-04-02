package com.grim3212.mc.decor;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.entity.DecorEntities;
import com.grim3212.mc.decor.item.DecorItems;
import com.grim3212.mc.decor.tile.DecorTileEntities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GrimDecor.modID, name = GrimDecor.modName, version = GrimDecor.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.decor.config.ConfigGuiFactory")
public class GrimDecor extends GrimPart {

	@Instance(GrimDecor.modID)
	public static GrimDecor INSTANCE;
	
	@SidedProxy(clientSide = "com.grim3212.mc.decor.proxy.DecorClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String modID = "grimdecor";
	public static final String modName = "Grim Decor";
	public static final String modVersion = "1.0.0";

	public GrimDecor() {
		super(GrimDecor.modID, GrimDecor.modName, GrimDecor.modVersion);
		addItem(new DecorBlocks());
		addItem(new DecorItems());
		addEntity(new DecorEntities());
		addTileEntity(new DecorTileEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Decor provides many different ways to decorate your Minecraft world.";
		data.credits = "Thanks to the following authors.";
		
		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	protected Item getCreativeTabIcon() {
		return Item.getItemFromBlock(DecorBlocks.calendar);
	}

	@Override
	protected GrimConfig setConfig() {
		return new DecorConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("calendar", modSection).addSubSectionPages(new PageCrafting("calendar", new ItemStack(DecorBlocks.calendar)));
		ManualRegistry.addSection("wall", modSection).addSubSectionPages(new PageCrafting("clock", DecorBlocks.clocks, 20));
		ManualRegistry.addSection("lights", modSection).addSubSectionPages(new PageCrafting("recipes", DecorBlocks.lights, 25));
	}
}