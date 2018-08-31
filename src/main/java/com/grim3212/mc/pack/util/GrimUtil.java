package com.grim3212.mc.pack.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.util.client.ManualUtil;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.BlockChangeEvents;
import com.grim3212.mc.pack.util.event.EntityDeathEvent;
import com.grim3212.mc.pack.util.event.EntityLivingEvents;
import com.grim3212.mc.pack.util.frozen.FrozenCapability;
import com.grim3212.mc.pack.util.frozen.FrozenCapability.IFrozenCapability;
import com.grim3212.mc.pack.util.frozen.FrozenClientEvents;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;
import com.grim3212.mc.pack.util.init.UtilBlocks;
import com.grim3212.mc.pack.util.init.UtilSounds;
import com.grim3212.mc.pack.util.network.MessageAutoTorch;
import com.grim3212.mc.pack.util.network.MessageFusRoDah;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimUtil extends GrimPart {

	public static GrimUtil INSTANCE = new GrimUtil();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.util.client.UtilClientProxy", serverSide = "com.grim3212.mc.pack.util.UtilCommonProxy")
	public static UtilCommonProxy proxy;

	public static final String partId = "util";
	public static final String partName = "Grim Util";

	public static boolean baubles = false;

	@CapabilityInject(IFrozenCapability.class)
	public static final Capability<IFrozenCapability> FROZEN_CAP = null;

	public GrimUtil() {
		super(GrimUtil.partId, GrimUtil.partName, new UtilConfig());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new BlockChangeEvents());

		if (UtilConfig.subpartChickenFeathers)
			MinecraftForge.EVENT_BUS.register(new EntityLivingEvents());

		if (UtilConfig.subpartFusRoDah) {
			MinecraftForge.EVENT_BUS.register(new UtilSounds());
			PacketDispatcher.registerMessage(MessageFusRoDah.class);
		}

		if (UtilConfig.subpartFrozen) {
			if (event.getSide() == Side.CLIENT)
				MinecraftForge.EVENT_BUS.register(new FrozenClientEvents());
			FrozenCapability.registerCapability();
		}

		if (UtilConfig.subpartGraves) {
			MinecraftForge.EVENT_BUS.register(new UtilBlocks());
			MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());

			baubles = Loader.isModLoaded("baubles");
			if (baubles) {
				GrimLog.info(GrimUtil.partName, "Found Baubles enabling grave support");
			}
		}

		if (UtilConfig.subpartAutoTorch)
			PacketDispatcher.registerMessage(MessageAutoTorch.class);

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		if (UtilConfig.subpartGraves)
			GameRegistry.registerTileEntity(TileEntityGrave.class, new ResourceLocation(GrimPack.modID, "grave"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualUtil.INSTANCE;
	}

	@Override
	public List<String> getImageUrls() {
		return Lists.newArrayList("assets/grimpack/images/util_main.png");
	}
}