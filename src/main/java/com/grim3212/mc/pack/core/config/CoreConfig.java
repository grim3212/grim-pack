package com.grim3212.mc.pack.core.config;

import java.util.Optional;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

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
	public Optional<ForgeConfigSpec> initClient(Builder clientBuilder) {
		clientBuilder.comment(name());

		showDebugInfo = clientBuilder.translation("grimpack.core.cfg.showDebugInfo").define(CONFIG_GENERAL_NAME + ".showDebugInfo", false);
		showCheckmark = clientBuilder.translation("grimpack.core.cfg.showCheckMark").define(CONFIG_GENERAL_NAME + ".showCheckmark", true);

		return Optional.of(clientBuilder.build());
	}

	@Override
	public Optional<ForgeConfigSpec> initServer(Builder serverBuilder) {
		serverBuilder.comment(name());

		useCuisine = serverBuilder.comment("Enable cuisine part").worldRestart().define(CONFIG_PARTS_NAME + ".useCuisine", true);
		useDecor = serverBuilder.comment("Enable decor part").worldRestart().define(CONFIG_PARTS_NAME + ".useDecor", true);
		useIndustry = serverBuilder.comment("Enable industry part").worldRestart().define(CONFIG_PARTS_NAME + ".useIndustry", true);
		useTools = serverBuilder.comment("Enable tools part").worldRestart().define(CONFIG_PARTS_NAME + ".useTools", true);
		useUtil = serverBuilder.comment("Enable util part").worldRestart().define(CONFIG_PARTS_NAME + ".useUtil", true);
		useWorld = serverBuilder.comment("Enable world part").worldRestart().define(CONFIG_PARTS_NAME + ".useWorld", true);

		subpartAluminum = serverBuilder.comment("Enable aluminum subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartAluminum", true);
		subpartCoal = serverBuilder.comment("Enable coal subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartCoal", true);
		subpartGraphite = serverBuilder.comment("Enable graphite subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartGraphite", true);
		subpartIron = serverBuilder.comment("Enable iron subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartIron", true);
		subpartRubber = serverBuilder.comment("Enable rubber subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartRubber", true);
		subpartSteel = serverBuilder.comment("Enable steel subpart").worldRestart().define(CONFIG_SUB_PARTS_NAME + ".subpartSteel", true);

		giveManualOnJoin = serverBuilder.translation("grimpack.core.cfg.giveManual").define(CONFIG_GENERAL_NAME + ".giveManualOnJoin", true);
		generateAluminum = serverBuilder.comment("Generate Aluminum").define(CONFIG_GENERAL_NAME + ".generateAluminum", true);

		return Optional.of(serverBuilder.build());
	}
}
