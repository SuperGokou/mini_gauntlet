package BaseGame;

public class Bits {
    public static int maxFieldValue(int fieldWidth) {
        assert fieldWidth > 0;
        return (1 << fieldWidth) - 1;
    }

    public static int getFieldValue(int field, int maskWidth, int maskOffset) {
        int unshiftedMask = maxFieldValue(maskWidth);
        int mask = unshiftedMask << maskOffset;

        int value = (field & mask) >>> maskOffset;
        return value & unshiftedMask;
    }
}
