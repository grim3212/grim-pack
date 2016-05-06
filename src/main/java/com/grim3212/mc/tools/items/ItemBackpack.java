package com.grim3212.mc.tools.items;

import java.util.List;

import com.grim3212.mc.tools.GrimTools;
import com.grim3212.mc.tools.client.gui.ToolsGuiHandler;
import com.grim3212.mc.tools.inventory.ContainerBackpack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBackpack extends Item {

	public static final String[] colorNumbers = new String[] { "454554", "BE3030", "667F33", "704425", "3366CC", "B266E5", "4C99B2", "999999", "4C4C4C", "F2B2CC", "7FCC19", "E5E533", "99B2F2", "E57FD8", "F4B33F", "FFFFFF" };

	public ItemBackpack() {
		this.maxStackSize = 1;
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
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityPlayer) {
		if (!par2World.isRemote) {
			entityPlayer.openGui(GrimTools.INSTANCE, ToolsGuiHandler.backpackGUI_ID, entityPlayer.worldObj, (int) entityPlayer.posX, (int) entityPlayer.posY, (int) entityPlayer.posZ);
		}
		return par1ItemStack;
	}

	public int getColor(ItemStack itemStack) {
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

	public void setColor(ItemStack itemStack, int color) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
		}
		nbtTagCompound.setInteger("color", color);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
		if (renderPass == 1)
			return Integer.parseInt(colorNumbers[15], 16);
		else {
			int packColor = this.getColor(itemStack);

			if (packColor < 0) {
				return Integer.parseInt("C65C35", 16);
			} else {
				return Integer.parseInt(colorNumbers[packColor], 16);
			}
		}
	}
}
