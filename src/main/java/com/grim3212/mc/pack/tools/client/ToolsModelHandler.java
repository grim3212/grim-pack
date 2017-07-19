package com.grim3212.mc.pack.tools.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.client.model.BetterBucketModel;
import com.grim3212.mc.pack.tools.items.ItemMaskArmor;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.util.EnumPelletType;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ToolsModelHandler {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent evt) {
		// Register custom model
		ModelLoaderRegistry.registerLoader(BetterBucketModel.LoaderDynBucket.instance);

		// BLOCKS
		RenderHelper.renderBlock(ToolsBlocks.black_diamond_ore);
		RenderHelper.renderBlock(ToolsBlocks.black_diamond_block);
		RenderHelper.renderBlock(ToolsBlocks.element_115_ore);

		// ITEMS
		RenderHelper.renderItem(ToolsItems.pellet_bag);
		RenderHelper.renderItem(ToolsItems.backpack);
		RenderHelper.renderItem(ToolsItems.portable_workbench);
		RenderHelper.renderItem(ToolsItems.loaded_knife);
		RenderHelper.renderItem(ToolsItems.unloaded_knife);
		RenderHelper.renderItem(ToolsItems.ammo_part);
		RenderHelper.renderItem(ToolsItems.button_part);
		RenderHelper.renderItem(ToolsItems.spring_part);
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
		RenderHelper.renderItem(ToolsItems.black_diamond_multi_tool);
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
		RenderHelper.renderVariantForge(ToolsItems.sling_pellet, EnumPelletType.names());
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
		RenderHelper.renderItem(ToolsItems.boomerang);
		RenderHelper.renderItem(ToolsItems.diamond_boomerang);

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

		if (CoreConfig.useIndustry) {
			RenderHelper.renderItem(ToolsItems.steel_multi_tool);
		}
	}

	private static void setBucketModelDefinition(Item item) {
		final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(GrimPack.modID, item.getUnlocalizedName().substring(5)), "inventory");

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return LOCATION;
			}
		});
		ModelBakery.registerItemVariants(item, LOCATION);
	}

}
