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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

import static fourthKey.FourthKeyInitializer.makeID;

public class ShopScreenPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ShopScreenPatch"));

    public static Texture amethystKey;
    public static Hitbox keyHitbox;
    private static final int KEY_COST = 49;
    public static final float KEY_X = 0.43F * Settings.WIDTH;
    public static final float KEY_Y = 190.0F;
    private static final float KEY_GOLD_X_OFFSET = -28.0F * Settings.scale;
    private static final float KEY_GOLD_Y_OFFSET = -33.0F * Settings.scale;
    private static final float KEY_PRICE_X_OFFSET = 42.0F * Settings.scale;
    private static final float KEY_PRICE_Y_OFFSET = 5.0F * Settings.scale;
    private static final float GOLD_IMG_WIDTH = ImageMaster.UI_GOLD.getWidth() * Settings.scale;

    private static void purchasePurpleKey(int cost) {
        AbstractDungeon.player.loseGold(cost);
        CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
        AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.set(AbstractDungeon.player, true);
        AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }

    private static void updatePurpleKey(ShopScreen __instance) {
        int cost = (int)(KEY_COST
            * (AbstractDungeon.player.hasRelic("The Courier") ? 0.8 : 1.0)
            * (AbstractDungeon.player.hasRelic("Membership Card") ? 0.5 : 1.0));
        if (!FourthKeyInitializer.disableAmethystKey) {
            keyHitbox.update();
            if (keyHitbox.hovered) {
                if (!AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                    __instance.moveHand(KEY_X - 175.0F, KEY_Y);
                    TipHelper.renderGenericTip(
                        InputHelper.mX + 50.0F * Settings.xScale,
                        InputHelper.mY + 50.0F * Settings.yScale,
                        uiStrings.TEXT[0],
                        uiStrings.TEXT[1]
                    );
                }
                if ((InputHelper.justReleasedClickLeft || CInputActionSet.select.isJustPressed())
                    && AbstractDungeon.player.gold >= cost && !AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                    if (!Settings.isTouchScreen) {
                        CInputActionSet.select.unpress();
                        purchasePurpleKey(cost);
                    } else {
                        if (AbstractDungeon.player.gold < cost) {
                            __instance.playCantBuySfx();
                            __instance.createSpeech(ShopScreen.getCantBuyMsg());
                        } else { // TODO: will need to make work for confirm button
                            __instance.confirmButton.hideInstantly();
                            __instance.confirmButton.show();
                            __instance.confirmButton.hb.clickStarted = false;
                            __instance.confirmButton.isDisabled = false;
                        }
                    }
                }
            }
        }
    }

    @SpirePatch2(
        clz = ShopScreen.class,
        method = "render"
    )
    public static class RenderShopPurpleKeyPatch {
        @SpireInsertPatch(
            loc = 1394
        )
        public static void Insert(SpriteBatch sb) {
            if (!FourthKeyInitializer.disableAmethystKey && !AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                sb.draw(amethystKey, KEY_X, KEY_Y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.UI_GOLD, KEY_X + KEY_GOLD_X_OFFSET, KEY_Y + KEY_GOLD_Y_OFFSET, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
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
                keyHitbox.render(sb);
            }
        }
    }

    @SpirePatch2(
        clz = ShopScreen.class,
        method = "update"
    )
    public static class PurchasePurpleKeyPatch {
        @SpireInsertPatch(
            loc = 605
        )
        public static void Insert() {
            int cost = (int)(KEY_COST
                * (AbstractDungeon.player.hasRelic("The Courier") ? 0.8 : 1.0)
                * (AbstractDungeon.player.hasRelic("Membership Card") ? 0.5 : 1.0));
            if (!FourthKeyInitializer.disableAmethystKey && keyHitbox.hovered && AbstractDungeon.player.gold >= cost && !AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                purchasePurpleKey(cost);
            }
        }
    }

    @SpirePatch2(
        clz = ShopScreen.class,
        method = "update"
    )
    public static class UpdatePurpleKeyPatch {
        @SpireInsertPatch(
            loc = 621
        )
        public static void Insert(ShopScreen __instance) {
            updatePurpleKey(__instance);
        }
    }
}
