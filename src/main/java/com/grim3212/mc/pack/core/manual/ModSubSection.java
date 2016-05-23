package com.grim3212.mc.pack.core.manual;

import java.util.ArrayList;
import java.util.Arrays;

import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.client.resources.I18n;

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
		return I18n.format(this.getUnlocalizedSubSectionName());
	}

	public ModSubSection addSubSectionPages(Page... pages) {
		this.pages.addAll(Arrays.asList(pages));

		for (Page page : pages) {
			page.setPartId(modID);
			page.setPageName(this.getUnlocalizedSubSectionName() + ".page." + page.getPageName());
			page.setLocalizedPageName(I18n.format(page.getPageName() + ".title"));

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
