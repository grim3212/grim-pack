package com.grim3212.mc.pack.decor.block.colorizer;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.init.DecorNames;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class BlockColorizerGrill extends BlockColorizerFireplaceBase {

	public BlockColorizerGrill() {
		super(DecorNames.GRILL);
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityGrill();
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (worldIn.getBlockState(pos).get(ACTIVE)) {
			if (!worldIn.isRemote) {
				PacketDispatcher.send(PacketDistributor.DIMENSION.with(() -> player.dimension), new MessageParticles(pos));
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ACTIVE, false));
			}
			worldIn.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
		}
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty()) {
			if (super.onBlockActivated(state, worldIn, pos, player, hand, hit)) {
				return true;
			}
		}

		if (worldIn.isRemote)
			return true;

		if (!heldItem.isEmpty() && ((heldItem.getItem() == Items.FLINT_AND_STEEL) || (heldItem.getItem() == Items.FIRE_CHARGE))) {
			if (!worldIn.getBlockState(pos).get(ACTIVE)) {
				heldItem.damageItem(1, player, (playerEnt) -> {
					playerEnt.sendBreakAnimation(hand);
				});
				worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(pos, state.with(ACTIVE, true));
			}
			return true;
		}

		INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);
		if (inamedcontainerprovider != null) {
			player.openContainer(inamedcontainerprovider);
		}
		return true;
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos) {
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof TileEntityGrill) {
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityGrill) tileentity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}

			super.onReplaced(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public Page getPage(BlockState state) {
		return ManualDecor.grill_page;
	}
}
