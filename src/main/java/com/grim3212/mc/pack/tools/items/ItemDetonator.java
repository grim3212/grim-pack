package com.grim3212.mc.pack.tools.items;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntityDetonator;
import com.grim3212.mc.pack.tools.util.EnumDetonatorType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemDetonator extends ItemManual {

	private final EnumDetonatorType type;

	public ItemDetonator(EnumDetonatorType type) {
		super(type.getUnlocalized());
		this.type = type;
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.detonators_page;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);

		if (!worldIn.isRemote) {
			EntityDetonator detonator = new EntityDetonator(worldIn, playerIn, type);

			if (detonator != null) {
				detonator.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, type.getThrowSpeed(), 1.0F);
				if (!playerIn.isCreative())
					stack.shrink(1);
				worldIn.spawnEntity(detonator);
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	public EnumDetonatorType getType() {
		return type;
	}
}
