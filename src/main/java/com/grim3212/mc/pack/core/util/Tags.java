package com.grim3212.mc.pack.core.util;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class Tags {
	public static final Tag<Item> DYES = makeWrapperTag("dyes");

	private static Tag<Item> makeWrapperTag(String s) {
		return new ItemTags.Wrapper(new ResourceLocation(s));
	}
}
