package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.industry.tile.TileEntityMachine;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiDerrick extends GuiMachine {

	public GuiDerrick(InventoryPlayer playerInv, TileEntityMachine te) {
		super(playerInv, te, "textures/gui/gui_derrick.png");
	}

}
