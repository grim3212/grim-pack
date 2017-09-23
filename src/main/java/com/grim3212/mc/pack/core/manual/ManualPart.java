package com.grim3212.mc.pack.core.manual;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.resources.I18n;

public class ManualPart {

	private final String partName;
	private final String partID;
	private int page;
	private List<String> imageUrls = Lists.newArrayList();
	private ArrayList<ManualChapter> chapters = new ArrayList<ManualChapter>();
	private String extraInfo = "";

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

	/**
	 * Used only for documenting.
	 * 
	 * The URL can be full 'https://i.imgur.com/oiqrWcM.png' Or relative to
	 * domain 'assets/images/coolImg.png'
	 * 
	 * @param urls
	 */
	public ManualPart addImageUrl(String urls) {
		this.imageUrls.add(urls);
		return this;
	}

	public ManualPart addImageUrls(List<String> urls) {
		this.imageUrls.addAll(urls);
		return this;
	}

	public List<String> getImageUrls() {
		return this.imageUrls;
	}

	public ManualPart setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
		return this;
	}

	public String getExtraInfo() {
		return I18n.format(extraInfo);
	}

	private boolean isExtra = false;

	public boolean isExtra() {
		return this.isExtra;
	}

	public ManualPart setExtra() {
		this.isExtra = true;
		return this;
	}
}