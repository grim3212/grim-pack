package com.grim3212.mc.pack.core.item;

import java.util.Set;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualItem;
import com.grim3212.mc.pack.core.manual.pages.Page;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;

public class ItemManualTool extends ItemTool implements IManualItem {

	protected ItemManualTool(String name, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
		this(name, 0.0f, 0.0f, materialIn, effectiveBlocksIn);
	}

	protected ItemManualTool(String name, float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn) {
		super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
		setUnlocalizedName(name);
		setRegistryName(new ResourceLocation(GrimPack.modID, name));
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

}
