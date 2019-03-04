package nerdhub.trinkets.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;

import nerdhub.trinkets.client.gui.GuiTrinkets;
import nerdhub.trinkets.container.ContainerHandler;
import nerdhub.trinkets.container.ContainerTrinkets;

/**
 * @author BrockWS
 */
public class GuiHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("GuiHandler");
        ScreenProviderRegistry.INSTANCE.registerFactory(ContainerHandler.TRINKETS_CONTAINER, (syncid, identifier, player, buf) ->
                new GuiTrinkets(new ContainerTrinkets(syncid, player), player)
        );
    }
}
