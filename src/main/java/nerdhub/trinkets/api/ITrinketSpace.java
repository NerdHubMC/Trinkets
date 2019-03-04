package nerdhub.trinkets.api;

import net.minecraft.util.Identifier;

/**
 * Holder object which specifies where a Trinket slot would go
 *
 * @author BrockWS
 * @since 1.0.0
 */
public interface ITrinketSpace {

    /**
     * Returns the type of Trinket that can be placed in this space
     *
     * @return Trinket Type
     */
    ITrinketType getTrinketType();

    /**
     * Returns the X Position of the slot
     *
     * @return How many slots from the left of the grid
     */
    int getX();

    /**
     * Returns the Y Position of the slot
     *
     * @return How many slots from the top of the grid
     */
    int getY();

    /**
     * Returns the background icon for the slot
     *
     * @return Background Icon
     */
    Identifier getIcon();
}
