package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFireplaceBase extends BlockColorizer {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	protected BlockFireplaceBase() {
		setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { ACTIVE }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (worldIn.getBlockState(pos).getBlock() != DecorBlocks.chimney) {
			if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
				if (!worldIn.isRemote) {
					PacketDispatcher.sendToDimension(new MessageParticles(pos), playerIn.dimension);
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, false));
				}
				worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && heldItem.getItem() != null) {
			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != null) {
				if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ)) {
					return true;
				}
			}
		}

		if (worldIn.isRemote)
			return true;

		ItemStack stack = playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem);

		if (state.getBlock() != DecorBlocks.chimney) {
			if ((stack != null) && ((stack.getItem() == Items.FLINT_AND_STEEL) || (stack.getItem() == Items.FIRE_CHARGE))) {
				if (!worldIn.getBlockState(pos).getValue(ACTIVE)) {
					stack.damageItem(1, playerIn);
					worldIn.playSound((EntityPlayer) null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == this && world.getBlockState(pos).getValue(ACTIVE)) {
			return 15;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityColorizer();
	}
}