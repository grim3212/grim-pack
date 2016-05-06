package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.core.util.NBTHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ItemBetterMilkBucket extends Item implements IFluidContainerItem {

	private final ItemBetterBucket parent;
	private boolean milkPause = false;

	public ItemBetterMilkBucket(ItemBetterBucket parent) {
		this.parent = parent;
	}

	public void pauseForMilk() {
		milkPause = true;
	}

	public ItemBetterBucket getParent() {
		return parent;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		ItemStack stack = new ItemStack(this);
		NBTHelper.setString(stack, "FluidName", "milk");
		NBTHelper.setInteger(stack, "Amount", parent.maxCapacity);
		subItems.add(stack);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		tooltip.add(StatCollector.translateToLocal("tooltip.buckets.contains") + ": " + NBTHelper.getInt(stack, "Amount") + "/" + parent.maxCapacity);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// Everything in here is only used for milk buckets and what happens
		// when you are finished drinking them
		if (!playerIn.capabilities.isCreativeMode) {
			// Set amount and type, if empty
			int amount = NBTHelper.getInt(stack, "Amount");
			NBTHelper.setInteger(stack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);
		}

		if (!worldIn.isRemote) {
			// Cure PotionEffects like a regular bucket of milk
			playerIn.curePotionEffects(new ItemStack(Items.milk_bucket));
		}

		playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);

		// Another check if the amount equals 0
		return parent.tryBreakBucket(stack);
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		int amount = NBTHelper.getInt(itemStack, "Amount");
		NBTHelper.setInteger(itemStack, "Amount", amount - FluidContainerRegistry.BUCKET_VOLUME);

		return parent.tryBreakBucket(itemStack);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return NBTHelper.getInt(stack, "Amount") >= FluidContainerRegistry.BUCKET_VOLUME;
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
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
		if (milkPause) {
			milkPause = false;
			return itemStackIn;
		}

		playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		// Don't show if the bucket is empty
		if (NBTHelper.getInt(stack, "Amount") <= 0)
			return false;
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		// Get remainder calculations from stored and maxAmount
		int reversedAmount = parent.maxCapacity - NBTHelper.getInt(stack, "Amount");
		return (double) reversedAmount / (double) parent.maxCapacity;
	}

	@Override
	public FluidStack getFluid(ItemStack container) {
		return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
	}

	@Override
	public int getCapacity(ItemStack container) {
		return parent.maxCapacity;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill) {
		// has to be exactly 1, must be handled from the caller
		if (container.stackSize != 1) {
			return 0;
		}

		// can only fill exact capacity
		if (resource == null || resource.amount % FluidContainerRegistry.BUCKET_VOLUME != 0) {
			return 0;
		}

		// fill the container
		if (doFill) {
			NBTHelper.setString(container, "FluidName", FluidRegistry.getFluidName(resource.getFluid()));
			NBTHelper.setInteger(container, "Amount", NBTHelper.getInt(container, "Amount") + resource.amount);
		} else {
			if (!ItemBetterBucket.isEmptyOrContains(container, FluidRegistry.getFluidName(resource.getFluid()))) {
				return 0;
			}
		}
		return resource.amount;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
		if (maxDrain % FluidContainerRegistry.BUCKET_VOLUME != 0 || maxDrain > parent.maxCapacity) {
			return null;
		}

		if (doDrain) {
			int amount = NBTHelper.getInt(container, "Amount");
			NBTHelper.setInteger(container, "Amount", amount - maxDrain);
		}

		ItemStack stack = tryBreakBucket(container);

		if (stack.getItem() != this) {
			container.setItem(stack.getItem());
			container.setTagCompound(stack.getTagCompound());
		}

		return getFluid(container);
	}

	public ItemStack tryBreakBucket(ItemStack stack) {
		if (NBTHelper.getInt(stack, "Amount") <= 0)
			return parent.tryBreakBucket(stack);
		else
			return stack;
	}
}
