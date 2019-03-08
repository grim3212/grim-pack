package com.grim3212.mc.pack.core.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.client.config.IConfigElement;

public class CoreConfig extends GrimConfig {

	public static final String CONFIG_NAME = "core";
	public static final String CONFIG_GENERAL_NAME = "core.general";
	public static final String CONFIG_PARTS_NAME = "core.parts";
	public static final String CONFIG_SUB_PARTS_NAME = "core.subparts";

	public static ForgeConfigSpec.BooleanValue useCuisine;
	public static ForgeConfigSpec.BooleanValue useDecor;
	public static ForgeConfigSpec.BooleanValue useIndustry;
	public static ForgeConfigSpec.BooleanValue useTools;
	public static ForgeConfigSpec.BooleanValue useUtil;
	public static ForgeConfigSpec.BooleanValue useWorld;

	public static ForgeConfigSpec.BooleanValue giveManualOnJoin;
	public static ForgeConfigSpec.BooleanValue showDebugInfo;
	public static ForgeConfigSpec.BooleanValue showCheckmark;

	public static ForgeConfigSpec.BooleanValue generateAluminum;

	public static ForgeConfigSpec.BooleanValue subpartAluminum;
	public static ForgeConfigSpec.BooleanValue subpartCoal;
	public static ForgeConfigSpec.BooleanValue subpartGraphite;
	public static ForgeConfigSpec.BooleanValue subpartIron;
	public static ForgeConfigSpec.BooleanValue subpartRubber;
	public static ForgeConfigSpec.BooleanValue subpartSteel;

	@Override
	public String name() {
		return CONFIG_NAME;
	}

	@Override
	public void init(Builder CLIENT_BUILDER, Builder SERVER_BUILDER) {
		// Set names
		CLIENT_BUILDER.comment(name());
		SERVER_BUILDER.comment(name());

		useCuisine = SERVER_BUILDER.comment("Enable cuisine part").worldRestart().define(CONFIG_PARTS_NAME + ".useCuisine", true);
		useDecor = SERVER_BUILDER.comment("Enable decor part").worldRestart().define(CONFIG_PARTS_NAME + ".useDecor", true);
		useIndustry = SERVER_BUILDER.comment("Enable industry part").worldRestart().define(CONFIG_PARTS_NAME + ".useIndustry", true);
		useTools = SERVER_BUILDER.comment("Enable tools part").worldRestart().define(CONFIG_PARTS_NAME + ".useTools", true);
		useUtil = SERVER_BUILDER.comment("Enable util part").worldRestart().define(CONFIG_PARTS_NAME + ".useUtil", true);
		useWorld = SERVER_BUILDER.comment("Enable world part").worldRestart().define(CONFIG_PARTS_NAME + ".useWorld", true);

		subpartAluminum = SERVER_BUILDER.comment("Enable aluminum subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartAluminum", true);
		subpartCoal = SERVER_BUILDER.comment("Enable coal subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartCoal", true);
		subpartGraphite = SERVER_BUILDER.comment("Enable graphite subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartGraphite", true);
		subpartIron = SERVER_BUILDER.comment("Enable iron subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartIron", true);
		subpartRubber = SERVER_BUILDER.comment("Enable rubber subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartRubber", true);
		subpartSteel = SERVER_BUILDER.comment("Enable steel subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartSteel", true);

		showDebugInfo = CLIENT_BUILDER.translation("grimpack.core.cfg.showDebugInfo").define(CONFIG_GENERAL_NAME + ".showDebugInfo", false);
		showCheckmark = CLIENT_BUILDER.translation("grimpack.core.cfg.showCheckMark").define(CONFIG_GENERAL_NAME + ".showCheckmark", true);

		giveManualOnJoin = SERVER_BUILDER.translation("grimpack.core.cfg.giveManual").define(CONFIG_GENERAL_NAME + ".giveManualOnJoin", true);

		if (CoreConfig.subpartAluminum.get())
			generateAluminum = SERVER_BUILDER.comment("Generate Aluminum").define(CONFIG_GENERAL_NAME + ".generateAluminum", true);
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		//list.addAll(new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements());
		//list.add(new DummyCategoryElement("coreParts", "grimpack.core.cfg.parts", new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_PARTS_NAME)).getChildElements()));
		//list.add(new DummyCategoryElement("coreSubPartCfg", "grimpack.core.cfg.subparts", new ConfigElement(GrimCore.INSTANCE.getConfig().getCategory(CONFIG_SUB_PARTS_NAME)).getChildElements()));
		return list;
	}
}
