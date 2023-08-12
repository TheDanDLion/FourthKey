package fourthKey.patches.characters;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.DoorLock;

import fourthKey.ModInitializer;
import fourthKey.relics.HeartBlessingPurple;

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
        method = SpirePatch.CLASS
    )
    public static class DownfallCompatabilityPatch {
        public static SpireField<Boolean> hasBrokenEmeraldKey = new SpireField<>(() -> false);
        public static SpireField<Boolean> hasBrokenRubyKey = new SpireField<>(() -> false);
        public static SpireField<Boolean> hasBrokenSapphireKey = new SpireField<>(() -> false);
        public static SpireField<Boolean> hasBrokenAmethystKey = new SpireField<>(() -> false);
    }

    @SpirePatch2(
        clz = AbstractPlayer.class,
        method = "initializeStarterRelics",
        requiredModId = "downfall"
    )
    public static class AddBrokenPurpleKeyRelicPatch {
        @SpireInsertPatch(
            loc = 473,
            localvars = {"relics"}
        )
        public static void Insert(AbstractPlayer __instance, ArrayList<String> relics) {
            if (ModInitializer.startWithAmethystKey && ModInitializer.downfallEvilMode) {
                ModInitializer.logger.info("Downfall Detected: Starting with Broken Amethyst Key");
                AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenAmethystKey.set(__instance, true);
                relics.add(HeartBlessingPurple.ID);
            }
        }
    }
}
