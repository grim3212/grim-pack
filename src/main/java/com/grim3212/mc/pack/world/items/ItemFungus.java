package com.grim3212.mc.pack.world.items;

import com.grim3212.mc.pack.world.blocks.BlockFungusBuilding;
import com.grim3212.mc.pack.world.blocks.BlockFungusGrowing;
import com.grim3212.mc.pack.world.blocks.BlockFungusKilling;
import com.grim3212.mc.pack.world.blocks.WorldBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemFungus extends ItemBlock {

	public ItemFungus(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(Integer.toString(itemstack.getItemDamage())).toString();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		if (stack != null) {
			int meta = stack.getItemDamage();

			if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_growing)) {
				return BlockFungusGrowing.color[meta];
			}
			if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_building)) {
				return BlockFungusBuilding.color[meta];
			}
			if (stack.getItem() == Item.getItemFromBlock(WorldBlocks.fungus_killing)) {
				return BlockFungusKilling.color[meta];
			}
		}
		return 0xffffff;
	}
}
