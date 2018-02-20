package com.grim3212.mc.pack.tools.blocks;

import java.util.Random;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.items.ToolsItems;
import com.grim3212.mc.pack.tools.tile.TileEntityCrystal;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystal extends BlockManual implements ITileEntityProvider {

	private static AxisAlignedBB BOUNDS = new AxisAlignedBB(0.125f, 0.0f, 0.125f, 0.875f, 1.0f, 0.875f);

	// Stored as metadata
	public static final PropertyEnum<EnumCrystalType> CRYSTAL_TYPE = PropertyEnum.create("type", EnumCrystalType.class);
	public static final PropertyBool CEILING = PropertyBool.create("ceiling");

	// Stored in TE
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 16);

	// Not stored
	public static final PropertyBool END = PropertyBool.create("end");

	public BlockCrystal(String name) {
		super(name, Material.GLASS, SoundType.GLASS);
		setTickRandomly(true);
		setLightLevel(0.3125F);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos);
	}

	public boolean canPlaceOnBlock(IBlockState state) {
		return state.isBlockNormalCube();
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(CRYSTAL_TYPE, EnumCrystalType.RED).withProperty(CEILING, false);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return MathHelper.clamp((2 + random.nextInt(2)) + random.nextInt(fortune + 2), 2, 6);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ToolsItems.shard;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(CRYSTAL_TYPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(CRYSTAL_TYPE, EnumCrystalType.values[meta & 7]).withProperty(CEILING, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(CRYSTAL_TYPE).ordinal();

		if (state.getValue(CEILING)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { CRYSTAL_TYPE, CEILING, END, AGE });
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);

		if (facing == EnumFacing.UP) {

			if (sameCrystal(state, world.getBlockState(pos.down()), false)) {
				return state.withProperty(CEILING, world.getBlockState(pos.down()).getValue(CEILING));
			} else if (canPlaceOnBlock(world.getBlockState(pos.down()))) {
				return state.withProperty(CEILING, false);
			} else if (sameCrystal(state, world.getBlockState(pos.up()), false)) {
				return state.withProperty(CEILING, world.getBlockState(pos.up()).getValue(CEILING));
			} else if (canPlaceOnBlock(world.getBlockState(pos.up()))) {
				return state.withProperty(CEILING, true);
			} else {
				return state;
			}

		} else if (facing == EnumFacing.DOWN) {

			if (sameCrystal(state, world.getBlockState(pos.up()), false)) {
				return state.withProperty(CEILING, world.getBlockState(pos.up()).getValue(CEILING));
			} else if (canPlaceOnBlock(world.getBlockState(pos.up()))) {
				return state.withProperty(CEILING, true);
			} else if (sameCrystal(state, world.getBlockState(pos.down()), false)) {
				return state.withProperty(CEILING, world.getBlockState(pos.down()).getValue(CEILING));
			} else if (canPlaceOnBlock(world.getBlockState(pos.down()))) {
				return state.withProperty(CEILING, false);
			} else {
				return state;
			}

		} else if (sameCrystal(state, world.getBlockState(pos.down()), false)) {
			return state.withProperty(CEILING, world.getBlockState(pos.down()).getValue(CEILING));
		} else if (canPlaceOnBlock(world.getBlockState(pos.down()))) {
			return state.withProperty(CEILING, false);
		} else if (sameCrystal(state, world.getBlockState(pos.up()), false)) {
			return state.withProperty(CEILING, world.getBlockState(pos.up()).getValue(CEILING));
		} else if (canPlaceOnBlock(world.getBlockState(pos.up()))) {
			return state.withProperty(CEILING, true);
		} else {
			return state;
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if (!worldIn.isRemote) {
			System.out.println("SERVER: " + state);
		} else {
			System.out.println("CLIENT: " + state);
		}

		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityCrystal) {
			TileEntityCrystal tec = (TileEntityCrystal) te;
			IBlockState testState = state.withProperty(AGE, tec.getAge());
			return getEnd(testState, worldIn, pos);
		}
		return state;
	}

	private IBlockState getEnd(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(CEILING)) {
			IBlockState belowState = worldIn.getBlockState(pos.down());
			return state.withProperty(END, !sameCrystal(state, belowState));
		} else {
			IBlockState aboveState = worldIn.getBlockState(pos.up());
			return state.withProperty(END, !sameCrystal(state, aboveState));
		}
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < EnumCrystalType.values.length; i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCrystal();
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	public boolean sameCrystal(IBlockState state, IBlockState compare) {
		return sameCrystal(state, compare, true);
	}

	public boolean sameCrystal(IBlockState state, IBlockState compare, boolean checkCeiling) {
		if (checkCeiling) {
			return state.getBlock() == compare.getBlock() && state.getValue(CRYSTAL_TYPE) == compare.getValue(CRYSTAL_TYPE) && state.getValue(CEILING) == compare.getValue(CEILING);
		} else {
			return state.getBlock() == compare.getBlock() && state.getValue(CRYSTAL_TYPE) == compare.getValue(CRYSTAL_TYPE);
		}
	}

	public int countSame(IBlockState state, World worldIn, BlockPos pos, boolean up) {
		int sameBlocks = 0;
		while (sameCrystal(state, worldIn.getBlockState(up ? pos.up(sameBlocks) : pos.down(sameBlocks)))) {
			sameBlocks++;
		}

		return sameBlocks;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		// Only allow growing of end pieces
		if (state.getValue(END)) {
			if (state.getValue(CEILING)) {
				if (sameCrystal(state, worldIn.getBlockState(pos.up()))) {
					if (worldIn.isAirBlock(pos.down())) {
						// Check if a new crystal can be grown
						int count = this.countSame(state, worldIn, pos, true);

						if (count < 10) {

							if (rand.nextInt(50) == 0) {
								worldIn.setBlockState(pos.down(), state);
							}
						}
					}
				}
			} else {
				if (sameCrystal(state, worldIn.getBlockState(pos.down()))) {
					if (worldIn.isAirBlock(pos.up())) {
						// Check if a new crystal can be grown

						int count = this.countSame(state, worldIn, pos, false);

						if (count < 10) {
							if (rand.nextInt(50) == 0) {
								worldIn.setBlockState(pos.up(), state);
							}
						}
					}
				}
			}
		}
	}
}
