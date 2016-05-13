package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.client.gui.ToolsGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPortableWorkbench extends Item {

	public ItemPortableWorkbench() {
		super();
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.openGui(GrimTools.INSTANCE, ToolsGuiHandler.workbenchGUI_ID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
		}

		return itemstack;
	}

}
