package com.duskhats.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/** Registers and executes the player-only {@code /hat} command. */
public final class HatCommand {
    private static final Component EMPTY_MAIN_HAND_MESSAGE =
            Component.literal("You must hold an item in your main hand.");
    private static final Component HAT_EQUIPPED_MESSAGE =
            Component.literal("Hat equipped!").withStyle(style -> style.withColor(0x91FF4E));
    private static final float EQUIP_SOUND_VOLUME = 1.0F;
    private static final float EQUIP_SOUND_PITCH = 1.0F;

    private HatCommand() {
    }

    /** Registers /hat for every player, without granting access to non-player sources. */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("hat")
                .requires(source -> source.getEntity() instanceof ServerPlayer)
                .executes(HatCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayerOrException();
        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (mainHandStack.isEmpty()) {
            source.sendFailure(EMPTY_MAIN_HAND_MESSAGE);
            return 0;
        }

        ItemStack previousHat = player.getItemBySlot(EquipmentSlot.HEAD);

        // Equip the exact stack in the main hand.
        player.setItemSlot(EquipmentSlot.HEAD, mainHandStack);

        // Store the previous hat. Any remainder stays in the main hand, preventing item loss.
        if (previousHat.isEmpty() || player.getInventory().add(previousHat)) {
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        } else {
            player.setItemInHand(InteractionHand.MAIN_HAND, previousHat);
        }

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ARMOR_EQUIP_GENERIC,
                SoundSource.PLAYERS,
                EQUIP_SOUND_VOLUME,
                EQUIP_SOUND_PITCH
        );

        source.sendSuccess(() -> HAT_EQUIPPED_MESSAGE, false);
        return Command.SINGLE_SUCCESS;
    }
}
