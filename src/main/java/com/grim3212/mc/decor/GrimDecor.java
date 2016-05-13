package com.grim3212.mc.decor;

import com.grim3212.mc.core.GrimCore;
import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.client.gui.DecorGuiHandler;
import com.grim3212.mc.decor.config.DecorConfig;
import com.grim3212.mc.decor.entity.DecorEntities;
import com.grim3212.mc.decor.item.DecorItems;
import com.grim3212.mc.decor.network.MessageParticles;
import com.grim3212.mc.decor.tile.DecorTileEntities;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = GrimDecor.modID, name = GrimDecor.modName, version = GrimCore.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.decor.config.ConfigGuiFactory")
public class GrimDecor extends GrimPart {

	@Instance(GrimDecor.modID)
	public static GrimDecor INSTANCE;

	@SidedProxy(clientSide = "com.grim3212.mc.decor.client.DecorClientProxy", serverSide = "com.grim3212.mc.decor.DecorCommonProxy")
	public static DecorCommonProxy proxy;

	public static final String modID = "grimdecor";
	public static final String modName = "Grim Decor";

	public GrimDecor() {
		super(GrimDecor.modID, GrimDecor.modName, GrimCore.modVersion);
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
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-decor/";

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new DecorGuiHandler());

		PacketDispatcher.registerMessage(MessageParticles.class);

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
		return Item.getItemFromBlock(DecorBlocks.calendar);
	}

	@Override
	protected GrimConfig setConfig() {
		return new DecorConfig();
	}
}