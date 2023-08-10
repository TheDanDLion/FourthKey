package fourthKey.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.DoorLock;

import fourthKey.ModInitializer;

public class AbstractPlayerPatch {

    @SpirePatch2(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
    )
    public static class PurpleKeyPatch {
        public static SpireField<Boolean> hasAmethystKey = new SpireField<>(() -> false);
        public static SpireField<DoorLock> lockPurple = new SpireField<>(() -> null);
    }

    @SpirePatch2(
        clz = AbstractPlayer.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class SetAmethystKeyPatch {
        public static void Postfix(AbstractPlayer __instance) {
            if (ModInitializer.startWithAmethystKey)
                PurpleKeyPatch.hasAmethystKey.set(__instance, true);
        }
    }

}
