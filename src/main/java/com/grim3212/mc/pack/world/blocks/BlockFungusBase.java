package com.grim3212.mc.pack.world.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockFungusBase extends BlockManual {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 15);

	private boolean canGrowUp;

	protected BlockFungusBase(String name, boolean growUp) {
		super(name, new MaterialFungus(), SoundType.PLANT);
		setTickRandomly(true);
		setCreativeTab(GrimCreativeTabs.GRIM_WORLD);
		this.setHardness(0.0F);
		this.setResistance(0.0F);
		this.setLightLevel(0.8F);
		this.setLightOpacity(10);
		this.canGrowUp = growUp;
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(TYPE, 0);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 100;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 100;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		return iblockstate.getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < 16; i++)
			items.add(new ItemStack(this, 1, i));
	}

	public abstract boolean canReplace(IBlockState side, IBlockState state);

	@Override
	public abstract void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand);

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		if (!stack.isEmpty() && (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemShears || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)) {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	public boolean spreadToSide(World world, BlockPos pos, IBlockState state, boolean growUp, boolean growDown) {
		IBlockState[] sideStates = { world.getBlockState(pos.down()), world.getBlockState(pos.west()), world.getBlockState(pos.east()), world.getBlockState(pos.north()), world.getBlockState(pos.south()), world.getBlockState(pos.up()) };
		int[] side = { 0, 1, 2, 3 };

		for (int q = 0; q < side.length; q++) {
			int randomPosition = world.rand.nextInt(side.length);
			int temp = side[q];
			side[q] = side[randomPosition];
			side[randomPosition] = temp;
		}

		// down
		if (growDown && canReplace(sideStates[0], state)) {
			buildWithMixing(world, pos.down(), state);
			return true;
		}

		// sides
		for (int r = 0; r <= 3; r++) {
			if (side[r] == 0 && canReplace(sideStates[1], state)) {
				buildWithMixing(world, pos.west(), state);
				return true;
			}
			if (side[r] == 1 && canReplace(sideStates[2], state)) {
				buildWithMixing(world, pos.east(), state);
				return true;
			}
			if (side[r] == 2 && canReplace(sideStates[3], state)) {
				buildWithMixing(world, pos.north(), state);
				return true;
			}
			if (side[r] == 3 && canReplace(sideStates[4], state)) {
				buildWithMixing(world, pos.south(), state);
				return true;
			}
		}

		// top
		if (growUp && canReplace(sideStates[5], state) && (canGrowUp || world.rand.nextInt(10) > 3)) {
			buildWithMixing(world, pos.up(), state);
			return true;
		}

		return false;
	}

	// basic fungus mixing
	public void buildWithMixing(World world, BlockPos pos, IBlockState state) {
		world.setBlockState(pos, state, 2);
	}
}
