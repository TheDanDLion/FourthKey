package fourthKey.patches.downfall.ui.campfire;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import fourthKey.patches.characters.AbstractPlayerPatch;
import javassist.CtBehavior;

@SpirePatch2(
    cls = "downfall.ui.campfire.BustKeyOption",
    method = "useOption",
    requiredModId = "downfall"
)
public class BustKeyOptionPatch {

    @SpireInsertPatch(
        locator = SapphireKeyLocator.class
    )
    public static void InsertSapphireKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenSapphireKey.set(AbstractDungeon.player, true);
    }

    @SpireInsertPatch(
        locator = EmeraldKeyLocator.class
    )
    public static void InsertEmeraldKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenEmeraldKey.set(AbstractDungeon.player, true);
    }

    @SpireInsertPatch(
        locator = RubyKeyLocator.class
    )
    public static void InsertRubyKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenRubyKey.set(AbstractDungeon.player, true);
    }

    private static class SapphireKeyLocator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ct) throws Exception {
            return LineFinder.findInOrder(ct, new Matcher.FieldAccessMatcher("downfall.patches.ui.campfire.AddBustKeyButtonPatches$KeyFields", "bustedSapphire"));
        }

    }

    private static class EmeraldKeyLocator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ct) throws Exception {
            return LineFinder.findInOrder(ct, new Matcher.FieldAccessMatcher("downfall.patches.ui.campfire.AddBustKeyButtonPatches$KeyFields", "bustedEmerald"));
        }

    }

    private static class RubyKeyLocator extends SpireInsertLocator {

        @Override
        public int[] Locate(CtBehavior ct) throws Exception {
            return LineFinder.findInOrder(ct, new Matcher.FieldAccessMatcher("downfall.patches.ui.campfire.AddBustKeyButtonPatches$KeyFields", "bustedRuby"));
        }

    }
}
