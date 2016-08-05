package com.grim3212.mc.pack.core.part;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.manual.ModSection;
import com.grim3212.mc.pack.core.part.IPartEntities.IPartTileEntities;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class GrimPart {

	public static final String COMMON_PROXY = "com.grim3212.mc.pack.core.proxy.CommonProxy";

	private String partId;
	private String partName;
	private GrimConfig config;
	private GrimPartCreativeTab creativeTab;
	private ModSection modSection;
	private List<IPartItems> items;
	private List<IPartEntities> entities;
	private List<IPartTileEntities> tileentities;
	private boolean useCreativeTab;

	public GrimPart(String partId, String partName) {
		this(partId, partName, true);
	}

	public GrimPart(String partId, String partName, boolean useCreativeTab) {
		this.partId = partId;
		this.partName = partName;
		this.useCreativeTab = useCreativeTab;
		this.items = new ArrayList<IPartItems>();
		this.entities = new ArrayList<IPartEntities>();
		this.tileentities = new ArrayList<IPartTileEntities>();
		this.config = this.setConfig();
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(GrimPack.modID)) {
			this.config.syncConfig();
		}
	}

	public boolean shouldUseCreativeTab() {
		return useCreativeTab;
	}

	public void setCreativeTab(GrimPartCreativeTab creativeTab) {
		this.creativeTab = creativeTab;
	}

	public abstract GrimConfig setConfig();

	public GrimConfig getGrimConfig() {
		return this.config;
	}

	public Configuration getConfig() {
		return this.config.config;
	}

	public ModSection setModSection(ModSection modSection) {
		return this.modSection = modSection;
	}

	public ModSection getModSection() {
		return modSection;
	}

	public void addEntity(IPartEntities entity) {
		this.entities.add(entity);
	}

	public void addItem(IPartItems item) {
		this.items.add(item);
	}

	public void addTileEntity(IPartTileEntities te) {
		this.tileentities.add(te);
	}

	/**
	 * Registers the config change event And iterates through items and entities
	 * 
	 * @param FMLPreInitializationEvent
	 *            event
	 */
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		// Initialize all items and blocks first
		for (int i = 0; i < this.items.size(); i++) {
			this.items.get(i).initItems();
		}

		for (int i = 0; i < this.entities.size(); i++) {
			this.entities.get(i).initEntities();
		}
	}

	/**
	 * Initializes tile entities
	 * 
	 * @param FMLInitializationEvent
	 *            event
	 */
	public void init(FMLInitializationEvent event) {
		// Then create recipes after all blocks are loaded
		for (int i = 0; i < this.items.size(); i++) {
			this.items.get(i).addRecipes();
		}

		for (int i = 0; i < this.tileentities.size(); i++) {
			this.tileentities.get(i).initTileEntities();
		}
	}

	/**
	 * @param FMLPostInitializationEvent
	 *            event
	 */
	public void postInit(FMLPostInitializationEvent event) {
	}

	protected Item getCreativeTabIcon() {
		return null;
	}

	public String getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	public GrimPartCreativeTab getCreativeTab() {
		return creativeTab;
	}
}
