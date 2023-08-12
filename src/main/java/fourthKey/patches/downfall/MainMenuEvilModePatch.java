package fourthKey.patches.downfall;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

import fourthKey.ModInitializer;

public class MainMenuEvilModePatch {

    public static boolean captureValue(boolean value) {
        return ModInitializer.downfallEvilMode = value;
    }

    @SpirePatch2(
        cls = "downfall.patches.MainMenuEvilMode$EvilMainMenuPanelButton",
        method = "buttonEffect",
        requiredModId = "downfall"
    )
    public static class SetEvilModeFlagPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess fa) throws CannotCompileException {
                    if (fa.getFieldName().equals("evilMode")) {
                        fa.replace("{ $proceed($$); fourthKey.patches.downfall.MainMenuEvilModePatch.captureValue(getResult() == downfall.patches.MainMenuEvilMode.Enums.PLAY_EVIL); }");
                    }
                }
            };
        }
    }
}
