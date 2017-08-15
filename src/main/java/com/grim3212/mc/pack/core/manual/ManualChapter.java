package com.grim3212.mc.pack.core.manual;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.gui.GuiManualChapter;
import com.grim3212.mc.pack.core.manual.gui.GuiManualPage;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.client.resources.I18n;

public class ManualChapter {

	private final String chapterId;
	private int page;
	private String partId;
	private List<Page> pages = Lists.newArrayList();
	private ManualPart part;

	public ManualChapter(String chapterId, String partId) {
		this.chapterId = chapterId;
		this.partId = partId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setPart(ManualPart part) {
		this.part = part;
	}

	public String getPartId() {
		return partId;
	}

	public String getChapterId() {
		return chapterId;
	}

	public String getUnlocalizedName() {
		return "grimpack.manual." + this.partId + ".subsection." + this.chapterId;
	}

	public String getName() {
		return I18n.format(this.getUnlocalizedName());
	}

	public ManualChapter addPages(Page... pages) {

		for (int i = 0; i < pages.length; i++) {
			Page page = pages[i];

			if (page != null) {
				page.setChapter(this);
				page.setTitle(I18n.format(this.getUnlocalizedName() + ".page." + page.getPageName() + ".title"));

				if (part != null)
					page.setLink(new GuiManualPage(this, new GuiManualChapter(part, this.getPage(), this.part.getPage()), i));

				// If using values set above
				if (page.setupMethod())
					page.setup();

				this.pages.add(page);
			}
		}

		return this;
	}

	/**
	 * Have to stop changing these
	 * 
	 * @return A ImmutableList of pages
	 */
	public List<Page> getPages() {
		return ImmutableList.copyOf(this.pages);
	}
}
