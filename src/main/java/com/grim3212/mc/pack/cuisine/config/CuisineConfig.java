package com.grim3212.mc.pack.cuisine.config;

import java.util.Optional;

import com.grim3212.mc.pack.core.config.GrimConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class CuisineConfig extends GrimConfig {

	public static final String CONFIG_NAME = "cuisine";
	public static final String CONFIG_GENERAL_NAME = "cuisine.general";
	public static final String CONFIG_PARTS_NAME = "cuisine.subparts";

	public static ForgeConfigSpec.BooleanValue generateCocoaTrees;
	public static ForgeConfigSpec.BooleanValue subpartChocolate;
	public static ForgeConfigSpec.BooleanValue subpartDairy;
	public static ForgeConfigSpec.BooleanValue subpartDragonFruit;
	public static ForgeConfigSpec.BooleanValue subpartHealth;
	public static ForgeConfigSpec.BooleanValue subpartPie;
	public static ForgeConfigSpec.BooleanValue subpartSoda;

	@Override
	public String name() {
		return CONFIG_NAME;
	}
	
	@Override
	public Optional<ForgeConfigSpec> initServer(Builder serverBuilder) {
		serverBuilder.comment(name());
		
		subpartChocolate = serverBuilder.comment("Enable chocolate subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartAluminum", true);
		subpartDairy = serverBuilder.comment("Enable dairy subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartCoal", true);
		subpartDragonFruit = serverBuilder.comment("Enable dragon fruit subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartGraphite", true);
		subpartHealth = serverBuilder.comment("Enable health subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartIron", true);
		subpartPie = serverBuilder.comment("Enable pie subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartRubber", true);
		subpartSoda = serverBuilder.comment("Enable soda subpart").worldRestart().define(CONFIG_PARTS_NAME + ".subpartSteel", true);
		
		generateCocoaTrees = serverBuilder.comment("Generate Cocoa Trees").worldRestart().define(CONFIG_GENERAL_NAME + ".subpartSteel", true);
		
		return Optional.of(serverBuilder.build());
	}
}
