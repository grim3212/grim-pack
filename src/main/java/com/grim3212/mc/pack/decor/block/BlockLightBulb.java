package com.grim3212.mc.pack.decor.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.item.DecorItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLightBulb extends BlockBreakable implements IManualBlock {

	protected static final AxisAlignedBB BULB_AABB = new AxisAlignedBB(0.2D, 0.3D, 0.2D, 0.8D, 1.0D, 0.8D);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	protected BlockLightBulb() {
		super(Material.GLASS, true);
		setSoundType(SoundType.GLASS);
		setHardness(0.1F);
		setUnlocalizedName("light_bulb");
		setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
		setRegistryName(new ResourceLocation(GrimPack.modID, "light_bulb"));
	}

	@Override
	public CreativeTabs getCreativeTabToDisplayOn() {
		return getDefaultState().getValue(ACTIVE) ? null : GrimCreativeTabs.GRIM_DECOR;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		neighborChanged(state, worldIn, pos, worldIn.getBlockState(pos.up()).getBlock(), pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isBlockIndirectlyGettingPowered(pos) > 0 || worldIn.isBlockIndirectlyGettingPowered(pos.up()) > 0) {
			worldIn.setBlockState(pos, getDefaultState().withProperty(ACTIVE, true));
		} else {
			worldIn.setBlockState(pos, getDefaultState());
		}

		if (!canPlaceBlockAt(worldIn, pos)) {
			dropBlockAsItem(worldIn, pos, getDefaultState(), 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.up()).isOpaqueCube();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return DecorItems.glass_shard;
	}

	@Override
	public int quantityDropped(Random random) {
		return 2 + random.nextInt(2);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BULB_AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess source, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(DecorBlocks.light_bulb, 1, 0);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(ACTIVE) ? 15 : 0;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.lights_page;
	}
}
