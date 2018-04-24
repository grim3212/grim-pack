package com.grim3212.mc.pack.tools.magic;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.util.NBTHelper;

import net.minecraft.item.ItemStack;

public class MagicRegistry {

	private static List<BaseMagic> loadedMagic = new ArrayList<BaseMagic>();

	public static void reset() {
		loadedMagic.clear();
	}

	public static void registerMagic(BaseMagic magic) {
		loadedMagic.add(magic);
	}
	
	public static List<BaseMagic> getLoadedMagic() {
		return loadedMagic;
	}

	public static BaseMagic getMagic(ItemStack stack) {
		int meta = NBTHelper.getInt(stack, "WandType");

		for (int i = 0; i < loadedMagic.size(); i++) {
			BaseMagic magic = loadedMagic.get(i);

			if (magic.getStoneType().getMeta() == meta) {
				return magic;
			}
		}

		return null;
	}
}
