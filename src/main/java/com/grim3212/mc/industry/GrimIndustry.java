package com.grim3212.mc.industry;

import com.grim3212.mc.core.config.GrimConfig;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.part.GrimPart;
import com.grim3212.mc.core.proxy.CommonProxy;
import com.grim3212.mc.industry.block.IndustryBlocks;
import com.grim3212.mc.industry.client.gui.IndustryGuiHandler;
import com.grim3212.mc.industry.config.IndustryConfig;
import com.grim3212.mc.industry.item.IndustryItems;
import com.grim3212.mc.industry.tile.IndustryTileEntities;
import com.grim3212.mc.industry.world.IndustryGenerate;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = GrimIndustry.modID, name = GrimIndustry.modName, version = GrimIndustry.modVersion, dependencies = "required-after:grimcore")
public class GrimIndustry extends GrimPart {

	@SidedProxy(clientSide = "com.grim3212.mc.industry.IndustryClientProxy", serverSide = COMMON_PROXY)
	public static CommonProxy proxy;

	@Instance(GrimIndustry.modID)
	public static GrimIndustry INSTANCE;

	public static final String modID = "grimindustry";
	public static final String modName = "Grim Industry";
	public static final String modVersion = "1.0.0";

	public GrimIndustry() {
		super(GrimIndustry.modID, GrimIndustry.modName, GrimIndustry.modVersion);
		addItem(new IndustryBlocks());
		addItem(new IndustryItems());
		addTileEntity(new IndustryTileEntities());
	}

	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		ModMetadata data = event.getModMetadata();
		data.description = "Grim Industry lets the player mess be a bit more technical.";
		data.url = "http://mods.grim3212.com/mc/" + "my-mods/grim-industry/";
		data.credits = "Thanks to the following authors. Leesgowest, LFalch, mattop101, Nandonalt.";

		GameRegistry.registerWorldGenerator(new IndustryGenerate(), 10);

		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new IndustryGuiHandler());

		proxy.registerModels();
	}

	@Override
	@EventHandler
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	protected Item getCreativeTabIcon() {
		return Item.getItemFromBlock(IndustryBlocks.togglerack);
	}

	@Override
	protected GrimConfig setConfig() {
		return new IndustryConfig();
	}

	@Override
	protected void setupManualPages(ModSection modSection) {
		ManualRegistry.addSection("toggle", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryBlocks.togglerack)));
		ManualRegistry.addSection("benches", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.workbenches, 25));
		ManualRegistry.addSection("ice", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryItems.ice, 25));
		ManualRegistry.addSection("elemental", modSection).addSubSectionPages(new PageCrafting("fire", new ItemStack(IndustryBlocks.fire_block)), new PageCrafting("water", new ItemStack(IndustryBlocks.water_block)), new PageCrafting("lava", new ItemStack(IndustryBlocks.lava_block)));
		ManualRegistry.addSection("spikes", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryBlocks.spike)));
		ManualRegistry.addSection("sensors", modSection).addSubSectionPages(new PageCrafting("recipes", IndustryBlocks.sensors, 20));
		ManualRegistry.addSection("attract", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.attracting, 25));
		ManualRegistry.addSection("repulse", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.repulsing, 25));
		ManualRegistry.addSection("gravitor", modSection).addSubSectionPages(new PageCrafting("recipe", IndustryBlocks.gravitoring, 25));
		ManualRegistry.addSection("boots", modSection).addSubSectionPages(new PageCrafting("recipe", new ItemStack(IndustryItems.gravity_boots)));
		ManualRegistry.addSection("refining", modSection).addSubSectionPages(new PageCrafting("uranium", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("armor", IndustryItems.armor, 20), new PageFurnace("uranium_smelt", new ItemStack(IndustryBlocks.uranium_ore)), new PageCrafting("refined_uranium", new ItemStack(IndustryItems.refined_uranium)), new PageCrafting("plutonium", new ItemStack(IndustryItems.plutonium_ingot)),
				new PageCrafting("refined_plutonium", new ItemStack(IndustryItems.refined_plutonium)), new PageCrafting("reactor_core", new ItemStack(IndustryItems.reactor_core)));
		ManualRegistry.addSection("reactor", modSection).addSubSectionPages(new PageFurnace("graphite", new ItemStack(Items.flint)), new PageCrafting("graphite_rod", new ItemStack(IndustryItems.graphite_rod)), new PageCrafting("reactor_case", new ItemStack(IndustryItems.reactor_core_case)), new PageCrafting("iron_parts", new ItemStack(IndustryItems.iron_parts)), new PageCrafting("reactor", new ItemStack(IndustryBlocks.reactor)));
		ManualRegistry.addSection("explosives", modSection).addSubSectionPages(new PageFurnace("aluminium", new ItemStack(Items.iron_ingot)), new PageCrafting("bomb_shell", new ItemStack(IndustryBlocks.bomb_shell)), new PageCrafting("c4", new ItemStack(IndustryBlocks.c4)), new PageCrafting("nuclear_bomb", new ItemStack(IndustryBlocks.nuclear_bomb)));
	}
}