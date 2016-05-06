package com.grim3212.mc.tools.client.gui;

import com.grim3212.mc.tools.inventory.BackpackInventory;
import com.grim3212.mc.tools.inventory.ContainerBackpack;
import com.grim3212.mc.tools.inventory.ContainerCustomWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ToolsGuiHandler implements IGuiHandler {

	public static int backpackGUI_ID = 0;
	public static int workbenchGUI_ID = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == backpackGUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.inventory.getCurrentItem(), player, 0);
			return new ContainerBackpack(backpackInventory, player.inventory);
		} else if (ID == workbenchGUI_ID) {
			return new ContainerCustomWorkbench(player.inventory, world, new BlockPos(x, y, z));
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == backpackGUI_ID) {
			BackpackInventory backpackInventory = new BackpackInventory(player.inventory.getCurrentItem(), player, 0);
			return new GuiBackpack(backpackInventory, player.inventory);
		} else if (ID == workbenchGUI_ID) {
			return new GuiPortable(player.inventory, world, new BlockPos(x, y, z), player.getCurrentEquippedItem());
		}

		return null;
	}

}
