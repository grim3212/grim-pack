package com.grim3212.mc.pack.decor.block.colorizer;

import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class BlockColorizerFireplaceBase extends BlockColorizer {

	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

	protected BlockColorizerFireplaceBase(String name) {
		super(name);
	}

	@Override
	protected BlockState getState() {
		return super.getState().with(ACTIVE, false);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(ACTIVE);
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		if (worldIn.getBlockState(pos).getBlock() != DecorBlocks.chimney) {
			if (worldIn.getBlockState(pos).get(ACTIVE)) {
				if (!worldIn.isRemote) {
					PacketDispatcher.send(PacketDistributor.DIMENSION.with(() -> player.dimension), new MessageParticles(pos));
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ACTIVE, false));
				}
				worldIn.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
			}
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

		if (state.getBlock() != DecorBlocks.chimney) {
			if (!heldItem.isEmpty() && ((heldItem.getItem() == Items.FLINT_AND_STEEL) || (heldItem.getItem() == Items.FIRE_CHARGE))) {
				if (!worldIn.getBlockState(pos).get(ACTIVE)) {
					heldItem.damageItem(1, player, (ent) -> {
						ent.sendBreakAnimation(EquipmentSlotType.MAINHAND);
					});
					worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
					worldIn.setBlockState(pos, state.with(ACTIVE, true));
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public int getLightValue(BlockState state, IEnviromentBlockReader world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() == this && world.getBlockState(pos).get(ACTIVE)) {
			return 15;
		}
		return super.getLightValue(state, world, pos);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileEntityColorizer();
	}
}