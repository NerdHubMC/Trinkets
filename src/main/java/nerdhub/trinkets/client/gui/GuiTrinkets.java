package nerdhub.trinkets.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.ingame.PlayerInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.packet.GuiCloseC2SPacket;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.Identifier;

import com.mojang.blaze3d.platform.GlStateManager;
import nerdhub.trinkets.Trinkets;
import nerdhub.trinkets.container.ContainerTrinkets;
import nerdhub.trinkets.container.TrinketsSlot;

/**
 * @author BrockWS
 */
public class GuiTrinkets extends ContainerScreen<ContainerTrinkets> implements ITabScreen {

    private static final Identifier TEXTURE = new Identifier(Trinkets.MOD_ID, "textures/gui/trinkets_inventory.png");
    private static final Identifier TEXTURE_SLOT = new Identifier(Trinkets.MOD_ID, "textures/gui/icon_slot.png");

    public GuiTrinkets(ContainerTrinkets container, PlayerEntity player) {
        super(container, player.inventory, new StringTextComponent("Trinkets"));
        this.width = 176;
        this.height = 166;
    }

    @Override
    public void initialize(MinecraftClient minecraftClient_1, int int_1, int int_2) {
        super.initialize(minecraftClient_1, int_1, int_2);
        this.addButton(new TabWidget(this, this.left, this.top + -28, "Main", 0, new ItemStack(Items.CHEST)));
        this.addButton(new TabWidget(this, this.left + 30, this.top + -28, "Trinkets", 1, new ItemStack(Items.DIAMOND)));
    }

    @Override
    public void render(int mouseX, int mouseY, float ticks) {
        this.drawBackground();
        super.render(mouseX, mouseY, ticks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY) {
        super.drawForeground(mouseX, mouseY);
    }

    @Override
    protected void drawBackground(float ticks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        //this.drawGradientRect(0,0,this.screenWidth, this.screenHeight, Color.GREEN.getRGB(), Color.GREEN.getRGB());
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

        PlayerInventoryScreen.drawEntity(this.left + 51, this.top + 75, 30, (float) (this.left + 51) - mouseX, (float) (this.top + 75 - 50) - mouseY, this.client.player);
    }

    @Override
    public int getSelectedTab() {
        return 1;
    }

    @Override
    public void onPress(ButtonWidget b) {
        if (!(b instanceof TabWidget))
            return;
        TabWidget tab = (TabWidget) b;

        switch (tab.getIndex()) {
            case 0:
                // Tell the server we closed the container
                MinecraftClient.getInstance().getNetworkHandler().sendPacket(new GuiCloseC2SPacket(this.container.syncId));
                MinecraftClient.getInstance().openScreen(new PlayerInventoryScreen(this.playerInventory.player));
                break;
            case 1:
            default:
        }
    }
}
