package com.grim3212.mc.cuisine;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.manual.pages.PageImageText;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.part.IPartItems;
import com.grim3212.mc.cuisine.block.CuisineBlocks;
import com.grim3212.mc.cuisine.config.CuisineConfig;
import com.grim3212.mc.cuisine.events.DropEvent;
import com.grim3212.mc.cuisine.events.OnBonemealEvent;
import com.grim3212.mc.cuisine.item.CuisineItems;
import com.grim3212.mc.cuisine.world.CuisineGenerate;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimCuisine.modID, name = GrimCuisine.modName, version = GrimCuisine.modVersion, dependencies = "required-after:grimcore", guiFactory = "com.grim3212.mc.cuisine.config.ConfigGuiFactory")
public class GrimCuisine extends GrimPart {

	@Instance(GrimCuisine.modID)
	public static GrimCuisine INSTANCE;

	public static final String modID = "grimcuisine";
	public static final String modName = "Grim Cuisine";
	public static final String modVersion = "1.0.0";

	public GrimCuisine() {
		super(GrimCuisine.modID, GrimCuisine.modName, GrimCuisine.modVersion);
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Cuisine provides many different food sources and health regeneration options.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim3212-cuisine/";
		data.credits = "Thanks to the follwoing authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		MinecraftForge.EVENT_BUS.register(new DropEvent());
		MinecraftForge.EVENT_BUS.register(new OnBonemealEvent());
		GameRegistry.registerWorldGenerator(new CuisineGenerate(), 25);
	}

	@Override
	protected Item getCreativeTabIcon() {
		return CuisineItems.cheese;
	}

	@Override
	protected GrimConfig setConfig() {
		return new CuisineConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("carbon", modSection).addSubSectionPages(new PageCrafting("carbon", CuisineItems.carbon, 25));
		ManualRegistry.addSection("soda", modSection).addSubSectionPages(new PageCrafting("types", CuisineItems.sodas, 15));
		ManualRegistry.addSection("dragonfruit", modSection).addSubSectionPages(new PageImageText("dragonfruit", "dragonFruitPage.png"));
		ManualRegistry.addSection("food", modSection).addSubSectionPages(new PageCrafting("sweets", CuisineItems.food, 25));
		ManualRegistry.addSection("health", modSection).addSubSectionPages(new PageCrafting("recipes", CuisineItems.health, 25));
		ManualRegistry.addSection("info", modSection).addSubSectionPages(new PageImageText("info", "infoPage.png"), new PageCrafting("bowl", new ItemStack(CuisineItems.milk_bowl)));
		ManualRegistry.addSection("butter", modSection).addSubSectionPages(new PageCrafting("churn", new ItemStack(CuisineBlocks.butter_churn)));
		ManualRegistry.addSection("cheese", modSection).addSubSectionPages(new PageCrafting("maker", new ItemStack(CuisineBlocks.cheese_maker)), new PageCrafting("block", CuisineItems.cheeseRecipe, 25));
		ManualRegistry.addSection("cocoa", modSection).addSubSectionPages(new PageImageText("tree", "cocoaTreePage.png"), new PageCrafting("fruit", new ItemStack(CuisineItems.cocoa_dust)), new PageCrafting("dye", CuisineItems.cocoaRecipe));
		ManualRegistry.addSection("bowlchoc", modSection).addSubSectionPages(new PageCrafting("bowlmilk", new ItemStack(CuisineItems.milk_bowl)), new PageCrafting("bowlChoc", new ItemStack(CuisineItems.chocolate_bowl)), new PageFurnace("bowlChocHot", new ItemStack(CuisineItems.chocolate_bowl)), new PageCrafting("chocBall", new ItemStack(CuisineItems.chocolate_ball)), new PageCrafting("cake", CuisineBlocks.cakes, 25));
		ManualRegistry.addSection("choco", modSection).addSubSectionPages(new PageCrafting("mould", new ItemStack(CuisineBlocks.chocolate_bar_mould)), new PageCrafting("bars", CuisineItems.choc, 25), new PageCrafting("candy", CuisineItems.candy, 25));

	}

	@Override
	protected IPartItems[] setItemParts() {
		return new IPartItems[] { new CuisineBlocks(), new CuisineItems() };
	}
}