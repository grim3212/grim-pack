package com.grim3212.mc.pack.world.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.world.blocks.BlockFungusBuilding;
import com.grim3212.mc.pack.world.blocks.BlockFungusGrowing;
import com.grim3212.mc.pack.world.blocks.BlockFungusKilling;
import com.grim3212.mc.pack.world.blocks.BlockFungusLayer;
import com.grim3212.mc.pack.world.blocks.BlockFungusMaze;
import com.grim3212.mc.pack.world.blocks.BlockFungusOre;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;
import com.grim3212.mc.pack.world.client.entity.RenderBobomb.RenderBobombFactory;
import com.grim3212.mc.pack.world.client.entity.RenderIceCube.RenderIceCubeFactory;
import com.grim3212.mc.pack.world.client.entity.RenderIcePixie.RenderIcePixieFactory;
import com.grim3212.mc.pack.world.client.entity.RenderParaBuzzy.RenderParaBuzzyFactory;
import com.grim3212.mc.pack.world.client.entity.RenderPerson.RenderPersonFactory;
import com.grim3212.mc.pack.world.client.entity.RenderTreasureMob.RenderTreasureMobFactory;
import com.grim3212.mc.pack.world.entity.EntityBobomb;
import com.grim3212.mc.pack.world.entity.EntityBomber;
import com.grim3212.mc.pack.world.entity.EntityFarmer;
import com.grim3212.mc.pack.world.entity.EntityIceCube;
import com.grim3212.mc.pack.world.entity.EntityIcePixie;
import com.grim3212.mc.pack.world.entity.EntityLumberJack;
import com.grim3212.mc.pack.world.entity.EntityMiner;
import com.grim3212.mc.pack.world.entity.EntityNotch;
import com.grim3212.mc.pack.world.entity.EntityParaBuzzy;
import com.grim3212.mc.pack.world.entity.EntityPsycho;
import com.grim3212.mc.pack.world.entity.EntityTreasureMob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WorldClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		// Entities
		RenderingRegistry.registerEntityRenderingHandler(EntityIcePixie.class, new RenderIcePixieFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityIceCube.class, new RenderIceCubeFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBobomb.class, new RenderBobombFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityParaBuzzy.class, new RenderParaBuzzyFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTreasureMob.class, new RenderTreasureMobFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityNotch.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/notch.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityPsycho.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/psycho.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityFarmer.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/farmer.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityLumberJack.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/lumberjack.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityMiner.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/miner.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityBomber.class, new RenderPersonFactory(new ResourceLocation(GrimPack.modID, "textures/entities/bomber.png")));
	}

	@Override
	public void initColors() {
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
				if (state.getBlock() instanceof BlockFungusKilling)
					return BlockFungusKilling.color[state.getValue(BlockFungusKilling.TYPE)];
				else if (state.getBlock() instanceof BlockFungusBuilding)
					return BlockFungusBuilding.color[state.getValue(BlockFungusBuilding.TYPE)];
				else if (state.getBlock() instanceof BlockFungusOre)
					return BlockFungusOre.color;
				else if (state.getBlock() instanceof BlockFungusLayer)
					return BlockFungusLayer.color[state.getValue(BlockFungusLayer.TYPE)];
				else if (state.getBlock() instanceof BlockFungusGrowing)
					return BlockFungusGrowing.color[state.getValue(BlockFungusGrowing.TYPE)];
				else if (state.getBlock() instanceof BlockFungusMaze)
					return BlockFungusMaze.color;
				else
					return 0xffffff;
			}

		}, WorldBlocks.fungus_growing, WorldBlocks.fungus_building, WorldBlocks.fungus_ore_building, WorldBlocks.fungus_layer_building, WorldBlocks.fungus_killing, WorldBlocks.fungus_maze);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (stack != null) {
					int meta = stack.getItemDamage();

					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_growing)) {
						return BlockFungusGrowing.color[meta];
					}
					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_building)) {
						return BlockFungusBuilding.color[meta];
					}
					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_ore_building)) {
						return BlockFungusOre.color;
					}
					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_layer_building)) {
						return BlockFungusLayer.color[meta];
					}
					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_killing)) {
						return BlockFungusKilling.color[meta];
					}
					if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_maze)) {
						return BlockFungusMaze.color;
					}
				}
				return 0xffffff;
			}
		}, WorldBlocks.fungus_growing, WorldBlocks.fungus_building, WorldBlocks.fungus_ore_building, WorldBlocks.fungus_layer_building, WorldBlocks.fungus_killing, WorldBlocks.fungus_maze);

	}
}
