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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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
	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.1875f, 0.0f, 0.1875f, 0.8125f, 0.25f, 0.8125f);

	public BlockAlarm() {
		super("alarm", Material.IRON, SoundType.METAL);
		setLightLevel(0.2f);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
		setHardness(2F);
		setResistance(1.0F);
	}

	@Override
	protected IBlockState getState() {
		return super.getState().withProperty(POWERED, false);
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
		return !worldIn.isAirBlock(pos.down()) && worldIn.getBlockState(pos.down()).isFullCube();
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		// Drop block if it has no base
		if (!canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
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

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWERED, meta == 1 ? true : false);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWERED) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWERED });
	}
}
