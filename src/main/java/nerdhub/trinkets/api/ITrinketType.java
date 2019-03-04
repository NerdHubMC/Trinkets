package nerdhub.trinkets.api;

import net.minecraft.util.Identifier;

/**
 * Holds information about a Type of Trinket
 *
 * @author BrockWS
 * @since 1.0.0
 */
public interface ITrinketType extends Comparable<ITrinketType> {

    /**
     * Unique ID for this Trinket Type, used by the tag system
     *
     * @return Unique id for the Trinket Type
     */
    Identifier getId();

    /**
     * The translation key for tooltips
     *
     * @return Translation Key
     */
    String getLangKey();

    /**
     * The translated string for tooltips
     *
     * @return Translated String
     */
    String getLocalizedName();
}
