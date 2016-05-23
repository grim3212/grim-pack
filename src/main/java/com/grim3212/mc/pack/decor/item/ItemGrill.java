package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGrill extends ItemBlock implements IItemColor {

	private Block block;

	public ItemGrill(Block block) {
		super(block);
		this.block = block;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
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

			Block blockType = Block.getBlockById(NBTHelper.getInt(stack, "blockID"));
			if (tileentity instanceof TileEntityGrill) {
				((TileEntityGrill) tileentity).setBlockID(NBTHelper.getInt(stack, "blockID"));
				((TileEntityGrill) tileentity).setBlockMeta(NBTHelper.getInt(stack, "blockMeta"));
				((TileEntityGrill) tileentity).setCustomName(stack.getDisplayName());
				worldIn.playSound(playerIn, pos, blockType.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (blockType.getSoundType().getVolume() + 1.0F) / 2.0F, blockType.getSoundType().getPitch() * 0.8F);
			}

			return EnumActionResult.SUCCESS;

		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.getBlockById(NBTHelper.getInt(stack, "blockID")).getUnlocalizedName() + " " + this.block.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format(new ItemStack(Block.getBlockById(NBTHelper.getInt(stack, "blockID")), 1, NBTHelper.getInt(stack, "blockMeta")).getDisplayName()) + " " + I18n.format(this.block.getUnlocalizedName() + ".name");
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		int blockID = NBTHelper.getInt(stack, "blockID");
		if (stack != null && stack.hasTagCompound()) {
			return Minecraft.getMinecraft().getItemColors().getColorFromItemstack(new ItemStack(Block.getBlockById(blockID)), tintIndex);
		}
		return 16777215;
	}
}