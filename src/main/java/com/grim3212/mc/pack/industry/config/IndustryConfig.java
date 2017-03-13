package com.grim3212.mc.pack.industry.config;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.config.ConfigUtils;
import com.grim3212.mc.pack.core.config.GrimConfig;
import com.grim3212.mc.pack.core.config.Recipe;
import com.grim3212.mc.pack.industry.GrimIndustry;
import com.grim3212.mc.pack.industry.util.MachineRecipes;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.IConfigElement;

public class IndustryConfig extends GrimConfig {

	public static final String CONFIG_NAME = "industry";
	public static final String CONFIG_GENERAL_NAME = "industry.general";
	public static final String CONFIG_EXTRUDER_NAME = "industry.extruder";
	public static final String CONFIG_REFINERY_RECIPES_NAME = "industry.customrefineryrecipes";

	public static List<ItemStack> workbenchUpgradeList = new ArrayList<ItemStack>();

	public static boolean generateUranium;
	public static boolean generateAluminum;
	public static boolean generateOilOre;

	// Workbench Upgrades
	public static String[] workbenchList;
	public static boolean useWorkbenchUpgrades;
	public static boolean useWhitelist;
	public static boolean returnDefaultIfListed;

	// Extruder settings
	public static int fuelPerMinedBlock;
	public static int fuelPerExtrudedBlock;
	public static float moveSpeed;
	public static int fuelPerStick;
	public static int fuelPerCoal;
	public static int fuelPerRedstone;
	public static int fuelPerLava;
	public static int fuelPerBlazerod;
	public static int fuelPerMagmaCream;
	public static float speedModifierStick;
	public static float speedModifierCoal;
	public static float speedModifierRedstone;
	public static float speedModifierMagmaCream;

	// Uranium Damage - add by SH
	public static float uraniumDamage;

	// Client Only Settings
	public static boolean showFanParticles;

