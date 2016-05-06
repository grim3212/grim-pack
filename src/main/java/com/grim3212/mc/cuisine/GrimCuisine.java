package com.grim3212.mc.cuisine;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.cuisine.block.CuisineBlocks;
import com.grim3212.mc.cuisine.config.CuisineConfig;
import com.grim3212.mc.cuisine.events.DropEvent;
import com.grim3212.mc.cuisine.events.OnBonemealEvent;
import com.grim3212.mc.cuisine.item.CuisineItems;
import com.grim3212.mc.cuisine.world.CuisineGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimCuisine.modID, name = GrimCuisine.modName, version = GrimCuisine.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.cuisine.config.ConfigGuiFactory")
public class GrimCuisine extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.cuisine.client.CuisineClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimCuisine.modID)
	public static GrimCuisine INSTANCE;

	public static final String modID = "grimcuisine";
	public static final String modName = "Grim Cuisine";
	public static final String modVersion = "1.0.0";

	public GrimCuisine() {
		super(GrimCuisine.modID, GrimCuisine.modName, GrimCuisine.modVersion);
		addItem(new CuisineBlocks());
		addItem(new CuisineItems());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Cuisine provides many different food sources and health regeneration options.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-cuisine/";
		data.credits = "Thanks to the follwoing authors. Leesgowest, LFalch, mattop101, Nandonalt.";

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
	protected GrimConfig setConfig() {
		return new CuisineConfig();
	}
}