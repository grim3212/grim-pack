package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.tile.TileEntityGravity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGravity extends BlockManual implements ITileEntityProvider {

	public static final PropertyBool POWERED = PropertyBool.create("powered");

	private int type;

	public BlockGravity(String name, int type) {
		super(name, Material.IRON, SoundType.METAL);
		this.type = type;
		setHardness(0.3F);
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected IBlockState getState() {
		return this.blockState.getBaseState().withProperty(POWERED, false);
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

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		updateGravitor(worldIn, pos);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		updateGravitor(worldIn, pos);
	}

	public void updateGravitor(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity te = worldIn.getTileEntity(pos);

		int powerLevel = worldIn.isBlockIndirectlyGettingPowered(pos);

		if (powerLevel > 0 && te instanceof TileEntityGravity) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, true), 2);
		} else {
			worldIn.setBlockState(pos, state.withProperty(POWERED, false), 2);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGravity(type);
	}

	@Override
	public Page getPage(IBlockState state) {
		if (state.getBlock() == IndustryBlocks.gravitor) {
			return ManualIndustry.gravitor_page;
		} else if (state.getBlock() == IndustryBlocks.attractor) {
			return ManualIndustry.attract_page;
		}

		return ManualIndustry.repulse_page;
	}
}
