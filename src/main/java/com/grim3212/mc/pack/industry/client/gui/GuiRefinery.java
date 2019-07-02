package com.grim3212.mc.pack.industry.client.gui;

import com.grim3212.mc.pack.industry.tile.TileEntityMachine;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerInventory;

public class GuiRefinery extends GuiMachine {

	public GuiRefinery(PlayerInventory playerInv, TileEntityMachine te) {
		super(playerInv, te, "textures/gui/gui_refinery.png");
	}

}
