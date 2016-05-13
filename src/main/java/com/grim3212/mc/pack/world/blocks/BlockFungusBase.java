package com.grim3212.mc.pack.world.blocks;

import java.util.List;
import java.util.Random;

import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockFungusBase extends Block {

	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 15);

	private boolean canGrowUp;

	protected BlockFungusBase(boolean growUp) {
		super(new MaterialFungus());
		setTickRandomly(true);
		setCreativeTab(GrimWorld.INSTANCE.getCreativeTab());
		this.canGrowUp = growUp;
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return (Integer) state.getValue(TYPE);
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
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 100;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 100;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
		if (worldIn.getBlockState(pos).getBlock() instanceof BlockFungusBase) {
			return false;
		}
		return super.shouldSideBeRendered(worldIn, pos, side);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < 16; i++)
			list.add(new ItemStack(this, 1, i));
	}

	public abstract boolean canReplace(IBlockState side, IBlockState state);

	@Override
	public abstract int getRenderColor(IBlockState state);

	@Override
	public abstract int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass);

	@Override
	public abstract void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand);

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
		super.harvestBlock(worldIn, player, pos, state, te);
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemSword) {
			float f = 0.7F;
			double d = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d1 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			double d2 = (double) (worldIn.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(worldIn, (double) pos.getX() + d, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(this, 1, (Integer) state.getValue(TYPE)));
			entityitem.setPickupDelay(10);
			if (!worldIn.isRemote)
				worldIn.spawnEntityInWorld(entityitem);
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

	@Override
	public int getBlockColor() {
		return 0xffffff;
	}
}
