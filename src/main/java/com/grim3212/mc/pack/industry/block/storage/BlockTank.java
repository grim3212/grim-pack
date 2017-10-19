package com.grim3212.mc.pack.industry.block.storage;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.property.UnlistedPropertyFluidStack;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityTank;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTank extends BlockManual implements ITileEntityProvider {

	public static final UnlistedPropertyFluidStack FLUID_STACK = UnlistedPropertyFluidStack.create("fluid_stack");

	public BlockTank() {
		super("tank", Material.GLASS, SoundType.GLASS);
		setHardness(1.0F);
		setResistance(10.0F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { FLUID_STACK });
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTank && state instanceof IExtendedBlockState) {
			TileEntityTank tef = (TileEntityTank) te;
			return ((IExtendedBlockState) state).withProperty(FLUID_STACK, tef.getFluidStack());
		}
		return state;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityTank) {
			TileEntityTank tef = (TileEntityTank) te;
			Utils.tryPlaceFluid(null, worldIn, pos, tef.tank, tef.getFluidStack());
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTank();
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.tank_page;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityTank) {
			TileEntityTank tef = (TileEntityTank) te;
			return tef.getLightLevel();
		}

		return super.getLightValue(state, world, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return (layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.TRANSLUCENT);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		if (heldItem == null) {
			return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
		}
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityTank) {
			TileEntityTank tank = (TileEntityTank) tile;
			boolean result = FluidUtil.interactWithFluidHandler(player, hand, tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null));
			if (result) {
				// player.setHeldItem(hand, result);
				player.inventoryContainer.detectAndSendChanges();
				world.markAndNotifyBlock(pos, null, state, state, 2);
				return true;
			}
		}
		return false;
	}
}
