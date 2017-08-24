package com.grim3212.mc.pack.tools.items;

import org.apache.commons.lang3.tuple.Pair;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.util.ChiselRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.oredict.OreDictionary;

public class ItemChisel extends ItemManual {

	public ItemChisel(int multiplier) {
		super(multiplier == 1 ? "iron_chisel" : "diamond_chisel");
		maxStackSize = 1;
		setMaxDamage(32 * multiplier);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.chisel_page;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);

		Pair<Block, Integer> check = Pair.of(state.getBlock(), state.getBlock().getMetaFromState(state));

		if (ChiselRegistry.chiselBlocks.contains(check)) {
			int bPos = ChiselRegistry.chiselBlocks.indexOf(check);

			int j1 = worldIn.rand.nextInt(100);
			worldIn.setBlockState(pos, ChiselRegistry.getState(bPos, worldIn, pos, facing, hitX, hitY, hitZ, playerIn, hand));
			float f = 0.7F;
			float f1 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			float f2 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			float f3 = worldIn.rand.nextFloat() * f + (1.0F - f) * 0.5F;
			int k1 = 0;
			if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
				if (k1 >= 0) {
					NonNullList<ItemStack> items = ChiselRegistry.chiselItem.get(bPos);
					int enchantLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, playerIn.getHeldItem(hand));

					if (items.isEmpty()) {
						NonNullList<ItemStack> drops = NonNullList.create();
						state.getBlock().getDrops(drops, worldIn, pos, state, enchantLevel);
						items = drops;
					} else {
						int count = ChiselRegistry.chiselBlocks.get(bPos).getLeft().quantityDroppedWithBonus(enchantLevel, worldIn.rand);

						if (enchantLevel > 0) {
							for (ItemStack stack : items) {
								stack.setCount(count);
							}
						}
					}

					for (ItemStack stack : items) {
						EntityItem entItem = new EntityItem(worldIn, (double) pos.getX() + f1, (double) pos.getY() + f2, (double) pos.getZ() + f3, stack);
						worldIn.spawnEntity(entItem);

						if (!(playerIn instanceof FakePlayer)) {
							entItem.onCollideWithPlayer(playerIn);
						}
					}

					if (j1 >= 94) {
						playerIn.getHeldItem(hand).damageItem(1, playerIn);
					}

					playerIn.getHeldItem(hand).damageItem(1, playerIn);
					worldIn.playSound((EntityPlayer) null, pos, state.getBlock().getSoundType(state, worldIn, pos, null).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
					return EnumActionResult.FAIL;
				}
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FORTUNE);
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		ItemStack mat = toRepair.getItem() == ToolsItems.iron_chisel ? new ItemStack(Items.IRON_INGOT) : new ItemStack(Items.DIAMOND);
		if (!mat.isEmpty() && OreDictionary.itemMatches(mat, repair, false))
			return true;
		return super.getIsRepairable(toRepair, repair);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment == Enchantments.FORTUNE;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return stack.getItem() == ToolsItems.iron_chisel ? 14 : 10;
	}
}
