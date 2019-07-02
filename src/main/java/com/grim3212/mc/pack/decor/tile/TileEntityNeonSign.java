package com.grim3212.mc.pack.decor.tile;

import java.util.UUID;

import javax.annotation.Nullable;

import com.grim3212.mc.pack.core.tile.TileEntityGrim;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityNeonSign extends TileEntityGrim implements ICommandSource {

	public final ITextComponent[] signText = new ITextComponent[] { new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent(""), new StringTextComponent("") };

	@OnlyIn(Dist.CLIENT)
	private boolean field_214070_b;
	private int lineBeingEdited = -1;
	private int field_214071_g = -1;
	private int field_214072_h = -1;
	private UUID owner;
	public int mode = 0;

	public TileEntityNeonSign() {
		super(DecorTileEntities.NEON_SIGN);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);
		compound.putInt("Mode", mode);

		for (int i = 0; i < 4; ++i) {
			String s = ITextComponent.Serializer.toJson(this.signText[i]);

			compound.putString("Text" + (i + 1), s);
		}

		compound.putUniqueId("Owner", owner);

		return compound;
	}

	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		this.mode = compound.getInt("Mode");

		for (int i = 0; i < 4; ++i) {
			String s = compound.getString("Text" + (i + 1));
			ITextComponent itextcomponent = ITextComponent.Serializer.fromJson(s);
			if (this.world instanceof ServerWorld) {
				try {
					this.signText[i] = TextComponentUtils.updateForEntity(this.getCommandSource((ServerPlayerEntity) null), itextcomponent, (Entity) null, 0);
				} catch (CommandSyntaxException var6) {
					this.signText[i] = itextcomponent;
				}
			} else {
				this.signText[i] = itextcomponent;
			}
		}

		this.owner = compound.getUniqueId("Owner");
	}

	@OnlyIn(Dist.CLIENT)
	public ITextComponent getText(int line) {
		return this.signText[line];
	}

	public void setText(int line, ITextComponent text) {
		this.signText[line] = text;
	}

	@Override
	public boolean onlyOpsCanSetNbt() {
		return true;
	}

	public void setOwner(Entity newOwner) {
		this.owner = newOwner.getUniqueID();
	}

	public Entity getOwner() {
		return this.owner != null && this.world instanceof ServerWorld ? ((ServerWorld) this.world).getEntityByUuid(this.owner) : null;
	}

	public boolean executeCommand(PlayerEntity playerIn) {
		for (ITextComponent itextcomponent : this.signText) {
			Style style = itextcomponent == null ? null : itextcomponent.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickevent = style.getClickEvent();
				if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					playerIn.getServer().getCommandManager().handleCommand(this.getCommandSource((ServerPlayerEntity) playerIn), clickevent.getValue());
				}
			}
		}

		return true;
	}

	@Override
	public void sendMessage(ITextComponent component) {
	}

	public CommandSource getCommandSource(@Nullable ServerPlayerEntity playerIn) {
		String s = playerIn == null ? "Sign" : playerIn.getName().getString();
		ITextComponent itextcomponent = (ITextComponent) (playerIn == null ? new StringTextComponent("Sign") : playerIn.getDisplayName());
		return new CommandSource(this, new Vec3d((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D), Vec2f.ZERO, (ServerWorld) this.world, 2, s, itextcomponent, this.world.getServer(), playerIn);
	}

	@Override
	public boolean shouldReceiveFeedback() {
		return false;
	}

	@Override
	public boolean shouldReceiveErrors() {
		return false;
	}

	@Override
	public boolean allowLogging() {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public void fromScreen(int p_214062_1_, int p_214062_2_, int p_214062_3_, boolean p_214062_4_) {
		this.lineBeingEdited = p_214062_1_;
		this.field_214071_g = p_214062_2_;
		this.field_214072_h = p_214062_3_;
		this.field_214070_b = p_214062_4_;
	}

	@OnlyIn(Dist.CLIENT)
	public void reset() {
		this.lineBeingEdited = -1;
		this.field_214071_g = -1;
		this.field_214072_h = -1;
		this.field_214070_b = false;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean func_214069_r() {
		return this.field_214070_b;
	}

	@OnlyIn(Dist.CLIENT)
	public int getEditLine() {
		return this.lineBeingEdited;
	}

	@OnlyIn(Dist.CLIENT)
	public int func_214065_t() {
		return this.field_214071_g;
	}

	@OnlyIn(Dist.CLIENT)
	public int func_214067_u() {
		return this.field_214072_h;
	}
}
