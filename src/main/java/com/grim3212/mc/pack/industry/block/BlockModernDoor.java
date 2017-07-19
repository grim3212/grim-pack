package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockModernDoor extends BlockDoor implements IManualBlock {

	protected BlockModernDoor(Material material) {
		super(material);

		String name;

		if (material == Material.GLASS) {
			setSoundType(SoundType.GLASS);
			name = "door_glass";
			setHardness(0.75F);
			setResistance(7.5F);
		} else if (material == Material.CIRCUITS) {
			setSoundType(SoundType.METAL);
			name = "door_chain";
			setHardness(0.5F);
			setResistance(5.0F);
		} else {
			setSoundType(SoundType.METAL);
			name = "door_steel";
			setHardness(1.0F);
			setResistance(10.0F);
		}

		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		Block block = world.getBlockState(pos).getBlock();
		if (block == IndustryBlocks.door_chain) {
			return new ItemStack(IndustryItems.door_chain_item);
		} else if (block == IndustryBlocks.door_glass) {
			return new ItemStack(IndustryItems.door_glass_item);
		} else if (block == IndustryBlocks.door_steel) {
			return new ItemStack(IndustryItems.door_steel_item);
		}

		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		Block block = state.getBlock();
		if (block == IndustryBlocks.door_chain) {
			return IndustryItems.door_chain_item;
		} else if (block == IndustryBlocks.door_glass) {
			return IndustryItems.door_glass_item;
		} else if (block == IndustryBlocks.door_steel) {
			return IndustryItems.door_steel_item;
		}

		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.doors_page;
	}
}
