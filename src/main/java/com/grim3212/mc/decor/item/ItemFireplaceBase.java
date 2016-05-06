package com.grim3212.mc.decor.item;

import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.decor.tile.TileEntityFireplace;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemFireplaceBase extends ItemBlock {

	private Block block;

	public ItemFireplaceBase(Block block) {
		super(block);
		this.block = block;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (block == Blocks.snow_layer && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1) {
			side = EnumFacing.UP;
		} else if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(side);
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!playerIn.canPlayerEdit(pos, side, stack)) {
			return false;
		} else if (pos.getY() == 255 && this.block.getMaterial().isSolid()) {
			return false;
		} else if (worldIn.canBlockBePlaced(this.block, pos, false, side, (Entity) null, stack)) {
			worldIn.setBlockState(pos, this.block.getDefaultState(), 3);

			--stack.stackSize;
			TileEntity tileentity = worldIn.getTileEntity(pos);

			Block blockType = Block.getBlockById(NBTHelper.getInt(stack, "blockID"));
			if (tileentity instanceof TileEntityFireplace) {
				((TileEntityFireplace) tileentity).setBlockID(NBTHelper.getInt(stack, "blockID"));
				((TileEntityFireplace) tileentity).setBlockMeta(NBTHelper.getInt(stack, "blockMeta"));
				worldIn.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), blockType.stepSound.getPlaceSound(), (blockType.stepSound.getVolume() + 1.0F) / 2.0F, blockType.stepSound.getFrequency() * 0.8F);
			}

			worldIn.getBlockState(pos).getBlock().setStepSound(blockType.stepSound);

			return true;

		} else {
			return false;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.getBlockById(NBTHelper.getInt(stack, "blockID")).getUnlocalizedName() + " " + this.block.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		ItemStack toPlaceStack = new ItemStack(Block.getBlockById(NBTHelper.getInt(stack, "blockID")), 1, NBTHelper.getInt(stack, "blockMeta"));

		if (toPlaceStack.getItem() != Item.getItemFromBlock(Blocks.air)) {
			return StatCollector.translateToLocal(toPlaceStack.getDisplayName() + " " + StatCollector.translateToLocal(this.block.getUnlocalizedName() + ".name"));
		}

		return super.getItemStackDisplayName(stack);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		int blockID = NBTHelper.getInt(stack, "blockID");
		if (stack != null && stack.hasTagCompound()) {
			return Block.getBlockById(blockID).getBlockColor();
		}
		return 16777215;
	}
}