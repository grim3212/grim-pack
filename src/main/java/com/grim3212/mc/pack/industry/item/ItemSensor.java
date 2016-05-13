package com.grim3212.mc.pack.industry.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSensor extends ItemBlock {

	private int type;

	public ItemSensor(Block block, Integer type) {
		super(block);
		this.type = type;
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer par2EntityPlayer, List<String> list, boolean par4) {
		switch (type) {
		case 0:
			list.add("Detects all entities");
			break;
		case 1:
			list.add("Detects only living entities");
			break;
		case 2:
			list.add("Detects the player");
			break;
		case 3:
			list.add("Detects only hostile entities");
			break;
		}
	}
}
