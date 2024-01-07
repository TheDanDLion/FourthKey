package fourthKey.patches.rooms;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

import fourthKey.FourthKeyInitializer;

@SpirePatch2(
    clz = MonsterRoomElite.class,
    method = "applyEmeraldEliteBuff"
)
public class MonsterRoomElitePatch {
    public static SpireReturn<Void> Prefix() {
        if (AbstractDungeon.actNum == 4 && FourthKeyInitializer.disableAct4Difficulty)
            return SpireReturn.Return();
        return SpireReturn.Continue();
    }
}
