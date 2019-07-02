package com.grim3212.mc.pack.core.init;

import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.manual.ItemInstructionManual;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

public class CoreInit {

	@ObjectHolder(CoreNames.ALUMINUM_ORE)
	public static Block aluminum_ore;
	@ObjectHolder(CoreNames.STEEL_BLOCK)
	public static Block steel_block;

	@ObjectHolder(CoreNames.INSTRUCTION_MANUAL)
	public static Item instruction_manual;
	@ObjectHolder(CoreNames.ALUMINUM_INGOT)
	public static Item aluminum_ingot;
	@ObjectHolder(CoreNames.ALUMINUM_CAN)
	public static Item aluminum_can;
	@ObjectHolder(CoreNames.ALUMINUM_SHAFT)
	public static Item aluminum_shaft;
	@ObjectHolder(CoreNames.STEEL_SHAFT)
	public static Item steel_shaft;
	@ObjectHolder(CoreNames.STEEL_INGOT)
	public static Item steel_ingot;
	@ObjectHolder(CoreNames.RUBBER)
	public static Item rubber;
	@ObjectHolder(CoreNames.IRON_STICK)
	public static Item iron_stick;
	@ObjectHolder(CoreNames.COAL_DUST)
	public static Item coal_dust;
	@ObjectHolder(CoreNames.GRAPHITE)
	public static Item graphite;
	@ObjectHolder(CoreNames.GRAPHITE_ROD)
	public static Item graphite_rod;

	@SubscribeEvent
	public void registerBlocks(Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (CoreConfig.subpartAluminum.get())
			r.register(new BlockManualPage(CoreNames.ALUMINUM_ORE, Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0F, 5.0F), "core:aluminum.aluminum"));

		if (CoreConfig.subpartSteel.get())
			r.register(new BlockManualPage(CoreNames.STEEL_BLOCK, Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0F, 20.0F), "core:steel.steel_block"));
	}

	@SubscribeEvent
	public void registerItems(Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(new ItemInstructionManual());

		if (CoreConfig.subpartAluminum.get()) {
			r.register(new ItemManualBlock(aluminum_ore, new Item.Properties().group(GrimItemGroups.GRIM_CORE)).setRegistryName(aluminum_ore.getRegistryName()));

			r.register(new ItemManualPage(CoreNames.ALUMINUM_CAN, "core:aluminum.aluminum_can", GrimItemGroups.GRIM_CORE));
			r.register(new ItemManualPage(CoreNames.ALUMINUM_INGOT, "core:aluminum.aluminum", GrimItemGroups.GRIM_CORE));
			r.register(new ItemManualPage(CoreNames.ALUMINUM_SHAFT, "core:aluminum.aluminum_shaft", GrimItemGroups.GRIM_CORE));
		}

		if (CoreConfig.subpartGraphite.get()) {
			r.register(new ItemManualPage(CoreNames.GRAPHITE_ROD, "core:common.graphite_rod", GrimItemGroups.GRIM_CORE));
			r.register(new ItemManualPage(CoreNames.GRAPHITE, "core:common.graphite", GrimItemGroups.GRIM_CORE));
		}

		if (CoreConfig.subpartSteel.get()) {
			r.register(new ItemManualBlock(steel_block, new Item.Properties().group(GrimItemGroups.GRIM_CORE)).setRegistryName(steel_block.getRegistryName()));

			r.register(new ItemManualPage(CoreNames.STEEL_SHAFT, "core:steel.steel_shaft", GrimItemGroups.GRIM_CORE));
			r.register(new ItemManualPage(CoreNames.STEEL_INGOT, "core:steel.steel_ingot", GrimItemGroups.GRIM_CORE));
		}

		if (CoreConfig.subpartCoal.get())
			r.register(new ItemManualPage(CoreNames.COAL_DUST, "core:common.coal_dust", GrimItemGroups.GRIM_CORE));

		if (CoreConfig.subpartRubber.get())
			r.register(new ItemManualPage(CoreNames.RUBBER, "core:common.rubber", GrimItemGroups.GRIM_CORE));

		if (CoreConfig.subpartIron.get())
			r.register(new ItemManualPage(CoreNames.IRON_STICK, "core:common.iron_stick", GrimItemGroups.GRIM_CORE));
	}

}