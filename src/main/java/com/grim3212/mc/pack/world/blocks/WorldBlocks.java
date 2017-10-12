package com.grim3212.mc.pack.world.blocks;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.world.config.WorldConfig;
import com.grim3212.mc.pack.world.items.ItemFungus;
import com.grim3212.mc.pack.world.items.ItemRune;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class WorldBlocks {

	public static final Block rune = new BlockRune();
	public static final Block randomite = new BlockRandomite();
	public static final Block corruption_block = new BlockCorruption();
	public static final BlockGunpowderReed gunpowder_reed_block = new BlockGunpowderReed();
	public static final Block glowstone_seeds = new BlockGlowstoneSeed();
	public static final Block fungus_growing = new BlockFungusGrowing();
	public static final Block fungus_building = new BlockFungusBuilding();
	public static final Block fungus_layer_building = new BlockFungusLayer();
	public static final Block fungus_ore_building = new BlockFungusOre();
	public static final Block fungus_killing = new BlockFungusKilling();
	public static final Block fungus_maze = new BlockFungusMaze();

	@SubscribeEvent
	public void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		if (WorldConfig.subpartRandomite)
			r.register(randomite);

		if (WorldConfig.subpartCorruption)
			r.register(corruption_block);

		if (WorldConfig.subpartGunpowderReeds)
			r.register(gunpowder_reed_block);

		if (WorldConfig.subpartGlowstoneSeeds)
			r.register(glowstone_seeds);

		if (WorldConfig.subpartFungus) {
			r.register(fungus_growing);
			r.register(fungus_building);
			r.register(fungus_layer_building);
			r.register(fungus_ore_building);
			r.register(fungus_killing);
			r.register(fungus_maze);
		}

		if (WorldConfig.subpartRuins) {
			r.register(rune);
		}
	}

	@SubscribeEvent
	public void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		if (WorldConfig.subpartCorruption)
			r.register(new ItemManualBlock(corruption_block).setRegistryName(corruption_block.getRegistryName()));

		if (WorldConfig.subpartGlowstoneSeeds)
			r.register(new ItemManualBlock(glowstone_seeds).setRegistryName(glowstone_seeds.getRegistryName()));

		if (WorldConfig.subpartRandomite)
			r.register(new ItemManualBlock(randomite).setRegistryName(randomite.getRegistryName()));

		if (WorldConfig.subpartFungus) {
			r.register(new ItemFungus(fungus_growing).setRegistryName(fungus_growing.getRegistryName()));
			r.register(new ItemFungus(fungus_building).setRegistryName(fungus_building.getRegistryName()));
			r.register(new ItemFungus(fungus_ore_building).setRegistryName(fungus_ore_building.getRegistryName()));
			r.register(new ItemFungus(fungus_layer_building).setRegistryName(fungus_layer_building.getRegistryName()));
			r.register(new ItemFungus(fungus_killing).setRegistryName(fungus_killing.getRegistryName()));
			r.register(new ItemFungus(fungus_maze).setRegistryName(fungus_maze.getRegistryName()));
		}

		if (WorldConfig.subpartRuins) {
			r.register(new ItemRune(rune).setRegistryName(rune.getRegistryName()));
		}

		initOreDict();
	}

	private void initOreDict() {
		if (WorldConfig.subpartFungus) {
			OreDictionary.registerOre("blockFungus", fungus_ore_building);
			OreDictionary.registerOre("blockFungus", fungus_building);
			OreDictionary.registerOre("blockFungus", fungus_growing);
			OreDictionary.registerOre("blockFungus", fungus_killing);
			OreDictionary.registerOre("blockFungus", fungus_layer_building);
			OreDictionary.registerOre("blockFungus", fungus_maze);
		}

		if (WorldConfig.subpartRandomite)
			OreDictionary.registerOre("oreRandomite", randomite);
	}
}