package nerdhub.trinkets.mixin;

import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.Screen;

import nerdhub.trinkets.client.gui.TabWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author BrockWS
 */
@Mixin(ContainerScreen.class)
public abstract class MixinContainerScreen extends Screen {

    @Inject(method = "drawMouseoverTooltip", at = @At(value = "HEAD"))
    private void drawMouseoverTooltip(int mouseX, int mouseY, CallbackInfo ci) {
        this.buttons.forEach(b -> {
            if (b instanceof TabWidget && b.isHovered())
                this.drawTooltip(b.getText(), mouseX, mouseY);
        });
    }
}
