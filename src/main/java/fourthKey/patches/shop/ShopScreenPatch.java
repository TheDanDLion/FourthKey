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
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.shop.ShopScreen;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.ModInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

public class ShopScreenPatch {

    public static Texture amethystKey;
    public static Hitbox keyHitbox;
    private static final int KEY_COST = 99;
    public static float keyX = 0.12F * Settings.WIDTH;
    public static float keyY;
    private static final float KEY_GOLD_X_OFFSET = -56.0F * Settings.scale;
    private static final float KEY_GOLD_Y_OFFSET = -100.0F * Settings.scale;
    private static final float KEY_PRICE_X_OFFSET = 14.0F * Settings.scale;
    private static final float KEY_PRICE_Y_OFFSET = -62.0F * Settings.scale;
    private static final float GOLD_IMG_WIDTH = ImageMaster.UI_GOLD.getWidth() * Settings.scale;

    private static void purchasePurpleKey() {
        AbstractDungeon.player.loseGold(KEY_COST);
        CardCrawlGame.sound.play("SHOP_PURCHASE", 0.1F);
        AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, true);
        AbstractDungeon.effectsQueue.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
    }

    private static void updatePurpleKey(ShopScreen __instance) {
        if (!ModInitializer.disableAmethystKey) {
            keyHitbox.update();
            if (keyHitbox.hovered) {
                __instance.moveHand(keyX, keyY);
                if ((InputHelper.justReleasedClickLeft || CInputActionSet.select.isJustPressed())
                    && AbstractDungeon.player.gold >= KEY_COST && !AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                    if (!Settings.isTouchScreen) {
                        CInputActionSet.select.unpress();
                        purchasePurpleKey();
                    } else {
                        if (AbstractDungeon.player.gold < KEY_COST) {
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
            if (!ModInitializer.disableAmethystKey && !AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                sb.draw(amethystKey, keyX, keyY, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.UI_GOLD, keyX + KEY_GOLD_X_OFFSET, keyY + KEY_GOLD_Y_OFFSET, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
                Color color = Color.WHITE.cpy();
                if (KEY_COST > AbstractDungeon.player.gold)
                    color = Color.SALMON.cpy();
                FontHelper.renderFontLeftTopAligned(
                    sb,
                    FontHelper.tipHeaderFont,
                    Integer.toString(KEY_COST),
                    keyX + KEY_PRICE_X_OFFSET,
                    keyY + KEY_PRICE_Y_OFFSET,
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
            if (!ModInitializer.disableAmethystKey && keyHitbox.hovered && AbstractDungeon.player.gold >= KEY_COST && !AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player))
                purchasePurpleKey();
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
