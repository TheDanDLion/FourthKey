package fourthKey.shopItems;

import basemod.abstracts.CustomShopItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.patches.vfx.ObtainKeyEffectPatch;

import static fourthKey.ModInitializer.makeID;

public class PurpleKey extends CustomShopItem {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PurpleKey"));

    public static Texture amethystKey;
    private static final int KEY_COST = 49;

    public PurpleKey() {
        super(AbstractDungeon.shopScreen, amethystKey, KEY_COST, uiStrings.TEXT[0], uiStrings.TEXT[1]);
    }

    @Override
    public void update(float rugY) {
        int cost = (int)(KEY_COST
            * (AbstractDungeon.player.hasRelic("The Courier") ? 0.8 : 1.0)
            * (AbstractDungeon.player.hasRelic("Membership Card") ? 0.5 : 1.0));
        this.price = cost;
        super.update(rugY);
    }

    @Override
    public void purchase() {
        AbstractDungeon.player.loseGold(this.price);
        CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }
}
