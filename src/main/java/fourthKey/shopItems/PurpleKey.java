package fourthKey.shopItems;

import basemod.abstracts.CustomShopItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.patches.vfx.ObtainKeyEffectPatch;

import static fourthKey.FourthKeyInitializer.makeID;

public class PurpleKey extends CustomShopItem {

    public static final String SHOP_ID = makeID("AmethystKey");

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PurpleKey"));

    public static Texture amethystKey;
    private static final int KEY_COST = 49;

    public PurpleKey() {
        super(SHOP_ID, amethystKey, KEY_COST, uiStrings.TEXT[0], uiStrings.TEXT[1]);
    }

    @Override
    public void purchase() {
        super.purchase();
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }
}
