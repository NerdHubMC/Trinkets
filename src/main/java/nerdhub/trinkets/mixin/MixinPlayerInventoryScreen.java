package nerdhub.trinkets.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ingame.AbstractPlayerInventoryScreen;
import net.minecraft.client.gui.ingame.PlayerInventoryScreen;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.PlayerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.packet.GuiCloseC2SPacket;
import net.minecraft.text.TextComponent;

import nerdhub.trinkets.api.TrinketsApi;
import nerdhub.trinkets.client.gui.ITabScreen;
import nerdhub.trinkets.client.gui.TabWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author BrockWS
 */
@Mixin(PlayerInventoryScreen.class)
public abstract class MixinPlayerInventoryScreen extends AbstractPlayerInventoryScreen<PlayerContainer> implements ITabScreen {

    @Shadow
    @Final
    private RecipeBookGui recipeBook;
    private boolean bookOpen = false;

    public MixinPlayerInventoryScreen(PlayerContainer container_1, PlayerInventory playerInventory_1, TextComponent textComponent_1) {
        super(container_1, playerInventory_1, textComponent_1);
    }

    @Inject(method = "onInitialized", at = @At(value = "TAIL"))
    private void onInitialized(CallbackInfo ci) {
        this.bookOpen = this.recipeBook.isOpen();
        this.addButton(new TabWidget(this, this.left, this.top + -28, "Main", 0, new ItemStack(Items.CHEST)));
        this.addButton(new TabWidget(this, this.left + 30, this.top + -28, "Trinkets", 1, new ItemStack(Items.DIAMOND)));
    }

    @Inject(method = "mouseClicked", at = @At(value = "RETURN", ordinal = 2))
    private void mouseClicked(double double_1, double double_2, int int_1, CallbackInfoReturnable<Boolean> cir) {
        if (this.bookOpen != this.recipeBook.isOpen()) {
            this.bookOpen = this.recipeBook.isOpen();
            this.buttons.forEach(b -> {
                if (!(b instanceof TabWidget))
                    return;
                b.x = MixinPlayerInventoryScreen.this.left + 30 * ((TabWidget) b).getIndex();
            });
        }
    }

    @Override
    public int getSelectedTab() {
        return 0;
    }

    @Override
    public void onPress(ButtonWidget b) {
        if (!(b instanceof TabWidget))
            return;
        TabWidget tab = (TabWidget) b;

        switch (tab.getIndex()) {
            case 0:
                // This GUI
                break;
            case 1:
                TrinketsApi.instance().openTrinketsScreen();
                break;
            default:
        }
    }
}
