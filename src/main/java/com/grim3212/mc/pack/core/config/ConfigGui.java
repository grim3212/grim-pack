package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.GrimCore;
import com.grim3212.mc.pack.cuisine.config.CuisineConfig;
import com.grim3212.mc.pack.decor.config.DecorConfig;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.util.config.UtilConfig;

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

		// Add core config category
		list.add(new DummyCategoryElement("core", "grimpack.core.cfg", CoreConfig.getConfigItems()));

		// Add cuisine config category
		list.add(new DummyCategoryElement("cuisine", "grimpack.cuisine.cfg", CuisineConfig.getConfigItems()));

		// Add decor config category
		list.add(new DummyCategoryElement("decor", "grimpack.decor.cfg", DecorConfig.getConfigItems()));

		// Add industry config category
		list.add(new DummyCategoryElement("industry", "grimpack.industry.cfg", IndustryConfig.getConfigItems()));

		// Add tools config category
		list.add(new DummyCategoryElement("tools", "grimpack.tools.cfg", ToolsConfig.getConfigItems()));

		// Add util config category
		list.add(new DummyCategoryElement("util", "grimpack.util.cfg", UtilConfig.getConfigItems()));

		return list;
	}

}
