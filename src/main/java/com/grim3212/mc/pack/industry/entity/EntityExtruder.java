package com.grim3212.mc.pack.industry.entity;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.gui.PackGuiHandler;
import com.grim3212.mc.pack.industry.config.IndustryConfig;
import com.grim3212.mc.pack.industry.inventory.InventoryExtruder;
import com.grim3212.mc.pack.industry.item.IndustryItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityExtruder extends Entity implements IEntityAdditionalSpawnData {

	/**
	 * The direction the extruder is facing
	 */
	private EnumFacing facing;
	/**
	 * The speed modifier depending on the fuel inserted
	 */
	private float speedModifier;
	private InventoryExtruder extruderInv;
	/**
	 * The counter to get the right place for the extruder's tip
	 */
	public float extrusionCounter;

	private static final DataParameter<String> CUSTOM_NAME = EntityDataManager.createKey(EntityExtruder.class, DataSerializers.STRING);
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(EntityExtruder.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer>createKey(EntityExtruder.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FUEL_AMOUNT = EntityDataManager.<Integer>createKey(EntityExtruder.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(EntityExtruder.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> EXTRUSION_WAVE = EntityDataManager.<Float>createKey(EntityExtruder.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> IS_RUNNING = EntityDataManager.<Boolean>createKey(EntityExtruder.class, DataSerializers.BOOLEAN);

	public EntityExtruder(World worldIn) {
		super(worldIn);
		this.setSize(0.865F, 0.856F);
		this.facing = EnumFacing.NORTH;
		this.extruderInv = new InventoryExtruder(this);
	}

	public EntityExtruder(World worldIn, EnumFacing facing, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y - this.height / 2, z);
		this.setFacing(facing);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	public void setFacing(EnumFacing facing) {
		this.facing = facing;

		if (facing == EnumFacing.UP) {
			setPositionAndRotation(posX, posY, posZ, 0.0F, -180F);
		} else if (facing == EnumFacing.DOWN) {
			setPositionAndRotation(posX, posY, posZ, 0.0F, 90F);
		} else {
			if (facing.getAxis() == EnumFacing.Axis.X)
				setPositionAndRotation(posX, posY, posZ, (facing.getOpposite().getHorizontalIndex() * 90 - 90) % 360, 0.0F);
			else
				setPositionAndRotation(posX, posY, posZ, (facing.getHorizontalIndex() * 90 - 90) % 360, 0.0F);
		}

		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		BlockPos pos = this.getPosition();
		pos = pos.offset(getFacing());

		IBlockState state = world.getBlockState(pos);

		if (!(state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK)) {
			if (this.getFuelAmount() <= 0) {
				ItemStack stack = this.extruderInv.getStackInSlot(0);
				if (stack != null) {
					int fuelBurnTime = this.getFuelBurnTime(stack);
					if (fuelBurnTime > 0) {
						this.setFuelAmount(this.getFuelAmount() + fuelBurnTime);
						speedModifier = this.getSpeedModifier(stack);
						this.extruderInv.decrStackSize(0, 1);
					}
				}
			}

			if (this.getFuelAmount() <= 0 || !this.getIsRunning()) {
				this.moveToBlockPosAndAngles(this.getPosition(), this.rotationYaw, this.rotationPitch);
			}

			if (this.getIsRunning() && this.getFuelAmount() > 0) {
				this.setFuelAmount(this.getFuelAmount() - 1);

				// Used for updating the extruders moving drill
				extrusionCounter += 0.59999999999999998D;
				extrusionCounter %= 360F;
				this.setExtrusionWave(MathHelper.sin(extrusionCounter));

				if (state.getBlock() != Blocks.AIR) {

					// Possibly add upgrades to fortune
					Item item = state.getBlock().getItemDropped(state, rand, 0);
					int amount = state.getBlock().quantityDropped(state, 0, rand);
					int meta = state.getBlock().damageDropped(state);

					if (amount > 0 && item != null) {
						this.extruderInv.addToMinedInventory(new ItemStack(item, amount, meta));
					}

					if (!this.world.isRemote) {
						this.world.setBlockToAir(pos);
						this.world.notifyNeighborsOfStateChange(pos, Blocks.AIR, true);
					}
					this.setFuelAmount(this.getFuelAmount() - IndustryConfig.fuelPerMinedBlock);
				}

				int prevX = this.getPosition().getX();
				// Gets Y without 0.5 tacked on
				int prevY = MathHelper.floor(this.getPositionVector().yCoord);
				int prevZ = this.getPosition().getZ();

				if (!world.isRemote) {

					int xOff = 0;
					int yOff = 0;
					int zOff = 0;

					switch (this.getFacing()) {
					case DOWN:
						yOff = -1;
						break;
					case EAST:
						xOff = 1;
						break;
					case NORTH:
						zOff = -1;
						break;
					case SOUTH:
						zOff = 1;
						break;
					case UP:
						yOff = 1;
						break;
					case WEST:
						xOff = -1;
						break;
					}

					this.move(MoverType.SELF, IndustryConfig.moveSpeed * speedModifier * xOff, IndustryConfig.moveSpeed * speedModifier * yOff, IndustryConfig.moveSpeed * speedModifier * zOff);

				}

				int x = MathHelper.floor(posX);
				int y = MathHelper.floor(posY);
				int z = MathHelper.floor(posZ);

				if (x != prevX || y != prevY || z != prevZ) {
					this.extrudeBlock(this.getPosition(), facing);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void extrudeBlock(BlockPos pos, EnumFacing facing) {
		ItemStack itemstack = this.extruderInv.nextExtruderStack();

		if (!itemstack.isEmpty()) {
			if (Block.getBlockFromItem(itemstack.getItem()) != null) {
				this.setFuelAmount(this.getFuelAmount() - IndustryConfig.fuelPerExtrudedBlock);
				if (!this.world.isRemote) {
					world.setBlockState(pos.offset(facing.getOpposite()), Block.getBlockFromItem(itemstack.getItem()).getStateFromMeta(itemstack.getMetadata()));
					SoundType soundType = Block.getBlockFromItem(itemstack.getItem()).getSoundType();
					world.playSound((EntityPlayer) null, pos.offset(facing.getOpposite()), soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
				}
			} else {
				EntityItem entityitem = new EntityItem(world, posX, posY, posZ, itemstack);
				if (!this.world.isRemote)
					world.spawnEntity(entityitem);
			}
		}
	}

	public int getFuelBurnTime(ItemStack itemstack) {
		if (itemstack.getItem() == Items.STICK) {
			return IndustryConfig.fuelPerStick;
		} else if (itemstack.getItem() == Items.COAL) {
			return IndustryConfig.fuelPerCoal;
		} else if (itemstack.getItem() == Items.REDSTONE) {
			return IndustryConfig.fuelPerRedstone;
		} else if (itemstack.getItem() == Items.LAVA_BUCKET) {
			return IndustryConfig.fuelPerLava;
		} else if (itemstack.getItem() == Items.BLAZE_ROD) {
			return IndustryConfig.fuelPerBlazerod;
		} else if (itemstack.getItem() == Items.MAGMA_CREAM) {
			return IndustryConfig.fuelPerMagmaCream;
		} else {
			return GameRegistry.getFuelValue(itemstack);
		}
	}

	public float getSpeedModifier(ItemStack itemstack) {
		if (itemstack.getItem() == Items.REDSTONE) {
			return IndustryConfig.speedModifierRedstone;
		} else if (itemstack.getItem() == Items.COAL) {
			return IndustryConfig.speedModifierCoal;
		} else if (itemstack.getItem() == Items.STICK) {
			return IndustryConfig.speedModifierStick;
		} else if (itemstack.getItem() == Items.MAGMA_CREAM) {
			return IndustryConfig.speedModifierMagmaCream;
		} else {
			return 1.0F;
		}
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return super.getEntityBoundingBox();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.world.isRemote || this.isDead)
			return true;

		this.setIsRunning(!this.getIsRunning());
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() + amount * 11.0F);
		this.setBeenAttacked();
		boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

		if (flag || this.getDamageTaken() > 40.0F) {
			if (!flag && this.world.getGameRules().getBoolean("doEntityDrops")) {
				dropItem(IndustryItems.extruder, 1);

			}

			this.emptyItems();
			this.setDead();
		}

		return true;
	}

	@Override
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 10.0F);
	}

	private void emptyItems() {
		for (ItemStack stack : this.extruderInv.getExtruderContents()) {
			if (!stack.isEmpty() && stack.getItem() != null) {
				EntityItem entityitem = new EntityItem(world, (float) posX, (float) posY, (float) posZ, stack);
				world.spawnEntity(entityitem);
			}
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(CUSTOM_NAME, "");
		this.getDataManager().register(TIME_SINCE_HIT, 0);
		this.getDataManager().register(FORWARD_DIRECTION, 1);
		this.getDataManager().register(FUEL_AMOUNT, 0);
		this.getDataManager().register(DAMAGE_TAKEN, 0.0F);
		this.getDataManager().register(EXTRUSION_WAVE, 0.0F);
		this.getDataManager().register(IS_RUNNING, false);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if (!player.world.isRemote)
			player.openGui(GrimPack.INSTANCE, PackGuiHandler.EXTRUDER_GUI_ID, player.world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());

		return EnumActionResult.SUCCESS;
	}

	public InventoryExtruder getExtruderInv() {
		return extruderInv;
	}

	public String getCustomName() {
		return this.getDataManager().get(CUSTOM_NAME);
	}

	public void setCustomName(String customName) {
		this.getDataManager().set(CUSTOM_NAME, customName);
	}

	public EnumFacing getFacing() {
		return facing;
	}

	/**
	 * Sets the extruders current amount of fuel
	 */
	public void setFuelAmount(int fuelAmount) {
		if (fuelAmount < 0) {
			fuelAmount = 0;
		}
		this.dataManager.set(FUEL_AMOUNT, fuelAmount);
	}

	/**
	 * Gets the extruders current amount of fuel
	 */
	public int getFuelAmount() {
		return this.dataManager.get(FUEL_AMOUNT);
	}

	/**
	 * Sets if the extruder is running
	 */
	public void setIsRunning(boolean running) {
		this.dataManager.set(IS_RUNNING, running);
	}

	/**
	 * Gets if the extruder is running
	 */
	public boolean getIsRunning() {
		return this.dataManager.get(IS_RUNNING);
	}

	/**
	 * Sets the position of the extruders drill
	 */
	public void setExtrusionWave(float wave) {
		this.dataManager.set(EXTRUSION_WAVE, wave);
	}

	/**
	 * Gets the position of the extruders drill
	 */
	public float getExtrusionWave() {
		return this.dataManager.get(EXTRUSION_WAVE);
	}

	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float damageTaken) {
		this.dataManager.set(DAMAGE_TAKEN, damageTaken);
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return this.dataManager.get(DAMAGE_TAKEN);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int timeSinceHit) {
		this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return this.dataManager.get(TIME_SINCE_HIT);
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int forwardDirection) {
		this.dataManager.set(FORWARD_DIRECTION, forwardDirection);
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return this.dataManager.get(FORWARD_DIRECTION);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		facing = EnumFacing.getFront(compound.getByte("facing"));
		this.setFuelAmount(compound.getInteger("fuel"));
		speedModifier = compound.getFloat("speedModifier");
		this.setIsRunning(compound.getBoolean("running"));
		this.extruderInv.contentHead = compound.getInteger("contentHead");

		if (compound.hasKey("CustomName", 8)) {
			this.getDataManager().set(CUSTOM_NAME, compound.getString("CustomName"));
		}

		NBTTagList nbttaglist = compound.getTagList("Items", 10);

		this.extruderInv.setExtruderContents(NonNullList.withSize(37, ItemStack.EMPTY));

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < this.extruderInv.getExtruderContents().size()) {
				this.extruderInv.getExtruderContents().set(j, new ItemStack(nbttagcompound));
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setByte("facing", (byte) facing.getIndex());
		compound.setInteger("fuel", this.getFuelAmount());
		compound.setFloat("speedModifier", speedModifier);
		compound.setBoolean("running", this.getIsRunning());
		compound.setInteger("contentHead", this.extruderInv.contentHead);

		if (this.hasCustomName()) {
			compound.setString("CustomName", this.getDataManager().get(CUSTOM_NAME));
		}

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.extruderInv.getExtruderContents().size(); ++i) {
			if (!this.extruderInv.getExtruderContents().get(i).isEmpty()) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				this.extruderInv.getExtruderContents().get(i).writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeByte((byte) facing.getIndex());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		this.facing = EnumFacing.getFront(additionalData.readByte());
	}

}
