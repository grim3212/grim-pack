package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.init.IndustryNames;
import com.grim3212.mc.pack.industry.item.ItemPaintRollerColor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRway extends BlockManual {

	public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 14);

	public BlockRway() {
		super(IndustryNames.RWAY, Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.0f, 15.0f));
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(TYPE, 0);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(TYPE);
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (!worldIn.isRemote) {
			if (player.getActiveItemStack() != null && ((player.getActiveItemStack().getItem() instanceof ItemPaintRollerColor))) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycle(TYPE));
			}
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualIndustry.rways_page;
	}
}
