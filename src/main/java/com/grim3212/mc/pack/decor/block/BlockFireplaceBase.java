package com.grim3212.mc.pack.decor.block;

import java.util.LinkedHashMap;
import java.util.List;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityTextured;
import com.grim3212.mc.pack.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockFireplaceBase extends BlockTextured {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	protected BlockFireplaceBase() {
		setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { ACTIVE }, new IUnlistedProperty[] { BLOCKID, BLOCKMETA });
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
		if (worldIn.isRemote)
			return true;

		ItemStack stack = playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem);

		if (state.getBlock() != DecorBlocks.chimney) {
			if ((stack != null) && ((stack.getItem() == Items.FLINT_AND_STEEL) || (stack.getItem() == Items.FIRE_CHARGE))) {
				if (!worldIn.getBlockState(pos).getValue(ACTIVE)) {
					stack.damageItem(1, playerIn);
					worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
				}

				return true;
			}
		}
		return true;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getValue(ACTIVE)) {
			return 15;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTextured && state instanceof IExtendedBlockState) {
			IExtendedBlockState blockState = (IExtendedBlockState) state;
			TileEntityTextured tef = (TileEntityTextured) te;
			return blockState.withProperty(BLOCKID, tef.getBlockID()).withProperty(BLOCKMETA, tef.getBlockMeta());
		}
		return state;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		LinkedHashMap<Block, Integer> loadedBlocks = BlockHelper.getBlocks();
		Block[] blocks = loadedBlocks.keySet().toArray(new Block[loadedBlocks.keySet().size()]);

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].getDefaultState().getMaterial() == Material.WOOD || blocks[i].getDefaultState().getMaterial() == Material.CLOTH || blocks[i].getDefaultState().getMaterial() == Material.GOURD || blocks[i].getDefaultState().getMaterial() == Material.ICE || blocks[i].getDefaultState().getMaterial() == Material.GLASS || blocks[i].getDefaultState().getMaterial() == Material.PACKED_ICE || blocks[i].getDefaultState().getMaterial() == Material.SPONGE || blocks[i].getDefaultState().getMaterial() == Material.GRASS || blocks[i].getDefaultState().getMaterial() == Material.GROUND) {
				continue;
			}

			if (loadedBlocks.get(blocks[i]) == 0) {
				ItemStack stack = new ItemStack(itemIn, 1);
				NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
				NBTHelper.setInteger(stack, "blockMeta", 0);
				list.add(stack);
			} else {
				for (int j = 0; j < loadedBlocks.get(blocks[i]); j++) {
					ItemStack stack = new ItemStack(itemIn, 1);
					NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
					NBTHelper.setInteger(stack, "blockMeta", j);
					list.add(stack);
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityTextured();
	}
}