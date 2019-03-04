package nerdhub.trinkets.internal;

import net.minecraft.util.Identifier;

import nerdhub.trinkets.api.ITrinketSpace;
import nerdhub.trinkets.api.ITrinketType;

/**
 * @author BrockWS
 */
class TrinketSpace implements ITrinketSpace {

    private ITrinketType type;
    private int x;
    private int y;
    private Identifier icon;

    TrinketSpace(ITrinketType type, int x, int y, Identifier icon) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.icon = icon;
    }

    @Override
    public ITrinketType getTrinketType() {
        return this.type;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public Identifier getIcon() {
        return this.icon;
    }
}
