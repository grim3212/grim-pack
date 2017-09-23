package com.grim3212.mc.pack.core.manual;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.grim3212.mc.pack.core.manual.pages.Page;

public class ManualRegistry {

	private static List<ManualPart> loadedParts = Lists.newArrayList();

	public static void registerMod(ManualPart part) {
		part.setPage(Math.floorDiv(loadedParts.size(), 12));
		loadedParts.add(part);
	}

	public static ManualPart getPartFromId(String partId) {
		for (ManualPart part : loadedParts) {
			if (part.getPartId().equals(partId)) {
				return part;
			}
		}
		return null;
	}

	public static List<ManualPart> getLoadedParts() {
		return loadedParts;
	}

	public static void addPage(ManualPart part, ManualChapter chapter) {
		chapter.setPage(Math.floorDiv(part.getChapters().size(), 12));
		if (part.getChapters().size() > 26)
			chapter.setPage((int) Math.floor(((part.getChapters().size() - 12) / 14) + 1));

		chapter.setPart(part);
		part.getChapters().add(chapter);
	}

	public static ManualChapter addChapter(String chapterName, ManualPart part) {
		ManualChapter chapter = new ManualChapter(chapterName, part.getPartId());
		ManualRegistry.addPage(part, chapter);
		return chapter;
	}

	@Nullable
	public static Page getPageFromString(String name) {
		String[] split = name.split(":");
		String id = split[0];

		if (split.length > 1) {
			String[] path = split[1].split("\\.");

			for (ManualPart part : loadedParts) {
				if (id.equals(part.getPartId())) {
					for (ManualChapter chapter : part.getChapters()) {
						if (chapter.getChapterId().equals(path[0])) {
							if (path.length > 1) {
								for (Page page : chapter.getPages()) {
									if (page.getPageName().equals(path[1])) {
										return page;
									}
								}
							} else if (path.length == 1) {
								return chapter.getPages().get(0);
							}
						}
					}
				}
			}
		}

		return null;
	}

	@Nullable
	public static String getStringFromPage(Page page) {
		if (page != null) {
			return page.getLink().getChapter().getPartId() + ":" + page.getLink().getChapter().getChapterId() + "." + page.getPageName();
		}

		return null;
	}

	public static void clearManual() {
		for (ManualPart part : getLoadedParts())
			part.getChapters().clear();
	}
}
