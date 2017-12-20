package com.grim3212.mc.pack.core.part;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.util.GrimLog;
import net.minecraftforge.fml.common.event.*;

import java.util.ArrayList;
import java.util.List;

public class PartRegistry {

	public static List<GrimPart> partsToLoad = new ArrayList<GrimPart>();
	// public static List<GrimPart> allParts =
	// Lists.newArrayList(GrimCore.INSTANCE, GrimCuisine.INSTANCE,
	// GrimDecor.INSTANCE, GrimIndustry.INSTANCE, GrimTools.INSTANCE,
	// GrimUtil.INSTANCE, GrimWorld.INSTANCE);

	public static void registerPart(GrimPart part) {
		partsToLoad.add(part);
		ManualRegistry.registerMod(part.setModSection(new ManualPart(part.getPartName(), part.getPartId())).addImageUrls(part.getImageUrls()).setExtraInfo(part.getExtraInfo()));
		GrimLog.info(GrimPack.modName, "Registered Grim Part: { " + part.getPartName() + " }");
	}

	public static GrimPart getPart(String partId) {
		for (GrimPart part : partsToLoad) {
			if (part.getPartId().equals(partId)) {
				return part;
			}
		}
		return null;
	}

	public static void preInit(FMLPreInitializationEvent event) {
		for (GrimPart part : partsToLoad) {
			part.preInit(event);
		}
	}

	public static void init(FMLInitializationEvent event) {
		for (GrimPart part : partsToLoad) {
			part.init(event);
		}
	}

	public static void postInit(FMLPostInitializationEvent event) {
		for (GrimPart part : partsToLoad) {
			part.postInit(event);
		}
	}

	public static void serverStarting(FMLServerStartingEvent event) {
		for (GrimPart part : partsToLoad) {
			part.serverStarting(event);
		}
	}

	public static void serverStarted(FMLServerStartedEvent event) {
		for (GrimPart part : partsToLoad) {
			part.serverStarted(event);
		}
	}
}
