package fourthKey.patches.hugyourelics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.RegenPower;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class PadlockPatch {
    
    private static RelicStrings RELIC_STRINGS = CardCrawlGame.languagePack.getRelicStrings(FourthKeyInitializer.makeID("Padlock"));
    private static String[] DESCRIPTIONS = RELIC_STRINGS.DESCRIPTIONS;

    @SpirePatch2(
        cls = "hugyourelics.relics.shop.Padlock",
        method = "atBattleStart",
        requiredModId = "HugYouRelics"
    )
    public static class ApplyBuff {
        public static void Postfix() {
            if (AbstractDungeon.player != null && !FourthKeyInitializer.disableAmethystKey && AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player))
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 3), 3));
        }
    }

    @SpirePatch2(
        cls = "hugyourelics.relics.shop.Padlock",
        method = "getUpdatedDescription",
        requiredModId = "HugYouRelics"
    )
    public static class UpdateDescription {
        public static String Postfix(String __result) {
            if (!FourthKeyInitializer.disableAmethystKey)
                return __result + " " + DESCRIPTIONS[0];
            return __result;
        }
    }
}
