package fourthKey.patches.shop;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
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
import com.megacrit.cardcrawl.shop.StorePotion;
import com.megacrit.cardcrawl.shop.StoreRelic;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import basemod.ReflectionHacks;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;

import static fourthKey.FourthKeyInitializer.makeID;

public class ShopScreenPatch {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("PurpleKey"));

    public static Texture amethystKey;
    public static Hitbox keyHitbox;
    private static final int KEY_COST = 49;
    public static final float KEY_X = 0.43F * Settings.WIDTH;
    public static final float KEY_Y = 190.0F * Settings.yScale;
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

    @SpirePatch2(clz = ShopScreen.class, method = "render")
    public static class RenderShopPurpleKeyPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen __instance, SpriteBatch sb) {
            if (Loader.isModLoaded("shopgrid")) return;
            if (FourthKeyInitializer.disableAmethystKey) return;
            if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) return;
            int cost = (int)(KEY_COST
                * (AbstractDungeon.player.hasRelic("The Courier") ? 0.8 : 1.0)
                * (AbstractDungeon.player.hasRelic("Membership Card") ? 0.5 : 1.0));
            sb.draw(amethystKey, KEY_X, KEY_Y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.UI_GOLD, KEY_X + KEY_GOLD_X_OFFSET, KEY_Y + KEY_GOLD_Y_OFFSET, GOLD_IMG_WIDTH, GOLD_IMG_WIDTH);
            Color color = Color.WHITE.cpy();
            if (cost > AbstractDungeon.player.gold)
                color = Color.SALMON.cpy();
            FontHelper.renderFontLeftTopAligned(
                sb,
                FontHelper.tipHeaderFont,
                Integer.toString(cost),
                KEY_X + KEY_PRICE_X_OFFSET,
                KEY_Y + KEY_PRICE_Y_OFFSET,
                color
            );
            keyHitbox.render(sb);
            if (keyHitbox.hovered) {
                TipHelper.renderGenericTip(
                    InputHelper.mX + 50.0F * Settings.xScale,
                    InputHelper.mY + 50.0F * Settings.yScale,
                    uiStrings.TEXT[0],
                    uiStrings.TEXT[1]
                );
            }
        }
    }

    @SpirePatch2(clz = ShopScreen.class, method = "update")
    public static class UpdatePurpleKeyPatch {
        @SpirePostfixPatch
        public static void Postfix(ShopScreen __instance) {
            if (Loader.isModLoaded("shopgrid")) return;
            if (FourthKeyInitializer.disableAmethystKey) return;
            if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) return;
            int cost = (int)(KEY_COST
                * (AbstractDungeon.player.hasRelic("The Courier") ? 0.8 : 1.0)
                * (AbstractDungeon.player.hasRelic("Membership Card") ? 0.5 : 1.0));
            keyHitbox.update();
            if (keyHitbox.hovered) {
                __instance.moveHand(KEY_X - 175.0F * Settings.xScale, KEY_Y);
                if (InputHelper.justClickedLeft) {
                    keyHitbox.clickStarted = true;
                }
                if (keyHitbox.clicked || CInputActionSet.select.isJustPressed()) {
                    keyHitbox.clicked = false;
                    CInputActionSet.select.unpress();
                    if (AbstractDungeon.player.gold >= cost) {
                        purchasePurpleKey(cost);
                    } else {
                        __instance.playCantBuySfx();
                        __instance.createSpeech(ShopScreen.getCantBuyMsg());
                    }
                }
            }
        }
    }

    // Integrates the key into the D-pad navigation state machine for controller players.
    @SpirePatch2(clz = ShopScreen.class, method = "updateControllerInput")
    public static class ControllerNavPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ShopScreen __instance) {
            if (Loader.isModLoaded("shopgrid")) return SpireReturn.Continue();
            if (!Settings.isControllerMode
                    || AbstractDungeon.topPanel.selectPotionMode
                    || !AbstractDungeon.topPanel.potionUi.isHidden
                    || AbstractDungeon.player.viewingRelics) {
                return SpireReturn.Continue();
            }
            if (FourthKeyInitializer.disableAmethystKey) return SpireReturn.Continue();
            if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) return SpireReturn.Continue();

            ArrayList<StoreRelic> relics = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "relics");
            ArrayList<StorePotion> potions = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "potions");

            if (keyHitbox.hovered) {
                // Navigate away from the key with the D-pad.
                if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                    if (!relics.isEmpty()) {
                        Gdx.input.setCursorPosition((int)relics.get(0).relic.hb.cX, Settings.HEIGHT - (int)relics.get(0).relic.hb.cY);
                    } else if (!potions.isEmpty()) {
                        Gdx.input.setCursorPosition((int)potions.get(0).potion.hb.cX, Settings.HEIGHT - (int)potions.get(0).potion.hb.cY);
                    } else if (__instance.purgeAvailable) {
                        float purgeX = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "purgeCardX");
                        float purgeY = ReflectionHacks.getPrivate(__instance, ShopScreen.class, "purgeCardY");
                        Gdx.input.setCursorPosition((int)purgeX, Settings.HEIGHT - (int)purgeY);
                    }
                } else if (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) {
                    if (!__instance.coloredCards.isEmpty()) {
                        Gdx.input.setCursorPosition(
                            (int)__instance.coloredCards.get(0).hb.cX,
                            Settings.HEIGHT - (int)__instance.coloredCards.get(0).hb.cY
                        );
                    } else if (!__instance.colorlessCards.isEmpty()) {
                        Gdx.input.setCursorPosition(
                            (int)__instance.colorlessCards.get(0).hb.cX,
                            Settings.HEIGHT - (int)__instance.colorlessCards.get(0).hb.cY
                        );
                    }
                }
                // Left/down from the key: no navigation (it is the leftmost item).
                return SpireReturn.Return();
            }

            // Navigate TO the key: intercept left-from-first-relic before vanilla sends
            // the cursor to the colorless cards.
            if (!relics.isEmpty() && relics.get(0).relic.hb.hovered
                    && (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())) {
                Gdx.input.setCursorPosition((int)keyHitbox.cX, Settings.HEIGHT - (int)keyHitbox.cY);
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }
}
