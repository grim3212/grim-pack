package com.grim3212.mc.pack.core.common;

import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonItems {

	// TODO: Look into only initializing blocks and items that should be
	// registered
	public static final Block aluminum_ore = (new BlockManualPage("aluminum_ore", Material.ROCK, SoundType.STONE, "core:aluminum.aluminum")).setHardness(1.0F).setResistance(5.0F).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Block steel_block = (new BlockManualPage("steel_block", Material.IRON, SoundType.METAL, "core:steel.steel_block")).setHardness(1.0F).setResistance(20.0F).setCreativeTab(GrimCreativeTabs.GRIM_CORE);

	public static final Item aluminum_ingot = (new ItemManualPage("aluminum_ingot", "core:aluminum.aluminum")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item aluminum_can = (new ItemManualPage("aluminum_can", "core:aluminum.aluminum_can")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item aluminum_shaft = (new ItemManualPage("aluminum_shaft", "core:aluminum.aluminum_shaft")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item steel_shaft = (new ItemManualPage("steel_shaft", "core:steel.steel_shaft")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item steel_ingot = (new ItemManualPage("steel_ingot", "core:steel.steel_ingot")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item rubber = (new ItemManualPage("rubber", "core:common.rubber")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item iron_stick = (new ItemManualPage("iron_stick", "core:common.iron_stick")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item coal_dust = (new ItemManualPage("coal_dust", "core:common.coal_dust")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item graphite = (new ItemManualPage("graphite", "core:common.graphite")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);
	public static final Item graphite_rod = (new ItemManualPage("graphite_rod", "core:common.graphite_rod")).setCreativeTab(GrimCreativeTabs.GRIM_CORE);

	@SubscribeEvent
	public void registerBlocks(Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (CoreConfig.subpartAluminum)
			r.register(aluminum_ore);

		if (CoreConfig.subpartSteel)
			r.register(steel_block);
	}

	@SubscribeEvent
	public void registerItems(Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (CoreConfig.subpartAluminum) {
			r.register(new ItemManualBlock(aluminum_ore).setRegistryName(aluminum_ore.getRegistryName()));

			r.register(aluminum_can);
			r.register(aluminum_ingot);
			r.register(aluminum_shaft);
		}

		if (CoreConfig.subpartGraphite) {
			r.register(graphite_rod);
			r.register(graphite);
		}

		if (CoreConfig.subpartSteel) {
			r.register(new ItemManualBlock(steel_block).setRegistryName(steel_block.getRegistryName()));

			r.register(steel_shaft);
			r.register(steel_ingot);
		}

		if (CoreConfig.subpartCoal)
			r.register(coal_dust);

		if (CoreConfig.subpartRubber)
			r.register(rubber);

		if (CoreConfig.subpartIron)
			r.register(iron_stick);

		initOreDict();
	}

	@SubscribeEvent
	public void registerRecipes(Register<IRecipe> evt) {
		if (CoreConfig.subpartGraphite)
			GameRegistry.addSmelting(Items.FLINT, new ItemStack(graphite), 0.35F);

		if (CoreConfig.subpartAluminum)
			GameRegistry.addSmelting(aluminum_ore, new ItemStack(aluminum_ingot, 1), 0.45F);
	}

	private void initOreDict() {
		if (CoreConfig.subpartAluminum) {
			OreDictionary.registerOre("oreAluminum", aluminum_ore);
			OreDictionary.registerOre("ingotAluminum", aluminum_ingot);
			OreDictionary.registerOre("stickAluminum", aluminum_shaft);
			OreDictionary.registerOre("can", aluminum_can);
		}

		if (CoreConfig.subpartGraphite) {
			OreDictionary.registerOre("stickGraphite", graphite_rod);
			OreDictionary.registerOre("graphite", graphite);
		}

		if (CoreConfig.subpartSteel) {
			OreDictionary.registerOre("blockSteel", steel_block);
			OreDictionary.registerOre("stickSteel", steel_shaft);
			OreDictionary.registerOre("ingotSteel", steel_ingot);
		}

		if (CoreConfig.subpartRubber)
			OreDictionary.registerOre("rubber", rubber);

		if (CoreConfig.subpartCoal)
			OreDictionary.registerOre("dustCoal", coal_dust);

		if (CoreConfig.subpartIron)
			OreDictionary.registerOre("stickIron", iron_stick);
	}

}