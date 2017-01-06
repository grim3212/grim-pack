package com.grim3212.mc.pack.decor.block;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;
import com.grim3212.mc.pack.decor.util.DecorUtil;
import com.grim3212.mc.pack.decor.util.DecorUtil.SlopeType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockHedge extends BlockColorizer {

	private final SlopeType type;
	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockHedge(SlopeType type) {
		this.type = type;
		this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] { FACING }, new IUnlistedProperty[] { BLOCK_STATE });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		TileEntity te = world.getTileEntity(pos);

		List<ItemStack> ret = new ArrayList<ItemStack>();
		if (te instanceof TileEntityColorizer) {
			if (((TileEntityColorizer) te).getBlockState().getBlock() instanceof BlockLeaves) {
				ItemStack item = new ItemStack(this);
				NBTHelper.setString(item, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
				NBTHelper.setInteger(item, "meta", 0);
				ret.add(item);
			} else {
				ret.add(new ItemStack(this));
			}
		}
		return ret;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (te instanceof TileEntityColorizer) {
			if (!(((TileEntityColorizer) te).getBlockState().getBlock() instanceof BlockLeaves)) {
				player.addStat(StatList.getBlockStats(this));
				player.addExhaustion(0.025F);

				harvesters.set(player);
				ItemStack itemstack = new ItemStack(this);
				NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
				NBTHelper.setInteger(itemstack, "meta", 0);
				spawnAsEntity(worldIn, pos, itemstack);
				harvesters.set(null);
			} else {
				player.addStat(StatList.getBlockStats(this));
				player.addExhaustion(0.025F);

				harvesters.set(player);
				int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
				this.dropBlockAsItem(worldIn, pos, state, i);
				harvesters.set(null);
			}
		} else {
			super.harvestBlock(worldIn, player, pos, state, (TileEntity) null, stack);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			if (!(((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE) == Blocks.AIR.getDefaultState()) && !(((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE).getBlock() instanceof BlockLeaves)) {
				IBlockState blockState = ((IExtendedBlockState) this.getExtendedState(state, worldIn, pos)).getValue(BLOCK_STATE);
				spawnAsEntity(worldIn, pos, new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState)));
			}
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean flag) {
		DecorUtil.addAxisAlignedBoxes(pos, entityBox, collidingBoxes, state, this.type.getNumPieces());
	}

	@Override
	public Page getPage(IBlockState state) {
		switch (this.type) {
		case Pyramid:
			return ManualDecor.pyramid_page;
		case FullPyramid:
			return ManualDecor.fullPyramid_page;
		case SlopedPost:
			return ManualDecor.slopedPost_page;
		default:
			return null;
		}
	}

}
