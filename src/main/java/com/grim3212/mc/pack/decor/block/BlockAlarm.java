package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.tile.TileEntityAlarm;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAlarm extends BlockManual implements ITileEntityProvider {

	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);

	private static final AxisAlignedBB UP_BOUNDS = new AxisAlignedBB(0.1875f, 0.0f, 0.1875f, 0.8125f, 0.25f, 0.8125f);
	private static final AxisAlignedBB DOWN_BOUNDS = new AxisAlignedBB(0.1875f, 0.75f, 0.1875f, 0.8125f, 1.0f, 0.8125f);
	private static final AxisAlignedBB NORTH_BOUNDS = new AxisAlignedBB(0.1875f, 0.1875f, 0.75f, 0.8125f, 0.8125f, 1.0f);
	private static final AxisAlignedBB SOUTH_BOUNDS = new AxisAlignedBB(0.1875f, 0.1875f, 0.0f, 0.8125f, 0.8125f, 0.25f);
	private static final AxisAlignedBB WEST_BOUNDS = new AxisAlignedBB(0.75f, 0.1875f, 0.1875f, 1.0f, 0.8125f, 0.8125f);
	private static final AxisAlignedBB EAST_BOUNDS = new AxisAlignedBB(0.0f, 0.1875f, 0.1875f, 0.25f, 0.8125f, 0.8125f);

	public BlockAlarm() {
		super("alarm", Material.IRON, SoundType.METAL);
		setLightLevel(0.2f);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
		setHardness(2F);
		setResistance(1.0F);
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(POWERED, false).withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case DOWN:
			return DOWN_BOUNDS;
		case EAST:
			return EAST_BOUNDS;
		case NORTH:
			return NORTH_BOUNDS;
		case SOUTH:
			return SOUTH_BOUNDS;
		case UP:
			return UP_BOUNDS;
		case WEST:
			return WEST_BOUNDS;
		}

		return FULL_BLOCK_AABB;
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
	public Page getPage(IBlockState state) {
		return ManualDecor.alarm_page;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.west()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.east()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.north()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.south()).isOpaqueCube() ? true : (worldIn.getBlockState(pos.up()).isOpaqueCube() ? true : worldIn.getBlockState(pos.down()).isOpaqueCube()))));
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return world.isSideSolid(pos.offset(facing.getOpposite()), facing, true) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		this.tryDropAlarm(worldIn, pos);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		// Drop block if it has no base
		if (tryDropAlarm(worldIn, pos)) {
			return;
		}

		TileEntity te = worldIn.getTileEntity(pos);

		if (te instanceof TileEntityAlarm) {
			if (worldIn.isBlockPowered(pos) && !state.getValue(POWERED)) {
				worldIn.setBlockState(pos, state.withProperty(POWERED, true));

				if (((TileEntityAlarm) te).alarmType == 26) {
					// Explode
					worldIn.setBlockToAir(pos);
					worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10F, true);
				} else {

					// Play the current sound set when activated
					worldIn.playSound((EntityPlayer) null, pos, ((TileEntityAlarm) te).getSound(), SoundCategory.BLOCKS, 1f, 1f);
				}
			} else if (!worldIn.isBlockPowered(pos) && state.getValue(POWERED)) {
				worldIn.setBlockState(pos, state.withProperty(POWERED, false));
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if (tile instanceof TileEntityAlarm) {
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.ALARM_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());

			// Play the current sound set
			worldIn.playSound(playerIn, pos, ((TileEntityAlarm) tile).getSound(), SoundCategory.BLOCKS, 1f, 1f);
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityAlarm();
	}

	public static EnumFacing getFacing(int meta) {
		return EnumFacing.getFront(meta & 7);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(POWERED, (meta & 8) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		byte b0 = 0;
		int i = b0 | state.getValue(FACING).getIndex();

		if (state.getValue(POWERED)) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}

	private boolean tryDropAlarm(World worldIn, BlockPos pos) {
		if (!canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
			worldIn.setBlockToAir(pos);
			return true;
		}

		return false;
	}
}
