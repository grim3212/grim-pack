package com.grim3212.mc.pack.tools;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.client.gui.ToolsGuiHandler;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.entity.ToolsEntities;
import com.grim3212.mc.pack.tools.event.MilkingEvent;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.network.MessageSwitchModes;
import com.grim3212.mc.pack.tools.network.MessageWandKeys;
import com.grim3212.mc.pack.tools.world.ToolsGenerate;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimTools.modID, name = GrimTools.modName, version = GrimCore.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.pack.tools.config.ConfigGuiFactory")
public class GrimTools extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.pack.tools.client.ToolsClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimTools.modID)
	public static GrimTools INSTANCE;

	public static final String modID = "grimtools";
	public static final String modName = "Grim Tools";

	public GrimTools() {
		super(GrimTools.modID, GrimTools.modName, GrimCore.modVersion);
		addItem(new ToolsItems());
		addItem(new ToolsBlocks());
		addEntity(new ToolsEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Tools adds in a bunch of tools and weapons.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-tools/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new ToolsGuiHandler());
		MinecraftForge.EVENT_BUS.register(new MilkingEvent());
		PacketDispatcher.registerMessage(MessageWandKeys.class);
		PacketDispatcher.registerMessage(MessageSwitchModes.class);

		GameRegistry.registerWorldGenerator(new ToolsGenerate(), 25);

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
		return ToolsItems.backpack;
	}

	@Override
	protected GrimConfig setConfig() {
		return new ToolsConfig();
	}
}