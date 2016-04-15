package com.grim3212.mc.industry.client.gui;

import com.grim3212.mc.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.industry.inventory.ContainerIronWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class IndustryGuiHandler implements IGuiHandler {

	public static final int ironGUI = 1;
	public static final int diamondGUI = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID == ironGUI) {
			return new ContainerIronWorkbench(player.inventory, world, pos);
		} else if (ID == diamondGUI) {
			return new ContainerDiamondWorkbench(player.inventory, world, pos);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID == ironGUI) {
			return new GuiCraftingIron(player, world, pos);
		} else if (ID == diamondGUI) {
			return new GuiCraftingDiamond(player, world, pos);
		}

		return null;
	}

}
