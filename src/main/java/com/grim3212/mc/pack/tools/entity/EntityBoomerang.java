package com.grim3212.mc.pack.tools.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.grim3212.mc.pack.core.manual.IManualEntry.IManualEntity;
import com.grim3212.mc.pack.core.manual.pages.Page;
import com.grim3212.mc.pack.tools.client.ManualTools;
import com.grim3212.mc.pack.tools.config.ToolsConfig;

import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EntityBoomerang extends Entity implements IManualEntity {

	private BlockPos activatedPos;
	protected boolean isBouncing;
	private double bounceFactor;
	private float prevBoomerangRotation;
	private boolean turningAround;
	protected int timeBeforeTurnAround;
	List<EntityItem> itemsPickedUp;
	private ItemStack selfStack;
	private static final DataParameter<Float> ROTATION = EntityDataManager.<Float>createKey(EntityBoomerang.class, DataSerializers.FLOAT);
	private static final DataParameter<Optional<UUID>> RETURN_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityBoomerang.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public EntityBoomerang(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 0.5F);
		this.bounceFactor = 0.84999999999999998D;
		this.turningAround = true;
		this.timeBeforeTurnAround = 0;
		this.itemsPickedUp = new ArrayList<EntityItem>();
	}

	public EntityBoomerang(World worldIn, EntityPlayer entity, ItemStack itemstack) {
		this(worldIn);
		this.selfStack = itemstack;
		this.setRotation(entity.rotationYaw, entity.rotationPitch);
		double x = -MathHelper.sin((entity.rotationYaw * 3.141593F) / 180F);
		double z = MathHelper.cos((entity.rotationYaw * 3.141593F) / 180F);
		motionX = 0.5D * x * (double) MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F);
		motionY = -0.5D * (double) MathHelper.sin((entity.rotationPitch / 180F) * 3.141593F);
		motionZ = 0.5D * z * (double) MathHelper.cos((entity.rotationPitch / 180F) * 3.141593F);
		setPosition(entity.posX, this.getReturnEntityY(entity), entity.posZ);
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		this.isBouncing = false;
		this.timeBeforeTurnAround = ToolsConfig.woodBoomerangRange <= 0 ? 30 : ToolsConfig.woodBoomerangRange;
		this.turningAround = false;
		this.setReturnToId(entity.getPersistentID());
	}

	public double getReturnEntityY(EntityPlayer entity) {
		return entity.posY + entity.getEyeHeight() - 0.10000000149011612D;
	}

	@Override
	public void onUpdate() {
		EntityPlayer player = this.getReturnTo();

		Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, false, false);

		if (raytraceresult != null) {
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos pos = raytraceresult.getBlockPos();
				IBlockState state = world.getBlockState(pos);

				if ((state.getMaterial() == Material.PLANTS && ToolsConfig.breaksPlants) || (state.getBlock() == Blocks.TORCH && ToolsConfig.breaksTorches)) {
					// The only time items hovering above get out of sync is
					// when destroyed using this method or manually spawning
					// entity items in.
					// No idea why and have tried many things to fix this.
					world.destroyBlock(pos, true);
				}

				if ((state.getBlock() instanceof BlockLever || state.getBlock() instanceof BlockButton) && ToolsConfig.hitsButtons) {
					if (timeBeforeTurnAround > 0 && ToolsConfig.turnAroundSwitch) {
						timeBeforeTurnAround = 0;
					}
					if (activatedPos == null || !activatedPos.equals(pos)) {
						activatedPos = pos;
						state.getBlock().onBlockActivated(world, pos, state, player, EnumHand.MAIN_HAND, raytraceresult.sideHit, (float) raytraceresult.hitVec.xCoord, (float) raytraceresult.hitVec.yCoord, (float) raytraceresult.hitVec.zCoord);
					}
				}
			}
		}
		if (!turningAround) {
			double d1 = motionX;
			double d3 = motionY;
			double d5 = motionZ;

			this.move(MoverType.SELF, motionX, motionY, motionZ);

			boolean flag = false;
			if (motionX != d1) {
				motionX = -d1;
				flag = true;
			}
			if (motionY != d3) {
				motionY = -d3;
				flag = true;
			}
			if (motionZ != d5) {
				motionZ = -d5;
				flag = true;
			}
			if (flag) {
				isBouncing = true;
				motionX *= bounceFactor;
				motionY *= bounceFactor;
				motionZ *= bounceFactor;
			}

			this.beforeTurnAround(player);

			if (timeBeforeTurnAround-- <= 0) {
				turningAround = true;
			}
		} else if (player != null) {
			double d2 = player.posX - posX;
			double d4 = this.getReturnEntityY(player) - posY;
			double d6 = player.posZ - posZ;
			double d7 = Math.sqrt(d2 * d2 + d4 * d4 + d6 * d6);
			if (d7 < 1.5D) {
				setEntityDead();
			}
			motionX = (0.5D * d2) / d7;
			motionY = (0.5D * d4) / d7;
			motionZ = (0.5D * d6) / d7;
			setPosition(posX + motionX, posY + motionY, posZ + motionZ);
		}

		determineRotation();
		prevBoomerangRotation = getBoomerangRotation();
		for (this.setBoomerangRotation(this.getBoomerangRotation() + 36F); this.getBoomerangRotation() > 360F; this.setBoomerangRotation(this.getBoomerangRotation() - 360F)) {
		}
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.5D, 0.5D, 0.5D));
		for (int i = 0; i < list.size(); i++) {
			Entity entity = list.get(i);
			if (entity instanceof EntityItem) {
				itemsPickedUp.add((EntityItem) entity);
				if (timeBeforeTurnAround > 0 && ToolsConfig.turnAroundItem) {
					timeBeforeTurnAround = 0;
				}
				continue;
			}
			if (!(entity instanceof EntityLiving) || entity == player) {
				continue;
			}

			this.onEntityHit(entity, player);

			if (timeBeforeTurnAround > 0 && ToolsConfig.turnAroundMob) {
				timeBeforeTurnAround = 0;
			}
		}

		Iterator<EntityItem> iterator = itemsPickedUp.iterator();
		while (iterator.hasNext()) {
			EntityItem item = iterator.next();
			if (!item.isDead) {
				item.setPosition(posX, posY, posZ);
			}
		}

		super.onUpdate();
	}

	public void beforeTurnAround(EntityPlayer player) {
		// NO-OP
	}

	public void onEntityHit(Entity hitEntity, EntityPlayer player) {
		if (ToolsConfig.woodBoomerangDamage > 0) {
			hitEntity.attackEntityFrom(causeNewDamage(this, player), ToolsConfig.woodBoomerangDamage);
		} else {
			hitEntity.attackEntityFrom(causeNewDamage(this, player), 0);
		}
	}

	public DamageSource causeNewDamage(EntityBoomerang entityboomerang, Entity entity) {
		return new EntityDamageSourceIndirect("boomerang", entityboomerang, entity);
	}

	public void setEntityDead() {
		if (this.getReturnTo() != null) {
			if (selfStack != null) {
				this.getReturnTo().inventory.addItemStackToInventory(selfStack);
			}
		}
		super.setDead();
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(ROTATION, 0.0F);
		this.getDataManager().register(RETURN_UNIQUE_ID, Optional.<UUID>absent());
	}

	public float getBoomerangRotation() {
		return this.getDataManager().get(ROTATION);
	}

	public void setBoomerangRotation(float rotationIn) {
		this.getDataManager().set(ROTATION, rotationIn);
	}

	@Nullable
	public UUID getReturnToId() {
		return (UUID) ((Optional<UUID>) this.dataManager.get(RETURN_UNIQUE_ID)).orNull();
	}

	public void setReturnToId(@Nullable UUID uuid) {
		this.dataManager.set(RETURN_UNIQUE_ID, Optional.fromNullable(uuid));
	}

	@Nullable
	public EntityPlayer getReturnTo() {
		try {
			UUID uuid = this.getReturnToId();
			// System.out.println(uuid == null ? "null" : uuid.toString());
			return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	public boolean isReturnTo(EntityLivingBase entityIn) {
		return entityIn == this.getReturnTo();
	}

	public void determineRotation() {
		rotationYaw = -57.29578F * (float) Math.atan2(motionX, motionZ);
		double d1 = Math.sqrt(motionZ * motionZ + motionX * motionX);
		rotationPitch = -57.29578F * (float) Math.atan2(motionY, d1);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.isBouncing = compound.getBoolean("IsBouncing");
		this.bounceFactor = compound.getDouble("BounceFactor");
		this.prevBoomerangRotation = compound.getFloat("PrevBoomerangRotation");
		this.setBoomerangRotation(compound.getFloat("BoomerangRotation"));
		this.turningAround = compound.getBoolean("TurningAround");
		this.timeBeforeTurnAround = compound.getInteger("TimeBeforeTurnAround");
		if (compound.hasKey("xPos") && compound.hasKey("yPos") && compound.hasKey("zPos"))
			this.activatedPos = new BlockPos(compound.getInteger("xPos"), compound.getInteger("yPos"), compound.getInteger("zPos"));

		if (compound.hasKey("ReturnToUUID", 8)) {
			try {
				this.setReturnToId(UUID.fromString(compound.getString("ReturnToUUID")));
			} catch (Throwable t) {
				// NO-OP
			}
		}

		this.selfStack = new ItemStack(compound.getCompoundTag("SelfStack"));

		NBTTagList itemsGathered = compound.getTagList("ItemsPickedUp", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < itemsGathered.tagCount(); i++) {
			NBTTagCompound tag = itemsGathered.getCompoundTagAt(i);
			EntityItem item = new EntityItem(world);
			item.readFromNBT(tag);
			this.itemsPickedUp.add(item);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("IsBouncing", isBouncing);
		compound.setDouble("BounceFactor", bounceFactor);
		compound.setFloat("PrevBoomerangRotation", prevBoomerangRotation);
		compound.setFloat("BoomerangRotation", this.getBoomerangRotation());
		compound.setBoolean("TurningAround", turningAround);
		compound.setInteger("TimeBeforeTurnAround", timeBeforeTurnAround);
		if (activatedPos != null) {
			compound.setInteger("xPos", activatedPos.getX());
			compound.setInteger("yPos", activatedPos.getY());
			compound.setInteger("zPos", activatedPos.getZ());
		}

		if (this.getReturnToId() == null) {
			compound.setString("ReturnToUUID", "");
		} else {
			compound.setString("ReturnToUUID", this.getReturnToId().toString());
		}

		NBTTagCompound selfStackTag = new NBTTagCompound();
		selfStack.writeToNBT(selfStackTag);
		compound.setTag("SelfStack", selfStackTag);

		NBTTagList itemsGathered = new NBTTagList();
		for (int i = 0; i < itemsPickedUp.size(); i++) {
			if (itemsPickedUp.get(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				itemsPickedUp.get(i).writeToNBT(tag);
				itemsGathered.appendTag(tag);
			}
		}

		compound.setTag("ItemsPickedUp", itemsGathered);
	}

	@Override
	public Page getPage(Entity entity) {
		return ManualTools.boomerang_page;
	}
}
