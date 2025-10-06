package net.lumerite.lumeritemod.core.handlers;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LumeriteMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ArmorEffectEvents {

    @SubscribeEvent
    public static void PlayerTickEvent(TickEvent.PlayerTickEvent event) {

        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ItemStack helmet = player.getInventory().armor.get(3);
            ItemStack boots = player.getInventory().armor.get(0);

            if (helmet.getItem() == ModItems.DRAGONE_HELMET.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1000, 1, true, false, false));

            } else {
                player.removeEffect(MobEffects.NIGHT_VISION);
            }

            if (boots.getItem() == ModItems.DRAGONE_BOOTS.get()) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1, true, false, false));

            } else {
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
            }
        }

    }

}
