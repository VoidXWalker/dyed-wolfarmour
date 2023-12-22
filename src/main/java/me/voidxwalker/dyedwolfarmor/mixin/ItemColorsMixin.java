package me.voidxwalker.dyedwolfarmor.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemColors.class)
public class ItemColorsMixin {

    @ModifyReturnValue(method = "create", at = @At("RETURN"))
    private static ItemColors colorItem(ItemColors original) {
        original.register((stack, tintIndex) -> {
            int m = ((DyeableItem)stack.getItem()).getColor(stack);
            if(m == DyeableItem.DEFAULT_COLOR||tintIndex > 0)
                return -1;
            return m;
        }, Items.WOLF_ARMOR);
        return original;
    }
}
