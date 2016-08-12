package com.grim3212.mc.pack.core.manual;

import java.util.ArrayList;
import net.minecraft.client.resources.I18n;

public class ManualPart {

	private final String partName;
	private final String partID;
	private int page;
	private ArrayList<ManualChapter> chapters = new ArrayList<ManualChapter>();

	public ManualPart(String partName, String partId) {
		this.partName = partName;
		this.partID = partId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public String getPartName() {
		return this.partName;
	}

	public String getPartId() {
		return this.partID;
	}

	public String getPartInfo() {
		return I18n.format("grimpack.manual." + partID + "." + "modinfo");
	}

	public ArrayList<ManualChapter> getChapters() {
		return this.chapters;
	}
}