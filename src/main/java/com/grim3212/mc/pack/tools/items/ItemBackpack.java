package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.inventory.ContainerBackpack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBackpack extends ItemManual {

	public static final String[] colorNumbers = new String[] { "454554", "BE3030", "667F33", "704425", "3366CC", "B266E5", "4C99B2", "999999", "4C4C4C", "F2B2CC", "7FCC19", "E5E533", "99B2F2", "E57FD8", "F4B33F", "FFFFFF" };

	public ItemBackpack() {
		this.maxStackSize = 1;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.backpack_page;
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> list) {
		for (int i = 0; i < 17; i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("color", i - 1);

			ItemStack backpack = new ItemStack(ToolsItems.backpack);
			backpack.setTagCompound(nbt);

			list.add(backpack);
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		String colors[] = { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white" };
		if (par1ItemStack.hasTagCompound()) {
			NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();
			if (nbttagcompound != null) {
				if (nbttagcompound.hasKey("color") && nbttagcompound.getInteger("color") != -1) {
					return super.getUnlocalizedName() + "_" + colors[nbttagcompound.getInteger("color")];
				}
			}
		}
		return super.getUnlocalizedName() + "_" + "leather";
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInventory, boolean isCurrentItem) {

		if (world.isRemote || !isCurrentItem) {
			return;
		}
		if (ContainerBackpack.notify) {
			ContainerBackpack.saveToNBT(itemStack);
			ContainerBackpack.notify = false;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (!worldIn.isRemote) {
			playerIn.openGui(GrimPack.INSTANCE, hand == EnumHand.MAIN_HAND ? PackGuiHandler.BACKPACK_MAIN_GUI_ID : PackGuiHandler.BACKPACK_OFF_GUI_ID, playerIn.worldObj, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	public static int getColor(ItemStack itemStack) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();

		if (nbtTagCompound == null) {
			return -1;
		}
		if (nbtTagCompound.hasKey("color")) {
			return nbtTagCompound.getInteger("color");
		} else {
			return -1;
		}
	}

	public static void setColor(ItemStack itemStack, int color) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
		}
		nbtTagCompound.setInteger("color", color);
	}
}
