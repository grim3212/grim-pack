package com.grim3212.mc.decor.item;

import java.util.LinkedHashMap;
import java.util.List;

import com.grim3212.mc.core.util.NBTHelper;
import com.grim3212.mc.decor.block.DecorBlocks;
import com.grim3212.mc.decor.tile.TileEntityTextured;
import com.grim3212.mc.decor.util.BlockHelper;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemLampPost extends Item {

	public ItemLampPost() {
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.air && worldIn.getBlockState(pos.up(2)).getBlock() == Blocks.air && worldIn.getBlockState(pos.up(3)).getBlock() == Blocks.air) {

			worldIn.setBlockState(pos.up(), DecorBlocks.lamp_post_bottom.getDefaultState(), 2);
			worldIn.setBlockState(pos.up(2), DecorBlocks.lamp_post_middle.getDefaultState(), 2);
			worldIn.setBlockState(pos.up(3), DecorBlocks.lamp_post_top.getDefaultState(), 2);

			TileEntity te_bottom = worldIn.getTileEntity(pos.up());
			TileEntity te_middle = worldIn.getTileEntity(pos.up(2));
			TileEntity te_top = worldIn.getTileEntity(pos.up(3));

			Block blockType = Block.getBlockById(NBTHelper.getInt(stack, "blockID"));
			if (te_bottom instanceof TileEntityTextured && te_middle instanceof TileEntityTextured && te_top instanceof TileEntityTextured) {
				((TileEntityTextured) te_bottom).setBlockID(NBTHelper.getInt(stack, "blockID"));
				((TileEntityTextured) te_bottom).setBlockMeta(NBTHelper.getInt(stack, "blockMeta"));

				((TileEntityTextured) te_middle).setBlockID(NBTHelper.getInt(stack, "blockID"));
				((TileEntityTextured) te_middle).setBlockMeta(NBTHelper.getInt(stack, "blockMeta"));

				((TileEntityTextured) te_top).setBlockID(NBTHelper.getInt(stack, "blockID"));
				((TileEntityTextured) te_top).setBlockMeta(NBTHelper.getInt(stack, "blockMeta"));

				worldIn.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), blockType.stepSound.getPlaceSound(), (blockType.stepSound.getVolume() + 1.0F) / 2.0F, blockType.stepSound.getFrequency() * 0.8F);
			}
		}

		return false;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
		LinkedHashMap<Block, Integer> loadedBlocks = BlockHelper.getBlocks();
		Block[] blocks = loadedBlocks.keySet().toArray(new Block[loadedBlocks.keySet().size()]);

		for (int i = 0; i < blocks.length; i++) {
			if (loadedBlocks.get(blocks[i]) == 0) {
				ItemStack stack = new ItemStack(item, 1);
				NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
				NBTHelper.setInteger(stack, "blockMeta", 0);
				list.add(stack);
			} else {
				for (int j = 0; j < loadedBlocks.get(blocks[i]); j++) {
					ItemStack stack = new ItemStack(item, 1);
					NBTHelper.setInteger(stack, "blockID", Block.getIdFromBlock(blocks[i]));
					NBTHelper.setInteger(stack, "blockMeta", j);
					list.add(stack);
				}
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.getBlockById(NBTHelper.getInt(stack, "blockID")).getUnlocalizedName() + " " + DecorItems.lamp_item.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocal(new ItemStack(Block.getBlockById(NBTHelper.getInt(stack, "blockID")), 1, NBTHelper.getInt(stack, "blockMeta")).getDisplayName()) + " " + StatCollector.translateToLocal(DecorItems.lamp_item.getUnlocalizedName() + ".name");
	}
}
