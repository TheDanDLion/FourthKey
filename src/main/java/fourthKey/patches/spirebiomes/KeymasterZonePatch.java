package fourthKey.patches.spirebiomes;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import fourthKey.campfire.BustPurpleKeyOption;
import fourthKey.patches.characters.AbstractPlayerPatch.PurpleKeyPatch;

public class KeymasterZonePatch {

    @SpirePatch2(cls = "spireMapOverhaul.zones.keymaster.KeymasterZone", method = "canSpawn", requiredModId = "anniv6")
    public static class SpawnWhenHavePurpleKey {
        public static boolean Postfix(boolean __result) {
            return __result && AbstractDungeon.player != null
                    && PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player);
        }
    }

    @SpirePatch2(cls = "spireMapOverhaul.zones.keymaster.KeymasterZone", method = "atBattleStart", requiredModId = "anniv6")
    public static class AddPurpleKeyEffectAtBattleStart {
        public static void Postfix() {
            AbstractDungeon.actionManager.addToBottom(AbstractDungeon.cardRandomRng.randomBoolean()
                    ? new GainEnergyAction(BustPurpleKeyOption.EXTRA_DRAW_OR_ENERGY)
                    : new DrawCardAction(BustPurpleKeyOption.EXTRA_DRAW_OR_ENERGY));
            ;
        }
    }
}
