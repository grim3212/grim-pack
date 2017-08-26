package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.GrimCore;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CoreConfig extends GrimConfig {

	public static final String CONFIG_NAME = "core";
	public static final String CONFIG_GENERAL_NAME = "core.general";
	public static final String CONFIG_PARTS_NAME = "core.parts";
	public static final String CONFIG_SUB_PARTS_NAME = "core.subparts";

	public static boolean useCuisine;
	public static boolean useDecor;
	public static boolean useIndustry;
	public static boolean useTools;
	public static boolean useUtil;
	public static boolean useWorld;

	public static boolean giveManualOnJoin;
	public static boolean showDebugInfo;
	public static boolean showCheckmark;

	public static boolean generateAluminum;

	public static boolean subpartAluminum;
	public static boolean subpartCoal;
	public static boolean subpartGraphite;
	public static boolean subpartIron;
	public static boolean subpartRubber;
	public static boolean subpartSteel;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void syncFirst() {
		showDebugInfo = config.get(CONFIG_GENERAL_NAME, "Show debug info in console", false, "grimpack.core.cfg.showDebugInfo").getBoolean();
	}

	@Override
	public void syncSubparts() {
		useCuisine = config.get(CONFIG_PARTS_NAME, "Enable cuisine part", true).setRequiresMcRestart(true).getBoolean();
		useDecor = config.get(CONFIG_PARTS_NAME, "Enable decor part", true).setRequiresMcRestart(true).getBoolean();
		useIndustry = config.get(CONFIG_PARTS_NAME, "Enable industry part", true).setRequiresMcRestart(true).getBoolean();
		useTools = config.get(CONFIG_PARTS_NAME, "Enable tools part", true).setRequiresMcRestart(true).getBoolean();
		useUtil = config.get(CONFIG_PARTS_NAME, "Enable util part", true).setRequiresMcRestart(true).getBoolean();
		useWorld = config.get(CONFIG_PARTS_NAME, "Enable world part", true).setRequiresMcRestart(true).getBoolean();

		subpartAluminum = config.get(CONFIG_SUB_PARTS_NAME, "Enable aluminum subpart", true).setRequiresMcRestart(true).getBoolean();
		subpartCoal = config.get(CONFIG_SUB_PARTS_NAME, "Enable coal subpart", true).setRequiresMcRestart(true).getBoolean();
		subpartGraphite = config.get(CONFIG_SUB_PARTS_NAME, "Enable graphite subpart", true).setRequiresMcRestart(true).getBoolean();
		subpartIron = config.get(CONFIG_SUB_PARTS_NAME, "Enable iron subpart", true).setRequiresMcRestart(true).getBoolean();
		subpartRubber = config.get(CONFIG_SUB_PARTS_NAME, "Enable rubber subpart", true).setRequiresMcRestart(true).getBoolean();
		subpartSteel = config.get(CONFIG_SUB_PARTS_NAME, "Enable steel subpart", true).setRequiresMcRestart(true).getBoolean();
	}

	@Override
	public void syncConfig() {
		syncSubparts();
		syncFirst();

		giveManualOnJoin = config.get(CONFIG_GENERAL_NAME, "Give Instruction Manual on World Join", true, "grimpack.core.cfg.giveManual").getBoolean();
		showCheckmark = config.get(CONFIG_GENERAL_NAME, "Show checkmark if available manual page", true, "grimpack.core.cfg.showCheckMark").getBoolean();

		if (CoreConfig.subpartAluminum)
			generateAluminum = config.get(CONFIG_GENERAL_NAME, "Generate Aluminum", true).getBoolean();

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.addAll(new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements());
		list.add(new DummyCategoryElement("coreParts", "grimpack.core.cfg.parts", new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("coreSubParts", "grimpack.core.cfg.subparts", new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_SUB_PARTS_NAME)).getChildElements()));
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

		subpartAluminum = buffer.readBoolean();
		subpartCoal = buffer.readBoolean();
		subpartGraphite = buffer.readBoolean();
		subpartIron = buffer.readBoolean();
		subpartRubber = buffer.readBoolean();
		subpartSteel = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(useCuisine);
		buffer.writeBoolean(useDecor);
		buffer.writeBoolean(useIndustry);
		buffer.writeBoolean(useTools);
		buffer.writeBoolean(useUtil);
		buffer.writeBoolean(useWorld);

		buffer.writeBoolean(subpartAluminum);
		buffer.writeBoolean(subpartCoal);
		buffer.writeBoolean(subpartGraphite);
		buffer.writeBoolean(subpartIron);
		buffer.writeBoolean(subpartRubber);
		buffer.writeBoolean(subpartSteel);
	}
}
