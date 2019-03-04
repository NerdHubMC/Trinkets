package nerdhub.trinkets.client.gui;

import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.ingame.PlayerInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.platform.GlStateManager;
import nerdhub.trinkets.Trinkets;
import nerdhub.trinkets.container.ContainerTrinkets;
import nerdhub.trinkets.container.TrinketsSlot;

/**
 * @author BrockWS
 */
public class GuiTrinkets extends ContainerScreen<ContainerTrinkets> {

    private static final Identifier TEXTURE = new Identifier(Trinkets.MOD_ID, "textures/gui/trinkets_inventory.png");
    private static final Identifier TEXTURE_SLOT = new Identifier(Trinkets.MOD_ID, "textures/gui/icon_slot.png");

    public GuiTrinkets(ContainerTrinkets container, PlayerEntity player) {
        super(container, player.inventory, new StringTextComponent("Trinkets"));
        this.width = 176;
        this.height = 166;
    }

    @Override
    public void draw(int mouseX, int mouseY, float ticks) {
        this.drawBackground();
        super.draw(mouseX, mouseY, ticks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
    }

    @Override
    protected void drawBackground(float ticks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedRect(this.left, this.top, 0, 0, this.width, this.height);
        this.container.slotList.forEach(slot -> {
            if (!(slot instanceof TrinketsSlot))
                return;
            this.client.getTextureManager().bindTexture(TEXTURE_SLOT);
            DrawableHelper.drawTexturedRect(this.left + slot.xPosition - 1, this.top + slot.yPosition - 1, 0, 0, 18, 18, 18f, 18f);
            this.client.getTextureManager().bindTexture(((TrinketsSlot) slot).getBackgroundIcon());
            DrawableHelper.drawTexturedRect(this.left + slot.xPosition - 1, this.top + slot.yPosition - 1, 0, 0, 18, 18, 18f, 18f);
        });

        PlayerInventoryScreen.drawEntity(this.left + 51, this.top + 73, 30, (float) (this.left + 51) - mouseX, (float) (this.top + 75 - 50) - mouseY, this.client.player);
    }
}
