package nerdhub.trinkets.container;

import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import nerdhub.trinkets.api.ITrinketSpace;
import nerdhub.trinkets.api.TrinketsApi;

/**
 * @author BrockWS
 */
public class TrinketsSlot extends Slot {

    private ITrinketSpace space;

    public TrinketsSlot(Inventory inv, int index, int x, int y, ITrinketSpace space) {
        super(inv, index, x, y);
        this.space = space;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return TrinketsApi.instance().isTrinketType(stack, this.space.getTrinketType());
    }

    public Identifier getBackgroundIcon() {
        return this.space.getIcon();
    }
}
