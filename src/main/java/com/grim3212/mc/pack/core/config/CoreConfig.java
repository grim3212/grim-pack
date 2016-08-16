package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.GrimCore;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CoreConfig extends GrimConfig {

	public static boolean giveManualOnJoin;
	public static boolean showCheckmark;
	public static final String CONFIG_NAME = "core";
	public static final String CONFIG_GENERAL_NAME = "core.general";
	public static final String CONFIG_PARTS_NAME = "core.parts";

	public static boolean useCuisine;
	public static boolean useDecor;
	public static boolean useIndustry;
	public static boolean useTools;
	public static boolean useUtil;
	public static boolean useWorld;

	@Override
	public void syncConfig() {
		giveManualOnJoin = config.get(CONFIG_GENERAL_NAME, "Give Instruction Manual on World Join", true, "grimpack.core.cfg.giveManual").getBoolean();
		showCheckmark = config.get(CONFIG_GENERAL_NAME, "Show checkmark if available manual page", true, "grimpack.core.cfg.showCheckMark").getBoolean();

		useCuisine = config.get(CONFIG_PARTS_NAME, "Enable cuisine part", true).setRequiresMcRestart(true).getBoolean();
		useDecor = config.get(CONFIG_PARTS_NAME, "Enable decor part", true).setRequiresMcRestart(true).getBoolean();
		useIndustry = config.get(CONFIG_PARTS_NAME, "Enable industry part", true).setRequiresMcRestart(true).getBoolean();
		useTools = config.get(CONFIG_PARTS_NAME, "Enable tools part", true).setRequiresMcRestart(true).getBoolean();
		useUtil = config.get(CONFIG_PARTS_NAME, "Enable util part", true).setRequiresMcRestart(true).getBoolean();
		useWorld = config.get(CONFIG_PARTS_NAME, "Enable world part", true).setRequiresMcRestart(true).getBoolean();
		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements());
		list.add(new DummyCategoryElement("coreParts", "grimpack.core.cfg.parts", new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		useCuisine = buffer.readBoolean();
		useDecor = buffer.readBoolean();
		useIndustry = buffer.readBoolean();
		useTools = buffer.readBoolean();
		useUtil = buffer.readBoolean();
		useWorld = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(useCuisine);
		buffer.writeBoolean(useDecor);
		buffer.writeBoolean(useIndustry);
		buffer.writeBoolean(useTools);
		buffer.writeBoolean(useUtil);
		buffer.writeBoolean(useWorld);
	}
}
