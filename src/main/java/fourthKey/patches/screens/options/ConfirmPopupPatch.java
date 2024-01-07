package fourthKey.patches.screens.options;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;

import fourthKey.FourthKeyInitializer;

@SpirePatch2(
    clz = ConfirmPopup.class,
    method = "yesButtonEffect",
    requiredModId = "downfall"
)
public class ConfirmPopupPatch {
    public static void Prefix(ConfirmPopup __instance) {
        switch(__instance.type) {
            case ABANDON_MAIN_MENU:
            case ABANDON_MID_RUN:
            case DELETE_SAVE:
                FourthKeyInitializer.downfallEvilMode = false;
                break;
            case EXIT:
                break;
            case SKIP_FTUE:
                break;
            default:
                break;
        }
    }
}
