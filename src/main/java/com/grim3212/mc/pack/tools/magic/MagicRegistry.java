package com.grim3212.mc.pack.tools.magic;

import java.util.ArrayList;
import java.util.List;

import com.grim3212.mc.pack.core.util.GrimLog;
import com.grim3212.mc.pack.core.util.NBTHelper;
import com.grim3212.mc.pack.tools.GrimTools;
import com.grim3212.mc.pack.tools.items.ItemMagicStone.StoneType;
import com.grim3212.mc.pack.tools.util.EnumCrystalType;

import net.minecraft.item.ItemStack;

public class MagicRegistry {

    private static List<BaseMagic> loadedMagic = new ArrayList<BaseMagic>();

    public static void reset() {
        loadedMagic.clear();
    }

    public static void registerMagic(BaseMagic magic) {
        for (int i = 0; i < loadedMagic.size(); i++) {
            BaseMagic check = loadedMagic.get(i);

            if (magic.getStoneType().equals(check.getStoneType())) {

                GrimLog.error(GrimTools.partName, "Failed to load magic [" + magic.getMagicName() + "]. Had duplicate stone type as [" + check.getMagicName() + "].");
                GrimLog.error(GrimTools.partName, "Both had the Stone Type " + check.getStoneType().toString() + ". You must change one to continue using them both.");

                return;
            }
        }

        loadedMagic.add(magic);
    }

    public static List<BaseMagic> getLoadedMagic() {
        return loadedMagic;
    }

    public static BaseMagic getMagic(ItemStack stack) {
        int meta = NBTHelper.getInt(stack, "WandType");

        for (int i = 0; i < loadedMagic.size(); i++) {
            BaseMagic magic = loadedMagic.get(i);

            if (magic.getStoneType().getMeta() == meta) {
                return magic;
            }
        }

        return null;
    }

    public static void loadMagic() {
        new MagicArmor().load(100, 0f, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.RED));
        new MagicDeath().load(100, 10f, new StoneType(EnumCrystalType.YELLOW, EnumCrystalType.BLACK));
        new MagicDynomite().load(100, 5f, new StoneType(EnumCrystalType.RED, EnumCrystalType.BLACK));
        new MagicFire().load(100, 0f, new StoneType(EnumCrystalType.RED, EnumCrystalType.YELLOW));
        new MagicHeal().load(100, 10f, new StoneType(EnumCrystalType.BLUE, EnumCrystalType.YELLOW));
        new MagicHome().load(100, 0f, new StoneType(EnumCrystalType.GREEN, EnumCrystalType.RED));
        new MagicHunger().load(100, 10f, new StoneType(EnumCrystalType.BLUE, EnumCrystalType.RED));
        new MagicIce().load(100, 10f, new StoneType(EnumCrystalType.BLUE, EnumCrystalType.WHITE));
        new MagicItem().load(100, 10f, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.GREEN));
        new MagicPolymorph().load(100, 20f, new StoneType(EnumCrystalType.PURPLE, EnumCrystalType.BLACK));
    }
}
