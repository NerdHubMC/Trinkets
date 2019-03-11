package nerdhub.trinkets.container;

import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import nerdhub.trinkets.api.TrinketsApi;
import nerdhub.trinkets.internal.ITrinketsHolder;

/**
 * @author BrockWS
 */
@SuppressWarnings({"SameParameterValue", "FieldCanBeLocal"})
public class ContainerTrinkets extends Container {

    private static final String[] EMPTY_ARMOR_SLOT_IDS = new String[]{
            "item/empty_armor_slot_boots",
            "item/empty_armor_slot_leggings",
            "item/empty_armor_slot_chestplate",
            "item/empty_armor_slot_helmet"
    };
    private static final EquipmentSlot[] EQUIPMENT_SLOT_ORDER = new EquipmentSlot[]{
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private PlayerEntity player;
    private ITrinketsHolder trinketsInventory;

    public ContainerTrinkets(int syncid, PlayerEntity player) {
        super(null, syncid);
        this.player = player;
        this.trinketsInventory = (ITrinketsHolder) player;

        this.addPlayerSlots(this.player.inventory, 8, 84);
        this.addArmorSlots(this.player.inventory, 8, 8);
        this.addTrinketSlots(this.trinketsInventory.getTrinketsInventory(), 77, 8);
    }

    @Override
    public boolean canUse(PlayerEntity var1) {
        return true;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotID) {
        if (slotID < 0 || slotID > 36)
            return ItemStack.EMPTY;
        Slot slot = this.slotList.get(slotID);
        if (slot == null || slot.getStack().isEmpty())
            return ItemStack.EMPTY;
        ItemStack stack = slot.getStack();
        ITrinketsHolder holder = (ITrinketsHolder) player;
        Inventory inv = holder.getTrinketsInventory();
        for (int i = 0; i < inv.getInvSize(); i++) {
            if (!inv.getInvStack(i).isEmpty() || !inv.isValidInvStack(i, stack))
                continue;
            inv.setInvStack(i, slot.takeStack(1));
            break;
        }
        return ItemStack.EMPTY;
    }

    private void addArmorSlots(PlayerInventory inv, int offsetX, int offsetY) {
        for (int y = 0; y < 4; y++) {
            EquipmentSlot equipmentSlot = EQUIPMENT_SLOT_ORDER[y];
            this.addSlot(new Slot(inv, 39 - y, offsetX, 18 * y + offsetY) {
                public boolean canInsert(ItemStack itemStack_1) {
                    return equipmentSlot == MobEntity.getPreferredEquipmentSlot(itemStack_1);
                }

                public int getMaxStackAmount() {
                    return 1;
                }

                @Environment(EnvType.CLIENT)
                public String getBackgroundSprite() {
                    return EMPTY_ARMOR_SLOT_IDS[equipmentSlot.getEntitySlotId()];
                }

                public boolean canTakeItems(PlayerEntity playerEntity_1) {
                    ItemStack itemStack_1 = this.getStack();
                    return (itemStack_1.isEmpty() || playerEntity_1.isCreative() || !EnchantmentHelper.hasBindingCurse(itemStack_1)) && super.canTakeItems(playerEntity_1);
                }
            });
        }
    }

    private void addPlayerSlots(PlayerInventory inv, int offsetX, int offsetY) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new Slot(inv, x + (y * 9) + 9, x * 18 + offsetX, y * 18 + offsetY));
            }
        }
        for (int x = 0; x < 9; x++)
            this.addSlot(new Slot(inv, x, x * 18 + offsetX, 58 + offsetY));
    }

    private void addTrinketSlots(Inventory inv, int offsetX, int offsetY) {
        AtomicInteger i = new AtomicInteger();
        TrinketsApi.instance().getTrinketSpaces().forEach(space -> {
            this.addSlot(new TrinketsSlot(inv, i.getAndIncrement(), space.getX() * 18 + offsetX, space.getY() * 18 + offsetY, space));
        });
    }
}
