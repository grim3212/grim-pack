package com.grim3212.mc.tools.client;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.client.entity.RenderProjectile.RenderProjectileFactory;
import com.grim3212.mc.core.manual.ManualRegistry;
import com.grim3212.mc.core.manual.ModSection;
import com.grim3212.mc.core.manual.pages.PageCrafting;
import com.grim3212.mc.core.manual.pages.PageFurnace;
import com.grim3212.mc.core.manual.pages.PageImageText;
import com.grim3212.mc.core.manual.pages.PageInfo;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.core.util.RecipeHelper;
import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.blocks.ToolsBlocks;
import com.grim3212.mc.tools.client.entity.RenderBlockPushPullFactory;
import com.grim3212.mc.tools.client.entity.RenderRayGun.RenderRayGunFactory;
import com.grim3212.mc.tools.client.entity.RenderThrowableFactory;
import com.grim3212.mc.tools.client.model.BetterBucketModel;
import com.grim3212.mc.tools.entity.EntityAdvRayw;
import com.grim3212.mc.tools.entity.EntityBallisticKnife;
import com.grim3212.mc.tools.entity.EntityBlockPushPull;
import com.grim3212.mc.tools.entity.EntityKnife;
import com.grim3212.mc.tools.entity.EntityPokeball;
import com.grim3212.mc.tools.entity.EntityRayw;
import com.grim3212.mc.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.tools.entity.EntitySlingpellet;
import com.grim3212.mc.tools.entity.EntitySpear;
import com.grim3212.mc.tools.entity.EntityTomahawk;
import com.grim3212.mc.tools.items.ItemMaskArmor;
import com.grim3212.mc.tools.items.ToolsItems;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ToolsClientProxy extends ClientProxy {

	@Override
	protected void registerModels() {
		// Register custom model
		ModelLoaderRegistry.registerLoader(BetterBucketModel.LoaderDynBucket.instance);

		// BLOCKS
		RenderHelper.renderBlock(ToolsBlocks.black_diamond_ore);
		RenderHelper.renderBlock(ToolsBlocks.black_diamond_block);
		RenderHelper.renderBlock(ToolsBlocks.element_115_ore);

		// ITEMS
		RenderHelper.renderItem(ToolsItems.backpack);
		RenderHelper.renderItem(ToolsItems.portable_workbench);
		RenderHelper.renderItem(ToolsItems.loaded_knife);
		RenderHelper.renderItem(ToolsItems.unloaded_knife);
		RenderHelper.renderItem(ToolsItems.ammo_part);
		RenderHelper.renderItem(ToolsItems.button_part);
		RenderHelper.renderItem(ToolsItems.spring_part);
		RenderHelper.renderItem(ToolsItems.casing_part);
		RenderHelper.renderItem(ToolsItems.rod_part);
		RenderHelper.renderItem(ToolsItems.black_diamond);
		RenderHelper.renderItem(ToolsItems.black_diamond_helmet);
		RenderHelper.renderItem(ToolsItems.black_diamond_chestplate);
		RenderHelper.renderItem(ToolsItems.black_diamond_leggings);
		RenderHelper.renderItem(ToolsItems.black_diamond_boots);
		RenderHelper.renderItem(ToolsItems.black_diamond_sword);
		RenderHelper.renderItem(ToolsItems.black_diamond_hoe);
		RenderHelper.renderItem(ToolsItems.black_diamond_axe);
		RenderHelper.renderItem(ToolsItems.black_diamond_shovel);
		RenderHelper.renderItem(ToolsItems.black_diamond_pickaxe);
		RenderHelper.renderItem(ToolsItems.tomahawk);
		RenderHelper.renderItem(ToolsItems.throwing_knife);
		RenderHelper.renderItem(ToolsItems.grip);
		RenderHelper.renderItem(ToolsItems.gold_ore_item);
		RenderHelper.renderItem(ToolsItems.iron_ore_item);
		RenderHelper.renderItem(ToolsItems.iron_chisel);
		RenderHelper.renderItem(ToolsItems.diamond_chisel);
		RenderHelper.renderItem(ToolsItems.extinguisher);
		RenderHelper.renderItem(ToolsItems.wood_hammer);
		RenderHelper.renderItem(ToolsItems.stone_hammer);
		RenderHelper.renderItem(ToolsItems.gold_hammer);
		RenderHelper.renderItem(ToolsItems.iron_hammer);
		RenderHelper.renderItem(ToolsItems.diamond_hammer);
		RenderHelper.renderItem(ToolsItems.machete_diamond);
		RenderHelper.renderItem(ToolsItems.machete_gold);
		RenderHelper.renderItem(ToolsItems.machete_iron);
		RenderHelper.renderItem(ToolsItems.machete_stone);
		RenderHelper.renderItem(ToolsItems.machete_wood);
		RenderHelper.renderItem(ToolsItems.machete_slime);
		RenderHelper.renderItem(ToolsItems.building_wand);
		RenderHelper.renderItem(ToolsItems.breaking_wand);
		RenderHelper.renderItem(ToolsItems.mining_wand);
		RenderHelper.renderItem(ToolsItems.reinforced_building_wand);
		RenderHelper.renderItem(ToolsItems.reinforced_breaking_wand);
		RenderHelper.renderItem(ToolsItems.reinforced_mining_wand);
		RenderHelper.renderItem(ToolsItems.diamond_multi_tool);
		RenderHelper.renderItem(ToolsItems.wooden_multi_tool);
		RenderHelper.renderItem(ToolsItems.stone_multi_tool);
		RenderHelper.renderItem(ToolsItems.iron_multi_tool);
		RenderHelper.renderItem(ToolsItems.golden_multi_tool);
		RenderHelper.renderItem(ToolsItems.obsidian_multi_tool);
		RenderHelper.renderItem(ToolsItems.pokeball);
		RenderHelper.renderVariantForge(ToolsItems.powerstaff, "push", "pull");
		RenderHelper.renderItem(ToolsItems.advanced_empty_energy_canister);
		RenderHelper.renderItem(ToolsItems.advanced_energy_canister);
		RenderHelper.renderItem(ToolsItems.advanced_ray_gun);
		RenderHelper.renderItem(ToolsItems.dark_iron_ingot);
		RenderHelper.renderItem(ToolsItems.element_115);
		RenderHelper.renderItem(ToolsItems.empty_energy_canister);
		RenderHelper.renderItem(ToolsItems.energy_canister);
		RenderHelper.renderItem(ToolsItems.ray_gun);
		RenderHelper.renderItem(ToolsItems.sling_shot);
		RenderHelper.renderItem(ToolsItems.sling_pellet);
		RenderHelper.renderItem(ToolsItems.spear);
		RenderHelper.renderItem(ToolsItems.iron_spear);
		RenderHelper.renderItem(ToolsItems.diamond_spear);
		RenderHelper.renderItem(ToolsItems.explosive_spear);
		RenderHelper.renderItem(ToolsItems.fire_spear);
		RenderHelper.renderItem(ToolsItems.slime_spear);
		RenderHelper.renderItem(ToolsItems.light_spear);
		RenderHelper.renderItem(ToolsItems.lightning_spear);
		RenderHelper.renderItem(ToolsItems.ultimate_fist);
		RenderHelper.renderVariantForge(ToolsItems.mask, ItemMaskArmor.types);

		setBucketModelDefinition(ToolsItems.wooden_bucket);
		setBucketModelDefinition(ToolsItems.stone_bucket);
		setBucketModelDefinition(ToolsItems.golden_bucket);
		setBucketModelDefinition(ToolsItems.diamond_bucket);
		setBucketModelDefinition(ToolsItems.obsidian_bucket);
		setBucketModelDefinition(ToolsItems.wooden_milk_bucket);
		setBucketModelDefinition(ToolsItems.stone_milk_bucket);
		setBucketModelDefinition(ToolsItems.golden_milk_bucket);
		setBucketModelDefinition(ToolsItems.diamond_milk_bucket);
		setBucketModelDefinition(ToolsItems.obsidian_milk_bucket);

		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockPushPull.class, new RenderBlockPushPullFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPokeball.class, new RenderThrowableFactory(ToolsItems.pokeball));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingpellet.class, new RenderThrowableFactory(ToolsItems.sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimTools.modID, "textures/entities/sonicw.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityAdvRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimTools.modID, "textures/entities/sonicAdv.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderProjectileFactory(new ResourceLocation(GrimTools.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSpear.class, new RenderProjectileFactory(new ResourceLocation(GrimTools.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityBallisticKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimTools.modID, "textures/entities/ballistic_knife.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityTomahawk.class, new RenderProjectileFactory(new ResourceLocation(GrimTools.modID, "textures/entities/tomahawk.png"), true));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimTools.modID, "textures/entities/throwing_knife.png"), true));

		// Key bindings
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
	}

	private void setBucketModelDefinition(Item item) {
		final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(GrimTools.modID, item.getUnlocalizedName().substring(5)), "inventory");

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return LOCATION;
			}
		});
		ModelBakery.registerItemVariants(item, LOCATION);
	}

	@Override
	protected void registerManual(ModSection modSection) {
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
