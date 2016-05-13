package com.grim3212.mc.pack.world.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.world.GrimWorld;

import net.minecraft.launchwrapper.IClassTransformer;

public class DesertGenClassTransformer implements IClassTransformer {

	public byte[] transform(String name, String transformedName, byte[] bytes) {
		if ("net.minecraft.world.biome.BiomeGenDesert".equals(transformedName)) {
			GrimLog.info(GrimWorld.modName, "INSIDE DEOBFUSCATED BIOMEGENDESERT TRANSFORMER ABOUT TO PATCH: " + transformedName);
			try {
				return patch("decorate", bytes, "(Lnet/minecraft/world/World;Ljava/util/Random;Lnet/minecraft/util/BlockPos;)V", "/com/grim3212/mc/world/asm/ASMMethods.class", "decorate");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bytes;
	}

	private byte[] patch(String name, byte[] bytes, String desc, String iFile, String rname) throws IOException {
		ClassNode cn = new ClassNode();
		ClassReader cr = new ClassReader(bytes);
		cr.accept(cn, 0);

		Iterator<MethodNode> methods = cn.methods.iterator();

		MethodNode mn = null;

		while (methods.hasNext()) {
			MethodNode m = (MethodNode) methods.next();

			if ((m.name.equals("func_180624_a")) && (m.desc.equals(desc))) {
				GrimLog.info(GrimWorld.modName, "Found Method - func_180624_a");
				mn = m;
				break;
			} else if ((m.name.equals("decorate")) && (m.desc.equals(desc))) {
				GrimLog.info(GrimWorld.modName, "Found Method - decorate");
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

			if (m.name.equals("func_180624_a")) {
				GrimLog.info(GrimWorld.modName, "Found Method for Replacement - func_180624_a");
				mnr = m;
				break;
			} else if (m.name.equals("decorate")) {
				GrimLog.info(GrimWorld.modName, "Found Method for Replacement - decorate");
				mnr = m;
				break;
			}
		}

		if (mnr != null)
			mn.instructions = mnr.instructions;

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		GrimLog.info(GrimWorld.modName, "Patching Complete");
		cn.accept(cw);
		return cw.toByteArray();
	}
}