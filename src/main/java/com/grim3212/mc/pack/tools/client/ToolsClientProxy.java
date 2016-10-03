package com.grim3212.mc.pack.tools.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.RenderHelper;
import com.grim3212.mc.pack.core.client.entity.RenderProjectile.RenderProjectileFactory;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.client.entity.RenderBlockPushPullFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderBoomerang.RenderBoomerangFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderRayGun.RenderRayGunFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderThrowableFactory;
import com.grim3212.mc.pack.tools.client.model.BetterBucketModel;
import com.grim3212.mc.pack.tools.entity.EntityAdvRayw;
import com.grim3212.mc.pack.tools.entity.EntityBallisticKnife;
import com.grim3212.mc.pack.tools.entity.EntityBlockPushPull;
import com.grim3212.mc.pack.tools.entity.EntityBoomerang;
import com.grim3212.mc.pack.tools.entity.EntityDiamondBoomerang;
import com.grim3212.mc.pack.tools.entity.EntityKnife;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;
import com.grim3212.mc.pack.tools.entity.EntityRayw;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.pack.tools.entity.EntitySlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityIronSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityExplosiveSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityFireSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityLightSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntityNetherrackSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSlingpellet;
import com.grim3212.mc.pack.tools.entity.EntitySpear;
import com.grim3212.mc.pack.tools.entity.EntityTomahawk;
import com.grim3212.mc.pack.tools.items.ItemBackpack;
import com.grim3212.mc.pack.tools.items.ItemMaskArmor;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ToolsClientProxy extends ClientProxy {

	@Override
	public void preInit() {
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
		RenderHelper.renderItem(ToolsItems.iron_sling_shot);
		RenderHelper.renderItem(ToolsItems.black_diamond_sling_shot);
		RenderHelper.renderItem(ToolsItems.sling_pellet);
		RenderHelper.renderItem(ToolsItems.iron_sling_pellet);
		RenderHelper.renderItem(ToolsItems.netherrack_sling_pellet);
		RenderHelper.renderItem(ToolsItems.fire_sling_pellet);
		RenderHelper.renderItem(ToolsItems.explosive_sling_pellet);
		RenderHelper.renderItem(ToolsItems.light_sling_pellet);
		RenderHelper.renderItem(ToolsItems.slime_sling_pellet);
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

		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockPushPull.class, new RenderBlockPushPullFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPokeball.class, new RenderThrowableFactory(ToolsItems.pokeball));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingpellet.class, new RenderThrowableFactory(ToolsItems.sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityIronSlingpellet.class, new RenderThrowableFactory(ToolsItems.iron_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityExplosiveSlingpellet.class, new RenderThrowableFactory(ToolsItems.explosive_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireSlingpellet.class, new RenderThrowableFactory(ToolsItems.fire_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityLightSlingpellet.class, new RenderThrowableFactory(ToolsItems.light_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityNetherrackSlingpellet.class, new RenderThrowableFactory(ToolsItems.netherrack_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSlingpellet.class, new RenderThrowableFactory(ToolsItems.slime_sling_pellet));
		RenderingRegistry.registerEntityRenderingHandler(EntityRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimPack.modID, "textures/entities/sonicw.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityAdvRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimPack.modID, "textures/entities/sonicAdv.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSpear.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityBallisticKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/ballistic_knife.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityTomahawk.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/tomahawk.png"), true));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/throwing_knife.png"), true));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerangFactory(new ResourceLocation(GrimPack.modID, "textures/items/boomerang.png"), "grimpack:items/boomerang"));
		RenderingRegistry.registerEntityRenderingHandler(EntityDiamondBoomerang.class, new RenderBoomerangFactory(new ResourceLocation(GrimPack.modID, "textures/items/diamond_boomerang.png"), "grimpack:items/diamond_boomerang"));

		// Key bindings
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
	}

	private void setBucketModelDefinition(Item item) {
		final ModelResourceLocation LOCATION = new ModelResourceLocation(new ResourceLocation(GrimPack.modID, item.getUnlocalizedName().substring(5)), "inventory");

		ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return LOCATION;
			}
		});
		ModelBakery.registerItemVariants(item, LOCATION);
	}

	@Override
	public void initColors() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (tintIndex == 1)
					return Integer.parseInt(ItemBackpack.colorNumbers[15], 16);
				else {
					int packColor = ItemBackpack.getColor(stack);

					if (packColor < 0) {
						return Integer.parseInt("C65C35", 16);
					} else {
						return Integer.parseInt(ItemBackpack.colorNumbers[packColor], 16);
					}
				}
			}
		}, ToolsItems.backpack);
	}
}
