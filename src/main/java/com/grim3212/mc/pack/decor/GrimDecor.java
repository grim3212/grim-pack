package com.grim3212.mc.pack.decor;

import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.decor.entity.DecorEntities;
import com.grim3212.mc.pack.decor.item.DecorItems;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.DecorTileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrimDecor extends GrimPart {

	public static GrimDecor INSTANCE = new GrimDecor();

	@SidedProxy(clientSide = "com.grim3212.mc.pack.decor.client.DecorClientProxy", serverSide = "com.grim3212.mc.pack.decor.DecorCommonProxy")
	public static DecorCommonProxy proxy;

	public static final String partId = "decor";
	public static final String partName = "Grim Decor";

	public static Achievement DECOR_START;

	public GrimDecor() {
		super(GrimDecor.partId, GrimDecor.partName, new DecorConfig());
		addItem(new DecorBlocks());
		addItem(new DecorItems());
		addEntity(new DecorEntities());
		addTileEntity(new DecorTileEntities());
	}

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		PacketDispatcher.registerMessage(MessageParticles.class);

		DECOR_START = Utils.addAchievement("achievement.decor_start", "decor_start", -5, -5, new ItemStack(DecorBlocks.calendar), GrimCore.OPEN_MANUAL);

		proxy.preInit();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		proxy.initColors();
	}

	@Override
	protected ItemStack getCreativeTabIcon() {
		return new ItemStack(DecorBlocks.calendar);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IManualPart getManual() {
		return ManualDecor.INSTANCE;
	}
}