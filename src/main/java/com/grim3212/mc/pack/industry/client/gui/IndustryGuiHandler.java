package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerIronWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.inventory.ContainerMachine;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class IndustryGuiHandler implements IGuiHandler {

	public static final int ironGUI = 1;
	public static final int diamondGUI = 2;
	public static final int modernFurnaceGUI = 3;
	public static final int derrickGUI = 4;
	public static final int refineryGUI = 5;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if (ID == ironGUI) {
			return new ContainerIronWorkbench(player.inventory, world, pos);
		} else if (ID == diamondGUI) {
			return new ContainerDiamondWorkbench(player.inventory, world, pos);
		} else if (ID == modernFurnaceGUI) {
			return new ContainerMFurnace(player.inventory, (TileEntityMFurnace) world.getTileEntity(pos));
		} else if (ID == derrickGUI || ID == refineryGUI) {
			return new ContainerMachine(player.inventory, (TileEntityMachine) world.getTileEntity(pos));
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
		} else if (ID == modernFurnaceGUI) {
			return new GuiMFurnace(player.inventory, (TileEntityMFurnace) world.getTileEntity(pos));
		} else if (ID == derrickGUI || ID == refineryGUI) {
			return new GuiMachine(player.inventory, (TileEntityMachine) world.getTileEntity(pos));
		}

		return null;
	}

}
