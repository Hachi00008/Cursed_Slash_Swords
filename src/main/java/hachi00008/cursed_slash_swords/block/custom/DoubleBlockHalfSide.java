package hachi00008.cursed_slash_swords.block.custom;

import net.minecraft.util.StringRepresentable;

public enum DoubleBlockHalfSide implements StringRepresentable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    DoubleBlockHalfSide(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
