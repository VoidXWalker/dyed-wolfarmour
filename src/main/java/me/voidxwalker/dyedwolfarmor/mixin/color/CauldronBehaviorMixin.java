package me.voidxwalker.dyedwolfarmor.mixin.color;

import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {

    @Shadow @Final CauldronBehavior CLEAN_DYEABLE_ITEM = null;

    @Inject(method = "registerBehavior",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/cauldron/CauldronBehavior;registerBucketBehavior(Ljava/util/Map;)V",ordinal = 1),locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void setCleanable(CallbackInfo ci, Map<Item, CauldronBehavior> map, Map<Item, CauldronBehavior> map2){
        map2.put(Items.WOLF_ARMOR, CLEAN_DYEABLE_ITEM);
    }
}
