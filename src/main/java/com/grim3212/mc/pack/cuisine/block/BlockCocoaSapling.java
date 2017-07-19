package com.grim3212.mc.pack.cuisine.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;
import com.grim3212.mc.pack.cuisine.item.CuisineItems;
import com.grim3212.mc.pack.cuisine.world.WorldGenCocoaTrees;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoaSapling extends BlockBush implements IGrowable, IManualBlock {

	protected static final AxisAlignedBB COCOA_SAPLING_AABB = new AxisAlignedBB(0.1f, 0.0F, 0.1F, 0.9F, 0.8F, 0.9f);
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	protected BlockCocoaSapling() {
		super(Material.PLANTS);
		setTickRandomly(true);
		setCreativeTab(null);
		setSoundType(SoundType.CLOTH);
		setHardness(0.0f);
		setUnlocalizedName("cocoa_tree_sapling");
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
		setRegistryName(new ResourceLocation(GrimPack.modID, "cocoa_tree_sapling"));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return COCOA_SAPLING_AABB;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return CuisineItems.cocoa_fruit;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
				this.grow(worldIn, rand, pos, state);
			}
		}
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if (state.getValue(STAGE) == 0) {
			world.setBlockState(pos, state.cycleProperty(STAGE), 4);
		} else {
			this.generateTree(world, pos, state, rand);
		}
	}

	public void generateTree(World world, BlockPos pos, IBlockState state, Random random) {
		world.setBlockToAir(pos);
		WorldGenCocoaTrees worldgencocoatrees = new WorldGenCocoaTrees();
		if (!worldgencocoatrees.generate(world, random, pos)) {
			world.setBlockState(pos, this.getDefaultState());
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(STAGE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { STAGE });
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(CuisineItems.cocoa_fruit);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double) worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualCuisine.cocoaFruit_page;
	}
}
