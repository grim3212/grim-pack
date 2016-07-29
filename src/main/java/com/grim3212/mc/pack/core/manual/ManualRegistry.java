package com.grim3212.mc.pack.core.manual;

import java.util.ArrayList;

public class ManualRegistry {

	private static ArrayList<ModSection> loadedMods = new ArrayList<ModSection>();
	private static ArrayList<ModSubSection> pages = new ArrayList<ModSubSection>();

	public static void registerMod(ModSection mod) {
		loadedMods.add(mod);
	}

	public static ArrayList<ModSection> getLoadedMods() {
		return loadedMods;
	}

	public static void addPage(ModSection mod, ModSubSection subsection) {
		pages.add(subsection);
		mod.getPages().add(subsection);
	}

	public static ModSubSection addSection(String subsectionName, ModSection modSection) {
		ModSubSection section = new ModSubSection(subsectionName, modSection.getModID());
		ManualRegistry.addPage(modSection, section);
		return section;
	}

	public static void clearManual() {
		pages.clear();
		for (ModSection mod : getLoadedMods())
			mod.getPages().clear();
	}
}
