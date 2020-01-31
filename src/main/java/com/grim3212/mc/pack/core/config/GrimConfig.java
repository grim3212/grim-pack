package com.grim3212.mc.pack.core.config;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.util.GrimLog;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public abstract class GrimConfig {

	public ForgeConfigSpec SERVER_CONFIG;
	public ForgeConfigSpec CLIENT_CONFIG;

	public GrimConfig() {
		initServer(new ForgeConfigSpec.Builder()).ifPresent(spec -> {
			SERVER_CONFIG = spec;
			ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG, GrimPack.modID + "_" + this.name() + "-server.toml");
			loadConfig(SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(GrimPack.modID + "_" + this.name() + "-server.toml"));
		});
		initClient(new ForgeConfigSpec.Builder()).ifPresent(spec -> {
			CLIENT_CONFIG = spec;
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG, GrimPack.modID + "_" + this.name() + "-client.toml");
			loadConfig(CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(GrimPack.modID + "_" + this.name() + "-client.toml"));
		});
	}

	public Optional<ForgeConfigSpec> initClient(ForgeConfigSpec.Builder clientBuilder) {
		return Optional.empty();
	}

	public Optional<ForgeConfigSpec> initServer(ForgeConfigSpec.Builder serverBuilder) {
		return Optional.empty();
	}

	public String name() {
		return "grimpack";
	}

	protected List<String> getImageUrls() {
		return Lists.newArrayList();
	}

	private void loadConfig(ForgeConfigSpec spec, Path path) {
		GrimLog.debug(name(), "Loading config file " + path);

		final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

		GrimLog.debug(name(), "Built TOML config for " + path.toString());
		configData.load();
		GrimLog.debug(name(), "Loaded TOML config file " + path.toString());
		spec.setConfig(configData);
	}

	@SubscribeEvent
	public void onLoad(final ModConfig.Loading configEvent) {
		GrimLog.debug(name(), "Loaded config file " + configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public void onFileChange(final ModConfig.Reloading configEvent) {
		GrimLog.fatal(name(), "Config just got changed on the file system!");
	}
}
