package com.grim3212.mc.pack.cuisine.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.cuisine.client.ManualCuisine;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSodaBottle extends ItemManual {

	public static final String[] sodaTypes = new String[] { "apple", "bottle", "slurm", "co2", "golden_apple", "spiked_orange", "root_beer", "cream_orange", "diamond", "orange", "cocoa", "carbonated_water", "mushroom" };

	public ItemSodaBottle() {
		super("soda");
		maxStackSize = 16;
		setHasSubtypes(true);
		setMaxDamage(0);
		this.setCreativeTab(GrimCreativeTabs.GRIM_CUISINE);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("grimpack.cuisine.soda.tooltip." + sodaTypes[stack.getItemDamage()]));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		ItemStack itemStackIn = playerIn.getHeldItem(hand);

		if (playerIn.shouldHeal()) {

			if (itemStackIn.getItemDamage() == 0) {
				playerIn.heal(10);
			}
			if (itemStackIn.getItemDamage() == 2) {
				playerIn.heal(3);
			}
			if (itemStackIn.getItemDamage() == 4) {
				playerIn.heal(20);
			}
			if (itemStackIn.getItemDamage() == 6) {
				playerIn.heal(5);
			}
			if (itemStackIn.getItemDamage() == 7) {
				playerIn.heal(10);
			}
			if (itemStackIn.getItemDamage() == 8) {
				playerIn.heal(20);
			}
			if (itemStackIn.getItemDamage() == 9) {
				playerIn.heal(8);
			}
			if (itemStackIn.getItemDamage() == 10) {
				playerIn.heal(14);
			}
			if (itemStackIn.getItemDamage() == 11) {
				playerIn.heal(2);
			}
			if (itemStackIn.getItemDamage() == 12) {
				playerIn.heal(5);
			}
			itemStackIn.shrink(1);
		}
		if (itemStackIn.getItemDamage() == 5) {
			playerIn.attackEntityFrom(DamageSource.GENERIC, 8.0F);
			itemStackIn.shrink(1);
		} else {
			playerIn.heal(0);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp(par1ItemStack.getItemDamage(), 0, 13);
		return super.getUnlocalizedName() + "." + sodaTypes[i];
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab))
			for (int j = 0; j < 13; ++j) {
				items.add(new ItemStack(this, 1, j));
			}
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getMetadata() == 1 || stack.getMetadata() == 3 || stack.getMetadata() == 11)
			return ManualCuisine.carbon_page;

		return ManualCuisine.soda_page;
	}
}
