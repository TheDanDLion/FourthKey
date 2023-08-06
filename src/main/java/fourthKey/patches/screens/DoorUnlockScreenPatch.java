package fourthKey.patches.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.DoorLock;
import com.megacrit.cardcrawl.screens.DoorUnlockScreen;

public class DoorUnlockScreenPatch {

    @SpireEnum
    public static SpireField<DoorLock> LockPurple = new SpireField<>(() -> null);

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "open"
    )
    public static class ResetLockPurplePatch {
        @SpireInsertPatch(
            loc = 62
        )
        public static void Insert(DoorUnlockScreen __instance) {
            LockPurple.get(__instance).reset();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "open"
    )
    public static class CreateLockPurplePatch {
        @SpireInsertPatch(
            loc = 69
        )
        public static void Insert(DoorUnlockScreen __instance, boolean eventVersion) {
            LockPurple.set(
                __instance,
                new DoorLock(
                    DoorLockPatch.PURPLE,
                    CardCrawlGame.playerPref.getBoolean(AbstractPlayer.PlayerClass.WATCHER.name() + "_WIN", false),
                    eventVersion
                )
            );
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
        public static void Insert(DoorUnlockScreen __instance) {
            LockPurple.get(__instance).update();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "exit"
    )
    public static class DisposeLockPurplePatch {
        public static void Prefix(DoorUnlockScreen __instance) {
            LockPurple.get(__instance).dispose();
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "updateLightUp"
    )
    public static class LightUpLockPurplePatch {
        @SpireInsertPatch(
            loc = 181
        )
        public static void Insert(DoorUnlockScreen __instance, boolean ___eventVersion, float ___lightUpTimer) {
            if (___lightUpTimer < (Settings.FAST_MODE ? 0.5F : 1.5F))
                LockPurple.get(__instance).flash(___eventVersion);
        }
    }

    @SpirePatch2(
        clz = DoorUnlockScreen.class,
        method = "unlock"
    )
    public static class UnlockLockPurplePatch {
        public static void Postfix(DoorUnlockScreen __instance, boolean ___animateCircle) {
            if (___animateCircle)
                LockPurple.get(__instance).unlock();
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
        public static void Insert(DoorUnlockScreen __instance, SpriteBatch sb) {
            LockPurple.get(__instance).render(sb);
        }
    }

}