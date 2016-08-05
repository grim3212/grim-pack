package com.grim3212.mc.pack.core.part;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.ModSection;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PartRegistry {

	public static List<GrimPart> partsToLoad = new ArrayList<GrimPart>();

	public static void registerPart(GrimPart part) {
		partsToLoad.add(part);
		ManualRegistry.registerMod(part.setModSection(new ModSection(part.getPartName(), part.getPartId())));
		if (part.shouldUseCreativeTab())
			part.setCreativeTab(new GrimPartCreativeTab(part));
		GrimLog.info(GrimPack.modName, "Registered Grim Part: { " + part.getPartName() + " }");
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
}
