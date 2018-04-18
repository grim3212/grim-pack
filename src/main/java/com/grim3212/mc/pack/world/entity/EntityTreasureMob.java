package com.grim3212.mc.pack.world.entity;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.world.client.ManualWorld;
import com.grim3212.mc.pack.world.inventory.InventoryTreasureChest;
import com.grim3212.mc.pack.world.util.WorldLootTables;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EntityTreasureMob extends EntityTameable implements IManualEntity, IInventoryChangedListener {

	private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
	private EntityAITempt aiTempt;
	private InventoryTreasureChest treasureChest;

	public EntityTreasureMob(World world) {
		super(world);
		setSize(0.85F, 0.80F);
		this.initTreasure();
	}

	private void initTreasure() {
		InventoryTreasureChest containerTreasure = this.treasureChest;
		this.treasureChest = new InventoryTreasureChest("entity.treasure.inventory", 27);
		this.treasureChest.setCustomName(this.getName());

		if (containerTreasure != null) {
			containerTreasure.removeInventoryChangeListener(this);
			int i = Math.min(containerTreasure.getSizeInventory(), this.treasureChest.getSizeInventory());

			for (int j = 0; j < i; ++j) {
				ItemStack itemstack = containerTreasure.getStackInSlot(j);

				if (!itemstack.isEmpty()) {
					this.treasureChest.setInventorySlotContents(j, itemstack.copy());
				}
			}
		}

		this.treasureChest.addInventoryChangeListener(this);
		this.itemHandler = new InvWrapper(this.treasureChest);
	}

	@Override
	protected void initEntityAI() {
		this.aiSit = new EntityAISit(this);
		this.aiTempt = new EntityAITempt(this, 0.6D, Items.GOLD_NUGGET, true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, this.aiTempt);
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 4.0F));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	}

	@Override
	public void updateAITasks() {
		if (this.getMoveHelper().isUpdating()) {
			double d0 = this.getMoveHelper().getSpeed();

			if (d0 == 0.6D) {
				this.setSneaking(true);
				this.setSprinting(false);
			} else if (d0 == 1.33D) {
				this.setSneaking(false);
				this.setSprinting(true);
			} else {
				this.setSneaking(false);
				this.setSprinting(false);
			}
		} else {
			this.setSneaking(false);
			this.setSprinting(false);
		}
	}

	@Override
	protected boolean canDespawn() {
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if (this.isTamed()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return WorldLootTables.ENTITIES_TREASURE_MOB;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable) {
		return null;
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualWorld.treasureChest_page;
	}

	@Override
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);

		if (tamed) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
		} else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		}
	}

	private void loadLoot(EntityPlayer player) {
		LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(this.getLootTable());

		LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.world);

		if (player != null) {
			lootcontext$builder.withLuck(player.getLuck());
		}

		loottable.fillInventory(this.treasureChest, this.rand, lootcontext$builder.build());
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);

		if (this.isTamed() && this.isOwner(player)) {
			if (player.isSneaking()) {
				// Access chest inventory
				this.openGUI(player);
				return true;
			} else if (!this.world.isRemote) {
				this.aiSit.setSitting(!this.isSitting());
			}
		} else if ((this.aiTempt == null || this.aiTempt.isRunning()) && itemstack.getItem() == Items.GOLD_NUGGET && player.getDistanceSq(this) < 9.0D) {
			if (!player.capabilities.isCreativeMode) {
				player.getHeldItem(hand).shrink(1);
			}

			if (!this.world.isRemote) {
				// Try and tame
				if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					// player.addStat(WorldAchievements.TREASURE);
					this.setTamedBy(player);
					this.playTameEffect(true);
					this.aiSit.setSitting(true);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.processInteract(player, hand);
	}

	@Override
	protected void setupTamedAI() {
		if (this.avoidEntity == null) {
			this.avoidEntity = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D);
		}

		this.tasks.removeTask(this.avoidEntity);

		if (!this.isTamed()) {
			this.tasks.addTask(3, this.avoidEntity);
		}
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);

		if (!world.isRemote)
			InventoryHelper.dropInventoryItems(world, this.getPosition(), this.treasureChest);
	}

	@Override
	protected boolean canDropLoot() {
		if (this.isTamed()) {
			return false;
		}
		return super.canDropLoot();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			if (this.aiSit != null) {
				this.aiSit.setSitting(false);
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		// Make a bit more rare
		return this.world.rand.nextInt(3) != 0;
	}

	@Override
	public void onInventoryChanged(IInventory invBasic) {
	}

	private void openGUI(EntityPlayer playerIn) {
		this.treasureChest.setCustomName(this.getName());

		if (!this.world.isRemote && this.isTamed()) {
			playerIn.openGui(GrimPack.INSTANCE, PackGuiHandler.TREASURE_MOB_GUI_ID, this.world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
		}
	}

	public InventoryTreasureChest getTreasureInventory() {
		return treasureChest;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		ItemStackHelper.loadAllItems(compound, this.treasureChest.getInventoryContents());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		ItemStackHelper.saveAllItems(compound, this.treasureChest.getInventoryContents());
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.loadLoot((EntityPlayer) null);
		return super.onInitialSpawn(difficulty, livingdata);
	}

	private IItemHandler itemHandler = null;

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) itemHandler;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
}
