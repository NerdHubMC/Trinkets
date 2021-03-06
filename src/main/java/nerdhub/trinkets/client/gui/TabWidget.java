package nerdhub.trinkets.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.platform.GlStateManager;

/**
 * @author BrockWS
 */
public class TabWidget extends ButtonWidget {

    private static final Identifier TABS = new Identifier("textures/gui/advancements/tabs.png");

    private ITabScreen screen;
    private int index;
    private ItemStack icon;
    private ItemRenderer itemRenderer;
    private TextRenderer textRenderer;

    public TabWidget(ITabScreen screen, int x, int y, String name, int index, ItemStack icon) {
        super(x, y, 26, 32, name, screen);
        this.screen = screen;
        this.index = index;
        this.icon = icon;
        this.itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        this.textRenderer = MinecraftClient.getInstance().textRenderer;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float ticks) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderBg(MinecraftClient.getInstance(), mouseX, mouseY);
        GuiLighting.enableForItems();
        this.drawIcon(MinecraftClient.getInstance(), mouseX, mouseY);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableLighting();
    }

    @Override
    protected void renderBg(MinecraftClient mc, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(TABS);
        int texX = this.index > 0 ? 28 : 0;
        int texY = this.isSelected() ? 32 : 0;
        this.drawTexturedRect(this.x, this.y, texX, texY, this.width, this.height);
    }

    protected void drawIcon(MinecraftClient mc, int mouseX, int mouseY) {
        this.zOffset = 100.0F;
        this.itemRenderer.zOffset = 100.0F;
        int int_4 = this.x + 6;
        int int_5 = this.y + 9;
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        this.itemRenderer.renderGuiItem(this.icon, int_4, int_5);
        this.itemRenderer.renderGuiItemOverlay(this.textRenderer, this.icon, int_4, int_5);
        GlStateManager.disableLighting();
        this.itemRenderer.zOffset = 0.0F;
        this.zOffset = 0.0F;
    }

    private boolean isSelected() {
        return this.screen.getSelectedTab() == this.index;
    }

    public int getIndex() {
        return this.index;
    }
}
