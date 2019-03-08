package com.grim3212.mc.pack.core.common;

import com.grim3212.mc.pack.core.block.BlockManualPage;
import com.grim3212.mc.pack.core.config.CoreConfig;
import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.item.ItemManualPage;
import com.grim3212.mc.pack.core.part.GrimItemGroups;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class CommonItems {

	// TODO: Look into only initializing blocks and items that should be
	// registered
	public static final Block aluminum_ore = (new BlockManualPage("aluminum_ore", Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0F, 5.0F), "core:aluminum.aluminum"));
	public static final Block steel_block = (new BlockManualPage("steel_block", Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(1.0F, 20.0F), "core:steel.steel_block"));

	public static final Item aluminum_ingot = (new ItemManualPage("aluminum_ingot", "core:aluminum.aluminum", GrimItemGroups.GRIM_CORE));
	public static final Item aluminum_can = (new ItemManualPage("aluminum_can", "core:aluminum.aluminum_can", GrimItemGroups.GRIM_CORE));
	public static final Item aluminum_shaft = (new ItemManualPage("aluminum_shaft", "core:aluminum.aluminum_shaft", GrimItemGroups.GRIM_CORE));
	public static final Item steel_shaft = (new ItemManualPage("steel_shaft", "core:steel.steel_shaft", GrimItemGroups.GRIM_CORE));
	public static final Item steel_ingot = (new ItemManualPage("steel_ingot", "core:steel.steel_ingot", GrimItemGroups.GRIM_CORE));
	public static final Item rubber = (new ItemManualPage("rubber", "core:common.rubber", GrimItemGroups.GRIM_CORE));
	public static final Item iron_stick = (new ItemManualPage("iron_stick", "core:common.iron_stick", GrimItemGroups.GRIM_CORE));
	public static final Item coal_dust = (new ItemManualPage("coal_dust", "core:common.coal_dust", GrimItemGroups.GRIM_CORE));
	public static final Item graphite = (new ItemManualPage("graphite", "core:common.graphite", GrimItemGroups.GRIM_CORE));
	public static final Item graphite_rod = (new ItemManualPage("graphite_rod", "core:common.graphite_rod", GrimItemGroups.GRIM_CORE));

	@SubscribeEvent
	public void registerBlocks(Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (CoreConfig.subpartAluminum.get())
			r.register(aluminum_ore);

		if (CoreConfig.subpartSteel.get())
			r.register(steel_block);
	}

	@SubscribeEvent
	public void registerItems(Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (CoreConfig.subpartAluminum.get()) {
			r.register(new ItemManualBlock(aluminum_ore).setRegistryName(aluminum_ore.getRegistryName()));

			r.register(aluminum_can);
			r.register(aluminum_ingot);
			r.register(aluminum_shaft);
		}

		if (CoreConfig.subpartGraphite.get()) {
			r.register(graphite_rod);
			r.register(graphite);
		}

		if (CoreConfig.subpartSteel.get()) {
			r.register(new ItemManualBlock(steel_block).setRegistryName(steel_block.getRegistryName()));

			r.register(steel_shaft);
			r.register(steel_ingot);
		}

		if (CoreConfig.subpartCoal.get())
			r.register(coal_dust);

		if (CoreConfig.subpartRubber.get())
			r.register(rubber);

		if (CoreConfig.subpartIron.get())
			r.register(iron_stick);
	}

	/*
	 * @SubscribeEvent public void registerRecipes(Register<IRecipe> evt) { if
	 * (CoreConfig.subpartGraphite) GameRegistry.addSmelting(Items.FLINT, new
	 * ItemStack(graphite), 0.35F);
	 * 
	 * if (CoreConfig.subpartAluminum) GameRegistry.addSmelting(aluminum_ore, new
	 * ItemStack(aluminum_ingot, 1), 0.45F); } private void initOreDict() { if
	 * (CoreConfig.subpartAluminum) { OreDictionary.registerOre("oreAluminum",
	 * aluminum_ore); OreDictionary.registerOre("ingotAluminum", aluminum_ingot);
	 * OreDictionary.registerOre("stickAluminum", aluminum_shaft);
	 * OreDictionary.registerOre("can", aluminum_can); }
	 * 
	 * if (CoreConfig.subpartGraphite) { OreDictionary.registerOre("stickGraphite",
	 * graphite_rod); OreDictionary.registerOre("graphite", graphite); }
	 * 
	 * if (CoreConfig.subpartSteel) { OreDictionary.registerOre("blockSteel",
	 * steel_block); OreDictionary.registerOre("stickSteel", steel_shaft);
	 * OreDictionary.registerOre("ingotSteel", steel_ingot); }
	 * 
	 * if (CoreConfig.subpartRubber) OreDictionary.registerOre("rubber", rubber);
	 * 
	 * if (CoreConfig.subpartCoal) OreDictionary.registerOre("dustCoal", coal_dust);
	 * 
	 * if (CoreConfig.subpartIron) OreDictionary.registerOre("stickIron",
	 * iron_stick); }
	 */

}