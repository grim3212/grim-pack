package com.grim3212.mc.tools;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.manual.pages.PageImageText;
import com.grim3212.mc.core.manual.pages.PageInfo;
import com.grim3212.mc.core.network.PacketDispatcher;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.tools.blocks.ToolsBlocks;
import com.grim3212.mc.tools.client.gui.ToolsGuiHandler;
import com.grim3212.mc.tools.config.ToolsConfig;
import com.grim3212.mc.tools.entity.ToolsEntities;
import com.grim3212.mc.tools.event.MilkingEvent;
import com.grim3212.mc.tools.items.ToolsItems;
import com.grim3212.mc.tools.network.MessageSwitchModes;
import com.grim3212.mc.tools.network.MessageWandKeys;
import com.grim3212.mc.tools.world.ToolsGenerate;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimTools.modID, name = GrimTools.modName, version = GrimTools.modVersion, dependencies = "required-after:grimcore")
public class GrimTools extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.tools.ToolsClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimTools.modID)
	public static GrimTools INSTANCE;

	public static final String modID = "grimtools";
	public static final String modName = "Grim Tools";
	public static final String modVersion = "1.0.0";

	public GrimTools() {
		super(GrimTools.modID, GrimTools.modName, GrimTools.modVersion);
		addItem(new ToolsItems());
		addItem(new ToolsBlocks());
		addEntity(new ToolsEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Tools adds in a bunch of tools and weapons.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-tools/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new ToolsGuiHandler());
		MinecraftForge.EVENT_BUS.register(new MilkingEvent());
		PacketDispatcher.registerMessage(MessageWandKeys.class);
		PacketDispatcher.registerMessage(MessageSwitchModes.class);

		GameRegistry.registerWorldGenerator(new ToolsGenerate(), 25);

		proxy.registerModels();
	}

	@Override
	protected Item getCreativeTabIcon() {
		return ToolsItems.backpack;
	}

	@Override
	protected GrimConfig setConfig() {
		return new ToolsConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("backpacks", modSection).addSubSectionPages(new PageCrafting("backpacks", new ItemStack(ToolsItems.backpack)), new PageImageText("colors", "colorsImage.png"));
		ManualRegistry.addSection("portable", modSection).addSubSectionPages(new PageCrafting("portableworkbench", new ItemStack(ToolsItems.portable_workbench)));
		ManualRegistry.addSection("parts", modSection).addSubSectionPages(new PageCrafting("part1", new ItemStack(ToolsItems.casing_part)), new PageCrafting("part2", new ItemStack(ToolsItems.spring_part)), new PageCrafting("part3", new ItemStack(ToolsItems.button_part)), new PageCrafting("part4", new ItemStack(ToolsItems.rod_part)));
		ManualRegistry.addSection("ballistic", modSection).addSubSectionPages(new PageCrafting("ballistic", new ItemStack(ToolsItems.unloaded_knife)), new PageCrafting("knives", new ItemStack(ToolsItems.ammo_part)));
		ManualRegistry.addSection("black", modSection).addSubSectionPages(new PageImageText("blackore", "blackore.png"), new PageFurnace("cookOre", new ItemStack(ToolsBlocks.black_diamond_ore)), new PageCrafting("craftblack", ToolsBlocks.black, 25));
		ManualRegistry.addSection("tools", modSection).addSubSectionPages(new PageCrafting("tools", ToolsItems.blackTools, 18));
		ManualRegistry.addSection("armor", modSection).addSubSectionPages(new PageCrafting("armor", ToolsItems.blackArmor, 18));
		ManualRegistry.addSection("wood", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.wooden_bucket)));
		ManualRegistry.addSection("stone", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.stone_bucket)));
		ManualRegistry.addSection("gold", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.golden_bucket)));
		ManualRegistry.addSection("diamond", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.diamond_bucket)));
		ManualRegistry.addSection("obsidian", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.obsidian_bucket)));
		ManualRegistry.addSection("milk", modSection).addSubSectionPages(new PageCrafting("recipe", RecipeHelper.getQuickIRecipeForItemStack(new ItemStack(Items.cake))));
		ManualRegistry.addSection("knife", modSection).addSubSectionPages(new PageCrafting("knife", new ItemStack(ToolsItems.throwing_knife)));
		ManualRegistry.addSection("tomahawk", modSection).addSubSectionPages(new PageCrafting("tomahawk", new ItemStack(ToolsItems.tomahawk)));
		ManualRegistry.addSection("grip", modSection).addSubSectionPages(new PageCrafting("grip", new ItemStack(ToolsItems.grip)));
		ManualRegistry.addSection("chisels", modSection).addSubSectionPages(new PageCrafting("chisels", ToolsItems.chisels, 25));
		ManualRegistry.addSection("ores", modSection).addSubSectionPages(new PageFurnace("ore", new ItemStack[] { new ItemStack(ToolsItems.gold_ore_item), new ItemStack(ToolsItems.iron_ore_item) }, 25));
		ManualRegistry.addSection("extinguisher", modSection).addSubSectionPages(new PageCrafting("extinguisher", ToolsItems.extinguisherRecipe), new PageCrafting("refill", ToolsItems.extinguisherRefillRecipe));
		ManualRegistry.addSection("hammers", modSection).addSubSectionPages(new PageCrafting("recipes", ToolsItems.hammers, 20));
		ManualRegistry.addSection("machetes", modSection).addSubSectionPages(new PageCrafting("base", ToolsItems.machetes, 20), new PageCrafting("slime", new ItemStack(ToolsItems.machete_slime)));
		ManualRegistry.addSection("info", modSection).addSubSectionPages(new PageInfo("info"));
		ManualRegistry.addSection("regular", modSection).addSubSectionPages(new PageCrafting("recipes", ToolsItems.regular, 20));
		ManualRegistry.addSection("reinforced", modSection).addSubSectionPages(new PageCrafting("recipes", ToolsItems.reinforced, 20));
		ManualRegistry.addSection("multi", modSection).addSubSectionPages(new PageCrafting("tools", ToolsItems.tools, 20));
		ManualRegistry.addSection("pokeball", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.pokeball)));
		ManualRegistry.addSection("staff", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(ToolsItems.powerstaff)));
		ManualRegistry.addSection("raygun", modSection).addSubSectionPages(new PageImageText("element", "element115.png"), new PageCrafting("canisters", ToolsItems.basicCanisters, 25), new PageCrafting("raygun", new ItemStack(ToolsItems.ray_gun)));
		ManualRegistry.addSection("advraygun", modSection).addSubSectionPages(new PageCrafting("darkIron", new ItemStack(ToolsItems.dark_iron_ingot)), new PageCrafting("canisters", ToolsItems.advCanisters, 25), new PageCrafting("advraygun", new ItemStack(ToolsItems.advanced_ray_gun)));
		ManualRegistry.addSection("sling", modSection).addSubSectionPages(new PageCrafting("pellets", ToolsItems.pellets, 20), new PageCrafting("slingshot", new ItemStack(ToolsItems.sling_shot)));
		ManualRegistry.addSection("basic", modSection).addSubSectionPages(new PageCrafting("recipes", ToolsItems.basics, 20));
		ManualRegistry.addSection("special", modSection).addSubSectionPages(new PageCrafting("recipes", ToolsItems.specials, 20));
		ManualRegistry.addSection("ultimate", modSection).addSubSectionPages(new PageCrafting("fist", new ItemStack(ToolsItems.ultimate_fist)));
		ManualRegistry.addSection("masks", modSection).addSubSectionPages(new PageCrafting("empty", ToolsItems.emptyRecipe), new PageCrafting("mobs", ToolsItems.mobs, 15));
	}
}