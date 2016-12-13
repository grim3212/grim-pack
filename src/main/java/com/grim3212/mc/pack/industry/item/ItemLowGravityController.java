package com.grim3212.mc.pack.industry.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemLowGravityController extends ItemManual {

	public boolean On;
	private int tickCount;

	public ItemLowGravityController() {
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		if (this.tickCount > 10) {
			this.On = (!this.On);
			this.tickCount = 0;
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualIndustry.controller_page;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
		if (this.On) {
			List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB((double) entityIn.getPosition().getX(), (double) entityIn.getPosition().getY(), (double) entityIn.getPosition().getZ(), (double) (entityIn.getPosition().getX() + 1), (double) (entityIn.getPosition().getY() + 1), (double) (entityIn.getPosition().getZ() + 1)).expand(16.0D, 16.0D, 16.0D));

			for (int i = 0; i < entityList.size(); i++) {
				if (((Entity) entityList.get(i)).motionY < 2.0D)
					((Entity) entityList.get(i)).motionY += 0.05D;
			}
		}
		this.tickCount += 1;
	}
}