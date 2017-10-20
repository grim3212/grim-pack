package com.grim3212.mc.pack.tools.util;

import java.io.IOException;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;

public class EntityDataSerializers {

	public static final DataSerializer<EnumPelletType> PELLET_TYPE = new DataSerializer<EnumPelletType>() {
		public void write(PacketBuffer buf, EnumPelletType value) {
			buf.writeEnumValue(value);
		}

		public EnumPelletType read(PacketBuffer buf) throws IOException {
			return (EnumPelletType) buf.readEnumValue(EnumPelletType.class);
		}

		public DataParameter<EnumPelletType> createKey(int id) {
			return new DataParameter<EnumPelletType>(id, this);
		}

		@Override
		public EnumPelletType copyValue(EnumPelletType value) {
			return value;
		}
	};

	public static final DataSerializer<EnumDetonatorType> DETONATOR_TYPE = new DataSerializer<EnumDetonatorType>() {
		public void write(PacketBuffer buf, EnumDetonatorType value) {
			buf.writeEnumValue(value);
		}

		public EnumDetonatorType read(PacketBuffer buf) throws IOException {
			return buf.readEnumValue(EnumDetonatorType.class);
		}

		public DataParameter<EnumDetonatorType> createKey(int id) {
			return new DataParameter<EnumDetonatorType>(id, this);
		}

		@Override
		public EnumDetonatorType copyValue(EnumDetonatorType value) {
			return value;
		}
	};

	// Register serializers
	public static void registerSerializers() {
		DataSerializers.registerSerializer(PELLET_TYPE);
		DataSerializers.registerSerializer(DETONATOR_TYPE);
	}
}
