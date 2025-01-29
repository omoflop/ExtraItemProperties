package mod.omo.client;

import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PropertyAPI {
    public static final Map<Identifier, SelectProperty.Type<?, ?>> select = new HashMap<>();

    public static void registerSelectProperty(Identifier id, SelectProperty.Type<?, ?> property) {
        select.put(id, property);
    }

}
