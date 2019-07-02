package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockElemental extends BlockManual {

	public static enum ElementType {
		WATER(IndustryNames.ELEMENTAL_WATER), FIRE(IndustryNames.ELEMENTAL_FIRE), LAVA(IndustryNames.ELEMENTAL_LAVA);

		private String name;

		ElementType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private ElementType type;

	protected BlockElemental(ElementType type) {
		super(type.getName(), Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(2.0f));
		this.type = type;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean p_220069_6_) {
		if (!worldIn.isRemote) {
			boolean isPowered = worldIn.isBlockPowered(pos);

			if (isPowered) {
				if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
					if (type == ElementType.WATER) {
						worldIn.setBlockState(pos.up(), Blocks.WATER.getDefaultState());
					} else if (type == ElementType.FIRE) {
						worldIn.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
					} else if (type == ElementType.LAVA) {
						worldIn.setBlockState(pos.up(), Blocks.LAVA.getDefaultState());
					}
				}
			} else {
				Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
				if (upBlock == Blocks.WATER || upBlock == Blocks.WATER || upBlock == Blocks.LAVA || upBlock == Blocks.LAVA || upBlock == Blocks.FIRE) {
					worldIn.destroyBlock(pos.up(), false);
				}
			}
		}
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return false;
	}

	@Override
	public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
		Block upBlock = worldIn.getBlockState(pos.up()).getBlock();
		if (upBlock == Blocks.WATER || upBlock == Blocks.WATER || upBlock == Blocks.LAVA || upBlock == Blocks.LAVA || upBlock == Blocks.FIRE) {
			worldIn.destroyBlock(pos.up(), false);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		if (state.getBlock() == IndustryBlocks.elemental_fire) {
			return ManualIndustry.fireBlock_page;
		} else if (state.getBlock() == IndustryBlocks.elemental_water) {
			return ManualIndustry.waterBlock_page;
		}

		return ManualIndustry.lavaBlock_page;
	}
}
