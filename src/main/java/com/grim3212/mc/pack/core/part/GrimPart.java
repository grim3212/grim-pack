package com.grim3212.mc.pack.core.part;

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
	private boolean syncConfigInstantly;

	public GrimPart(String partId, String partName, GrimConfig config) {
		this(partId, partName, config, false);
	}

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
	public GrimPart(String partId, String partName, GrimConfig config, boolean syncConfigInstantly) {
		this.partId = partId;
		this.partName = partName;
		this.config = config;
		this.syncConfigInstantly = syncConfigInstantly;

		if (syncConfigInstantly) {
			this.config.syncConfig();
		}
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

	@SideOnly(Side.CLIENT)
	public abstract IManualPart getManual();

	public Configuration getConfig() {
		return this.getGrimConfig().config;
	}

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

		if (!syncConfigInstantly)
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
		if (!syncConfigInstantly)
			this.getGrimConfig().syncConfig();

		// if (event.getSide() == Side.CLIENT)
		// getManual().initPages();
	}

	/**
	 * @param FMLPostInitializationEvent
	 *            event
	 */
	public void postInit(FMLPostInitializationEvent event) {
	}

	public String getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}
}
