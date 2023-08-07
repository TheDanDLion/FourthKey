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

import static fourthKey.ModInitializer.makeUIPath;

public class DoorLockPatch {

    @SpireEnum
    public static DoorLock.LockColor PURPLE;

    @SpirePatch2(
        clz = DoorLock.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class DoorLockConstructorPatch {
        public static void Postfix(LockColor c, @ByRef Texture[] ___lockImg, @ByRef Texture[] ___glowImg, @ByRef float[] ___targetY, @ByRef boolean[] ___glowing, boolean eventVersion) {
            switch (c) {
                case BLUE:
                    ___lockImg[0] = ImageMaster.loadImage(makeUIPath("blueLock.png"));
                    ___glowImg[0] = ImageMaster.loadImage(makeUIPath("blueLockGlow.png"));
                    ___targetY[0] = (eventVersion ? 752.0F : 800.0F) * Settings.scale;
                    break;
                case GREEN:
                    ___lockImg[0] = ImageMaster.loadImage(makeUIPath("greenLock.png"));
                    ___glowImg[0] = ImageMaster.loadImage(makeUIPath("greenLockGlow.png"));
                    break;
                case RED:
                    ___lockImg[0] = ImageMaster.loadImage(makeUIPath("redLock.png"));
                    ___glowImg[0] = ImageMaster.loadImage(makeUIPath("redLockGlow.png"));
                    break;
            }
            if (c == PURPLE) {
                ___lockImg[0] = ImageMaster.loadImage(makeUIPath("purpleLock.png"));
                ___glowImg[0] = ImageMaster.loadImage(makeUIPath("purpleLockGlow.png"));
                ___glowing[0] = true;
                ___targetY[0] = (eventVersion ? -748.0F : -700.0F) * Settings.scale;
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
        public static SpireReturn<Void> Insert(LockColor ___c, float ___unlockTimer, float ___startY, float ___targetY, @ByRef float[] ___x, @ByRef float[] ___y) {
            switch (___c) {
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
            if (___c == PURPLE) {
                ___x[0] = Interpolation.pow5In.apply(1000.0F * Settings.scale, 0.0F, ___unlockTimer / 2.0F);
                ___y[0] = Interpolation.pow5In.apply(___targetY, ___startY, ___unlockTimer / 2.0F);
            }
            return SpireReturn.Return();
        }
    }

}
