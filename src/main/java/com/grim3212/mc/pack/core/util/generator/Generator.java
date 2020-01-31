package com.grim3212.mc.pack.core.util.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.ManualChapter;
import com.grim3212.mc.pack.core.manual.ManualPart;
import com.grim3212.mc.pack.core.manual.ManualRegistry;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.manual.pages.PageInfo;
import com.grim3212.mc.pack.core.part.PartRegistry;
import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.generator.renderers.IconRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.versions.forge.ForgeVersion;

public class Generator {

	public static final String GENERATOR_NAME = "Grim Generator";
	private static final String GRIMPACK_VERSION = "5.2.12.73";
	private static final String JEI_VERSION = "4.7.11.101";
	private static final String JER_VERSION = "0.8.5.26";

	public static String directory = "";

	public static Set<ItemStack> exports = new LinkedHashSet<>();

	/*
	 * Used for information on website only
	 */
	private static List<ManualPart> extraParts = Lists.newArrayList();

	/**
	 * Starts the generation of files for documenting from each of the subparts
	 * 
	 * If you want everything regenerated make sure all subparts are loaded as this
	 * only pulls the data loaded into Minecraft
	 * 
	 * Should only be called when loaded in a world after all the pages have gotten
	 * properly setup
	 * 
	 * @param saveLocation Folder where all of the files generated will be created
	 */
	public static boolean document(String saveLocation) {
		File file = new File(saveLocation, GrimPack.modName + "-" + Minecraft.getInstance().getVersion() + "-" + GRIMPACK_VERSION + "-" + Minecraft.getInstance().getLanguageManager().getCurrentLanguage().getCode() + ".json");

		GrimLog.info(GENERATOR_NAME, "Starting documentation generation at " + file);

		// Create the json object
		JsonObject obj = createJson();

		GrimLog.info(GENERATOR_NAME, "Trying to write Json to file...");
		try {
			Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

			// Get pretty print version of json
			String prettyJson = new GsonBuilder().disableHtmlEscaping().create().toJson(new JsonParser().parse(obj.toString()).getAsJsonObject());

			// Write the JsonObject
			writer.write(prettyJson);
			writer.close();
			GrimLog.info(GENERATOR_NAME, "Successfully wrote Json to file!");
		} catch (IOException e) {
			GrimLog.error(GENERATOR_NAME, "Json could not be written to file!");
			e.printStackTrace();
			return false;
		}

		GrimLog.info(GENERATOR_NAME, "Finished documentation generation");

		return true;
	}

	public static void images(String saveLocation) {
		Minecraft.getInstance().deferTask(new Runnable() {

			@Override
			public void run() {
				GrimLog.info(GENERATOR_NAME, "Starting image generation " + saveLocation);

				directory = saveLocation + "/assets";

				if (exports.isEmpty()) {
					GrimLog.error(GENERATOR_NAME, "ItemStacks to export was empty. Run '/grim_documentation_generation' at least once!");
				} else {
					IconRenderer.renderItems(exports);
				}

				GrimLog.info(GENERATOR_NAME, "Finished image generation");
			}
		});
	}

