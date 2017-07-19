package com.grim3212.mc.pack.tools.client;

import com.grim3212.mc.pack.GrimPack;
import com.grim3212.mc.pack.core.client.entity.RenderProjectile.RenderProjectileFactory;
import com.grim3212.mc.pack.core.client.entity.RenderThrowable.RenderThrowableFactory;
import com.grim3212.mc.pack.core.proxy.ClientProxy;
import com.grim3212.mc.pack.tools.client.entity.RenderBlockPushPullFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderBoomerang.RenderBoomerangFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderRayGun.RenderRayGunFactory;
import com.grim3212.mc.pack.tools.client.entity.RenderSlingPellet.RenderSlingPelletFactory;
import com.grim3212.mc.pack.tools.entity.EntityAdvRayw;
import com.grim3212.mc.pack.tools.entity.EntityBallisticKnife;
import com.grim3212.mc.pack.tools.entity.EntityBlockPushPull;
import com.grim3212.mc.pack.tools.entity.EntityBoomerang;
import com.grim3212.mc.pack.tools.entity.EntityDiamondBoomerang;
import com.grim3212.mc.pack.tools.entity.EntityKnife;
import com.grim3212.mc.pack.tools.entity.EntityPokeball;
import com.grim3212.mc.pack.tools.entity.EntityRayw;
import com.grim3212.mc.pack.tools.entity.EntitySlimeSpear;
import com.grim3212.mc.pack.tools.entity.EntitySlingPellet;
import com.grim3212.mc.pack.tools.entity.EntitySpear;
import com.grim3212.mc.pack.tools.entity.EntityTomahawk;
import com.grim3212.mc.pack.tools.items.ItemBackpack;
import com.grim3212.mc.pack.tools.items.ItemPelletBag;
import com.grim3212.mc.pack.tools.items.ToolsItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ToolsClientProxy extends ClientProxy {

	@Override
	public void preInit() {
		// ENTITYS
		RenderingRegistry.registerEntityRenderingHandler(EntityBlockPushPull.class, new RenderBlockPushPullFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPokeball.class, new RenderThrowableFactory(new ItemStack(ToolsItems.pokeball)));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlingPellet.class, new RenderSlingPelletFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimPack.modID, "textures/entities/sonicw.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityAdvRayw.class, new RenderRayGunFactory(new ResourceLocation(GrimPack.modID, "textures/entities/sonic_adv.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntitySlimeSpear.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/spears.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityBallisticKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/ballistic_knife.png")));
		RenderingRegistry.registerEntityRenderingHandler(EntityTomahawk.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/tomahawk.png"), true));
		RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderProjectileFactory(new ResourceLocation(GrimPack.modID, "textures/entities/throwing_knife.png"), true));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerangFactory(new ResourceLocation(GrimPack.modID, "textures/items/boomerang.png"), "grimpack:items/boomerang"));
		RenderingRegistry.registerEntityRenderingHandler(EntityDiamondBoomerang.class, new RenderBoomerangFactory(new ResourceLocation(GrimPack.modID, "textures/items/diamond_boomerang.png"), "grimpack:items/diamond_boomerang"));

		// Key bindings
		MinecraftForge.EVENT_BUS.register(new KeyBindHelper());
	}

	@Override
	public void initColors() {
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (tintIndex == 1)
					return Integer.parseInt(ItemBackpack.colorNumbers[15], 16);
				else {
					int packColor = ItemBackpack.getColor(stack);

					if (packColor < 0) {
						return Integer.parseInt("C65C35", 16);
					} else {
						return Integer.parseInt(ItemBackpack.colorNumbers[packColor], 16);
					}
				}
			}
		}, ToolsItems.backpack);

		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				if (tintIndex == 1)
					return Integer.parseInt(ItemPelletBag.colorNumbers[15], 16);
				else {
					int packColor = ItemPelletBag.getColor(stack);

					if (packColor < 0) {
						return Integer.parseInt("C65C35", 16);
					} else {
						return Integer.parseInt(ItemPelletBag.colorNumbers[packColor], 16);
					}
				}
			}
		}, ToolsItems.pellet_bag);
	}
}
