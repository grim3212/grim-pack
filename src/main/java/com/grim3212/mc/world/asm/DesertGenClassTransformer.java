package com.grim3212.mc.world.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.grim3212.mc.core.util.GrimLog;
import com.grim3212.mc.world.GrimWorld;

import net.minecraft.launchwrapper.IClassTransformer;

public class DesertGenClassTransformer implements IClassTransformer {

	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		if (arg0.equals("asa")) {
			GrimLog.info(GrimWorld.modName, "INSIDE OBFUSCATED BIOMEGENDESERT TRANSFORMER ABOUT TO PATCH: " + arg0);
			try {
				return patch("a", arg2, "(Laqu;Ljava/util/Random;Ldt;)V", "/com/grim3212/mc/world/asm/ASMMethods.class", "func_180624_a");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (arg0.equals("net.minecraft.world.biome.BiomeGenDesert")) {
			GrimLog.info(GrimWorld.modName, "INSIDE DEOBFUSCATED BIOMEGENDESERT TRANSFORMER ABOUT TO PATCH: " + arg0);
			try {
				return patch("decorate", arg2, "(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V", "/com/grim3212/mc/world/asm/ASMMethods.class", "decorate");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return arg2;
	}

	private byte[] patch(String name, byte[] bytes, String desc, String iFile, String rname) throws IOException {
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);

		cr.accept(cn, 0);
		Iterator<MethodNode> methods = cn.methods.iterator();

		MethodNode mn = null;

		while (methods.hasNext()) {
			MethodNode m = (MethodNode) methods.next();

			if ((m.name.equals(name)) && (m.desc.equals(desc))) {
				GrimLog.info(GrimWorld.modName, "Found Method");
				mn = m;
				break;
			}
		}

		InputStream in = DesertGenClassTransformer.class.getResourceAsStream(iFile);
		byte[] newByte = IOUtils.toByteArray(in);

		ClassNode cnr = new ClassNode();
		ClassReader crr = new ClassReader(newByte);
		crr.accept(cnr, 0);

		Iterator<MethodNode> replacemethods = cnr.methods.iterator();
		MethodNode mnr = null;

		while (replacemethods.hasNext()) {
			MethodNode m = (MethodNode) replacemethods.next();

			if (m.name.equals(rname)) {
				GrimLog.info(GrimWorld.modName, "Found Method for Replacement");
				mnr = m;
				break;
			}
		}

		mn.instructions = mnr.instructions;
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

		GrimLog.info(GrimWorld.modName, "Patching Complete");

		cn.accept(cw);
		return cw.toByteArray();
	}
}