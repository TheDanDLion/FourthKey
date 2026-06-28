package fourthKey.patches.shopgrid;

import java.util.Properties;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import shopgrid.ShopGridInitializer;
import shopgrid.interfaces.PostShopInitializeSubscriber;
import shopgrid.ui.ShopGrid;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.shopItems.PurpleKey;

public class ShopGridHelper implements PostShopInitializeSubscriber {

    public static void subscribe() {
        ShopGridInitializer.subscribe(new ShopGridHelper());
    }

    @Override
    public void receivePostShopInitialize() {
        if (Loader.isModLoaded("alwaysneverkeys")) {
            int keyMode = -1;
            try {
                SpireConfig config = new SpireConfig("alwaysNeverKeys", "alwaysNeverKeysConf", new Properties());
                config.load();
                keyMode = config.getInt("keyMode");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (keyMode == 2)
                return;
        }

        if (!FourthKeyInitializer.disableAmethystKey && !AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player)) {
            FourthKeyInitializer.logger.info("Adding Amethyst Key to shop");
            ShopGrid.tryAddItem(new PurpleKey());
        }
    }
}
