package com.craftless.tutorial.entities;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags.Blocks;

public class GenericBulletEntity extends Entity {
   private UUID field_234609_b_;
   private int field_234610_c_;
   private boolean field_234611_d_;
   private double damage = 2.0D;
   private int knockbackStrength;
   private SoundEvent hitSound = this.getHitEntitySound();
   
   private static final DataParameter<Byte> PIERCE_LEVEL = EntityDataManager.createKey(AbstractArrowEntity.class, DataSerializers.BYTE);


   public GenericBulletEntity(EntityType<? extends GenericBulletEntity> p_i231584_1_, World p_i231584_2_) {
      super(p_i231584_1_, p_i231584_2_);
      this.setNoGravity(true);
   }
   
   

   public GenericBulletEntity(EntityType<? extends GenericBulletEntity> p_i231584_1_, World p_i231584_2_, boolean isAffectedByGravity) {
	      super(p_i231584_1_, p_i231584_2_);
	      this.setNoGravity(!isAffectedByGravity);
   }

   public void setShooter(@Nullable Entity entityIn) {
      if (entityIn != null) {
         this.field_234609_b_ = entityIn.getUniqueID();
         this.field_234610_c_ = entityIn.getEntityId();
      }

   }

   protected SoundEvent getHitEntitySound() {
      return SoundEvents.ITEM_BOTTLE_FILL;
   }


   @Nullable
   public Entity func_234616_v_() {
      if (this.field_234609_b_ != null && this.world instanceof ServerWorld) {
         return ((ServerWorld)this.world).getEntityByUuid(this.field_234609_b_);
      } else {
         return this.field_234610_c_ != 0 ? this.world.getEntityByID(this.field_234610_c_) : null;
      }
   }

   protected void writeAdditional(CompoundNBT compound) {
      if (this.field_234609_b_ != null) {
         compound.putUniqueId("Owner", this.field_234609_b_);
      }

      if (this.field_234611_d_) {
         compound.putBoolean("LeftOwner", true);
      }
      
      compound.putDouble("damage", this.damage);


   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   protected void readAdditional(CompoundNBT compound) {
      if (compound.hasUniqueId("Owner")) {
         this.field_234609_b_ = compound.getUniqueId("Owner");
      }

      this.field_234611_d_ = compound.getBoolean("LeftOwner");
      

      if (compound.contains("damage", 99)) {
         this.damage = compound.getDouble("damage");
      }
   }

   /**
    * Called to update the entity's position/logic.
    */
   @Override
   public void tick() {
	      super.tick();
	      boolean flag = this.noClip;
	      Vector3d vector3d = this.getMotion();
	      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
	         float f = MathHelper.sqrt(horizontalMag(vector3d));
	         this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
	         this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
	         this.prevRotationYaw = this.rotationYaw;
	         this.prevRotationPitch = this.rotationPitch;
	      }

	      BlockPos blockpos = this.getPosition();
	      BlockState blockstate = this.world.getBlockState(blockpos);
	      if (!blockstate.isAir(this.world, blockpos) && !flag) {
	         VoxelShape voxelshape = blockstate.getCollisionShape(this.world, blockpos);
	         if (!voxelshape.isEmpty()) {
	            Vector3d vector3d1 = this.getPositionVec();

	            for(AxisAlignedBB axisalignedbb : voxelshape.toBoundingBoxList()) {
	               if (axisalignedbb.offset(blockpos).contains(vector3d1)) {
	                  this.onGround = true;
	                  break;
	               }
	            }
	         }
	      }

	      if (this.isWet()) {
	         this.extinguish();
	      }

	      if (this.onGround && !flag) {
	         this.remove();

	      } else {
	         Vector3d vector3d2 = this.getPositionVec();
	         Vector3d vector3d3 = vector3d2.add(vector3d);
	         RayTraceResult raytraceresult = this.world.rayTraceBlocks(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
	         if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
	            vector3d3 = raytraceresult.getHitVec();
	         }

	         while(!this.removed) {
	            EntityRayTraceResult entityraytraceresult = this.rayTraceEntities(vector3d2, vector3d3);
	            if (entityraytraceresult != null) {
	               raytraceresult = entityraytraceresult;
	            }

	            if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
	               Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
	               Entity entity1 = this.func_234616_v_();
	               if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canAttackPlayer((PlayerEntity)entity)) {
	                  raytraceresult = null;
	                  entityraytraceresult = null;
	               }
	            }

	            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
	               this.onImpact(raytraceresult);
	               this.isAirBorne = true;
	            }

