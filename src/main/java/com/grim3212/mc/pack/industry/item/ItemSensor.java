package com.grim3212.mc.pack.industry.item;

import java.util.List;

import com.grim3212.mc.pack.industry.block.IndustryBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSensor extends ItemBlock {

	public ItemSensor(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (this.block == IndustryBlocks.wooden_sensor) {
			tooltip.add("Detects all entities");
		} else if (this.block == IndustryBlocks.stone_sensor) {
			tooltip.add("Detects only living entities");
		} else if (this.block == IndustryBlocks.iron_sensor) {
			tooltip.add("Detects the player");
		} else if (this.block == IndustryBlocks.netherrack_sensor) {
			tooltip.add("Detects only hostile entities");
		}
	}
}
