package nerdhub.trinkets.api;

import java.lang.reflect.Method;

/**
 * API Entry point
 *
 * @author BrockWS
 * @since 1.0.0
 */
public class TrinketsApi {

    private static ITrinketsApi API;

    /**
     * Gets, caches and returns the TrinketsApi
     *
     * @return TrinketsApi
     */
    public static ITrinketsApi instance() {
        if (TrinketsApi.API == null) {
            try {
                Class clazz = Class.forName("nerdhub.trinkets.internal.Api");
                Method instanceAccessor = clazz.getMethod("instance");
                TrinketsApi.API = (ITrinketsApi) instanceAccessor.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return TrinketsApi.API;
    }
}