	            if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
	               break;
	            }

	            raytraceresult = null;
	         }

	         vector3d = this.getMotion();
	         double d3 = vector3d.x;
	         double d4 = vector3d.y;
	         double d0 = vector3d.z;

	         double d5 = this.getPosX() + d3;
	         double d1 = this.getPosY() + d4;
	         double d2 = this.getPosZ() + d0;
	         float f1 = MathHelper.sqrt(horizontalMag(vector3d));
	         if (flag) {
	            this.rotationYaw = (float)(MathHelper.atan2(-d3, -d0) * (double)(180F / (float)Math.PI));
	         } else {
	            this.rotationYaw = (float)(MathHelper.atan2(d3, d0) * (double)(180F / (float)Math.PI));
	         }

	         this.rotationPitch = (float)(MathHelper.atan2(d4, (double)f1) * (double)(180F / (float)Math.PI));
	         this.rotationPitch = func_234614_e_(this.prevRotationPitch, this.rotationPitch);
	         this.rotationYaw = func_234614_e_(this.prevRotationYaw, this.rotationYaw);
	         float f2 = 0.99F;
	         float f3 = 0.05F;
	         if (this.isInWater()) {
	            for(int j = 0; j < 4; ++j) {
	               float f4 = 0.25F;
	               this.world.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
	            }

	            f2 = this.getWaterDrag();
	         }

	         this.setMotion(vector3d.scale((double)f2));
	         if (!this.hasNoGravity() && !flag) {
	            Vector3d vector3d4 = this.getMotion();
	            this.setMotion(vector3d4.x, vector3d4.y - (double)0.05F, vector3d4.z);
	         }

	         this.setPosition(d5, d1, d2);
	         this.doBlockCollisions();
	      }
	      
	      
	      
	      
	      
	      
	      
	   }

   private int getPierceLevel() {
	return this.dataManager.get(PIERCE_LEVEL);
   }

