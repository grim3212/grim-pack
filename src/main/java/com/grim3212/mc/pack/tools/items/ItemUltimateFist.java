package com.grim3212.mc.pack.tools.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.grim3212.mc.pack.core.item.ItemManualTool;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemUltimateFist extends ItemManualTool {

	public ItemUltimateFist() {
		super("ultimate_fist", ToolMaterial.DIAMOND, Sets.<Block>newHashSet());
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return ToolsConfig.fistHasDurability ? ToolsConfig.fistDurabilityAmount : 0;
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.ultimateFist_page;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return true;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
		if (slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", ToolsConfig.fistEntityDamage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", ToolsConfig.fistAttackSpeed, 0));
		}
		return multimap;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return ToolsConfig.fistBlockBreakSpeed;
	}
}
