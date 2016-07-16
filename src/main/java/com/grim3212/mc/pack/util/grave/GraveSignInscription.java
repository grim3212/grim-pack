package com.grim3212.mc.pack.util.grave;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.minecraft.entity.player.EntityPlayerMP;

public class GraveSignInscription implements IGraveSignInscription {

	public String[] getInscription(EntityPlayerMP player) {
		return new String[] { player.getName(), "", new SimpleDateFormat("MMM d ''yy").format(Calendar.getInstance().getTime()), new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()) };
	}
}