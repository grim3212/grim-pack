package com.grim3212.mc.pack.util.frozen;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.util.GrimUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FrozenCapability {

	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IFrozenCapability.class, new FrozenStorage(), FrozenDefaultImpl.class);
		MinecraftForge.EVENT_BUS.register(new FrozenEvents());
	}

	public static class FrozenEvents {
		@SubscribeEvent
		public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (!(event.getObject() instanceof EntityPlayer)) {
				event.addCapability(new ResourceLocation(GrimPack.modID, "IFrozenCapability"), new ICapabilitySerializable<NBTPrimitive>() {
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
					public NBTPrimitive serializeNBT() {
						return (NBTPrimitive) GrimUtil.FROZEN_CAP.getStorage().writeNBT(GrimUtil.FROZEN_CAP, inst, null);
					}

					@Override
					public void deserializeNBT(NBTPrimitive nbt) {
						GrimUtil.FROZEN_CAP.getStorage().readNBT(GrimUtil.FROZEN_CAP, inst, null, nbt);
					}
				});

			}
		}

		@SubscribeEvent
		public void entityHurt(EntityInteract event) {
			final IFrozenCapability frozen = event.getTarget().getCapability(GrimUtil.FROZEN_CAP, null);
			if (frozen != null) {
				frozen.setFrozen(true);
			}
		}
	}

	public static interface IFrozenCapability {

		public boolean isFrozen();

		public void setFrozen(boolean frozen);

	}

	public static class FrozenStorage implements IStorage<IFrozenCapability> {

		@Override
		public NBTBase writeNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, EnumFacing side) {
			return new NBTTagByte((byte) (instance.isFrozen() ? 1 : 0));
		}

		@Override
		public void readNBT(Capability<IFrozenCapability> capability, IFrozenCapability instance, EnumFacing side, NBTBase nbt) {
			instance.setFrozen(((NBTPrimitive) nbt).getByte() == 1);
		}

	}

	public static class FrozenDefaultImpl implements IFrozenCapability {

		private boolean isFrozen = false;

		@Override
		public boolean isFrozen() {
			return isFrozen;
		}

		@Override
		public void setFrozen(boolean frozen) {
			this.isFrozen = frozen;
		}

	}
}
