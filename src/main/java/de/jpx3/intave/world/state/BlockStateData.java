package de.jpx3.intave.world.state;

import de.jpx3.intave.patchy.annotate.PatchyAutoTranslation;
import net.minecraft.server.v1_16_R3.BlockStateBoolean;
import net.minecraft.server.v1_16_R3.BlockStateInteger;
import net.minecraft.server.v1_16_R3.IBlockData;
import net.minecraft.server.v1_16_R3.IBlockState;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;

public abstract class BlockStateData<T> {
  private final String name;
  private final T defaultValue;

  protected BlockStateData(String name, T defaultValue) {
    this.name = name;
    this.defaultValue = defaultValue;
  }

  public abstract void build();

  public abstract Object convert();

  public T value(Block block) {
    return BlockStateServerBridge.valueOf(block, this);
  }

  public String name() {
    return name;
  }

  @PatchyAutoTranslation
  public static final class BlockStateServerBridge {

    @PatchyAutoTranslation
    public static <T> T valueOf(Block block, BlockStateData<T> blockStateData) {
      CraftBlock craftBlock = (CraftBlock) block;
      CraftBlockData craftBlockData = (CraftBlockData) craftBlock.getBlockData();
      IBlockState<?> blockState = (IBlockState<?>) blockStateData.convert();
      IBlockData state = craftBlockData.getState();
      // containsKey
      if (state.b(blockState)) {
        //noinspection unchecked
        return (T) state.get(blockState);
      } else {
        return blockStateData.defaultValue;
      }
    }

    // Converter

    @PatchyAutoTranslation
    public static Object booleanStateOf(String name) {
      return BlockStateBoolean.of(name);
    }

    @PatchyAutoTranslation
    public static Object integerStateOf(String name, int min, int max) {
      return BlockStateInteger.of(name, min, max);
    }
  }
}