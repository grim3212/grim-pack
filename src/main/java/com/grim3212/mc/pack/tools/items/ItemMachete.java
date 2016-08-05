package com.grim3212.mc.pack.tools.items;

import java.util.HashSet;

import com.google.common.collect.Sets;
import com.grim3212.mc.pack.tools.GrimTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class ItemMachete extends ItemTool {

	public static final HashSet<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {});
	public static Material[] material = new Material[] { Material.LEAVES, Material.CLOTH, Material.CACTUS, Material.VINE, Material.WEB };

	protected ItemMachete(ToolMaterial material) {
		super(3.2f, -2.15f, material, blocksEffectiveAgainst);
		setMaxStackSize(1);
		setCreativeTab(GrimTools.INSTANCE.getCreativeTab());
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		for (int i = 0; i < material.length; ++i) {
			if (material[i] == state.getMaterial()) {
				return this.efficiencyOnProperMaterial;
			}
		}
		return super.getStrVsBlock(stack, state);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (stack.getItem() != ToolsItems.machete_slime) {
			stack.damageItem(1, attacker);
			return true;
		} else {
			stack.damageItem(2, attacker);
			byte var4 = 1;
			double var5 = attacker.posX - attacker.posX;

			double var7;
			for (var7 = attacker.posZ - attacker.posZ; var5 * var5 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
				var5 = (Math.random() - Math.random()) * 0.01D;
			}

			target.knockBack(attacker, var4, -var5, -var7);
			return true;
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return state.getBlock() == Blocks.WEB;
	}

	@Override
	public boolean isFull3D() {
		return true;
	}
}
