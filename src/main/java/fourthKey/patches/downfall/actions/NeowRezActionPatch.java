package fourthKey.patches.downfall.actions;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import fourthKey.FourthKeyInitializer;
import javassist.CtBehavior;

@SpirePatch2(
    cls = "downfall.actions.NeowRezAction",
    method = "update",
    requiredModId = "downfall"
)
public class NeowRezActionPatch {
    @SpireInsertPatch(
        locator = Locator.class,
        localvars = {"q"}
    )
    public static void Insert(AbstractMonster q) {
        if (FourthKeyInitializer.downfallEvilMode && !FourthKeyInitializer.disableAct4Difficulty && AbstractDungeon.actNum == 4) {
            switch(AbstractDungeon.miscRng.random(0, 3)) {
                case 0:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, q, new StrengthPower(q, 2), 2));
                    break;
                case 1:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, q, new MetallicizePower(q, 15), 15));
                    break;
                case 2:
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, q, new RegenerateMonsterPower(q, 15), 15));
                    break;
                case 3:
                    AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(q, 0.25F, true));
                    break;
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ct) throws Exception {
            return LineFinder.findInOrder(ct, new Matcher.MethodCallMatcher(GameActionManager.class, "addToTop"));
        }
    }
}
