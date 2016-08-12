package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.core.part.GrimPart;
import com.grim3212.mc.pack.core.part.PartRegistry;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig {

	public ConfigGui(GuiScreen gui) {
		super(gui, getConfigItems(), GrimPack.modID, false, false, GuiConfig.getAbridgedConfigPath(GrimCore.INSTANCE.getConfig().toString()));
	}

	private static List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		for (GrimPart part : PartRegistry.partsToLoad)
			list.add(new DummyCategoryElement(part.getPartId(), "grimpack." + part.getPartId() + ".cfg", part.getGrimConfig().getConfigItems()));

		return list;
	}

}
