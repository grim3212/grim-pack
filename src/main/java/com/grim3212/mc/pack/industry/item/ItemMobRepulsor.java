package com.grim3212.mc.pack.industry.item;

import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.industry.client.ManualIndustry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemMobRepulsor extends ItemManual {

	public boolean On;
	private int tickCount;
	private double upInc = 0.2D;

	public ItemMobRepulsor() {
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
		return ManualIndustry.mob_repulsor_page;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
		if (this.On) {
			List<Entity> entityList = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB((double) entityIn.getPosition().getX(), (double) entityIn.getPosition().getY(), (double) entityIn.getPosition().getZ(), (double) (entityIn.getPosition().getX() + 1), (double) (entityIn.getPosition().getY() + 1), (double) (entityIn.getPosition().getZ() + 1)).expand(16.0D, 16.0D, 16.0D));

			for (int i = 0; i < entityList.size(); i++) {
				Entity entity = (Entity) entityList.get(i);
				if ((entity instanceof EntityMob)) {
					//entity.setFire(4);
					//entity.setGlowing(true);
					//copied from applyEntityCollision removed code affecting player
					if (!entity.noClip && !entityIn.noClip)
					{
						double d0 = entity.posX - entityIn.posX;
						double d1 = entity.posZ - entityIn.posZ;
						double d2 = MathHelper.abs_max(d0, d1);

						if (d2 >= 0.009999999776482582D)
						{
							d2 = (double)MathHelper.sqrt_double(d2);
							d0 = d0 / d2;
							d1 = d1 / d2;
							double d3 = 1.0D / d2;

							if (d3 > 1.0D)
							{
								d3 = 1.0D;
							}

							d0 = d0 * d3;
							d1 = d1 * d3;
							//d0 = d0 * 0.05000000074505806D;
							//d1 = d1 * 0.05000000074505806D;
							d0 = d0 * this.upInc;
							d1 = d1 * this.upInc;
							d0 = d0 * (double)(1.0F - entityIn.entityCollisionReduction);
							d1 = d1 * (double)(1.0F - entityIn.entityCollisionReduction);

							if (!entity.isBeingRidden())
							{
								entity.addVelocity(d0, 0.0D, d1);
							}
						}
					}
				} else if ((entity instanceof EntityItem)) {
					Item id = ((EntityItem) entity).getEntityItem().getItem();
					if (id instanceof ItemArrow || id instanceof ItemFireball) {
						entity.motionX -= this.upInc / 2;
						entity.motionY += this.upInc / 1000;
						entity.motionZ -= this.upInc / 2;
					}
				}
			}
		}
		this.tickCount += 1;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		this.On = false;
		return true;
	}
}