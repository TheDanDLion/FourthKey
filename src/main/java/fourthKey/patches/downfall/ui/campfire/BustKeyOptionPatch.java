package fourthKey.patches.downfall.ui.campfire;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import fourthKey.patches.characters.AbstractPlayerPatch;

@SpirePatch2(
    cls = "downfall.ui.campfire.BustKeyOption",
    method = "useOption",
    requiredModId = "downfall"
)
public class BustKeyOptionPatch {

    @SpireInsertPatch(
        loc = 170
    )
    public static void InsertSapphireKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenSapphireKey.set(AbstractDungeon.player, true);
    }

    @SpireInsertPatch(
        loc = 174
    )
    public static void InsertEmeraldKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenEmeraldKey.set(AbstractDungeon.player, true);
    }

    @SpireInsertPatch(
        loc = 178
    )
    public static void InsertRubyKey() {
        AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenRubyKey.set(AbstractDungeon.player, true);
    }
}
