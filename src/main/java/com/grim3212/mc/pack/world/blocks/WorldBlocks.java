package com.grim3212.mc.pack.world.blocks;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.world.items.ItemFungus;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class WorldBlocks {

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
	public static void initBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> r = evt.getRegistry();

		r.register(randomite);
		r.register(corruption_block);
		r.register(gunpowder_reed_block);
		r.register(glowstone_seeds);
		r.register(fungus_growing);
		r.register(fungus_building);
		r.register(fungus_layer_building);
		r.register(fungus_ore_building);
		r.register(fungus_killing);
		r.register(fungus_maze);
	}

	@SubscribeEvent
	public static void initItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> r = evt.getRegistry();

		r.register(new ItemManualBlock(corruption_block).setRegistryName(corruption_block.getRegistryName()));
		r.register(new ItemManualBlock(glowstone_seeds).setRegistryName(glowstone_seeds.getRegistryName()));
		r.register(new ItemManualBlock(gunpowder_reed_block).setRegistryName(gunpowder_reed_block.getRegistryName()));
		r.register(new ItemManualBlock(randomite).setRegistryName(randomite.getRegistryName()));
		r.register(new ItemFungus(fungus_growing).setRegistryName(fungus_growing.getRegistryName()));
		r.register(new ItemFungus(fungus_building).setRegistryName(fungus_building.getRegistryName()));
		r.register(new ItemFungus(fungus_ore_building).setRegistryName(fungus_ore_building.getRegistryName()));
		r.register(new ItemFungus(fungus_layer_building).setRegistryName(fungus_layer_building.getRegistryName()));
		r.register(new ItemFungus(fungus_killing).setRegistryName(fungus_killing.getRegistryName()));
		r.register(new ItemFungus(fungus_maze).setRegistryName(fungus_maze.getRegistryName()));

		initOreDict();
	}

	private static void initOreDict() {
		OreDictionary.registerOre("blockFungus", fungus_ore_building);
		OreDictionary.registerOre("blockFungus", fungus_building);
		OreDictionary.registerOre("blockFungus", fungus_growing);
		OreDictionary.registerOre("blockFungus", fungus_killing);
		OreDictionary.registerOre("blockFungus", fungus_layer_building);
		OreDictionary.registerOre("blockFungus", fungus_maze);
		OreDictionary.registerOre("oreRandomite", randomite);
	}
}