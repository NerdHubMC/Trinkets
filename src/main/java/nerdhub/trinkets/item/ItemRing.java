package nerdhub.trinkets.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

import nerdhub.trinkets.api.ITrinket;

/**
 * @author BrockWS
 */
public class ItemRing extends Item implements ITrinket {

    public ItemRing() {
        super(new Settings().stackSize(1).rarity(Rarity.UNCOMMON).itemGroup(ItemGroup.MISC));
    }
}
