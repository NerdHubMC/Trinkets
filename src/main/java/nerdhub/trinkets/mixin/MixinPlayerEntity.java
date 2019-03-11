package nerdhub.trinkets.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import nerdhub.trinkets.internal.ITrinketsHolder;
import nerdhub.trinkets.internal.TrinketsInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author BrockWS
 */
@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity implements ITrinketsHolder {

    private Inventory trinketsInv;

    public Inventory getTrinketsInventory() {
        return trinketsInv;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        trinketsInv = new TrinketsInventory((PlayerEntity) (Object)this, 16);
    }

    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"))
    private void dropInventory(CallbackInfo ci) {
        if (this.trinketsInv.isInvEmpty())
            return;
        for (int i = 0; i < this.trinketsInv.getInvSize(); i++) {
            ItemStack stack = this.trinketsInv.getInvStack(i);
            if (stack == null || stack.isEmpty())
                continue;
            this.dropItem(stack, true, false);
            this.trinketsInv.setInvStack(i, ItemStack.EMPTY);
        }
    }

    @Inject(method = "readCustomDataFromTag", at = @At(value = "TAIL"))
    private void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        if (tag.containsKey("trinkets", 9)) {
            ListTag list = tag.getList("trinkets", 10);

            for (int i = 0; i < list.size(); i++) {
                trinketsInv.setInvStack(i, ItemStack.fromTag(list.getCompoundTag(i)));
            }
        }
    }

    @Inject(method = "writeCustomDataToTag", at = @At(value = "TAIL"))
    private void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        ListTag list = new ListTag();

        for (int i = 0; i < trinketsInv.getInvSize(); i++) {
            ItemStack stack = trinketsInv.getInvStack(i);
            CompoundTag compoundTag = new CompoundTag();
            if (!stack.isEmpty())
                stack.toTag(compoundTag);
            list.add(compoundTag);
        }

        tag.put("trinkets", list);
    }

    @Shadow
    public abstract ItemEntity dropItem(ItemStack itemStack_1, boolean boolean_1, boolean boolean_2);
}
