package com.grim3212.mc.util;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageImageText;
import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.util.config.UtilConfig;
import com.grim3212.mc.util.event.EntityDeathEvent;
import com.grim3212.mc.util.event.BlockChangeEvents;
import com.grim3212.mc.util.network.MessageFusRoDah;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GrimUtil.modID, name = GrimUtil.modName, version = GrimUtil.modVersion, dependencies = "required-after:grimcore")
public class GrimUtil extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.util.UtilClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimUtil.modID)
	public static GrimUtil INSTANCE;

	public static final String modID = "grimutil";
	public static final String modName = "Grim Util";
	public static final String modVersion = "1.0.0";

	public GrimUtil() {
		super(GrimUtil.modID, GrimUtil.modName, GrimUtil.modVersion);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Util adds in a bunch of small utilities that can make playing more fun..";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-util/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		PacketDispatcher.registerMessage(MessageFusRoDah.class);
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new BlockChangeEvents());

		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	protected Item getCreativeTabIcon() {
		return Items.golden_hoe;
	}

	@Override
	protected GrimConfig setConfig() {
		return new UtilConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("autoitem", modSection).addSubSectionPages(new PageImageText("info", "autoitem.png"));
		ManualRegistry.addSection("fusrodah", modSection).addSubSectionPages(new PageImageText("info", "fusrodah.png"));
		ManualRegistry.addSection("time", modSection).addSubSectionPages(new PageImageText("info", "time.png"));
		ManualRegistry.addSection("grave", modSection).addSubSectionPages(new PageImageText("info", "grave.png"));
		if(UtilConfig.doubleDoors)
			ManualRegistry.addSection("doors", modSection).addSubSectionPages(new PageImageText("info", "doors.png"));
		if (UtilConfig.infiniteLava)
			ManualRegistry.addSection("lava", modSection).addSubSectionPages(new PageImageText("info", "lava.png"));
	}
}