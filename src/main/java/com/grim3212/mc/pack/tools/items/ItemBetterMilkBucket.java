package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.items.ItemBetterBucket.BucketFluidHandler;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBetterMilkBucket extends ItemManual {

	private final ItemBetterBucket parent;
	private boolean milkPause = false;

	public ItemBetterMilkBucket(ItemBetterBucket parent) {
		this.parent = parent;
		this.setMaxStackSize(1);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.milkBucket_page;
	}

	public void pauseForMilk() {
		milkPause = true;
	}

	public ItemBetterBucket getParent() {
		return parent;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		ItemStack stack = new ItemStack(this);
		ItemBetterBucket.setFluid(stack, "milk");
		ItemBetterBucket.setAmount(stack, parent.maxCapacity);
		subItems.add(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(I18n.format("tooltip.buckets.contains") + ": " + ItemBetterBucket.getAmount(stack) + "/" + parent.maxCapacity);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		// Everything in here is only used for milk buckets and what happens
		// when you are finished drinking them
		if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
			// Set amount and type, if empty
			int amount = ItemBetterBucket.getAmount(stack);
			ItemBetterBucket.setAmount(stack, amount - Fluid.BUCKET_VOLUME);
		}

		if (!worldIn.isRemote) {
			// Cure PotionEffects like a regular bucket of milk
			entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}

		if (entityLiving instanceof EntityPlayer) {
			((EntityPlayer) entityLiving).addStat(StatList.getObjectUseStats(this));
		}

		// Another check if the amount equals 0
		return parent.tryBreakBucket(stack);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		int amount = ItemBetterBucket.getAmount(itemStack);
		ItemBetterBucket.setAmount(itemStack, amount - Fluid.BUCKET_VOLUME);

		return parent.tryBreakBucket(itemStack);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return ItemBetterBucket.getAmount(stack) >= Fluid.BUCKET_VOLUME;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (milkPause) {
			milkPause = false;
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
		}

		playerIn.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		// Don't show if the bucket is empty
		if (ItemBetterBucket.getAmount(stack) <= 0)
			return false;
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		// Get remainder calculations from stored and maxAmount
		int reversedAmount = parent.maxCapacity - ItemBetterBucket.getAmount(stack);
		return (double) reversedAmount / (double) parent.maxCapacity;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new BucketFluidHandler(stack, parent.empty, parent.maxCapacity);
	}

}
