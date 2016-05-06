package com.grim3212.mc.world;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.world.blocks.WorldBlocks;
import com.grim3212.mc.world.config.WorldConfig;
import com.grim3212.mc.world.entity.WorldEntities;
import com.grim3212.mc.world.gen.GrimWorldGenerator;
import com.grim3212.mc.world.items.WorldItems;
import com.grim3212.mc.world.util.DesertWellLoot;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimWorld.modID, name = GrimWorld.modName, version = GrimWorld.modVersion, dependencies = "required-after:grimcore")
public class GrimWorld extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.world.client.WorldClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimWorld.modID)
	public static GrimWorld INSTANCE;

	public static final String modID = "grimworld";
	public static final String modName = "Grim World";
	public static final String modVersion = "1.0.0";

	public GrimWorld() {
		super(GrimWorld.modID, GrimWorld.modName, GrimWorld.modVersion);
		addItem(new WorldBlocks());
		addItem(new WorldItems());
		addEntity(new WorldEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Util adds in a bunch of small utilities that can make playing more fun..";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-world/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		GameRegistry.registerWorldGenerator(new GrimWorldGenerator(), 5);
		DesertWellLoot.init();

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
		return WorldItems.gunpowder_reed_item;
	}

	@Override
	protected GrimConfig setConfig() {
		return new WorldConfig();
	}
}