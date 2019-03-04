package nerdhub.trinkets.internal;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;

import nerdhub.trinkets.api.ITrinketType;

/**
 * @author BrockWS
 */
class TrinketType implements ITrinketType {

    private Identifier id;
    private String langKey;

    TrinketType(Identifier id, String langKey) {
        this.id = id;
        this.langKey = langKey;
    }

    public Identifier getId() {
        return this.id;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public String getLocalizedName() {
        return I18n.translate(this.getLangKey());
    }

    @Override
    public int compareTo(ITrinketType o) {
        //return this.getId().compareTo(o.getId()); FIXME Identifier#compareTo is broken
        return 0;
    }
}
