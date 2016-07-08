package com.grim3212.mc.pack.core.client.gui;

import com.grim3212.mc.pack.core.manual.gui.GuiManualIndex;
import com.grim3212.mc.pack.decor.client.gui.GuiGrill;
import com.grim3212.mc.pack.decor.inventory.ContainerGrill;
import com.grim3212.mc.pack.decor.tile.TileEntityGrill;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingDiamond;
import com.grim3212.mc.pack.industry.client.gui.GuiCraftingIron;
import com.grim3212.mc.pack.industry.client.gui.GuiMFurnace;
import com.grim3212.mc.pack.industry.client.gui.GuiMachine;
import com.grim3212.mc.pack.industry.inventory.ContainerDiamondWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerIronWorkbench;
import com.grim3212.mc.pack.industry.inventory.ContainerMFurnace;
import com.grim3212.mc.pack.industry.inventory.ContainerMachine;
import com.grim3212.mc.pack.industry.tile.TileEntityMFurnace;
import com.grim3212.mc.pack.industry.tile.TileEntityMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class PackGuiHandler implements IGuiHandler {

	public static final int MANUAL_GUI_ID = 0;
	public static final int GRILL_GUI_ID = 1;
	public static final int IRON_GUI_ID = 2;
	public static final int DIAMOND_GUI_ID = 3;
	public static final int MODERN_FURNACE_GUI_ID = 4;
	public static final int DERRICK_GUI_ID = 5;
	public static final int REFINERY_GUI_ID = 6;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileentity = world.getTileEntity(pos);

		if ((ID == GRILL_GUI_ID) && ((tileentity instanceof TileEntityGrill))) {
			return new ContainerGrill(player.inventory, (TileEntityGrill) tileentity);
		} else if (ID == IRON_GUI_ID) {
			return new ContainerIronWorkbench(player.inventory, world, pos);
		} else if (ID == DIAMOND_GUI_ID) {
			return new ContainerDiamondWorkbench(player.inventory, world, pos);
		} else if (ID == MODERN_FURNACE_GUI_ID) {
			return new ContainerMFurnace(player.inventory, (TileEntityMFurnace) world.getTileEntity(pos));
		} else if (ID == DERRICK_GUI_ID || ID == REFINERY_GUI_ID) {
			return new ContainerMachine(player.inventory, (TileEntityMachine) world.getTileEntity(pos));
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity tileentity = world.getTileEntity(pos);

		if (ID == MANUAL_GUI_ID) {
			return GuiManualIndex.activeManualPage;
		} else if (ID == IRON_GUI_ID) {
			return new GuiCraftingIron(player, world, pos);
		} else if (ID == DIAMOND_GUI_ID) {
			return new GuiCraftingDiamond(player, world, pos);
		} else if (ID == MODERN_FURNACE_GUI_ID) {
			return new GuiMFurnace(player.inventory, (TileEntityMFurnace) world.getTileEntity(pos));
		} else if (ID == DERRICK_GUI_ID || ID == REFINERY_GUI_ID) {
			return new GuiMachine(player.inventory, (TileEntityMachine) world.getTileEntity(pos));
		} else if ((ID == GRILL_GUI_ID) && ((tileentity instanceof TileEntityGrill))) {
			return new GuiGrill(player.inventory, (TileEntityGrill) tileentity);
		}

		return null;
	}

}
