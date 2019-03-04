package nerdhub.trinkets.container;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;

import nerdhub.trinkets.Trinkets;

/**
 * @author BrockWS
 */
public class ContainerHandler implements ModInitializer {

    public static final Identifier TRINKETS_CONTAINER = new Identifier(Trinkets.MOD_ID, "trinkets_container");

    @Override
    public void onInitialize() {
        System.out.println("ContainerHandler");
        ContainerProviderRegistry.INSTANCE.registerFactory(ContainerHandler.TRINKETS_CONTAINER, (syncid, identifier, player, buf) ->
                new ContainerTrinkets(syncid, player)
        );
    }
}
