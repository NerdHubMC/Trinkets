package nerdhub.trinkets.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * May be implemented by an item to allow more control.
 *
 * @author BrockWS
 * @since 1.0.0
 */
public interface ITrinket {

    /**
     * Called when the Trinket is equipped by the player
     *
     * @param stack Stack of Trinket
     * @param player Player that equipped the Trinket
     * @param type The type the Trinket was equipped as
     */
    default void onEquipped(ItemStack stack, PlayerEntity player, ITrinketType type) {

    }

    /**
     * Called when the Trinket is unequipped by the player
     *
     * @param stack Stack of Trinket
     * @param player Player that unequipped the Trinket
     * @param type The type the Trinket was equipped as
     */
    default void onUnequipped(ItemStack stack, PlayerEntity player, ITrinketType type) {

    }

    /**
     * Called every Player Tick while equipped.
     * <br>
     * <strong>Try to only do stuff every X ticks instead</strong>
     *
     * @param stack Stack of Trinket
     * @param player Player that has equipped the Trinket
     * @param type The type the Trinket was equipped as
     */
    default void onTick(ItemStack stack, PlayerEntity player, ITrinketType type) {

    }

    /**
     * Called before the stack is placed in the Trinkets inventory
     *
     * @param stack Stack of Trinket
     * @param player Player that is trying to equip the Trinket
     * @param type The type the player is trying to equip to
     * @return True if it can be equipped, else False
     */
    default boolean canEquip(ItemStack stack, PlayerEntity player, ITrinketType type) {
        return true;
    }

    /**
     * Called before the stack is removed from the Trinkets inventory
     *
     * @param stack Stack of Trinket
     * @param player The player that is trying to unequip the Trinket
     * @param type The type the Trinket was equipped as
     * @return True if the Trinket can be unequipped, else False
     */
    default boolean canUnequip(ItemStack stack, PlayerEntity player, ITrinketType type) {
        return true;
    }
}
