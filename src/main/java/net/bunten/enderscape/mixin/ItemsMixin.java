package net.bunten.enderscape.mixin;

import net.bunten.enderscape.registry.EnderscapeItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Items.class)
public abstract class ItemsMixin {

    //@ModifyArgs(method = "registerItem(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/Item;)Lnet/minecraft/world/item/Item;", at = @At(value = "INVOKE", target = ""))
  //  private static void Enderscape$registerItem(Args args) {
   //     ResourceKey<Item> key = args.get(0);
   //     if (key.location().equals(ResourceLocation.withDefaultNamespace("shulker_shell"))) {
   //         args.set(2, EnderscapeItems.SHULKER_SHELL_PROPERTIES.get());
  //      }
   // }
}