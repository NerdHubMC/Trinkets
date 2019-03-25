package nerdhub.trinkets.api;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;

/**
 * Trinkets API methods
 *
 * @author BrockWS
 * @since 1.0.0
 */
public interface ITrinketsApi {

    /**
     * Check if Trinkets has loaded initial Trinkets data
     *
     * @return True of mod has loaded, else false
     */
    boolean isModLoaded();

    /**
     * Creates a new Trinket Type
     *
     * @param id Unique ID of the Trinket Type. Must be the same as the tag
     * @return New Trinket Type
     */
    ITrinketType createTrinketType(Identifier id);

    /**
     * Creates a new Trinket Type
     *
     * @param id  Unique ID of the Trinket Type. Must be the same as the tag
     * @param key The translation key to be used for tooltips, defaults to tooltips.trinkets.id
     * @return New Trinket Type
     */
    ITrinketType createTrinketType(Identifier id, String key);

    /**
     * Returns a cached list of all registered Trinket Types
     *
     * @return List of all Trinket Types
     */
    List<ITrinketType> getTrinketTypes();

    /**
     * Returns a list of all trinket types that are attached to the given item
     *
     * @param stack ItemStack to check
     * @return List of Trinket Types
     */
    List<ITrinketType> getTypesFromItem(ItemStack stack);

    /**
     * Returns a list of all trinket types that are attached to the given item
     *
     * @param item Item to check
     * @return List of Trinket Types
     */
    List<ITrinketType> getTypesFromItem(Item item);

    /**
     * Checks if the given item is a Trinket
     *
     * @param stack Stack to check
     * @return True if the item is a Trinket, else false
     */
    boolean isTrinket(ItemStack stack);

    /**
     * Checks if the given item is a Trinket
     *
     * @param item Item to check
     * @return True if the item is a Trinket, else false
     */
    boolean isTrinket(Item item);

    /**
     * Checks if the given stack has the given TrinketType
     *
     * @param stack ItemStack to check
     * @param type  TrinketType to check
     * @return True if the item has the Trinket Type, else false
     */
    boolean isTrinketType(ItemStack stack, ITrinketType type);

    /**
     * Checks if the given stack has the given TrinketType
     *
     * @param item Item to check
     * @param type TrinketType to check
     * @return True if the item has the Trinket Type, else false
     */
    boolean isTrinketType(Item item, ITrinketType type);

    /**
     * Creates a new Trinket Space for the Trinkets Inventory. A space is basically a slot in the Screen.
     * Contact us if you are adding extra slots and we may consider adding it natively in the mod.
     *
     * @param type     The type of Trinket that can be stored here, can be null for any type of Trinket
     * @param x        Position along the X axis, by default is restricted to between 0 and 4
     * @param y        Position along the Y axis, by default is restricted to between 0 and 3
     * @param slotIcon The slot icon that will be drawn when no Trinket is placed in the slot
     * @return The new Trinket Space
     */
    ITrinketSpace createTrinketSpace(ITrinketType type, int x, int y, Identifier slotIcon);

    /**
     * Returns the list of Trinket Spaces
     *
     * @return Unmodifiable list of Trinket Spaces
     */
    List<ITrinketSpace> getTrinketSpaces();

    /**
     * Gets the Localized Name for the Trinket Type
     *
     * @param type Type to get the name of
     * @return Translated String
     */
    String getLocalizedName(ITrinketType type);

    /**
     * Builds the tooltip TextComponent that gets attached to every item that has a Trinkets tag
     *
     * @param stack Stack to build tooltip from
     * @return Text Component
     */
    TextComponent getTooltipComponent(ItemStack stack);

    /**
     * Builds the tooltip TextComponent that gets attached to every item that has a Trinkets tag
     *
     * @param item Item to build tooltip from
     * @return Text Component
     */
    TextComponent getTooltipComponent(Item item);

    /**
     * Builds the tooltip TextComponent that gets attached to every item that has a Trinkets tag
     *
     * @param types List of types to be added to the tooltip
     * @return Text Component
     */
    TextComponent getTooltipComponent(ITrinketType... types);

    /**
     * Builds the tooltip TextComponent that gets attached to every item that has a Trinkets tag
     *
     * @param types List of types to be added to the tooltip
     * @return Text Component
     */
    TextComponent getTooltipComponent(List<ITrinketType> types);

    /**
     * Call to open the Trinkets Screen
     * <p>
     * CLIENT ONLY
     */
    void openTrinketsScreen();

    /**
     * Call to open the Trinkets Screen.
     * <p>
     * SERVER ONLY
     *
     * @param player Player
     */
    void openTrinketsScreen(PlayerEntity player);
}
