package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.industry.item.ItemPaintRollerColor;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRway extends Block {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 14);

	public BlockRway() {
		super(Material.ROCK);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(TYPE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote) {
			if (playerIn.getActiveItemStack() != null && ((playerIn.getActiveItemStack().getItem() instanceof ItemPaintRollerColor))) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(TYPE));
			}
		}
	}
}
