package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualArmor;
import com.grim3212.mc.pack.core.item.ItemManualAxe;
import com.grim3212.mc.pack.core.item.ItemManualHoe;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.item.ItemManualPickaxe;
import com.grim3212.mc.pack.core.item.ItemManualSpade;
import com.grim3212.mc.pack.core.item.ItemManualSword;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.tools.blocks.ToolsBlocks;
import com.grim3212.mc.pack.tools.config.ToolsConfig;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket.BucketType;
import com.grim3212.mc.pack.tools.util.DispenseBehaviors;
import com.grim3212.mc.pack.tools.util.EnumSpearType;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ToolsItems {

	public static final ToolMaterial blackdiamond = Utils.addToolMaterial(ToolsConfig.blackDiamondToolMaterial);
	public static final ToolMaterial obsidianToolMaterial = Utils.addToolMaterial(ToolsConfig.obsidianToolMaterial);
	public static final ArmorMaterial masks = Utils.addArmorMaterial(GrimPack.modID + ":masks", SoundEvents.BLOCK_CLOTH_PLACE, ToolsConfig.maskArmorMaterial);
	public static final ArmorMaterial blackarmor = Utils.addArmorMaterial(GrimPack.modID + ":blackarmor", SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, ToolsConfig.blackDiamondArmorMaterial);

	public static final Item backpack = new ItemBackpack();
	public static final Item pellet_bag = new ItemPelletBag();
	public static final Item portable_workbench = new ItemPortableWorkbench("portable_workbench");
	public static final Item tomahawk = new ItemTomahawk();
	public static final Item grip = (new ItemManualPage("grip", "tools:ballistic.grip")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item throwing_knife = new ItemKnife();
	public static final Item loaded_knife = new ItemBallisticKnife(true, false);
	public static final Item unloaded_knife = new ItemBallisticKnife(false, false);
	public static final Item ammo_part = new ItemBallisticKnife(false, true);
	public static final Item button_part = (new ItemManualPage("button_part", "tools:ballistic.part3")).setMaxStackSize(1).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item spring_part = (new ItemManualPage("spring_part", "tools:ballistic.part2")).setMaxStackSize(1).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond = (new ItemManualPage("black_diamond", "tools:black.cookOre")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_pickaxe = (new ItemManualPickaxe("black_diamond_pickaxe", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_shovel = (new ItemManualSpade("black_diamond_shovel", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_axe = (new ItemManualAxe("black_diamond_axe", blackdiamond, 8.0f, -2.8f, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_hoe = (new ItemManualHoe("black_diamond_hoe", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_sword = (new ItemManualSword("black_diamond_sword", blackdiamond, "tools:tools.tools")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_helmet = (new ItemManualArmor("black_diamond_helmet", blackarmor, 4, EntityEquipmentSlot.HEAD, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_chestplate = (new ItemManualArmor("black_diamond_chestplate", blackarmor, 4, EntityEquipmentSlot.CHEST, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_leggings = (new ItemManualArmor("black_diamond_leggings", blackarmor, 4, EntityEquipmentSlot.LEGS, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item black_diamond_boots = (new ItemManualArmor("black_diamond_boots", blackarmor, 4, EntityEquipmentSlot.FEET, "tools:tools.armor")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item wooden_bucket = new ItemBetterBucket(1, 0, 1000f, new ItemStack(Items.STICK, 3), BucketType.wood);
	public static final Item stone_bucket = new ItemBetterBucket(1, 0, new ItemStack(Blocks.COBBLESTONE, 3), BucketType.stone);
	public static final Item golden_bucket = new ItemBetterBucket(4, 0, BucketType.gold);
	public static final Item diamond_bucket = new ItemBetterBucket(16, 1, BucketType.diamond);
	public static final Item obsidian_bucket = new ItemBetterBucket(32, 2, true, BucketType.obsidian);
	public static final Item wooden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) wooden_bucket);
	public static final Item stone_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) stone_bucket);
	public static final Item golden_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) golden_bucket);
	public static final Item diamond_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) diamond_bucket);
	public static final Item obsidian_milk_bucket = new ItemBetterMilkBucket((ItemBetterBucket) obsidian_bucket);
	public static final Item iron_chisel = new ItemChisel(1);
	public static final Item iron_ore_item = (new ItemManualPage("iron_ore_item", "tools:chisels.ore")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item gold_ore_item = (new ItemManualPage("gold_ore_item", "tools:chisels.ore")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item diamond_chisel = new ItemChisel(4);
	public static final Item extinguisher = new ItemExtinguisher();
	public static final Item wood_hammer = new ItemHammer(ToolMaterial.WOOD);
	public static final Item stone_hammer = new ItemHammer(ToolMaterial.STONE);
	public static final Item gold_hammer = new ItemHammer(ToolMaterial.GOLD);
	public static final Item iron_hammer = new ItemHammer(ToolMaterial.IRON);
	public static final Item diamond_hammer = new ItemHammer(ToolMaterial.DIAMOND);
	public static final Item machete_gold = new ItemMachete("machete_gold", ToolMaterial.GOLD);
	public static final Item machete_iron = new ItemMachete("machete_iron", ToolMaterial.IRON);
	public static final Item machete_diamond = new ItemMachete("machete_diamond", ToolMaterial.DIAMOND);
	public static final Item machete_stone = new ItemMachete("machete_stone", ToolMaterial.STONE);
	public static final Item machete_wood = new ItemMachete("machete_wood", ToolMaterial.WOOD);
	public static final Item machete_slime = new ItemMachete("machete_slime", ToolMaterial.IRON);
	public static final Item building_wand = new ItemBuildingWand(false);
	public static final Item breaking_wand = new ItemBreakingWand(false);
	public static final Item mining_wand = new ItemMiningWand(false);
	public static final Item reinforced_building_wand = new ItemBuildingWand(true);
	public static final Item reinforced_breaking_wand = new ItemBreakingWand(true);
	public static final Item reinforced_mining_wand = new ItemMiningWand(true);
	public static final Item diamond_multi_tool = new ItemMultiTool("diamond_multi_tool", ToolMaterial.DIAMOND);
	public static final Item wooden_multi_tool = new ItemMultiTool("wooden_multi_tool", ToolMaterial.WOOD);
	public static final Item stone_multi_tool = new ItemMultiTool("stone_multi_tool", ToolMaterial.STONE);
	public static final Item iron_multi_tool = new ItemMultiTool("iron_multi_tool", ToolMaterial.IRON);
	public static final Item golden_multi_tool = new ItemMultiTool("golden_multi_tool", ToolMaterial.GOLD);
	public static final Item obsidian_multi_tool = new ItemMultiTool("obsidian_multi_tool", obsidianToolMaterial);
	public static final Item black_diamond_multi_tool = new ItemMultiTool("black_diamond_multi_tool", blackdiamond);
	public static final Item steel_multi_tool = new ItemMultiTool("steel_multi_tool", IndustryItems.steel);
	public static final Item pokeball = new ItemPokeball();
	public static final Item powerstaff = new ItemPowerStaff();
	public static final Item energy_canister = (new ItemManualPage("energy_canister", "tools:raygun.canisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item element_115 = (new ItemManualPage("element_115", "tools:raygun.element")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item empty_energy_canister = (new ItemManualPage("empty_energy_canister", "tools:raygun.canisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item ray_gun = new ItemRayg();
	public static final Item advanced_empty_energy_canister = (new ItemManualPage("advanced_empty_energy_canister", "tools:raygun.advCanisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item advanced_energy_canister = (new ItemManualPage("advanced_energy_canister", "tools:raygun.advCanisters")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item advanced_ray_gun = new ItemAdvRayg();
	public static final Item dark_iron_ingot = (new ItemManualPage("dark_iron_ingot", "tools:raygun.darkIron")).setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	public static final Item sling_shot = new ItemSlingshot();
	public static final Item sling_pellet = new ItemSlingPellet();
	public static final Item spear = new ItemSpear(EnumSpearType.STONE);
	public static final Item iron_spear = new ItemSpear(EnumSpearType.IRON);
	public static final Item diamond_spear = new ItemSpear(EnumSpearType.DIAMOND);
	public static final Item explosive_spear = new ItemSpear(EnumSpearType.EXPLOSIVE);
	public static final Item fire_spear = new ItemSpear(EnumSpearType.FIRE);
	public static final Item slime_spear = new ItemSpear(EnumSpearType.SLIME);
	public static final Item light_spear = new ItemSpear(EnumSpearType.LIGHT);
	public static final Item lightning_spear = new ItemSpear(EnumSpearType.LIGHTNING);
	public static final Item ultimate_fist = new ItemUltimateFist();
	public static final Item mask = new ItemMaskArmor(masks, 3);
	public static final Item boomerang = new ItemBoomerang("boomerang");
	public static final Item diamond_boomerang = new ItemDiamondBoomerang();
	public static final Item grenade_launcher = new ItemGrenadeLauncher();
	public static final Item grenade = (new ItemManualPage("grenade", "tools:grenade.grenade")).setFull3D().setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (ToolsConfig.subpartBoomerangs) {
			r.register(boomerang);
			r.register(diamond_boomerang);
		}

		if (ToolsConfig.subpartBackpacks)
			r.register(backpack);

		if (ToolsConfig.subpartPortableWorkbench)
			r.register(portable_workbench);

		if (ToolsConfig.subpartBlackDiamond) {
			r.register(black_diamond);
			r.register(black_diamond_boots);
			r.register(black_diamond_leggings);
			r.register(black_diamond_chestplate);
			r.register(black_diamond_helmet);
			r.register(black_diamond_sword);
			r.register(black_diamond_hoe);
			r.register(black_diamond_axe);
			r.register(black_diamond_shovel);
			r.register(black_diamond_pickaxe);
		}

		if (ToolsConfig.subpartBuckets) {
			r.register(wooden_bucket);
			r.register(wooden_milk_bucket);
			r.register(stone_bucket);
			r.register(stone_milk_bucket);
			r.register(golden_bucket);
			r.register(golden_milk_bucket);
			r.register(diamond_bucket);
			r.register(diamond_milk_bucket);
			r.register(obsidian_bucket);
			r.register(obsidian_milk_bucket);
		}

		if (ToolsConfig.subpartKnives) {
			r.register(throwing_knife);
			r.register(tomahawk);
			r.register(grip);
			r.register(loaded_knife);
			r.register(unloaded_knife);
			r.register(ammo_part);
			r.register(button_part);
			r.register(spring_part);
		}

		if (ToolsConfig.subpartChisel) {
			r.register(diamond_chisel);
			r.register(gold_ore_item);
			r.register(iron_ore_item);
			r.register(iron_chisel);
		}

		if (ToolsConfig.subpartExtinguisher)
			r.register(extinguisher);

		if(ToolsConfig.subpartGrenadeLauncher) {
			r.register(grenade);
			r.register(grenade_launcher);
		}
		
		if (ToolsConfig.subpartHammers) {
			r.register(wood_hammer);
			r.register(stone_hammer);
			r.register(gold_hammer);
			r.register(iron_hammer);
			r.register(diamond_hammer);
		}

		if (ToolsConfig.subpartMachetes) {
			r.register(machete_gold);
			r.register(machete_iron);
			r.register(machete_diamond);
			r.register(machete_stone);
			r.register(machete_wood);
			r.register(machete_slime);
		}

		if (ToolsConfig.subpartWands) {
			r.register(building_wand);
			r.register(breaking_wand);
			r.register(mining_wand);
			r.register(reinforced_building_wand);
			r.register(reinforced_breaking_wand);
			r.register(reinforced_mining_wand);
		}

		if (ToolsConfig.subpartPokeball)
			r.register(pokeball);

		if (ToolsConfig.subpartPowerstaff)
			r.register(powerstaff);

		if (ToolsConfig.subpartRayGuns) {
			r.register(energy_canister);
			r.register(element_115);
			r.register(empty_energy_canister);
			r.register(ray_gun);
			r.register(advanced_empty_energy_canister);
			r.register(advanced_energy_canister);
			r.register(advanced_ray_gun);
			r.register(dark_iron_ingot);
		}

		if (ToolsConfig.subpartSlingshots) {
			r.register(sling_shot);
			r.register(sling_pellet);
			r.register(pellet_bag);
		}

		if (ToolsConfig.subpartSpears) {
			r.register(spear);
			r.register(iron_spear);
			r.register(diamond_spear);
			r.register(explosive_spear);
			r.register(fire_spear);
			r.register(slime_spear);
			r.register(light_spear);
			r.register(lightning_spear);
		}

		if (ToolsConfig.subpartUltimateFist)
			r.register(ultimate_fist);

		if (ToolsConfig.subpartMasks)
			r.register(mask);

		if (ToolsConfig.subpartMultiTools) {
			r.register(diamond_multi_tool);
			r.register(wooden_multi_tool);
			r.register(stone_multi_tool);
			r.register(iron_multi_tool);
			r.register(golden_multi_tool);
			r.register(obsidian_multi_tool);
			r.register(black_diamond_multi_tool);
			if (CoreConfig.useIndustry && IndustryConfig.subpartSteel)
				r.register(steel_multi_tool);
		}

		// Set repair items
		if (ToolsConfig.subpartMultiTools)
			obsidianToolMaterial.setRepairItem(new ItemStack(Blocks.OBSIDIAN));

		if (ToolsConfig.subpartBlackDiamond) {
			blackdiamond.setRepairItem(new ItemStack(black_diamond));
			blackarmor.setRepairItem(new ItemStack(black_diamond));
		}

		if (ToolsConfig.subpartMasks)
			masks.setRepairItem(new ItemStack(Items.PAPER));

		// Register all of the dispenser behaviors
		DispenseBehaviors.register();

		initOreDict();
	}

	private void initOreDict() {
		if (ToolsConfig.subpartBuckets) {
			OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
			OreDictionary.registerOre("bucketMilk", wooden_milk_bucket);
			OreDictionary.registerOre("bucketMilk", stone_milk_bucket);
			OreDictionary.registerOre("bucketMilk", golden_milk_bucket);
			OreDictionary.registerOre("bucketMilk", diamond_milk_bucket);
			OreDictionary.registerOre("bucketMilk", obsidian_milk_bucket);
		}

		if (ToolsConfig.subpartBlackDiamond)
			OreDictionary.registerOre("gemBlackDiamond", black_diamond);

		if (ToolsConfig.subpartChisel) {
			OreDictionary.registerOre("oreIron", iron_ore_item);
			OreDictionary.registerOre("oreGold", gold_ore_item);
		}

		if (ToolsConfig.subpartRayGuns) {
			OreDictionary.registerOre("ingotDarkIron", dark_iron_ingot);
			OreDictionary.registerOre("element115", element_115);
			OreDictionary.registerOre("canEnergy", energy_canister);
			OreDictionary.registerOre("canAdvancedEnergy", advanced_energy_canister);
		}
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		if (ToolsConfig.subpartChisel) {
			GameRegistry.addSmelting(iron_ore_item, new ItemStack(Items.IRON_INGOT), 0.4F);
			GameRegistry.addSmelting(gold_ore_item, new ItemStack(Items.GOLD_INGOT), 1F);
		}

		if (ToolsConfig.subpartBlackDiamond)
			GameRegistry.addSmelting(ToolsBlocks.black_diamond_ore, new ItemStack(black_diamond, 1), 1.0F);
	}
}