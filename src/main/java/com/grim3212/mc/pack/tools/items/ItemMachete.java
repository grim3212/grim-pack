package com.grim3212.mc.pack.tools.items;

import java.util.HashSet;

import com.google.common.collect.Sets;
import com.grim3212.mc.pack.core.item.ItemManualTool;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemMachete extends ItemManualTool {

	public static final HashSet<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {});
	public static Material[] material = new Material[] { Material.LEAVES, Material.CLOTH, Material.CACTUS, Material.VINE, Material.WEB };

	protected ItemMachete(String name, ToolMaterial material) {
		super(name, 3.2f, -2.15f, material, blocksEffectiveAgainst);
		setMaxStackSize(1);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		if (stack.getItem() == ToolsItems.machete_slime)
			return ManualTools.macheteSlime_page;

		return ManualTools.machete_page;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		for (int i = 0; i < material.length; ++i) {
			if (material[i] == state.getMaterial()) {
				return this.efficiency;
			}
		}
		return super.getDestroySpeed(stack, state);
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
