package fourthKey.patches.events.beyond;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;

import fourthKey.patches.characters.AbstractPlayerPatch;

public class SpireHeartPatch {

    public static boolean resetKey = false;

    @SpirePatch2(
        clz = SpireHeart.class,
        method = "buttonEffect"
    )
    public static class CheckForPurpleKeyPatch {
        @SpireInsertPatch(
            loc = 184
        )
        public static void Insert() {
            if (!AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                // setting one of the keys to false to force going down the other route
                Settings.hasRubyKey = false;
                resetKey = true;
            }
        }
    }

    @SpirePatch2(
        clz = SpireHeart.class,
        method = "buttonEffect"
    )
    public static class ResetRubyKeySettingPatch {
        @SpireInsertPatch(
            loc = 211
        )
        public static void Insert() {
            if (resetKey) {
                resetKey = false;
                Settings.hasRubyKey = true;
            }
        }
    }
}
