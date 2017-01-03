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
	};

	// Register serializers
	static {
		DataSerializers.registerSerializer(PELLET_TYPE);
	}
}
