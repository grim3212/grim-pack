package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
public class ItemGrill extends ItemBlock {

	private Block block;

	public ItemGrill(Block block) {
		super(block);
		this.block = block;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (block == Blocks.SNOW_LAYER && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1) {
			facing = EnumFacing.UP;
		} else if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		if (stack.stackSize == 0) {
			return EnumActionResult.FAIL;
		} else if (!playerIn.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		} else if (pos.getY() == 255 && this.block.getDefaultState().getMaterial().isSolid()) {
			return EnumActionResult.FAIL;
		} else if (worldIn.canBlockBePlaced(this.block, pos, false, facing, (Entity) null, stack)) {
			worldIn.setBlockState(pos, this.block.getDefaultState(), 3);

			--stack.stackSize;
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityGrill) {
				((TileEntityGrill) tileentity).setBlockState(NBTHelper.getString(stack, "registryName"), NBTHelper.getInt(stack, "meta"));
				((TileEntityGrill) tileentity).setCustomName(stack.getDisplayName());
				worldIn.playSound(playerIn, pos, this.block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (this.block.getSoundType().getVolume() + 1.0F) / 2.0F, this.block.getSoundType().getPitch() * 0.8F);
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