package com.grim3212.mc.pack.util.frozen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.util.GrimUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FrozenCapability {

	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IFrozenCapability.class, new FrozenStorage(), FrozenDefaultImpl.class);
		MinecraftForge.EVENT_BUS.register(new FrozenEvents());
	}

	public static void frozen(EntityLivingBase entity, boolean freeze, int duration) {
		final IFrozenCapability frozen = entity.getCapability(GrimUtil.FROZEN_CAP, null);
		if (frozen != null) {
			frozen.setFrozen(freeze);
			frozen.setDuration(duration);
			frozen.setTime(0);
			if (entity instanceof EntityLiving) {
				EntityLiving livingEntity = (EntityLiving) entity;
				livingEntity.setNoAI(freeze);
				livingEntity.setSilent(freeze);
			}
		}
	}

	public static void freezeEntity(EntityLivingBase entity, int duration) {
		frozen(entity, true, duration);
	}

	public static void unFreezeEntity(EntityLivingBase entity) {
		frozen(entity, false, 0);
	}

	public static class FrozenEvents {
		@SubscribeEvent
		public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (!(event.getObject() instanceof EntityPlayer) && event.getObject() instanceof EntityLivingBase) {
				event.addCapability(new ResourceLocation(GrimPack.modID, "IFrozenCapability"), new ICapabilitySerializable<NBTTagCompound>() {
					IFrozenCapability inst = GrimUtil.FROZEN_CAP.getDefaultInstance();

					@Override
					public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
						return capability == GrimUtil.FROZEN_CAP;
					}

					@Override
					@Nullable
					public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
						return capability == GrimUtil.FROZEN_CAP ? GrimUtil.FROZEN_CAP.<T>cast(inst) : null;
					}

					@Override
					public NBTTagCompound serializeNBT() {
						return (NBTTagCompound) GrimUtil.FROZEN_CAP.getStorage().writeNBT(GrimUtil.FROZEN_CAP, inst, null);
					}

					@Override
					public void deserializeNBT(NBTTagCompound nbt) {
						GrimUtil.FROZEN_CAP.getStorage().readNBT(GrimUtil.FROZEN_CAP, inst, null, nbt);
					}
				});

			}
		}

		@SubscribeEvent
		public void interact(EntityInteract event) {
			if (event.getTarget() instanceof EntityLivingBase) {
				freezeEntity((EntityLivingBase) event.getTarget(), 30);
			}
		}

		@SubscribeEvent
		public void livingUpdate(LivingUpdateEvent event) {
			final IFrozenCapability frozen = event.getEntityLiving().getCapability(GrimUtil.FROZEN_CAP, null);
			if (frozen != null && frozen.isFrozen()) {
				if (frozen.isTimed()) {
					frozen.setTime(frozen.getTime() + 1);

					if (frozen.getTime() >= frozen.getDuration()) {
						unFreezeEntity(event.getEntityLiving());
					}
				}
			}
		}
	}

	public static interface IFrozenCapability {

		public boolean isFrozen();

		public void setFrozen(boolean frozen);

		public void setDuration(int ticks);

		public int getDuration();

		public int getTime();

		public void setTime(int ticks);

		public boolean isTimed();

	}

	public static class FrozenStorage implements IStorage<IFrozenCapability> {

		@Override
		public NBTBase writeNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, EnumFacing side) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setBoolean("IsFrozen", instance.isFrozen());
			tag.setInteger("Duration", instance.getDuration());
			tag.setInteger("Time", instance.getTime());

			return tag;
		}

		@Override
		public void readNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound tag = (NBTTagCompound) nbt;

			instance.setFrozen(tag.getBoolean("IsFrozen"));
			instance.setDuration(tag.getInteger("Duration"));
			instance.setTime(tag.getInteger("Time"));
		}

	}

	public static class FrozenDefaultImpl implements IFrozenCapability {

		private boolean isFrozen = false;
		private int duration = 0;
		private int time = 0;

		@Override
		public boolean isFrozen() {
			return this.isFrozen;
		}

		@Override
		public void setFrozen(boolean frozen) {
			this.isFrozen = frozen;
		}

		@Override
		public void setDuration(int ticks) {
			this.duration = ticks;
		}

		@Override
		public int getDuration() {
			return this.duration;
		}

		@Override
		public boolean isTimed() {
			return this.duration > 0;
		}

		@Override
		public int getTime() {
			return this.time;
		}

		@Override
		public void setTime(int ticks) {
			this.time = ticks;
		}

	}
}
