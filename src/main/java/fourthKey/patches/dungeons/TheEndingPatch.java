package fourthKey.patches.dungeons;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapRoomNode;

import fourthKey.FourthKeyInitializer;

public class TheEndingPatch {

    @SpirePatch2(
        clz = TheEnding.class,
        method = "generateSpecialMap"
    )
    public static class BurningSpearAndShieldPatch {

        @SpireInsertPatch(
            loc = 85,
            localvars = {"enemyNode"}
        )
        public static void Insert(MapRoomNode enemyNode) {
            if (!FourthKeyInitializer.disableAct4Difficulty) {
                enemyNode.hasEmeraldKey = true;
            }
        }
    }
}