	private static JsonObject createJson() {
		setupExtras();

		Minecraft mc = Minecraft.getInstance();

		JsonObject json = new JsonObject();
		json.addProperty("mcversion", mc.getVersion());
		json.addProperty("forgeversion", ForgeVersion.getVersion());

		// Hardcode values for now
		json.addProperty("version", GRIMPACK_VERSION);
		json.addProperty("jeiversion", JEI_VERSION);
		json.addProperty("jerversion", JER_VERSION);
		json.addProperty("lang", mc.getLanguageManager().getCurrentLanguage().getCode());
		json.addProperty("generated", System.currentTimeMillis());

		JsonArray parts = new JsonArray();

		// Get registered parts as well as extras
		List<ManualPart> loadedParts = getParts();

		for (ManualPart part : loadedParts) {
			GrimLog.info(GENERATOR_NAME, "Starting documentation generation for " + part.getPartName());

			// Part info
			JsonObject partObj = new JsonObject();
			partObj.addProperty("id", part.getPartId());
			partObj.addProperty("name", part.getPartName());

			if (part.getExtraInfo().isEmpty())
				partObj.addProperty("info", part.getPartInfo());
			else
				partObj.addProperty("info", part.getExtraInfo());

			if (part.isExtra()) {
				partObj.addProperty("extra", true);
			}

			// Add chapters for each part
			JsonArray chapters = new JsonArray();
			for (ManualChapter chapter : part.getChapters()) {
				JsonObject chapterObj = new JsonObject();
				chapterObj.addProperty("id", chapter.getChapterId());
				chapterObj.addProperty("name", chapter.getName());

				if (!chapter.getExtraInfo().isEmpty())
					chapterObj.addProperty("info", chapter.getExtraInfo());

				JsonArray pages = new JsonArray();
				for (Page page : chapter.getPages()) {
					pages.add(page.deconstruct());
				}
				// Add pages
				chapterObj.add("pages", pages);

				// Add in chapter Image Urls
				if (chapter.getImageUrls() != null && chapter.getImageUrls().size() > 0) {
					JsonArray urls = new JsonArray();
					for (String s : chapter.getImageUrls())
						urls.add(s);

					chapterObj.add("imageUrls", urls);
				}

				// Add chapter
				chapters.add(chapterObj);
			}

			// Add in config chapter for each part
			if (!part.isExtra()) {
				//chapters.add(PartRegistry.getPart(part.getPartId()).getGrimConfig().deconstruct());
			}
				
			// Add chapters
			partObj.add("chapters", chapters);

			// Add in part Image Urls
			if (part.getImageUrls() != null && part.getImageUrls().size() > 0) {
				JsonArray urls = new JsonArray();
				for (String s : part.getImageUrls())
					urls.add(s);

				partObj.add("imageUrls", urls);
			}

			// Add part
			parts.add(partObj);

			GrimLog.info(GENERATOR_NAME, "Finished documentation generation for " + part.getPartName());
		}

		// Add all parts
		json.add("parts", parts);

		// Get display names for recipe types
		String[] recipeTypes = { "crafting_shaped", "crafting_shapeless", "furnace", "REFINERY", "MODERN_FURNACE", "DERRICK" };
		for (String type : recipeTypes) {
			json.addProperty(type, I18n.format("grimpack.generator." + type));
		}

		return json;
	}

	public static String getIconDir() {
		return directory + "/icons";
	}

	public static void addExtraPart(ManualPart part) {
		extraParts.add(part);
	}

	public static List<ManualPart> getParts() {
		// Extra parts appear before main info
		List<ManualPart> combined = Lists.newArrayList(extraParts);
		combined.addAll(ManualRegistry.getLoadedParts());

		return combined;
	}

	private static void setupExtras() {
		extraParts.clear();

		ManualPart info = new ManualPart("Information", "info").setExtra();
		ManualRegistry.addChapter("intro", info).addPages(new PageInfo("info"), new PageInfo("download"), new PageInfo("wip"));
		// ManualRegistry.addChapter("recipes", info).addPages(new
		// PageCrafting("crafting", 10, new ItemStack(CoreItems.instruction_manual), new
		// ItemStack(CuisineItems.chocolate_bowl)), new PageFurnace("furnace", new
		// ItemStack(CommonItems.aluminum_ore)), new PageMachine("other", new
		// ItemStack(Items.ROTTEN_FLESH), MachineType.REFINERY));
		ManualRegistry.addChapter("configuration", info).addPages(new PageInfo("parts"), new PageInfo("lists"), new PageInfo("tools"), new PageInfo("armor"), new PageInfo("other")).setExtraInfo("grimpack.manual.info.subsection.configuration.info");
		ManualRegistry.addChapter("resourcepacks", info).addPages(new PageInfo("looks"), new PageInfo("recipes")).setExtraInfo("grimpack.manual.info.subsection.resourcepacks.info");
		ManualRegistry.addChapter("faq", info).addPages(new PageInfo("about")).setExtraInfo("grimpack.manual.info.subsection.faq.info");
		ManualRegistry.addChapter("changelog", info).addPages(new PageInfo("changes"));
		addExtraPart(info);
	}
}