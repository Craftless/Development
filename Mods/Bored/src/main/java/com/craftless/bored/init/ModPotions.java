package com.craftless.test.init;

import com.craftless.test.Test;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModPotions 
{
	public static final DeferredRegister<Effect> POTION_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Test.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Test.MOD_ID);
	
	public static final RegistryObject<Potion> HASTE_POTION = POTIONS.register("haste_effect", () -> new Potion(new EffectInstance(Effects.HASTE, 30 * 20 * 60)));
}
