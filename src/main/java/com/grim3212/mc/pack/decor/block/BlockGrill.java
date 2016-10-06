package com.grim3212.mc.pack.decor.block;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualBlock;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.network.PacketDispatcher;
import com.grim3212.mc.pack.decor.client.ManualDecor;
import com.grim3212.mc.pack.decor.network.MessageParticles;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGrill extends BlockFireplaceBase implements IManualBlock {

	public BlockGrill() {
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntityGrill();
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (worldIn.getBlockState(pos).getValue(ACTIVE)) {
			if (!worldIn.isRemote) {
				PacketDispatcher.sendToDimension(new MessageParticles(pos), playerIn.dimension);
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(ACTIVE, false));
			}
			worldIn.playSound(playerIn, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (heldItem != null && heldItem.getItem() != null) {
			Block block = Block.getBlockFromItem(heldItem.getItem());
			if (block != null) {
				if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ)) {
					return true;
				}
			}
		}

		if (worldIn.isRemote)
			return true;

		ItemStack stack = playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem);

		if ((stack != null) && ((stack.getItem() == Items.FLINT_AND_STEEL) || (stack.getItem() == Items.FIRE_CHARGE))) {
			if (!worldIn.getBlockState(pos).getValue(ACTIVE)) {
				stack.damageItem(1, playerIn);
				worldIn.playSound((EntityPlayer) null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
				worldIn.setBlockState(pos, state.withProperty(ACTIVE, true));
			}
			return true;
		}

		playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.GRILL_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityGrill tileentity = (TileEntityGrill) worldIn.getTileEntity(pos);

		if (tileentity != null) {
			if (!worldIn.isRemote) {
				for (int var8 = 0; var8 < tileentity.getSizeInventory(); var8++) {
					ItemStack var9 = tileentity.getStackInSlot(var8);

					if (var9 != null) {
						float var10 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
						float var11 = worldIn.rand.nextFloat() * 0.8F + 0.1F;
						EntityItem var14;
						for (float var12 = worldIn.rand.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; worldIn.spawnEntityInWorld(var14)) {
							int var13 = worldIn.rand.nextInt(21) + 10;

							if (var13 > var9.stackSize) {
								var13 = var9.stackSize;
							}

							var9.stackSize -= var13;
							var14 = new EntityItem(worldIn, pos.getX() + var10, pos.getY() + var11, pos.getZ() + var12, new ItemStack(var9.getItem(), var13, var9.getItemDamage()));
							float var15 = 0.05F;
							var14.motionX = ((float) worldIn.rand.nextGaussian() * var15);
							var14.motionY = ((float) worldIn.rand.nextGaussian() * var15 + 0.2F);
							var14.motionZ = ((float) worldIn.rand.nextGaussian() * var15);

							if (var9.hasTagCompound()) {
								var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
							}
						}
					}
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public Page getPage(IBlockState state) {
		return ManualDecor.grill_page;
	}
}
