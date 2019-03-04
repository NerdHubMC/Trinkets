package nerdhub.trinkets;


import java.util.List;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.registry.CommandRegistry;

import nerdhub.textilelib.event.client.TooltipCreationCallback;
import nerdhub.textilelib.event.entity.player.PlayerTickCallback;
import nerdhub.trinkets.api.ITrinket;
import nerdhub.trinkets.api.ITrinketSpace;
import nerdhub.trinkets.api.ITrinketType;
import nerdhub.trinkets.api.TrinketsApi;
import nerdhub.trinkets.container.ContainerHandler;
import nerdhub.trinkets.internal.ITrinketsHolder;
import nerdhub.trinkets.item.ItemRing;

/**
 * @author BrockWS
 */
public class Trinkets implements ModInitializer {

    public static final String MOD_ID = "trinkets";
    public static boolean LOADED = false;

    @Override
    public void onInitialize() {
        System.out.println("Initializing Trinkets!");
        ITrinketType ring = TrinketsApi.instance().createTrinketType(new Identifier(Trinkets.MOD_ID, "ring"));
        ITrinketType bandana = TrinketsApi.instance().createTrinketType(new Identifier(Trinkets.MOD_ID, "bandana"));
        ITrinketType backpack = TrinketsApi.instance().createTrinketType(new Identifier(Trinkets.MOD_ID, "backpack"));
        ITrinketType belt = TrinketsApi.instance().createTrinketType(new Identifier(Trinkets.MOD_ID, "belt"));

        TrinketsApi.instance().createTrinketSpace(bandana, 2, 1, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_bandana.png"));

        TrinketsApi.instance().createTrinketSpace(backpack, 2, 2, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_backpack.png"));

        TrinketsApi.instance().createTrinketSpace(ring, 0, 3, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_ring.png"));
        TrinketsApi.instance().createTrinketSpace(ring, 1, 3, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_ring.png"));
        TrinketsApi.instance().createTrinketSpace(belt, 2, 3, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_belt.png"));
        TrinketsApi.instance().createTrinketSpace(ring, 3, 3, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_ring.png"));
        TrinketsApi.instance().createTrinketSpace(ring, 4, 3, new Identifier(Trinkets.MOD_ID, "textures/gui/icon_ring.png"));

        LOADED = true;

        Registry.register(Registry.ITEM, new Identifier(Trinkets.MOD_ID, "demo_ring"), new ItemRing());

        CommandRegistry.INSTANCE.register(false, dispatcher -> dispatcher.register(ServerCommandManager
                .literal(Trinkets.MOD_ID)
                .executes(context -> {
                    ContainerProviderRegistry.INSTANCE.openContainer(ContainerHandler.TRINKETS_CONTAINER, context.getSource().getPlayer(), buf -> buf.writeByte(0));
                    return 1;
                })
        ));

        TooltipCreationCallback.EVENT.register((stack, context, list) -> {
            if (!TrinketsApi.instance().isTrinket(stack))
                return;
            TextComponent component = TrinketsApi.instance().getTooltipComponent(stack);
            if (component != null)
                list.add(component);
            else
                list.add(new StringTextComponent("Failed to get tooltip for Trinkets!"));
        });

        PlayerTickCallback.EVENT.register(playerEntity -> {
            ITrinketsHolder holder = (ITrinketsHolder) playerEntity;
            Inventory inv = holder.getTrinketsInventory();
            List<ITrinketSpace> spaces = TrinketsApi.instance().getTrinketSpaces();
            for (int i = 0; i < spaces.size(); i++) {
                ItemStack stack = inv.getInvStack(i);
                if (stack.isEmpty() || !(stack.getItem() instanceof ITrinket))
                    continue;
                ((ITrinket) stack.getItem()).onTick(stack, playerEntity, spaces.get(i).getTrinketType());
            }
        });
    }
}
