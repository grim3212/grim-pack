package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.item.ItemManualBlock;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.BlockSloped;
import com.grim3212.mc.pack.decor.block.BlockSloped.EnumHalf;
import com.grim3212.mc.pack.decor.block.BlockSlopedRotate;
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

	private Block block;
	private boolean simpleRotate = false;

	public ItemSloped(Block block) {
		this(block, false);
	}

	public ItemSloped(Block block, boolean simpleRotate) {
		super(block);
		this.block = block;
		this.simpleRotate = simpleRotate;
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
			EnumHalf half = EnumHalf.BOTTOM;
			if (!(facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D))) {
				half = EnumHalf.TOP;
			}

			if (this.block instanceof BlockSlopedRotate) {
				if (!simpleRotate) {
					worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockSlopedRotate.FACING, EnumFacing.getHorizontal(MathHelper.floor_double(playerIn.rotationYaw * 4.0F / 360.0F) & 0x3).rotateY()).withProperty(BlockSloped.HALF, half), 3);
				} else {
					worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockSlopedRotate.FACING, playerIn.getHorizontalFacing()).withProperty(BlockSloped.HALF, half), 3);
				}
			} else {
				worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockSloped.HALF, half), 3);
			}

			--stack.stackSize;
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

		if (toPlaceStack.getItem() != null) {
			return I18n.translateToLocal(toPlaceStack.getDisplayName() + " " + I18n.translateToLocal(this.block.getUnlocalizedName() + ".name"));
		} else {
			return I18n.translateToLocal(this.block.getUnlocalizedName() + ".name");
		}
	}
}
