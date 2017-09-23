package com.grim3212.mc.pack.core.part;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GrimPart {

	public static final String COMMON_PROXY = "com.grim3212.mc.pack.core.proxy.CommonProxy";

	private String partId;
	private String partName;
	private GrimConfig config;
	private ManualPart manualPart;

	/**
	 * Constructor for a GrimPart
	 * 
	 * @param partId
	 * @param partName
	 * @param config
	 *            The config to use
	 * @param useCreativeTab
	 *            If the part should have a creative tab
	 * @param syncConfigInstantly
	 *            If the config should be synced during creation. The only thing
	 *            that uses this is GrimCore for determining which parts to load
	 */
	public GrimPart(String partId, String partName, GrimConfig config) {
		this.partId = partId;
		this.partName = partName;
		this.config = config;

		this.config.syncSubparts();
	}

	@SideOnly(Side.CLIENT)
	public abstract IManualPart getManual();

	public ManualPart setModSection(ManualPart manualPart) {
		return this.manualPart = manualPart;
	}

	public ManualPart getManualPart() {
		return manualPart;
	}

	/**
	 * Registers the config change event And iterates through items and entities
	 * 
	 * @param FMLPreInitializationEvent
	 *            event
	 */
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		this.getGrimConfig().syncFirst();
	}

	/**
	 * Initializes tile entities
	 * 
	 * @param FMLInitializationEvent
	 *            event
	 */
	public void init(FMLInitializationEvent event) {
		// Sync config after preinit so we make sure all blocks and items are
		// loaded
		this.getGrimConfig().syncConfig();
	}

	/**
	 * @param FMLPostInitializationEvent
	 *            event
	 */
	public void postInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Side.CLIENT)
			getManual().initPages();
	}

	public String getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.getModID().equals(GrimPack.modID)) {
			this.getGrimConfig().syncConfig();
		}
	}

	public GrimConfig getGrimConfig() {
		return this.config;
	}

	public Configuration getConfig() {
		return this.getGrimConfig().config;
	}

	public List<String> getImageUrls() {
		return Lists.newArrayList();
	}

	public String getExtraInfo() {
		return "";
	}
}
