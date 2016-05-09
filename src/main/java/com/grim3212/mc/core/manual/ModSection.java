package com.grim3212.mc.core.manual;

import java.util.ArrayList;

import net.minecraft.util.text.translation.I18n;

public class ModSection {

	private final String modName;
	private final String modID;
	private ArrayList<ModSubSection> pages = new ArrayList<ModSubSection>();

	public ModSection(String modName, String modID) {
		this.modName = modName;
		this.modID = modID;
	}

	public String getModName() {
		return this.modName;
	}

	public String getModID() {
		return this.modID;
	}

	public String getModSectionInfo() {
		return I18n.translateToLocal("grim.manual." + modID + "." + "modinfo");
	}

	public ArrayList<ModSubSection> getPages() {
		return this.pages;
	}
}