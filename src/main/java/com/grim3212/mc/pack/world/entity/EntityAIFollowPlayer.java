package com.grim3212.mc.pack.world.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

public class EntityAIFollowPlayer extends EntityAIBase {
	private final EntityLiving theFollower;
	private EntityPlayer thePlayer;
	private final PathNavigate followerPathfinder;
	World world;
	private final double followSpeed;
	private int timeToRecalcPath;
	float maxDist;
	float minDist;

	public EntityAIFollowPlayer(EntityLiving theFollower, double followSpeedIn, float minDistIn, float maxDistIn) {
		this.theFollower = theFollower;
		this.world = theFollower.world;
		this.followerPathfinder = theFollower.getNavigator();
		this.followSpeed = followSpeedIn;
		this.minDist = minDistIn;
		this.maxDist = maxDistIn;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		EntityPlayer player = world.getClosestPlayerToEntity(theFollower, minDist * maxDist);

		if (player == null) {
			return false;
		} else if (player.isSpectator()) {
			return false;
		} else if (this.theFollower.getDistanceSqToEntity(player) < (double) (this.minDist * this.minDist)) {
			return false;
		} else {
			this.thePlayer = player;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !this.followerPathfinder.noPath() && this.theFollower.getDistanceSqToEntity(this.thePlayer) > (double) (this.maxDist * this.maxDist) && this.theFollower.getAttackTarget() == null;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.timeToRecalcPath = 0;
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.thePlayer = null;
		this.followerPathfinder.clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		this.theFollower.getLookHelper().setLookPositionWithEntity(this.thePlayer, 10.0F, (float) this.theFollower.getVerticalFaceSpeed());

		if (--this.timeToRecalcPath <= 0) {
			this.timeToRecalcPath = 10;

			this.followerPathfinder.tryMoveToEntityLiving(this.thePlayer, this.followSpeed);
		}
	}
}