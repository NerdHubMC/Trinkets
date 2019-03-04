package nerdhub.trinkets.internal;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.InventoryUtil;

import nerdhub.trinkets.api.ITrinket;
import nerdhub.trinkets.api.TrinketsApi;

/**
 * @author BrockWS
 */
public final class TrinketsInventory implements Inventory {

    private PlayerEntity player;
    private int size;
    private DefaultedList<ItemStack> stacks;

    public TrinketsInventory(PlayerEntity player, int size) {
        this.player = player;
        this.size = size;
        this.stacks = DefaultedList.create(this.size, ItemStack.EMPTY);
    }

    @Override
    public int getInvSize() {
        return this.size;
    }

    @Override
    public boolean isInvEmpty() {
        return this.stacks.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return slot >= 0 && slot < this.stacks.size() ? this.stacks.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        if (!this.canUnequip(slot))
            return ItemStack.EMPTY;
        ItemStack stack = InventoryUtil.splitStack(this.stacks, slot, amount);
        if (!stack.isEmpty())
            this.markDirty();
        return stack;
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        ItemStack stack = this.stacks.set(slot, ItemStack.EMPTY);
        this.markDirty();
        return stack;
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getAmount() > this.getInvMaxStackAmount()) {
            stack.setAmount(this.getInvMaxStackAmount());
        }

        this.markDirty();
    }

    @Override
    public int getInvMaxStackAmount() {
        return 1;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) {
        return this.canEquip(slot, stack);
    }

    @Override
    public void clear() {
        this.stacks = DefaultedList.create(this.size, ItemStack.EMPTY);
    }

    private boolean canUnequip(int slot) {
        ItemStack stack = this.getInvStack(slot);
        if (stack.isEmpty() || !(stack.getItem() instanceof ITrinket))
            return true;
        return ((ITrinket) stack.getItem()).canUnequip(stack, this.player, TrinketsApi.instance().getTrinketSpaces().get(slot).getTrinketType());
    }

    private boolean canEquip(int slot, ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof ITrinket))
            return false;
        return ((ITrinket) stack.getItem()).canEquip(stack, this.player, TrinketsApi.instance().getTrinketSpaces().get(slot).getTrinketType());
    }
}
