package com.grim3212.mc.pack.core.part;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

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

	public static void setup(final FMLCommonSetupEvent event) {
		for (GrimPart part : partsToLoad) {
			part.setup(event);
		}
	}

	public static void clientSetup(final FMLClientSetupEvent event) {
		for (GrimPart part : partsToLoad) {
			part.clientSetup(event);
		}
	}

	public static void serverSetup(final FMLDedicatedServerSetupEvent event) {
		for (GrimPart part : partsToLoad) {
			part.serverSetup(event);
		}
	}
}
