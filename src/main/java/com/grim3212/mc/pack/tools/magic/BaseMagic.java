package com.grim3212.mc.pack.tools.magic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.tools.items.ItemMagicStone.StoneType;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class BaseMagic {

	private final String name;

	private StoneType stoneType;
	private int maxUses;
	private float baseRange;

	public BaseMagic(String name) {
		this.name = name;
	}

	public abstract int performMagic(World world, EntityPlayer playerIn, EnumHand hand, int dmgLeft, float range);

	public String getMagicName() {
		return this.name;
	}

	public int getMaxUses() {
		return this.maxUses;
	}

	public float getBaseRange() {
		return this.baseRange;
	}

	public StoneType getStoneType() {
		return this.stoneType;
	}

	public void load(int defaultMaxUses, float defaultBaseRange, StoneType defaultStoneType) {
		File directoryFile = new File(GrimPack.configDir, GrimPack.modID + "/magic");
		File magicFile = new File(GrimPack.configDir, GrimPack.modID + "/magic/" + getMagicName() + "_magic_type.json");

		boolean enabled = true;

		if (magicFile.exists()) {
			int maxUses = defaultMaxUses;
			float baseRange = defaultBaseRange;
			StoneType stoneType = defaultStoneType;

			try {
				JsonReader reader = new JsonReader(new FileReader(magicFile));
				reader.beginObject();

				while (reader.hasNext()) {
					String name = reader.nextName();

					if (name.equals("enabled")) {
						enabled = reader.nextBoolean();
					} else if (name.equals("maxUses")) {
						maxUses = reader.nextInt();
					} else if (name.equals("baseRange")) {
						baseRange = (float) reader.nextDouble();
					} else if (name.equals("stoneType")) {
						reader.beginArray();
						stoneType = new StoneType(EnumCrystalType.fromString(reader.nextString()), EnumCrystalType.fromString(reader.nextString()));
						reader.endArray();
					} else {
						reader.skipValue();
					}
				}

				reader.endObject();
				reader.close();
			} catch (IOException e) {
				throw new JsonSyntaxException("Magic type '" + getMagicName() + "' had a problem loading.");
			}

			this.maxUses = maxUses;
			this.baseRange = baseRange;
			this.stoneType = stoneType;

		} else {
			try {
				if (!directoryFile.exists()) {
					directoryFile.mkdirs();
				}

				if (magicFile.createNewFile()) {
					JsonWriter writer = new JsonWriter(new FileWriter(magicFile));
					writer.setIndent("\t");
					writer.setHtmlSafe(true);
					writer.beginObject();

					writer.name("enabled").value(true);
					writer.name("maxUses").value(defaultMaxUses);
					writer.name("baseRange").value(defaultBaseRange);
					writer.name("stoneType");
					writer.beginArray();
					writer.value(defaultStoneType.mainType.getUnlocalized());
					writer.value(defaultStoneType.subType.getUnlocalized());
					writer.endArray();

					writer.endObject();
					writer.close();

					// Reset to defaults if the file was deleted and already
					// had values
					this.maxUses = defaultMaxUses;
					this.baseRange = defaultBaseRange;
					this.stoneType = defaultStoneType;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (enabled) {
			// Register the magic type if enabled
			MagicRegistry.registerMagic(this);

			GrimLog.debugInfo(ConfigUtils.getCurrentPart(), "Loaded Magic Type - '" + getMagicName() + "_magic_type.json'. " + "[Name:" + getMagicName() + ", MaxUses:" + maxUses + ", BaseRange:" + baseRange + ", StoneType:" + stoneType.toString() + "]");
		}
	}
}
