package fourthKey.patches.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.DoorLock;
import com.megacrit.cardcrawl.screens.DoorLock.LockColor;

public class DoorLockPatch {

    private final static Texture AMETHYST_LOCK = ImageMaster.loadImage("fourthKeyResources/images/ui/purpleLock.png");
    private final static Texture EMERALD_LOCK = ImageMaster.loadImage("fourthKeyResources/images/ui/greenLock.png");
    private final static Texture RUBY_LOCK = ImageMaster.loadImage("fourthKeyResources/images/ui/redLock.png");
    private final static Texture SAPPHIRE_LOCK = ImageMaster.loadImage("fourthKeyResources/images/ui/blueLock.png");

    private final static Texture AMETHYST_GLOW = ImageMaster.loadImage("fourthKeyResources/images/ui/purpleLockGlow.png");
    private final static Texture EMERALD_GLOW = ImageMaster.loadImage("fourthKeyResources/images/ui/greenLockGlow.png");
    private final static Texture RUBY_GLOW = ImageMaster.loadImage("fourthKeyResources/images/ui/redLockGlow.png");
    private final static Texture SAPPHIRE_GLOW = ImageMaster.loadImage("fourthKeyResources/images/ui/blueLockGlow.png");

    @SpireEnum
    public static DoorLock.LockColor PURPLE;

    @SpirePatch2(
        clz = DoorLock.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class DoorLockConstructorPatch {
        public static void Postfix(LockColor c, Texture ___lockImg, Texture ___glowImg, @ByRef float[] ___targetY, boolean eventVersion) {
            switch (c) {
                case BLUE:
                    ___lockImg = SAPPHIRE_LOCK;
                    ___glowImg = SAPPHIRE_GLOW;
                    break;
                case GREEN:
                    ___lockImg = EMERALD_LOCK;
                    ___glowImg = EMERALD_GLOW;
                    break;
                case RED:
                    ___lockImg = RUBY_LOCK;
                    ___glowImg = RUBY_GLOW;
                    break;
            }
            if (c == PURPLE) {
                ___lockImg = AMETHYST_LOCK;
                ___glowImg = AMETHYST_GLOW;
                ___targetY[0] = (eventVersion ? 752.0F : 800.0F) * Settings.scale;
            }
        }
    }

    @SpirePatch2(
        clz = DoorLock.class,
        method = "updateUnlockAnimation"
    )
    public static class UpdateNewKeyUnlockAnimationPatch {
        @SpireInsertPatch(
            loc = 87
        )
        public static SpireReturn<Void> Insert(LockColor c, float ___unlockTimer, float ___startY, float ___targetY, @ByRef float[] ___x, @ByRef float[] ___y) {
            switch (c) {
                case BLUE:
                    ___x[0] = Interpolation.pow5In.apply(1000.0F * Settings.scale, 0.0F, ___unlockTimer / 2.0F);
                    ___y[0] = Interpolation.pow5In.apply(___targetY, ___startY, ___unlockTimer / 2.0F);
                    break;
                case GREEN:
                    ___x[0] = Interpolation.pow5In.apply(-1000.0F * Settings.scale, 0.0F, ___unlockTimer / 2.0F);
                    ___y[0] = Interpolation.pow5In.apply(___targetY, ___startY, ___unlockTimer / 2.0F);
                    break;
                case RED:
                    ___x[0] = Interpolation.pow5In.apply(-1000.0F * Settings.scale, 0.0F, ___unlockTimer / 2.0F);
                    ___y[0] = Interpolation.pow5In.apply(___targetY, ___startY, ___unlockTimer / 2.0F);
                    break;
            }
            if (c == PURPLE) {
                ___x[0] = Interpolation.pow5In.apply(1000.0F * Settings.scale, 0.0F, ___unlockTimer / 2.0F);
                ___y[0] = Interpolation.pow5In.apply(___targetY, ___startY, ___unlockTimer / 2.0F);
            }
            return SpireReturn.Return();
        }
    }

}
