package fourthKey.patches.ui.panels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import fourthKey.ModInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;

public class TopPanelPatch {

    public static Texture rubyKey;
    public static Texture emeraldKey;
    public static Texture sapphireKey;
    public static Texture amethystKey;
    public static Texture keySlots;

    // compatability with Downfall
    public static Texture brokenRubyKey;
    public static Texture brokenEmeraldKey;
    public static Texture brokenSapphireKey;
    public static Texture brokenAmethystKey;

    @SpirePatch2(
        clz = TopPanel.class,
        method = "renderName"
    )
    public static class RenderPurpleKeyPatch {

        @SpireInsertPatch(
            loc = 1320
        )
        public static SpireReturn<Void> Insert(SpriteBatch sb, float ___ICON_Y, String ___name, String ___title, float ___nameX, float ___NAME_Y, float ___titleX, float ___titleY) {
            if (!ModInitializer.disableAmethystKey) {
                sb.draw(keySlots, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);

                boolean evilMode = ModInitializer.downfallEvilMode;

                if (evilMode && AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenRubyKey.get(AbstractDungeon.player))
                    sb.draw(brokenRubyKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                else if (Settings.hasRubyKey)
                    sb.draw(rubyKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                if (evilMode && AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenEmeraldKey.get(AbstractDungeon.player))
                    sb.draw(brokenEmeraldKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                else if (Settings.hasEmeraldKey)
                    sb.draw(emeraldKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                if (evilMode && AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenSapphireKey.get(AbstractDungeon.player))
                    sb.draw(brokenSapphireKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                else if (Settings.hasSapphireKey)
                    sb.draw(sapphireKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                if (evilMode && AbstractPlayerPatch.DownfallCompatabilityPatch.hasBrokenAmethystKey.get(AbstractDungeon.player))
                    sb.draw(brokenAmethystKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                else if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player))
                    sb.draw(amethystKey, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);


                FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, ___name, ___nameX, ___NAME_Y, Color.WHITE);
                if (Settings.isMobile) {
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, ___title, ___nameX, ___titleY - 30.0F * Settings.scale, Color.LIGHT_GRAY);
                } else {
                    FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipBodyFont, ___title, ___titleX, ___titleY, Color.LIGHT_GRAY);
                }
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

}
