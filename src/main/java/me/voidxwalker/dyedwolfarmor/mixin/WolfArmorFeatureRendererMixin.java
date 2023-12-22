package me.voidxwalker.dyedwolfarmor.mixin;

import me.voidxwalker.dyedwolfarmor.DyedArmor;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeableItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(net.minecraft.client.render.entity.feature.WolfArmorFeatureRenderer.class)
public class WolfArmorFeatureRendererMixin {
    @ModifyArgs(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/WolfEntity;FFFFFF)V", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/WolfArmorFeatureRenderer;renderModel(Lnet/minecraft/client/render/entity/model/EntityModel;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFF)V"))
    public void colorWolfArmorModel(Args args) {
        WolfEntity wolf = args.get(5);
        int m =((DyedArmor) wolf).getArmorColor();
        if(m != DyeableItem.DEFAULT_COLOR){
            args.set(6, (m >> 16 & 255) / 255.0F);
            args.set(7, (m >> 8 & 255) / 255.0F);
            args.set(8, (m & 255) / 255.0F);
        }
    }
}
