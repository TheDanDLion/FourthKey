package fourthKey.patches.dungeons;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapRoomNode;

import fourthKey.patches.characters.AbstractPlayerPatch;

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
            if (AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                enemyNode.hasEmeraldKey = true;
            }
        }
    }
}
