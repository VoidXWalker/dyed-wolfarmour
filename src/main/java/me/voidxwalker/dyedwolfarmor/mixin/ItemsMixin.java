package me.voidxwalker.dyedwolfarmor.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.DyeableAnimalArmorItem;
import net.minecraft.server.command.DataCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(targets = "net.minecraft.item.Items")
public class ItemsMixin {
    @WrapOperation(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=wolf_armor")
            ),
            at = @At(value = "NEW", target = "Lnet/minecraft/item/AnimalArmorItem;*",ordinal = 0)
    )
    private static AnimalArmorItem modifyItemObject(int bonus,
                                                    AnimalArmorItem.Type type,
                                                    String name,
                                                    net.minecraft.item.Item.Settings settings,
                                                    Operation<DataCommand.ObjectType> original)
    {
        return new DyeableAnimalArmorItem(bonus, type, name, settings);
    }
}
