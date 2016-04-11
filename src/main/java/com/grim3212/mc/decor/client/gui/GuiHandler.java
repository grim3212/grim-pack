package com.grim3212.mc.decor.client.gui;

import com.grim3212.mc.decor.block.container.ContainerGrill;
import com.grim3212.mc.decor.tile.TileEntityGrill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));

		if ((ID == 0) && ((tileentity instanceof TileEntityGrill))) {
			return new ContainerGrill(player.inventory, (TileEntityGrill) tileentity);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileentity = world.getTileEntity(new BlockPos(x, y, z));

		if ((ID == 0) && ((tileentity instanceof TileEntityGrill))) {
			return new GuiGrill(player.inventory, (TileEntityGrill) tileentity);
		}
		return null;
	}

}
