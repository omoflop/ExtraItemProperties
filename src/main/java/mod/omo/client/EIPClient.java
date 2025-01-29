package mod.omo.client;

import mod.omo.client.property.BiomeProperty;
import mod.omo.client.property.InstrumentProperty;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class EIPClient implements ClientModInitializer {

    public static @Nullable Entity nonLivingEntity;

    @Override
    public void onInitializeClient() {
        PropertyAPI.registerSelectProperty(Identifier.ofVanilla("instrument"), InstrumentProperty.TYPE);
        PropertyAPI.registerSelectProperty(Identifier.ofVanilla("biome"), BiomeProperty.TYPE);

    }
}

