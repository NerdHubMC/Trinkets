package nerdhub.trinkets.internal;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.tag.TagRegistry;

import nerdhub.trinkets.Trinkets;
import nerdhub.trinkets.api.ITrinketSpace;
import nerdhub.trinkets.api.ITrinketType;
import nerdhub.trinkets.api.ITrinketsApi;

/**
 * @author BrockWS
 */
public class Api implements ITrinketsApi {

    private static final Tag<Item> TRINKETS_TAG = TagRegistry.item(new Identifier(Trinkets.MOD_ID, "trinket"));
    private static ITrinketsApi INSTANCE;
    private Map<Identifier, ITrinketType> trinketTypes;
    private Map<Identifier, Tag<Item>> trinketTypeTags;
    private List<ITrinketSpace> trinketSpaces;

    private List<ITrinketType> trinketTypesCache;
    private List<Tag<Item>> trinketTypeTagsCache;

    private Api() {
        this.trinketTypes = new HashMap<>();
        this.trinketTypeTags = new HashMap<>();
        this.trinketSpaces = new ArrayList<>();
    }

    @Override
    public boolean isModLoaded() {
        return Trinkets.LOADED;
    }

    @Override
    public ITrinketType createTrinketType(Identifier id) {
        return this.createTrinketType(id, "tooltip.trinkets." + id.getPath());
    }

    @Override
    public ITrinketType createTrinketType(Identifier id, String key) {
        TrinketType trinketType = new TrinketType(id, key);
        Tag<Item> tag = TagRegistry.item(id);

        trinketTypes.put(id, trinketType);
        trinketTypeTags.put(id, tag);
        trinketTypesCache = Collections.unmodifiableList(new ArrayList<>(this.trinketTypes.values()));
        trinketTypeTagsCache = Collections.unmodifiableList(new ArrayList<>(this.trinketTypeTags.values()));
        return trinketType;
    }

    @Override
    public List<ITrinketType> getTrinketTypes() {
        return this.trinketTypesCache;
    }

    @Override
    public List<ITrinketType> getTypesFromItem(ItemStack stack) {
        return this.getTypesFromItem(stack != null && !stack.isEmpty() ? stack.getItem() : null);
    }

    @Override
    public List<ITrinketType> getTypesFromItem(Item item) {
        Collection<Identifier> tags = ItemTags.getContainer().getTagsFor(item);
        if (!tags.contains(TRINKETS_TAG.getId())) // All trinkets MUST have the tag "trinkets:trinket"
            return null;
        tags.removeIf(id -> !this.trinketTypeTags.containsKey(id));
        if (tags.size() < 1) // No types were specified, so its considered to have *everything*
            return this.getTrinketTypes();
        Stream<ITrinketType> types = this.getTrinketTypes().stream().filter(type -> tags.contains(type.getId()));
        return types.collect(Collectors.toList());
    }

    @Override
    public boolean isTrinket(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return false;
        return this.isTrinket(stack.getItem());
    }

    @Override
    public boolean isTrinket(Item item) {
        if (item == null)
            return false;
        return ItemTags.getContainer().getTagsFor(item).contains(TRINKETS_TAG.getId());
    }

    @Override
    public boolean isTrinketType(ItemStack stack, ITrinketType type) {
        if (!this.isTrinket(stack))
            return false;
        return this.isTrinketType(stack.getItem(), type);
    }

    @Override
    public boolean isTrinketType(Item item, ITrinketType type) {
        if (!this.isTrinket(item))
            return false;
        Collection<Identifier> tags = ItemTags.getContainer().getTagsFor(item);
        tags.removeIf(id -> !type.getId().getNamespace().equalsIgnoreCase(type.getId().getNamespace())); // Remove all tags that are not the type
        tags.removeIf(id -> id.equals(TRINKETS_TAG.getId())); // Remove the main Trinkets tag since we already know its a trinket
        return tags.size() < 1 || tags.contains(type.getId());
    }

    @Override
    public ITrinketSpace createTrinketSpace(ITrinketType type, int x, int y, Identifier slotIcon) {
        if (this.trinketSpaces.stream().anyMatch(space -> space.getX() == x && space.getY() == y))
            throw new RuntimeException(String.format("Trinkets space already exists at %s %s!", x, y));
        ITrinketSpace ts = new TrinketSpace(type, x, y, slotIcon);
        this.trinketSpaces.add(ts);
        return ts;
    }

    @Override
    public List<ITrinketSpace> getTrinketSpaces() {
        return Collections.unmodifiableList(this.trinketSpaces);
    }

    @Override
    public String getLocalizedName(ITrinketType type) {
        if (type == null)
            return I18n.translate("tooltip." + Trinkets.MOD_ID + ".any");
        return type.getLocalizedName();
    }

    @Override
    public TextComponent getTooltipComponent(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return this.getTooltipComponent(Collections.emptyList());
        return this.getTooltipComponent(stack.getItem());
    }

    @Override
    public TextComponent getTooltipComponent(Item item) {
        if (item == null)
            return this.getTooltipComponent(Collections.emptyList());
        return this.getTooltipComponent(this.getTypesFromItem(item));
    }

    @Override
    public TextComponent getTooltipComponent(ITrinketType... types) {
        if (types == null || types.length == 0)
            return this.getTooltipComponent(Collections.emptyList());
        return this.getTooltipComponent(Arrays.asList(types));
    }

    @Override
    public TextComponent getTooltipComponent(List<ITrinketType> types) {
        StringBuilder s = new StringBuilder();

        if (types != null && types.size() > 0 && types.size() < this.getTrinketTypes().size()) {
            for (ITrinketType type : types) {
                s.append(this.getLocalizedName(type));
                s.append(", ");
            }

            s.setLength(s.length() - 2);
        } else {
            s.append(this.getLocalizedName(null));
        }

        TextComponent t = new TranslatableTextComponent("tooltip." + Trinkets.MOD_ID, s.toString());
        t.getStyle().setColor(TextFormat.GOLD);
        return t;
    }

    public static ITrinketsApi instance() {
        if (INSTANCE == null)
            INSTANCE = new Api();
        return INSTANCE;
    }
}
