package fourthKey.patches.ui.panels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import fourthKey.patches.characters.AbstractPlayerPatch;

public class TopPanelPatch {

    @SpirePatch2(
        clz = TopPanel.class,
        method = "renderName"
    )
    public static class RenderPurpleKeyPatch {

        @SpireInsertPatch(
            loc = 1320
        )
        public static SpireReturn<Void> Insert(SpriteBatch sb, float ___ICON_Y) {
            // TODO: change images so they are drawn over different positions
            if (Settings.hasRubyKey)
                sb.draw(ImageMaster.RUBY_KEY, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if (Settings.hasEmeraldKey)
                sb.draw(ImageMaster.EMERALD_KEY, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if (Settings.hasSapphireKey)
                sb.draw(ImageMaster.SAPPHIRE_KEY, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if (AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player))
                sb.draw(ImageMaster.SAPPHIRE_KEY, -32.0F + 46.0F * Settings.scale, ___ICON_Y - 32.0F + 29.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            return SpireReturn.Return();
        }
    }

}
