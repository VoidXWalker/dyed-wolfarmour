package me.voidxwalker.dyedwolfarmor.mixin.color;

import com.llamalad7.mixinextras.sugar.Local;
import me.voidxwalker.dyedwolfarmor.DyedArmor;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(net.minecraft.client.render.entity.feature.WolfArmorFeatureRenderer.class)
public class WolfArmorFeatureRendererMixin {
    @ModifyArgs(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/WolfEntity;FFFFFF)V", at = @org.spongepowered.asm.mixin.injection.At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/WolfEntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    public void colorWolfArmorModel(Args args, @Local(ordinal = 0) WolfEntity wolf) {
        System.out.println(new ItemStack(Items.WOLF_ARMOR).isIn(ItemTags.TRIMMABLE_ARMOR));
        int m =((DyedArmor) wolf).getArmorColor();
        if(m != DyeableItem.DEFAULT_COLOR){
            args.set(4, (m >> 16 & 255) / 255.0F);
            args.set(5, (m >> 8 & 255) / 255.0F);
            args.set(6, (m & 255) / 255.0F);
        }
    }
}
