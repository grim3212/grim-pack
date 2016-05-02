package com.grim3212.mc.industry.block;

import com.grim3212.mc.industry.item.ItemPaintRollerColor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRway extends Block {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 14);

	public BlockRway() {
		super(Material.rock);
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
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { TYPE });
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (!worldIn.isRemote) {
			if (playerIn.getCurrentEquippedItem() != null && ((playerIn.getCurrentEquippedItem().getItem() instanceof ItemPaintRollerColor))) {
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).cycleProperty(TYPE));
			}
		}
	}
}
