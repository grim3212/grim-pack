package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerFacing;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate;
import com.grim3212.mc.pack.decor.block.colorizer.BlockColorizerSlopedRotate.EnumHalf;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemSloped extends ItemManualBlock {
	/*
	 * Determines if the rotation should be based on the direction the player is
	 * looking
	 */
	private boolean simpleRotate = false;

	public ItemSloped(Block block) {
		this(block, false);
	}

	public ItemSloped(Block block, boolean simpleRotate) {
		super(block);
		this.simpleRotate = simpleRotate;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (block == Blocks.SNOW_LAYER && ((Integer) iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1) {
			facing = EnumFacing.UP;
		} else if (!block.isReplaceable(worldIn, pos)) {
			pos = pos.offset(facing);
		}

		ItemStack stack = playerIn.getHeldItem(hand);
		if (stack.getCount() == 0) {
			return EnumActionResult.FAIL;
		} else if (!playerIn.canPlayerEdit(pos, facing, stack)) {
			return EnumActionResult.FAIL;
		} else if (pos.getY() == 255 && this.block.getDefaultState().getMaterial().isSolid()) {
			return EnumActionResult.FAIL;
		} else if (worldIn.mayPlace(this.block, pos, false, facing, (Entity) null)) {
			EnumHalf half = EnumHalf.BOTTOM;
			if (!(facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D))) {
				half = EnumHalf.TOP;
			}

			if (this.block instanceof BlockColorizerSlopedRotate) {
				if (!simpleRotate) {
					worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockColorizerSlopedRotate.FACING, EnumFacing.getHorizontal(MathHelper.floor(playerIn.rotationYaw * 4.0F / 360.0F) & 0x3).rotateY()).withProperty(BlockColorizerSlopedRotate.HALF, half), 3);
				} else {
					worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockColorizerSlopedRotate.FACING, playerIn.getHorizontalFacing()).withProperty(BlockColorizerSlopedRotate.HALF, half), 3);
				}
			} else if (this.block instanceof BlockColorizerFacing) {
				worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockColorizerFacing.FACING, facing), 3);
			} else {
				worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockColorizerSlopedRotate.HALF, half), 3);
			}

			stack.shrink(1);
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityColorizer) {
				((TileEntityColorizer) tileentity).setBlockState(NBTHelper.getString(stack, "registryName"), NBTHelper.getInt(stack, "meta"));

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

		if (!toPlaceStack.isEmpty()) {
			return I18n.translateToLocal(toPlaceStack.getDisplayName() + " " + I18n.translateToLocal(this.block.getUnlocalizedName() + ".name"));
		} else {
			return I18n.translateToLocal(this.block.getUnlocalizedName() + ".name");
		}
	}
}
