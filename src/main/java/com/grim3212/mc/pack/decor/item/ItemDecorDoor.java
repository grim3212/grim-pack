package com.grim3212.mc.pack.decor.item;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.decor.block.DecorBlocks;
import com.grim3212.mc.pack.decor.tile.TileEntityColorizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemDecorDoor extends ItemBlock implements IManualItem {

	public ItemDecorDoor() {
		super(DecorBlocks.decor_door);
		setCreativeTab(GrimCreativeTabs.GRIM_DECOR);
		this.setUnlocalizedName("decor_door_item");
		this.setRegistryName(new ResourceLocation(GrimPack.modID, "decor_door_item"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (facing != EnumFacing.UP) {
			return EnumActionResult.FAIL;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (!block.isReplaceable(worldIn, pos)) {
				pos = pos.offset(facing);
			}

			ItemStack stack = playerIn.getHeldItem(hand);
			if (playerIn.canPlayerEdit(pos, facing, stack) && this.block.canPlaceBlockAt(worldIn, pos)) {
				EnumFacing enumfacing = EnumFacing.fromAngle((double) playerIn.rotationYaw);
				int i = enumfacing.getFrontOffsetX();
				int j = enumfacing.getFrontOffsetZ();
				boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
				placeDoor(stack, worldIn, pos, enumfacing, this.block, flag);
				SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, playerIn);
				worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);
				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.FAIL;
			}
		}
	}

	private void placeDoor(ItemStack stack, World worldIn, BlockPos pos, EnumFacing facing, Block door, boolean isRightHinge) {
		BlockPos blockpos = pos.offset(facing.rotateY());
		BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
		int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
		int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
		boolean flag = worldIn.getBlockState(blockpos1).getBlock() == door || worldIn.getBlockState(blockpos1.up()).getBlock() == door;
		boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == door || worldIn.getBlockState(blockpos.up()).getBlock() == door;

		if ((!flag || flag1) && j <= i) {
			if (flag1 && !flag || j < i) {
				isRightHinge = false;
			}
		} else {
			isRightHinge = true;
		}

		BlockPos blockpos2 = pos.up();
		boolean flag2 = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(blockpos2);
		IBlockState iblockstate = door.getDefaultState().withProperty(BlockDoor.FACING, facing).withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT).withProperty(BlockDoor.POWERED, Boolean.valueOf(flag2)).withProperty(BlockDoor.OPEN, Boolean.valueOf(flag2));
		worldIn.setBlockState(pos, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 2);
		worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 2);
		worldIn.notifyNeighborsOfStateChange(pos, door, true);
		worldIn.notifyNeighborsOfStateChange(blockpos2, door, true);

		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityColorizer) {
			((TileEntityColorizer) tileentity).setBlockState(NBTHelper.getString(stack, "registryName"), NBTHelper.getInt(stack, "meta"));
		}

		TileEntity tileentity2 = worldIn.getTileEntity(blockpos2);
		if (tileentity2 instanceof TileEntityColorizer) {
			((TileEntityColorizer) tileentity2).setBlockState(NBTHelper.getString(stack, "registryName"), NBTHelper.getInt(stack, "meta"));
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		ItemStack itemstack = new ItemStack(this);
		NBTHelper.setString(itemstack, "registryName", Block.REGISTRY.getNameForObject(Blocks.AIR).toString());
		NBTHelper.setInteger(itemstack, "meta", 0);
		if (isInCreativeTab(tab))
			items.add(itemstack);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
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

	@Override
	public CreativeTabs getCreativeTab() {
		return super.getCreativeTab();
	}
}
