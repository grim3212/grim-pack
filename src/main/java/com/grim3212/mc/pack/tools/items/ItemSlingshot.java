package com.grim3212.mc.pack.tools.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.grim3212.mc.pack.core.item.ItemManual;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.core.part.GrimCreativeTabs;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.core.util.Utils;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.entity.EntitySlingPellet;
import com.grim3212.mc.pack.tools.util.EnumPelletType;
import com.grim3212.mc.pack.tools.util.EnumSlingshotModes;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemSlingshot extends ItemManual {

	public ItemSlingshot() {
		super("sling_shot");
		this.maxStackSize = 1;
		setCreativeTab(GrimCreativeTabs.GRIM_TOOLS);
	}

	@Override
	public Page getPage(ItemStack stack) {
		return ManualTools.slingshot_page;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String mode = NBTHelper.getString(stack, "Mode");
		tooltip.add(I18n.format("tooltip.slingshot.currentMode") + I18n.format("grimtools.slingshot." + (mode.isEmpty() ? EnumSlingshotModes.RANDOM.getUnlocalized() : mode)));
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTHelper.setString(stack, "Mode", EnumSlingshotModes.RANDOM.getUnlocalized());
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		ItemStack self = new ItemStack(this, 1, 0);
		if (isInCreativeTab(tab))
			items.add(NBTHelper.setStringItemStack(self, "Mode", EnumSlingshotModes.RANDOM.getUnlocalized()));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		// Will set mode if there is no value
		EnumSlingshotModes mode = EnumSlingshotModes.getFromString(NBTHelper.getString(playerIn.getHeldItem(hand), "Mode"));

		if (playerIn.capabilities.isCreativeMode) {
			EnumPelletType ammo = this.getAmmo(mode, playerIn, worldIn, true);

			worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if (!worldIn.isRemote) {
				EntitySlingPellet pellet = new EntitySlingPellet(worldIn, playerIn, ammo);
				pellet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
				worldIn.spawnEntity(pellet);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
		}

		if (!playerIn.capabilities.isCreativeMode) {

			// Get type of ammo required
			EnumPelletType ammoRequired = this.getAmmo(mode, playerIn, worldIn, false);

			// If player has ammo shoot it and consume
			if (playerIn.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet, 1, ammoRequired.ordinal()))) {
				// Consume item
				Utils.consumePlayerItem(playerIn, new ItemStack(ToolsItems.sling_pellet, 1, ammoRequired.ordinal()));
				worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				if (!worldIn.isRemote) {
					EntitySlingPellet pellet = new EntitySlingPellet(worldIn, playerIn, ammoRequired);
					pellet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
					worldIn.spawnEntity(pellet);
				}
				return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
			}

			// If player has pellet bag search them for ammo
			if (playerIn.inventory.hasItemStack(new ItemStack(ToolsItems.pellet_bag))) {

				// Search players inventory
				for (int i = 0; i < playerIn.inventory.getSizeInventory(); i++) {
					// Check if the item is a pellet bag
					ItemStack foundBag = playerIn.inventory.getStackInSlot(i);
					if (foundBag.getItem() == ToolsItems.pellet_bag) {
						if (Utils.hasItemHandler(foundBag)) {

							// Try and simulate a consume and see if it was
							// empty if not then consume the item and shoot
							if (!Utils.consumeHandlerItem(Utils.getItemHandler(foundBag), new ItemStack(ToolsItems.sling_pellet, 1, ammoRequired.ordinal()), 1, true, false).isEmpty()) {
								// Consume item
								Utils.consumeHandlerItem(Utils.getItemHandler(foundBag), new ItemStack(ToolsItems.sling_pellet, 1, ammoRequired.ordinal()));
								worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
								if (!worldIn.isRemote) {
									EntitySlingPellet pellet = new EntitySlingPellet(worldIn, playerIn, ammoRequired);
									pellet.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
									worldIn.spawnEntity(pellet);
								}
								return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
							}
						}
					}
				}
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
	}

	public EnumPelletType getAmmo(EnumSlingshotModes mode, EntityPlayer player, World worldIn, boolean isCreative) {
		switch (mode) {
		case ALL:
			if (isCreative) {
				return EnumPelletType.values[worldIn.rand.nextInt(EnumPelletType.values.length)];
			}

			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack foundItem = player.inventory.getStackInSlot(i);
				if (!foundItem.isEmpty()) {
					if (foundItem.getItem() == ToolsItems.sling_pellet) {
						return ItemSlingPellet.getPelletType(foundItem);
					}
					if (foundItem.getItem() == ToolsItems.pellet_bag) {
						if (Utils.hasItemHandler(foundItem)) {
							ItemStack pelletStack = Utils.consumeHandlerItem(Utils.getItemHandler(foundItem), new ItemStack(ToolsItems.sling_pellet), 1, true, true);

							if (!pelletStack.isEmpty()) {
								return ItemSlingPellet.getPelletType(pelletStack);
							}
						}
					}
				}
			}
			return EnumPelletType.STONE;
		case EXPLOSIVE:
			return EnumPelletType.EXPLOSION;
		case FIRE:
			return EnumPelletType.FIRE;
		case IRON:
			return EnumPelletType.IRON;
		case LIGHT:
			return EnumPelletType.LIGHT;
		case NETHERRACK:
			return EnumPelletType.NETHERRACK;
		case RANDOM:
			if (isCreative) {
				return EnumPelletType.values[worldIn.rand.nextInt(EnumPelletType.values.length)];
			}

			// Create set so there are no duplicates
			HashSet<EnumPelletType> types = new HashSet<EnumPelletType>();

			// Search player inventory
			for (int i = 0; i < EnumPelletType.values.length; i++) {
				if (player.inventory.hasItemStack(new ItemStack(ToolsItems.sling_pellet, 1, i))) {
					types.add(EnumPelletType.values[i]);
				}
			}

			// Search player inventory for Pellet Bags then search those
			if (player.inventory.hasItemStack(new ItemStack(ToolsItems.pellet_bag))) {
				for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack foundBag = player.inventory.getStackInSlot(i);
					if (foundBag.getItem() == ToolsItems.pellet_bag) {
						for (int j = 0; j < EnumPelletType.values.length; j++) {
							if (Utils.hasItemHandler(foundBag)) {

								if (!Utils.consumeHandlerItem(Utils.getItemHandler(foundBag), new ItemStack(ToolsItems.sling_pellet, 1, j), 1, true, false).isEmpty()) {
									types.add(EnumPelletType.values[j]);
								}
							}
						}
					}
				}
			}

			// Add set to list and get random value
			List<EnumPelletType> pelletTypes = new ArrayList<EnumPelletType>();
			pelletTypes.addAll(types);
			if (!pelletTypes.isEmpty())
				return pelletTypes.get(worldIn.rand.nextInt(pelletTypes.size()));
		case STONE:
			return EnumPelletType.STONE;
		}
		return EnumPelletType.STONE;
	}
}
