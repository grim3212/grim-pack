package com.grim3212.mc.pack.util;

import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.proxy.CommonProxy;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.util.client.ManualUtil;
import com.grim3212.mc.pack.util.config.UtilConfig;
import com.grim3212.mc.pack.util.event.BlockChangeEvents;
import com.grim3212.mc.pack.util.event.EntityDeathEvent;
import com.grim3212.mc.pack.util.grave.TileEntityGrave;
import com.grim3212.mc.pack.util.init.UtilBlocks;
import com.grim3212.mc.pack.util.network.MessageFusRoDah;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimUtil extends GrimPart {

	public static GrimUtil INSTANCE = new GrimUtil();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.util.client.UtilClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	public static final String partID = "util";
	public static final String partName = "Grim Util";

	public static SoundEvent fusrodahSound;
	public static SoundEvent fusrodahOldSound;

	public GrimUtil() {
		super(GrimUtil.partID, GrimUtil.partName, new UtilConfig(), false);
		addItem(new UtilBlocks());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		PacketDispatcher.registerMessage(MessageFusRoDah.class);
		MinecraftForge.EVENT_BUS.register(new EntityDeathEvent());
		MinecraftForge.EVENT_BUS.register(new BlockChangeEvents());
		fusrodahSound = Utils.registerSound("fusrodah");
		fusrodahOldSound = Utils.registerSound("fusrodah-old");

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);

		GameRegistry.registerTileEntity(TileEntityGrave.class, "grimpack:grave");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualUtil.INSTANCE;
	}
}