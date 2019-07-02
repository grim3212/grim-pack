package com.grim3212.mc.pack.industry.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.block.BlockManual;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.industry.client.ManualIndustry;
import com.grim3212.mc.pack.industry.item.IndustryItems;
import com.grim3212.mc.pack.industry.item.ItemPositionFinder;
import com.grim3212.mc.pack.industry.tile.TileEntitySpecificSensor;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.PushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpecificSensor extends BlockManual implements ITileEntityProvider {

	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockSpecificSensor(boolean upgraded) {
		super(upgraded ? "upgraded_specific_sensor" : "specific_sensor", Material.ROCK, SoundType.STONE);
		if (upgraded) {
			setHardness(3.0F);
			setResistance(12F);
		} else {
			setHardness(1.5F);
			setResistance(10F);
		}
		setCreativeTab(GrimCreativeTabs.GRIM_INDUSTRY);
	}

	@Override
	protected BlockState getState() {
		return this.blockState.getBaseState().withProperty(ACTIVE, false);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntitySpecificSensor) {
			TileEntitySpecificSensor teSpecific = (TileEntitySpecificSensor) te;

			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!heldItem.isEmpty()) {
				if (heldItem.getItem() == IndustryItems.position_finder) {
					BlockPos coords = ((ItemPositionFinder) heldItem.getItem()).getCoords(heldItem);
					if (coords != null) {
						teSpecific.setSensorPos(coords);
						return true;
					}
				}
			}
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.SPECIFIC_SENSOR_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntitySpecificSensor) {
				((TileEntitySpecificSensor) tileentity).setCustomInventoryName(stack.getDisplayName());
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySpecificSensor();
	}

	@Override
	public Page getPage(BlockState state) {
		if (this == IndustryBlocks.specific_sensor)
			return ManualIndustry.specificSensor_page;
		else
			return ManualIndustry.upgradedSpecificSensor_page;
	}

	@Override
	public BlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ACTIVE, meta == 1 ? true : false);
	}

	@Override
	public int getMetaFromState(BlockState state) {
		return state.getValue(ACTIVE) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ACTIVE });
	}

	@Override
	public int getWeakPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ACTIVE))
			return 15;
		return 0;
	}

	@Override
	public int getStrongPower(BlockState blockState, IBlockAccess blockAccess, BlockPos pos, Direction side) {
		if (blockState.getValue(ACTIVE))
			return 15;
		return 0;
	}

	@Override
	public boolean canProvidePower(BlockState state) {
		return true;
	}

	@Override
	public PushReaction getMobilityFlag(BlockState state) {
		return PushReaction.BLOCK;
	}
}