	@Override
	public void syncConfig() {
		generateUranium = config.get(CONFIG_GENERAL_NAME, "Generate Uranium", true).getBoolean();
		generateAluminum = config.get(CONFIG_GENERAL_NAME, "Generate Aluminum", true).getBoolean();
		generateOilOre = config.get(CONFIG_GENERAL_NAME, "Generate Oil Ore", true).getBoolean();
		useWorkbenchUpgrades = config.get(CONFIG_GENERAL_NAME, "Do workbench upgrades double result", false).getBoolean();
		useWhitelist = config.get(CONFIG_GENERAL_NAME, "Workbench whitelist else blacklist", false).getBoolean();
		workbenchList = config.get(CONFIG_GENERAL_NAME, "Workbench black or white list", new String[] { "oredict:blockDiamond", "oredict:gemDiamond", "oredict:blockIron", "oredict:ingotIron", "oredict:blockGold", "oredict:ingotGold", "oredict:blockLapis", "oredict:gemLapis", "oredict:blockRedstone", "oredict:dustRedstone", "oredict:blockEmerald", "oredict:gemEmerald", "oredict:blockQuartz", "oredict:gemQuartz", "oredict:blockCoal", "minecraft:coal", "oredict:blockBlackDiamond", "oredict:gemBlackDiamond", "oredict:blockSteel", "oredict:ingotSteel" }).getStringList();
		returnDefaultIfListed = config.get(CONFIG_GENERAL_NAME, "Return Default Recipe If Listed", true).getBoolean();
		showFanParticles = config.get(CONFIG_GENERAL_NAME, "Show Fan Particles", true).getBoolean();

		fuelPerMinedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per mined block", 400).getInt();
		fuelPerExtrudedBlock = config.get(CONFIG_EXTRUDER_NAME, "Fuel per extruded block", 200).getInt();
		moveSpeed = (float) config.get(CONFIG_EXTRUDER_NAME, "Extruder move speed", 0.1f).getDouble();
		fuelPerStick = config.get(CONFIG_EXTRUDER_NAME, "Fuel per stick", 50).getInt();
		fuelPerCoal = config.get(CONFIG_EXTRUDER_NAME, "Fuel per coal", 1600).getInt();
		fuelPerRedstone = config.get(CONFIG_EXTRUDER_NAME, "Fuel per redstone", 3200).getInt();
		fuelPerLava = config.get(CONFIG_EXTRUDER_NAME, "Fuel per lava", 6400).getInt();
		fuelPerBlazerod = config.get(CONFIG_EXTRUDER_NAME, "Fuel per blazerod", 12800).getInt();
		fuelPerMagmaCream = config.get(CONFIG_EXTRUDER_NAME, "Fuel per magma cream", 19200).getInt();
		speedModifierStick = (float) config.get(CONFIG_EXTRUDER_NAME, "Stick speed modifier", 0.3F).getDouble();
		speedModifierCoal = (float) config.get(CONFIG_EXTRUDER_NAME, "Coal speed modifier", 0.8F).getDouble();
		speedModifierRedstone = (float) config.get(CONFIG_EXTRUDER_NAME, "Redstone speed modifier", 1.4F).getDouble();
		speedModifierMagmaCream = (float) config.get(CONFIG_EXTRUDER_NAME, "Magmacream speed modifier", 2.2F).getDouble();

		// Workbench list
		ConfigUtils.loadItemsOntoList(workbenchList, IndustryConfig.workbenchUpgradeList);

		uraniumDamage = config.get(CONFIG_GENERAL_NAME, "Damage from Uranium Ore", 4).getInt();

		// Refinery Recipes
		config.get(CONFIG_REFINERY_RECIPES_NAME, "grimpack.industry.cfg.refineryRecipes", new String[] { "grimpack:crude_oil>grimpack:fuel>0.25", "grimpack:super_crude_oil>grimpack:crude_oil>0.1", "log>grimpack:rubber>0.1", "log2>grimpack:rubber>0.1", "dirt>clay_ball>0.25", "rotten_flesh>leather>0.25", "poisonous_potato>potato>0.25", "spider_eye>nether_wart>0.25", "paper>painting>0.25", "egg>feather>0.25", "water_bucket>fish>0.25", "lava_bucket>fire_charge>0.25", "carrot_on_a_stick>lead>0.25", "gunpowder>blaze_powder>0.25", "vine>string>0.25", "ender_pearl>ender_eye>0.25" });
		config.addCustomCategoryComment(CONFIG_REFINERY_RECIPES_NAME, "Use this to add new refinery recipes. \nTo add a new recipe add a line then write out the [RawItemName] separated by a '>' then write out the [RefinedItemName]. For mod items make sure to add the modID with a colon ':' and the then the item name. \nExample: grimpack:chocolate_ball>grimpack:chocolate_bar");

		if (!config.getCategory(CONFIG_REFINERY_RECIPES_NAME).isEmpty()) {
			String[] recipes = config.getCategory(CONFIG_REFINERY_RECIPES_NAME).get("grimpack.industry.cfg.refineryRecipes").getStringList();
			this.loadMachineRecipes(recipes);
		}
		super.syncConfig();
	}

	@Override
	public List<IConfigElement> getConfigItems() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
		list.add(new DummyCategoryElement("industryGeneralCfg", "grimpack.industry.cfg.general", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_GENERAL_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("industryExtruderCfg", "grimpack.industry.cfg.extruder", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_EXTRUDER_NAME)).getChildElements()));
		list.add(new DummyCategoryElement("industryRefineryRecipesCfg", "grimpack.industry.cfg.refineryRecipesCat", new ConfigElement(GrimIndustry.INSTANCE.getConfig().getCategory(CONFIG_REFINERY_RECIPES_NAME)).getChildElements()));
		return list;
	}

	@Override
	public void readFromServer(PacketBuffer buffer) {
		useWorkbenchUpgrades = buffer.readBoolean();
	}

	@Override
	public void writeToClient(PacketBuffer buffer) {
		buffer.writeBoolean(useWorkbenchUpgrades);
	}

	public void loadMachineRecipes(String[] string) {
		// Clear to then repopulate
		MachineRecipes.INSTANCE.getRefineryList().clear();

		List<Recipe> recipes = ConfigUtils.loadConfigurableRecipes(string, true);

		for (Recipe recipe : recipes) {
			MachineRecipes.INSTANCE.addRefineryRecipe(recipe.getInput(), recipe.getOutput(), recipe.getExperience());
		}
	}
}
