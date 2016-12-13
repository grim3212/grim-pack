package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.util.ChiselRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChisel extends ItemManual {

	private int type;

	public ItemChisel(int multiplier) {
		maxStackSize = 1;
		setMaxDamage(32 * multiplier);
		setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.chisel_page;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if (ChiselRegistry.chiselBlocks.contains(block)) {
			int j1 = worldIn.rand.nextInt(100);
			type = ChiselRegistry.chiselBlocks.indexOf(block);
			worldIn.setBlockState(pos, ChiselRegistry.chiselReturnb.get(type).getDefaultState());
			float f = 0.7F;
			float f1 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			float f2 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			float f3 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			int k1 = 0;
			if (!worldIn.isRemote) {

				if (k1 >= 0) {
					Item item = ChiselRegistry.chiselItem.get(type);
					int l1 = ChiselRegistry.chiselItemQuantity.get(type).intValue();
					int i2 = ChiselRegistry.chiselItemDamage.get(type).intValue();
					worldIn.spawnEntity(new EntityItem(worldIn, (double) pos.getX() + f1, (double) pos.getY() + f2, (double) pos.getZ() + f3, new ItemStack(item, l1, i2)));

					if (j1 >= 94) {
						EntityItem items = new EntityItem(worldIn, (float) pos.getX() + f1, (float) pos.getY() + f2, (float) pos.getZ() + f3, new ItemStack(item, l1, i2));
						worldIn.spawnEntity(items);
						playerIn.getHeldItem(hand).damageItem(1, playerIn);
					}

					playerIn.getHeldItem(hand).damageItem(1, playerIn);
					worldIn.playSound((EntityPlayer) null, pos, block.getSoundType(state, worldIn, pos, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					return EnumActionResult.FAIL;
				}
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
