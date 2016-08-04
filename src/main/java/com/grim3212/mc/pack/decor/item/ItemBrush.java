package com.grim3212.mc.pack.decor.item;

import java.util.List;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.decor.GrimDecor;
import com.grim3212.mc.pack.decor.block.BlockColorizer;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ItemBrush extends Item {

	public ItemBrush() {
		this.setCreativeTab(GrimDecor.INSTANCE.getCreativeTab());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add("Clear block information from furniture");
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (world.isRemote) {
			if (player.isSneaking()) {
				TileEntity tileentity = world.getTileEntity(pos);

				if (tileentity instanceof TileEntityColorizer) {
					IBlockState storedState = ((TileEntityColorizer) tileentity).getBlockState();

					ItemStack storedstack = new ItemStack(storedState.getBlock(), 1, storedState.getBlock().getMetaFromState(storedState));
					if (storedstack.getItem() != null)
						player.addChatMessage(new TextComponentString("Stored: " + storedstack.getDisplayName()));
					else
						player.addChatMessage(new TextComponentString("Stored: " + "Empty"));
				}
				return EnumActionResult.SUCCESS;
			}
		}

		IBlockState targetBlockState = world.getBlockState(pos);

		if (targetBlockState.getBlock() instanceof BlockColorizer) {
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntityColorizer) {
				clearTileEntityData(world, pos, targetBlockState, player);
				return EnumActionResult.PASS;
			}
		}

		return EnumActionResult.PASS;

	}

	@SuppressWarnings("deprecation")
	private void clearTileEntityData(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof TileEntityColorizer) {
			TileEntityColorizer tileColorizer = (TileEntityColorizer) te;
			IBlockState storedState = tileColorizer.getBlockState();

			// Can only clear a filled colorizer
			if (storedState != Blocks.AIR.getDefaultState()) {

				EntityItem blockDropped = new EntityItem(worldIn, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), new ItemStack(tileColorizer.getBlockState().getBlock(), 1, tileColorizer.getBlockState().getBlock().getMetaFromState(tileColorizer.getBlockState())));
				if (!worldIn.isRemote) {
					worldIn.spawnEntityInWorld(blockDropped);
					if (!(player instanceof FakePlayer)) {
						blockDropped.onCollideWithPlayer(player);
					}
				}

				setTileEntityData(worldIn, pos, state, null);

				if (state.getBlock() == DecorBlocks.lamp_post_bottom) {
					setTileEntityData(worldIn, pos.up(), state, null);
					setTileEntityData(worldIn, pos.up(2), state, null);
				} else if (state.getBlock() == DecorBlocks.lamp_post_middle) {
					setTileEntityData(worldIn, pos.up(), state, null);
					setTileEntityData(worldIn, pos.down(), state, null);
				} else if (state.getBlock() == DecorBlocks.lamp_post_top) {
					setTileEntityData(worldIn, pos.down(), state, null);
					setTileEntityData(worldIn, pos.down(2), state, null);
				}

				worldIn.playSound(player, pos, state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (state.getBlock().getSoundType().getVolume() + 1.0F) / 2.0F, state.getBlock().getSoundType().getPitch() * 0.8F);
			}
		}
	}

	public static void setTileEntityData(World worldIn, BlockPos pos, IBlockState state, @Nullable IBlockState toSetState) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityColorizer) {
			TileEntityColorizer te = (TileEntityColorizer) tileentity;
			te.setBlockState(toSetState != null ? toSetState : Blocks.AIR.getDefaultState());
			worldIn.setBlockState(pos, state.getBlock().getExtendedState(worldIn.getBlockState(pos), worldIn, pos));
		}
	}
}
