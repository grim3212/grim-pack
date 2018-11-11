package com.grim3212.mc.pack.tools.items;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.magic.BaseMagic;
import com.grim3212.mc.pack.tools.magic.MagicRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemMagicWand extends ItemManual {

	public ItemMagicWand() {
		super("magic_wand");
		setMaxStackSize(1);
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		BaseMagic magic = MagicRegistry.getMagic(stack);

		if (magic != null) {
			return super.getUnlocalizedName() + "." + magic.getMagicName();
		}

		return super.getUnlocalizedName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab))
			for (int j = 0; j < MagicRegistry.getLoadedMagic().size(); ++j) {
				ItemStack wand = new ItemStack(this, 1);
				NBTHelper.setInteger(wand, "WandType", MagicRegistry.getLoadedMagic().get(j).getStoneType().getMeta());
				items.add(wand);
			}
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		BaseMagic magic = MagicRegistry.getMagic(stack);

		return magic != null ? magic.getMaxUses() : 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		BaseMagic magic = MagicRegistry.getMagic(stack);

		if (magic != null) {
			// Get the amount of damage left
			int damageLeft = stack.getMaxDamage() - stack.getItemDamage();
			// Possibly modify range later on
			int damage = magic.performMagic(worldIn, playerIn, handIn, damageLeft, magic.getBaseRange());

			playerIn.swingArm(handIn);

			if (!playerIn.isCreative()) {
				stack.damageItem(damage, playerIn);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}