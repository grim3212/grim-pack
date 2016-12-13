package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemDecorStairs extends ItemBlock {

	private Block block;

	public ItemDecorStairs(Block block) {
		super(block);
		this.block = block;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack stack = playerIn.getHeldItem(hand);
		if (stack.getCount() != 0 && playerIn.canPlayerEdit(pos, facing, stack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity) null)) {
			int i = this.getMetadata(stack.getMetadata());
			IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, playerIn, hand);

			if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
				SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, playerIn);
				worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);

				TileEntity tileentity = worldIn.getTileEntity(pos);
				if (tileentity instanceof TileEntityColorizer) {
					((TileEntityColorizer) tileentity).setBlockState(NBTHelper.getString(stack, "registryName"), NBTHelper.getInt(stack, "meta"));
				}
			}

			return EnumActionResult.SUCCESS;
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.block.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		ItemStack toPlaceStack = new ItemStack(Block.REGISTRY.getObject(new ResourceLocation(NBTHelper.getString(stack, "registryName"))), 1, NBTHelper.getInt(stack, "meta"));

		if (toPlaceStack.getItem() != null) {
			return I18n.translateToLocal(toPlaceStack.getDisplayName() + " " + I18n.translateToLocal(this.block.getUnlocalizedName() + ".name"));
		} else {
			return I18n.translateToLocal(this.block.getUnlocalizedName() + ".name");
		}
	}
}
