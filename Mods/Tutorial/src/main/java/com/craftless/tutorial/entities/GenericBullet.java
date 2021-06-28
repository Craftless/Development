package com.craftless.tutorial.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GenericBullet extends Entity
{

	private UUID shooterUUID;
	private int shooterID;

	
	public GenericBullet(EntityType<?> entityTypeIn, World worldIn) 
	{
		super(entityTypeIn, worldIn);
	}
	
	public void setShooter(@Nullable Entity entityIn) {
		if (entityIn != null) {
			this.shooterUUID = entityIn.getUniqueID();
	        this.shooterID = entityIn.getEntityId();
	    }
	}

	@Override
	protected void registerData() {
	}

	@Override
	protected void readAdditional(CompoundNBT compound) 
	{
	}

	@Override
	protected void writeAdditional(CompoundNBT compound) {
	}
	
	

	@Nullable
    public Entity getShooter() {
       if (this.shooterUUID != null && this.world instanceof ServerWorld) {
          return ((ServerWorld)this.world).getEntityByUuid(this.shooterUUID);
       } else {
          return this.shooterID != 0 ? this.world.getEntityByID(this.shooterID) : null;
       }
    }
	
	@Override
	public IPacket<?> createSpawnPacket() {
		Entity entity = this.getShooter();
	    return new SSpawnObjectPacket(this, entity == null ? 0 : entity.getEntityId());
	}


	


	
}
