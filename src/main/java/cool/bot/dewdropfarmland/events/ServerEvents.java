package cool.bot.dewdropfarmland.events;

import cool.bot.dewdropfarmland.DewDropFarmland;
import cool.bot.dewdropfarmland.registry.ModElements;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.tag.ModTags;

public class ServerEvents {
    @Mod.EventBusSubscriber(modid = DewDropFarmland.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onBlockInteract(RightClickBlock event) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getPos();
            BlockState clickedBlockState = event.getLevel().getBlockState(blockPos);
            ItemStack stack = player.getItemInHand(event.getHand());
            if (stack.getItem() == ModElements.GARDEN_POT_ITEM.get()) {
                if (event.getLevel().getBlockState(blockPos.below()).getBlock() instanceof AirBlock && event.getLevel().getBlockState(blockPos.below().below()).getBlock() instanceof AirBlock) {
                    if (clickedBlockState.getBlock() instanceof ChainBlock) {
                        if (!event.getLevel().isClientSide) {
                            event.getLevel().setBlock(blockPos.below().below(), ModElements.IRON_HANGING_GARDEN_POT.get().defaultBlockState(), 3);
                            stack.shrink(1);
                        }
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    } else if (DewDropFarmland.FARMERS_DELIGHT_INSTALLED && clickedBlockState.is(ModTags.ROPES)) {
                        if (!event.getLevel().isClientSide) {
                            event.getLevel().setBlock(blockPos.below().below(), ModElements.ROPE_HANGING_GARDEN_POT.get().defaultBlockState(), 3);
                            stack.shrink(1);
                        }
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }
}