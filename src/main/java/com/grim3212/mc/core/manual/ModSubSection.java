package com.grim3212.mc.core.manual;

import java.util.ArrayList;
import java.util.Arrays;

import com.grim3212.mc.core.manual.pages.Page;

import net.minecraft.util.StatCollector;

public class ModSubSection {

	private final String subsectionName;
	private String modID;
	private ArrayList<Page> pages = new ArrayList<Page>();

	public ModSubSection(String subsectionName, String modID) {
		this.subsectionName = subsectionName;
		this.modID = modID;
	}

	public String getUnlocalizedSubSectionName() {
		return "grim.manual." + this.modID + ".subsection." + this.subsectionName;
	}

	public String getSubSectionName() {
		return StatCollector.translateToLocal(this.getUnlocalizedSubSectionName());
	}

	public ModSubSection addSubSectionPages(Page... pages) {
		this.pages.addAll(Arrays.asList(pages));

		for (Page page : pages) {
			page.setModid(modID);
			page.setPageName(this.getUnlocalizedSubSectionName() + ".page." + page.getPageName());
			page.setLocalizedPageName(StatCollector.translateToLocal(page.getPageName() + ".title"));

			// If using values set above
			if (page.setupMethod())
				page.setup();
		}

		return this;
	}

	public ArrayList<Page> getPages() {
		return this.pages;
	}
}
