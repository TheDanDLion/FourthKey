    package fourthKey.patches.downfall.monsters;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import fourthKey.FourthKeyInitializer;

public class NeowBossFinalPatch {
    @SpirePatch2(
        cls = "downfall.monsters.NeowBossFinal",
        method = SpirePatch.CONSTRUCTOR,
        requiredModId = "downfall"
    )
    public static class IncreaseHpPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (!FourthKeyInitializer.disableAct4Difficulty && m.getClassName().equals(AbstractMonster.class.getName())
                        && m.getMethodName().equals("setHp")) {
                        m.replace("{$proceed($1+100);}");
                    }
                }
            };
        }
    }

    @SpirePatch2(
        cls = "downfall.monsters.NeowBossFinal",
        method = "usePreBattleAction",
        requiredModId = "downfall"
    )
    public static class IncreaseDifficultyPatch {
        @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"beatAmount", "invincibleAmt"}
        )
        public static void Insert(@ByRef int[] beatAmount, @ByRef int[] invincibleAmt) {
            if (!FourthKeyInitializer.disableAct4Difficulty) {
                beatAmount[0]++;
                invincibleAmt[0] -= 50;
                switch(AbstractDungeon.mapRng.random(0, 3)) {
                    case 0:
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, 5), 5));
                        break;
                    case 1:
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MetallicizePower(m, 25), 25));
                        break;
                    case 2:
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, 25), 25));
                        break;
                    case 3:
                        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
                            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(m, 0.11F, true));

                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                return LineFinder.findInOrder(ct, new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom"));
            }
        }
    }
}
