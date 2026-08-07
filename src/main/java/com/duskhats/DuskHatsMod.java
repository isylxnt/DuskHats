package com.duskhats;

import com.duskhats.command.HatCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/** Punto de entrada exclusivamente del servidor para DuskHats. */
public final class DuskHatsMod implements ModInitializer {
    public static final String MOD_ID = "duskhats";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                HatCommand.register(dispatcher)
        );
    }
}
