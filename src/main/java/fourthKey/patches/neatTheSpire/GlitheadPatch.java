package fourthKey.patches.neatTheSpire;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import fourthKey.patches.characters.AbstractPlayerPatch;

@SpirePatch2(
    cls = "neatTheSpire.relics.Gilthead",
    method = "onEquip",
    requiredModId = "neatTheSpire"
)
public class GlitheadPatch {
    @SpirePostfixPatch
    public static void Postfix() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(p)) {
            p.increaseMaxHp(4, true);
        }
    }
}
