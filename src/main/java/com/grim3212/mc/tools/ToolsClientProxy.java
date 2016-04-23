package com.grim3212.mc.tools;

import com.grim3212.mc.core.client.RenderHelper;
import com.grim3212.mc.core.proxy.ClientProxy;
import com.grim3212.mc.tools.blocks.ToolsBlocks;
import com.grim3212.mc.tools.client.KeyBindHelper;
import com.grim3212.mc.tools.client.entity.RenderBallisticKnife.BallisticKnifeFactory;
import com.grim3212.mc.tools.client.entity.RenderBlockPushPullFactory;
import com.grim3212.mc.tools.client.entity.RenderKnife.KnifeFactory;
import com.grim3212.mc.tools.client.entity.RenderThrowableFactory;
import com.grim3212.mc.tools.client.entity.RenderRayGun.RenderRayGunFactory;
import com.grim3212.mc.tools.client.entity.RenderTomahawk.TomahawkFactory;
import com.grim3212.mc.tools.client.model.BetterBucketModel;
import com.grim3212.mc.tools.entity.EntityAdvRayw;
import com.grim3212.mc.tools.entity.EntityBallisticKnife;
import com.grim3212.mc.tools.entity.EntityBlockPushPull;
import com.grim3212.mc.tools.entity.EntityKnife;
import com.grim3212.mc.tools.entity.EntityPokeball;
import com.grim3212.mc.tools.entity.EntityRayw;
import com.grim3212.mc.tools.entity.EntitySlingpellet;
import com.grim3212.mc.tools.entity.EntityTomahawk;
import com.grim3212.mc.tools.items.ToolsItems;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ToolsClientProxy extends ClientProxy {

	@Override
	public void registerModels() {
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
		RenderingRegistry.registerEntityRenderingHandler(EntityBallisticKnife.class, new BallisticKnifeFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTomahawk.class, new TomahawkFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new KnifeFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPokeball.class, new RenderThrowableFactory(ToolsItems.pokeball));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingpellet.class, new RenderThrowableFactory(ToolsItems.sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockPushPull.class, new RenderBlockPushPullFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimTools.modID, "textures/entities/sonicw.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityAdvRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimTools.modID, "textures/entities/sonicAdv.png")));

		// Key bindings
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
	}

	public void setBucketModelDefinition(Item item) {
		final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(GrimTools.modID, item.getUnlocalizedName().substring(5)), "inventory");

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return LOCATION;
			}
		});
		ModelBakery.registerItemVariants(item, LOCATION);
	}
}
