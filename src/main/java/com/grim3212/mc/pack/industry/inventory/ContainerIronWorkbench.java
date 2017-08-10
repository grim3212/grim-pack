package com.grim3212.mc.pack.industry.inventory;

import java.util.Iterator;

import com.grim3212.mc.pack.core.util.RecipeHelper;
import com.grim3212.mc.pack.industry.block.IndustryBlocks;
import com.grim3212.mc.pack.industry.config.IndustryConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerIronWorkbench extends ContainerWorkbench {

	private EntityPlayer player;
	private World worldObj;
	private BlockPos pos;
	private boolean isPortable;

	public ContainerIronWorkbench(EntityPlayer player, World world, BlockPos pos, boolean isPortable) {
		super(player.inventory, world, pos);
		this.player = player;
		this.worldObj = world;
		this.pos = pos;
		this.isPortable = isPortable;
	}

	@Override
	public void onCraftMatrixChanged(IInventory iinventory) {
		if (!worldObj.isRemote) {

			ItemStack recipeStack = ItemStack.EMPTY;
			EntityPlayerMP entityplayermp = (EntityPlayerMP) player;
			IRecipe found = CraftingManager.findMatchingRecipe(this.craftMatrix, this.worldObj);

			if (found != null && (found.isHidden() || !this.worldObj.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.getRecipeBook().containsRecipe(found))) {
				this.craftResult.setRecipeUsed(found);
				recipeStack = found.getCraftingResult(this.craftMatrix);
			}

			// Reset slot contents so that weird issues stop occuring
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, ItemStack.EMPTY));

			if (IndustryConfig.useWhitelist) {
				Iterator<ItemStack> itr = IndustryConfig.workbenchUpgradeList.iterator();

				while (itr.hasNext()) {
					ItemStack stack = itr.next();

					if (RecipeHelper.compareItemStacks(recipeStack, stack)) {
						this.craftResult.setInventorySlotContents(0, recipeStack);
						entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, recipeStack));
						break;
					}
				}
			} else {
				Iterator<ItemStack> itr = IndustryConfig.workbenchUpgradeList.iterator();

				boolean blacklisted = false;

				while (itr.hasNext()) {
					ItemStack stack = itr.next();

					if (RecipeHelper.compareItemStacks(recipeStack, stack)) {
						blacklisted = true;
						break;
					}
				}

				if (!blacklisted) {
					this.craftResult.setInventorySlotContents(0, recipeStack);
					entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, recipeStack));
				}
			}

			if (!craftResult.getStackInSlot(0).isEmpty()) {
				if (IndustryConfig.useWorkbenchUpgrades)
					if (craftResult.getStackInSlot(0).getCount() >= 64) {
						craftResult.getStackInSlot(0).setCount(127);
					} else {
						craftResult.getStackInSlot(0).setCount(craftResult.getStackInSlot(0).getCount() * 2);
					}
			} else {
				// If it returned a empty value then it was either blacklisted,
				// whitelisted or didn't have an item so just try and to craft
				// with the default recipe
				if (IndustryConfig.returnDefaultIfListed)
					this.slotChangedCraftingGrid(this.worldObj, this.player, this.craftMatrix, this.craftResult);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return isPortable ? true : this.worldObj.getBlockState(this.pos).getBlock() != IndustryBlocks.iron_workbench ? false : playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
}
