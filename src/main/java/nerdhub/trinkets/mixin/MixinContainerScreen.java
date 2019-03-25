package nerdhub.trinkets.mixin;

import java.util.List;

import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.TextComponent;

import nerdhub.trinkets.client.gui.TabWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author BrockWS
 */
@Mixin(ContainerScreen.class)
public abstract class MixinContainerScreen extends Screen{

    protected MixinContainerScreen(TextComponent textComponent_1) {
        super(textComponent_1);
    }

    @Inject(method = "drawMouseoverTooltip", at = @At(value = "HEAD"))
    private void drawMouseoverTooltip(int mouseX, int mouseY, CallbackInfo ci) {
        this.buttons.forEach(b -> {
            if (b instanceof TabWidget && b.isHovered())
                this.drawTooltip(b.getMessage(), mouseX, mouseY);
        });
    }
}
