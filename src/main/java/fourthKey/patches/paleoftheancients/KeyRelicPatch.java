package fourthKey.patches.paleoftheancients;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.ui.panels.TopPanelPatch;
import javassist.CtBehavior;

import static fourthKey.FourthKeyInitializer.makeID;

public class KeyRelicPatch {
    
    public static final String ID = makeID("KeyRelic");

    private static RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;

    public static Texture keySlotsOutline;

    @SpirePatch2(
        cls = "paleoftheancients.relics.KeyRelic",
        method = SpirePatch.CONSTRUCTOR,
        requiredModId = "paleoftheancients"
    )
    public static class SetFourthKeyImagesPatch {
        public static void Postfix(CustomRelic __instance) {
            if (!FourthKeyInitializer.disableAmethystKey) {
                __instance.img = TopPanelPatch.keySlots;
                __instance.outlineImg = keySlotsOutline;
            }
        }
    }

    @SpirePatch2(
        cls = "paleoftheancients.relics.KeyRelic",
        method = "getUpdatedDescription",
        requiredModId = "paleoftheancients"
    )
    public static class UpdateDescriptionPatch {
        public static String Postfix(String __result) {
            StringBuilder result = new StringBuilder();
            result.append(__result);
            if (!FourthKeyInitializer.disableAmethystKey && AbstractDungeon.player != null && AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                result.append(" ").append(DESCRIPTIONS[0]);
            }
            return result.toString();
        }
    }

    @SpirePatch2(
        cls = "paleoftheancients.relics.KeyRelic",
        method = "atBattleStart",
        requiredModId = "paleoftheancients"
    )
    public static class ApplyAmethystKeyAtBattleStartPatch {
        @SpireInsertPatch(
            locator = Locator.class
        )
        public static void Insert() {
            if (!FourthKeyInitializer.disableAmethystKey && AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                AbstractDungeon.actionManager.addToBottom(new GainGoldAction(AbstractDungeon.miscRng.random(5, 10)));
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                Matcher finalMatcher = new Matcher.NewExprMatcher(RelicAboveCreatureAction.class);
                return LineFinder.findInOrder(ct, finalMatcher);
            }
        }
    }

    @SpirePatch2(
        cls = "paleoftheancients.relics.KeyRelic",
        method = "atTurnStart",
        requiredModId = "paleoftheancients"
    )
    public static class AmethystKeyEffectAtTurnStartPatch {
        public static void Postfix() {
            if (!FourthKeyInitializer.disableAmethystKey && AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MantraPower(AbstractDungeon.player, 1), 1));
            }
        }
    }

    @SpirePatch2(
        cls = "paleoftheancients.relics.KeyRelic",
        method = "renderInTopPanel",
        requiredModId = "paleoftheancients"
    )
    public static class RenderFourthKeyInTopPanelPatch {

        private static void draw(SpriteBatch sb, CustomRelic __instance, Texture img, float offsetX) {
            sb.draw(img, __instance.currentX - 32.0F + offsetX, __instance.currentY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, __instance.scale, __instance.scale, 0F, 0, 0, 64, 64, false, false);
        }

        @SpireInsertPatch(
            locator = Locator.class
        )
        public static SpireReturn<Void> Insert(CustomRelic __instance, SpriteBatch sb) {
            if (!FourthKeyInitializer.disableAmethystKey) {
                float offsetX = ((Float)ReflectionHacks.getPrivateStatic(AbstractRelic.class, "offsetX")).floatValue();
                draw(sb, __instance, __instance.img, offsetX);
                if (Settings.hasRubyKey)
                    draw(sb, __instance, TopPanelPatch.rubyKey, offsetX);
                if (Settings.hasEmeraldKey)
                    draw(sb, __instance, TopPanelPatch.emeraldKey, offsetX);
                if (Settings.hasSapphireKey)
                    draw(sb, __instance, TopPanelPatch.sapphireKey, offsetX);
                if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player))
                    draw(sb, __instance, TopPanelPatch.amethystKey, offsetX);
                __instance.renderFlash(sb, true);
                __instance.hb.render(sb);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findInOrder(ct, finalMatcher);
            }
        }
    }

}
