package fourthKey.patches.shop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

public class ShopScreenPatch {

    private static final Texture AMETHYST_KEY = new Texture("fourthKeyResources/images/ui/purpleKey.png");
    private static final Hitbox KEY_HITBOX = new Hitbox(AMETHYST_KEY.getWidth(), AMETHYST_KEY.getHeight());
    private static final int KEY_COST = 99;
    private static final float KEY_X = 50.0F * Settings.scale;
    private static final float KEY_Y = Settings.scale / 2.0F;
    private static final float KEY_PRICE_X_OFFSET = -56.0F * Settings.scale;
    private static final float KEY_PRICE_Y_OFFSET = -100.0F * Settings.scale;

    @SpirePatch2(
        clz = ShopScreenPatch.class,
        method = "render"
    )
    public static class RenderShopPurpleKeyPatch {
        @SpireInsertPatch(
            loc = 1394
        )
        public static void Insert(SpriteBatch sb) {
            if (!AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                sb.setColor(Color.WHITE);
                sb.draw(AMETHYST_KEY, KEY_X, KEY_Y);
                Color color = Color.WHITE.cpy();
                if (KEY_COST > AbstractDungeon.player.gold)
                    color = Color.SALMON.cpy();
                FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    Integer.toString(KEY_COST),
                    KEY_X + KEY_PRICE_X_OFFSET,
                    KEY_Y + KEY_PRICE_Y_OFFSET,
                    color
                );
            }
        }
    }

    @SpirePatch2(
        clz = ShopScreenPatch.class,
        method = "update"
    )
    public static class PurchasePurpleKeyPatch {
        @SpireInsertPatch(
            loc = 605
        )
        public static void Insert() {
            KEY_HITBOX.update();
            if (KEY_HITBOX.hovered) {
                AbstractDungeon.player.loseGold(KEY_COST);
                CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
                AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
                AbstractDungeon.effectsQueue.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
            }
        }
    }
}
