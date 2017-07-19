package com.grim3212.mc.pack.industry.block;

import java.util.Random;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;
import com.grim3212.mc.pack.industry.util.MachineRecipes.MachineType;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDerrick extends BlockManual implements ITileEntityProvider {

	protected BlockDerrick() {
		super("derrick", Material.IRON, SoundType.METAL);
		setHardness(1.0F);
		setResistance(10.0F);
		setLightLevel(0.5F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		return block == IndustryBlocks.steel_pipe || block == IndustryBlocks.oil_ore;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		boolean flag = !this.canPlaceBlockAt(worldIn, pos);
		if (flag) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityMachine) {
				((TileEntityMachine) tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityMachine) {
			if (((TileEntityMachine) te).isWorking()) {
				double d0 = (double) pos.getX() + 0.5D;
				double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
				double d2 = (double) pos.getZ() + 0.5D;
				double d3 = 0.52D;
				double d4 = rand.nextDouble() * 0.6D - 0.3D;
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;

		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityMachine) {
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.DERRICK_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityMachine) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMachine) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachine(MachineType.DERRICK);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualIndustry.derrick_page;
	}
}
