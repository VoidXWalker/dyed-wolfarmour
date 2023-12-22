package me.voidxwalker.dyedwolfarmor.mixin;

import me.voidxwalker.dyedwolfarmor.DyedArmor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.entity.passive.WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements DyedArmor {
    private static TrackedData<Integer> ARMOR_COLOR;

    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void onStatic(CallbackInfo ci) {
        ARMOR_COLOR = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initArmorTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(ARMOR_COLOR, DyeableItem.DEFAULT_COLOR);
    }
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeArmorData(net.minecraft.nbt.NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("ArmorColor", this.getArmorColor());
    }
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readArmorData(net.minecraft.nbt.NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("ArmorColor", 99)) {
            this.setArmorColor(nbt.getInt("ArmorColor"));
        }
    }
    @Inject(method = "interactMob",at = @At(value = "INVOKE",target= "Lnet/minecraft/entity/passive/WolfEntity;setArmored(Z)V"))
    public void setArmorColor(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if(item instanceof DyeableAnimalArmorItem dyeAble) {
            this.setArmorColor(dyeAble.getColor(itemStack));
        }
    }
    @Redirect(method = "interactMob",at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/passive/WolfEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
    public net.minecraft.entity.ItemEntity dropItem(WolfEntity wolfEntity, ItemConvertible itemConvertible) {
        Item item = itemConvertible.asItem();
        ItemStack itemStack = new ItemStack(item);
        if(item instanceof DyeableItem dyeAble) {
            dyeAble.setColor(itemStack, this.getArmorColor());
        }
        return this.dropStack(itemStack);
    }
    @Redirect(method = "dropInventory",at = @At(value = "INVOKE",target = "Lnet/minecraft/entity/passive/WolfEntity;dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;"))
    public net.minecraft.entity.ItemEntity dropItem2(WolfEntity wolfEntity, ItemConvertible itemConvertible) {
        Item item = itemConvertible.asItem();
        ItemStack itemStack = new ItemStack(item);
        if(item instanceof DyeableItem dyeAble) {
            dyeAble.setColor(itemStack, this.getArmorColor());
        }
        return this.dropStack(itemStack);
    }
    public int getArmorColor() {
        return this.dataTracker.get(ARMOR_COLOR);
    }
    public void setArmorColor(int color) {
        if (!this.getWorld().isClient) {
            this.dataTracker.set(ARMOR_COLOR, color);
        }
    }
}
