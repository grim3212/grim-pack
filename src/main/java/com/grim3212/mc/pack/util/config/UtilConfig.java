package com.grim3212.mc.pack.util.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.util.GrimUtil;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class UtilConfig extends GrimConfig {

	public static final String CONFIG_NAME = "util";
	public static final String CONFIG_GENERAL_NAME = "util.general";
	public static final String CONFIG_DEBUG_NAME = "util.debug";

	// Sync to client
	public static double frd_power;
	public static double frd_lift;
	public static boolean enableFusRoDah;
	public static boolean spawnGraves;
	public static boolean infiniteLava;
	public static boolean doubleDoors;
	public static double fusrodahCooldown;

	// Client Only
	public static boolean soundEnabled;
	public static boolean useOldSound;
	public static boolean enableAutoReplace;

	// Debug
	public static boolean showCollisionBoxes;

	@Override
	public void syncConfig() {
		frd_power = config.get(CONFIG_GENERAL_NAME, "Horizontal Pushing Force", 2.0D).getDouble();
		frd_lift = config.get(CONFIG_GENERAL_NAME, "Upward Pushing Force", 0.8D).getDouble();
		soundEnabled = config.get(CONFIG_GENERAL_NAME, "Sound Enabled", true).getBoolean();
		useOldSound = config.get(CONFIG_GENERAL_NAME, "Use Original FusRoDah Sound", false).getBoolean();
		spawnGraves = config.get(CONFIG_GENERAL_NAME, "Spawn graves on player death", true).getBoolean();
		enableFusRoDah = config.get(CONFIG_GENERAL_NAME, "Enable FusRoDah", true).getBoolean();
		fusrodahCooldown = config.get(CONFIG_GENERAL_NAME, "FusRoDah cooldown (seconds)", 5).getDouble();
		infiniteLava = config.get(CONFIG_GENERAL_NAME, "Use Infinite Lava", false).getBoolean();
		doubleDoors = config.get(CONFIG_GENERAL_NAME, "Use Double Doors", true).getBoolean();
		enableAutoReplace = config.get(CONFIG_GENERAL_NAME, "Enable Automatic Item Replacer", false).getBoolean();
		showCollisionBoxes = config.get(CONFIG_DEBUG_NAME, "Show collision boxes", false).getBoolean();

		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("utilGeneralCfg", "grimpack.util.cfg.general", new ConfigElement(GrimUtil.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("utilDebugCfg", "grimpack.util.cfg.debug", new ConfigElement(GrimUtil.INSTANCE.getConfig().getCategory(CONFIG_DEBUG_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		doubleDoors = buffer.readBoolean();
		enableFusRoDah = buffer.readBoolean();
		fusrodahCooldown = buffer.readDouble();
		frd_lift = buffer.readDouble();
		frd_power = buffer.readDouble();
		infiniteLava = buffer.readBoolean();
		spawnGraves = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(doubleDoors);
		buffer.writeBoolean(enableFusRoDah);
		buffer.writeDouble(fusrodahCooldown);
		buffer.writeDouble(frd_lift);
		buffer.writeDouble(frd_power);
		buffer.writeBoolean(infiniteLava);
		buffer.writeBoolean(spawnGraves);
	}
}
