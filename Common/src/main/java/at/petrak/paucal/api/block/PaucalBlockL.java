package at.petrak.paucal.api.block;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

/**
 * "Paucal Block Library"
 */
public class PaucalBlockL {
    /**
     * The same thing as the protected method
     */
    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
        BlockEntityType<A> typeHere, BlockEntityType<E> selfType, BlockEntityTicker<? super E> ticker) {
        return selfType == typeHere ? (BlockEntityTicker<A>) ticker : null;
    }
}
