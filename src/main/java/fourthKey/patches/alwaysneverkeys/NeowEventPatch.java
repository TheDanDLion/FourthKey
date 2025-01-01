package fourthKey.patches.alwaysneverkeys;

import java.util.Properties;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;

import fourthKey.patches.vfx.ObtainKeyEffectPatch;

@SpirePatch2(clz = NeowEvent.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
        boolean.class }, requiredModId = "alwaysneverkeys")
public class NeowEventPatch {
    public static void Postfix() {
        int keyMode = -1;
        try {
            SpireConfig config = new SpireConfig("alwaysNeverKeys", "alwaysNeverKeysConf", new Properties());
            config.load();
            keyMode = config.getInt("keyMode");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (keyMode == 1) {
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffectPatch.PURPLE));
        }
    }
}