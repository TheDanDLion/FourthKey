package fourthKey.patches.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DoorLock;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class DoorUnlockScreenPatch {

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "open"
    )
    public static class ResetLockPurplePatch {
        @SpireInsertPatch(
            loc = 62
        )
        public static void Insert() {
            if (!FourthKeyInitializer.disableAmethystKey)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).reset();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "open"
    )
    public static class CreateLockPurplePatch {
        @SpireInsertPatch(
            loc = 67
        )
        public static void Insert(boolean eventVersion) {
            if (!FourthKeyInitializer.disableAmethystKey)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.set(AbstractDungeon.player, new DoorLock(DoorLockPatch.PURPLE, true, eventVersion));
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "update"
    )
    public static class UpdateLockPurplePacth {
        @SpireInsertPatch(
            loc = 113
        )
        public static void Insert() {
            if (!FourthKeyInitializer.disableAmethystKey)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).update();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "exit"
    )
    public static class DisposeLockPurplePatch {
        public static void Prefix() {
            if (!FourthKeyInitializer.disableAmethystKey)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).dispose();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "updateLightUp"
    )
    public static class LightUpLockPurplePatch {
        @SpireInsertPatch(
            loc = 194
        )
        public static void Insert(boolean ___eventVersion, float ___lightUpTimer) {
            if (!FourthKeyInitializer.disableAmethystKey && ___lightUpTimer < (Settings.FAST_MODE ? 0.25F : 1.5F))
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).flash(___eventVersion);
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "unlock"
    )
    public static class UnlockLockPurplePatch {
        public static void Postfix(boolean ___animateCircle) {
            if (!FourthKeyInitializer.disableAmethystKey && ___animateCircle)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).unlock();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "render"
    )
    public static class RenderPurpleKeyPatch {
        @SpireInsertPatch(
            loc = 285
        )
        public static void Insert(SpriteBatch sb) {
            if (!FourthKeyInitializer.disableAmethystKey)
                AbstractPlayerPatch.PurpleKeyPatch.lockPurple.get(AbstractDungeon.player).render(sb);
        }
    }

}