private boolean func_234615_h_() {
      Entity entity = this.func_234616_v_();
      if (entity != null) {
         for(Entity entity1 : this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), (p_234613_0_) -> {
            return !p_234613_0_.isSpectator() && p_234613_0_.canBeCollidedWith();
         })) {
            if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
               return false;
            }
         }
      }

      return true;
   }

   /**
    * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
    */
   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      Vector3d vector3d = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy, this.rand.nextGaussian() * (double)0.0075F * (double)inaccuracy).scale((double)velocity);
      this.setMotion(vector3d);
      float f = MathHelper.sqrt(horizontalMag(vector3d));
      this.rotationYaw = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI));
      this.rotationPitch = (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI));
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
   }

   public void func_234612_a_(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
      float f = -MathHelper.sin(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      float f1 = -MathHelper.sin((p_234612_2_ + p_234612_4_) * ((float)Math.PI / 180F));
      float f2 = MathHelper.cos(p_234612_3_ * ((float)Math.PI / 180F)) * MathHelper.cos(p_234612_2_ * ((float)Math.PI / 180F));
      this.shoot((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
      Vector3d vector3d = p_234612_1_.getMotion();
      this.setMotion(this.getMotion().add(vector3d.x, p_234612_1_.isOnGround() ? 0.0D : vector3d.y, vector3d.z));
   }

   /**
    * Called when this EntityFireball hits a block or entity.
    */
   protected void onImpact(RayTraceResult result) {
      RayTraceResult.Type raytraceresult$type = result.getType();
      if (raytraceresult$type == RayTraceResult.Type.ENTITY) {
         this.onEntityHit((EntityRayTraceResult)result);
      } else if (raytraceresult$type == RayTraceResult.Type.BLOCK) {
         this.func_230299_a_((BlockRayTraceResult)result);
      }

   }

  

   /**
    * Called when the arrow hits an entity
    */
   protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
   }
 
   protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
      BlockState blockstate = this.world.getBlockState(p_230299_1_.getPos());
      if (blockstate.getBlock().getTags().contains(Blocks.GLASS.getName()))
      {
    	  world.setBlockState(p_230299_1_.getPos(), net.minecraft.block.Blocks.AIR.getDefaultState());
      }
   }

   /**
    * Updates the entity motion clientside, called by packets from the server
    */
   @OnlyIn(Dist.CLIENT)
   public void setVelocity(double x, double y, double z) {
      this.setMotion(x, y, z);
      if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
         float f = MathHelper.sqrt(x * x + z * z);
         this.rotationPitch = (float)(MathHelper.atan2(y, (double)f) * (double)(180F / (float)Math.PI));
         this.rotationYaw = (float)(MathHelper.atan2(x, z) * (double)(180F / (float)Math.PI));
         this.prevRotationPitch = this.rotationPitch;
         this.prevRotationYaw = this.rotationYaw;
         this.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
      }

   }

   protected boolean func_230298_a_(Entity p_230298_1_) {
      if (!p_230298_1_.isSpectator() && p_230298_1_.isAlive() && p_230298_1_.canBeCollidedWith()) {
         Entity entity = this.func_234616_v_();
         return entity == null || this.field_234611_d_ || !entity.isRidingSameEntity(p_230298_1_);
      } else {
         return false;
      }
   }

   protected void func_234617_x_() {
      Vector3d vector3d = this.getMotion();
      float f = MathHelper.sqrt(horizontalMag(vector3d));
      this.rotationPitch = func_234614_e_(this.prevRotationPitch, (float)(MathHelper.atan2(vector3d.y, (double)f) * (double)(180F / (float)Math.PI)));
      this.rotationYaw = func_234614_e_(this.prevRotationYaw, (float)(MathHelper.atan2(vector3d.x, vector3d.z) * (double)(180F / (float)Math.PI)));
   }

   @Nullable
   protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
      return ProjectileHelper.rayTraceEntities(this.world, this, startVec, endVec, this.getBoundingBox().expand(this.getMotion()).grow(1.0D), this::func_230298_a_);
   }
   
   protected static float func_234614_e_(float p_234614_0_, float p_234614_1_) {
      while(p_234614_1_ - p_234614_0_ < -180.0F) {
         p_234614_0_ -= 360.0F;
      }

      while(p_234614_1_ - p_234614_0_ >= 180.0F) {
         p_234614_0_ += 360.0F;
      }

      return MathHelper.lerp(0.2F, p_234614_0_, p_234614_1_);
   }

   public void setPierceLevel(byte level) {
	      this.dataManager.set(PIERCE_LEVEL, level);
   }
	@Override
	protected void registerData() 
	{
	      this.dataManager.register(PIERCE_LEVEL, (byte)0);
	}

	public void setEnchantmentEffectsFromEntity(LivingEntity p_190547_1_, float p_190547_2_) {
	      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
	      int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
	      this.setDamage((double)(p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().getId() * 0.11F));
	      if (i > 0) {
	         this.setDamage(this.getDamage() + (double)i * 0.5D + 0.5D);
	      }

	      if (j > 0) {
	         this.setKnockbackStrength(j);
	      }

	      if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0) {
	         this.setFire(100);
	      }

	   }

	
	@Override
	public IPacket<?> createSpawnPacket() {
		return null;
	}
	
	public void setDamage(double damageIn) {
	      this.damage = damageIn;
	   }

	   public double getDamage() {
	      return this.damage;
	   }

	   /**
	    * Sets the amount of knockback the arrow applies when it hits a mob.
	    */
	   public void setKnockbackStrength(int knockbackStrengthIn) {
	      this.knockbackStrength = knockbackStrengthIn;
	   }

	   /**
	    * Returns true if it's possible to attack this entity with an item.
	    */
	   public boolean canBeAttackedWithItem() {
	      return false;
	   }

	   protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
	      return 0.13F;
	   }



	protected float getWaterDrag() 
	{
		return 0.6F;
	}
}