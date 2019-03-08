package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.part.PartRegistry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiScreen {

	public ConfigGui(GuiScreen parent) {
	}

	private static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		for (GrimPart part : PartRegistry.partsToLoad)
			list.add(new DummyCategoryElement(part.getPartId(), "grimpack." + part.getPartId() + ".cfg", part.getGrimConfig().getConfigItems()));

		return list;
	}

}
