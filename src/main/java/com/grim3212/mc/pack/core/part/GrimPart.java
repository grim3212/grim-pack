package com.grim3212.mc.pack.core.part;

import java.util.List;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.manual.IManualPart;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

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
	 * @param config              The config to use
	 * @param useCreativeTab      If the part should have a creative tab
	 * @param syncConfigInstantly If the config should be synced during creation.
	 *                            The only thing that uses this is GrimCore for
	 *                            determining which parts to load
	 */
	public GrimPart(String partId, String partName, GrimConfig config) {
		this.partId = partId;
		this.partName = partName;
		this.config = config;
	}

	@OnlyIn(Dist.CLIENT)
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
	 * @param FMLPreInitializationEvent event
	 */
	public void setup(final FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void serverSetup(final FMLDedicatedServerSetupEvent event) {
	}

	/**
	 * @param FMLPostInitializationEvent event
	 */
	/*public void postInit(FMLPostInitializationEvent event) {
		if (event.getSide() == Dist.CLIENT)
			getManual().initPages();
	}*/

	public String getPartId() {
		return partId;
	}

	public String getPartName() {
		return partName;
	}

	public GrimConfig getGrimConfig() {
		return this.config;
	}

	public List<String> getImageUrls() {
		return Lists.newArrayList();
	}

	public String getExtraInfo() {
		return "";
	}

	@SubscribeEvent
	public void onLoad(final ModConfig.Loading configEvent) {
		GrimLog.debug(getPartId(), "Loaded config file " + configEvent.getConfig().getFileName());

	}

	@SubscribeEvent
	public void onFileChange(final ModConfig.ConfigReloading configEvent) {
		GrimLog.fatal(getPartId(), "Config just got changed on the file system!");
	}
}
