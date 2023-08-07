package fourthKey.patches.monsters.ending;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.RegenerateMonsterPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import fourthKey.ModInitializer;

public class CorruptHeartPatch {

    private static final int LOW_ASC_HP_LIMIT = 800;
    private static final int NEW_HP_LIMIT = 900;
    private static final int STR_GAIN = 1;
    private static final float HP_MOD = 0.11f;
    private static final int METALLICIZE = 25;
    private static final int REGEN = 25;

    @SpirePatch2(
        clz = CorruptHeart.class,
        method = SpirePatch.CONSTRUCTOR
    )
    public static class IncreaseCorruptHeartHealthPatch {
        public static void Postfix(CorruptHeart __instance) {
            if (!ModInitializer.disableAct4Difficulty) {
                __instance.currentHealth = AbstractDungeon.ascensionLevel >= 19 ? NEW_HP_LIMIT : LOW_ASC_HP_LIMIT;
                if (Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies")) {
                    float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                    __instance.currentHealth = (int)(__instance.currentHealth * mod);
                }
                if (ModHelper.isModEnabled("MonsterHunter"))
                    __instance.currentHealth = (int)(__instance.currentHealth * 1.5F);
                __instance.maxHealth = __instance.currentHealth;
            }
        }
    }

    @SpirePatch2(
        clz = CorruptHeart.class,
        method = "usePreBattleAction"
    )
    public static class ApplyHarderPowersPatch {

        @SpireInsertPatch(
            loc = 91,
            localvars = {"invincibleAmt", "beatAmount"}
        )
        public static void Insert(CorruptHeart __instance, @ByRef int[] invincibleAmt, @ByRef int[] beatAmount) {
            if (!ModInitializer.disableAct4Difficulty) {
                invincibleAmt[0] -= 50;
                beatAmount[0]++;
                switch (AbstractDungeon.mapRng.random(0, 3)) {
                    case 0:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new StrengthPower(__instance, STR_GAIN), STR_GAIN));
                        break;
                    case 1:
                        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(__instance, HP_MOD, true));
                        break;
                    case 2:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new MetallicizePower(__instance, METALLICIZE), METALLICIZE));
                        break;
                    case 3:
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new RegenerateMonsterPower(__instance, REGEN), REGEN));
                        break;
                }
            }
        }
    }
}
