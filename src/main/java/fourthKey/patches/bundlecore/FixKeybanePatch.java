package fourthKey.patches.bundlecore;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor;

import fourthKey.FourthKeyInitializer;
import fourthKey.patches.characters.AbstractPlayerPatch;
import javassist.CtBehavior;

public class FixKeybanePatch {
    
    private static boolean equipping = false;

    public static int countKeys() {
        int count = 0;
        if (Settings.hasRubyKey)
            count++;
        if (Settings.hasSapphireKey)
            count++;
        if (Settings.hasEmeraldKey)
            count++;
        if (AbstractPlayerPatch.PurpleKeyPatch.hasAmethystKey.get(AbstractDungeon.player))
            count++;
        return count;
    }

    public static boolean triggerOnEquip() {
        return countKeys() >= 3 && !(Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey);
    }

    @SpirePatch2(
        clz = ObtainKeyEffect.class,
        method = "update",
        requiredModId = "bundlecore"
    )
    public static class CheckForAmethystKey {
        @SpireInsertPatch(
            locator = Locator.class
        )
        public static void Insert(ObtainKeyEffect __instance, KeyColor ___keyColor) {
            int keyCount = countKeys();
            if (keyCount == 2
                && ((Settings.hasRubyKey && Settings.hasEmeraldKey)
                    || (Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    || (Settings.hasRubyKey && Settings.hasSapphireKey)))
                        return;

            if (keyCount == 2) {
                if (AbstractDungeon.player.hasRelic("bundle_of_food:DeliciousdonuBoss"))
                    AbstractDungeon.player.getRelic("bundle_of_food:DeliciousdonuBoss").onTrigger();
                if (AbstractDungeon.player.hasRelic("bundlecore:DeliciousDecaBoss"))
                    AbstractDungeon.player.getRelic("bundlecore:DeliciousDecaBoss").onTrigger();
                if (AbstractDungeon.player.hasRelic("bundlecore:TemporalTimepieceBoss"))
                    AbstractDungeon.player.getRelic("bundlecore:TemporalTimepieceBoss").onTrigger();
                if (AbstractDungeon.player.hasRelic("bundlecore:MenacingRavenBoss"))
                    AbstractDungeon.player.getRelic("bundlecore:MenacingRavenBoss").onTrigger();
                if (AbstractDungeon.player.hasRelic("bundlecore:CursedBrandBoss"))
                    AbstractDungeon.player.getRelic("bundlecore:CursedBrandBoss").onTrigger();
                if (AbstractDungeon.player.hasRelic("bundle_of_content:InsultingNoteQuest"))
                    AbstractDungeon.player.getRelic("bundle_of_content:InsultingNoteQuest").onTrigger();
                if (AbstractDungeon.player.hasRelic("Bundle_Of_Terra:CursedfishQuest"))
                    AbstractDungeon.player.getRelic("Bundle_Of_Terra:CursedfishQuest").onTrigger();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ct) throws Exception {
                return LineFinder.findInOrder(ct, new Matcher.MethodCallMatcher(SoundMaster.class, "playA"));
            }
        }
    }

    public static class DeliciousdonuBoss {
        @SpirePatch2(
            cls = "foodbundle.relics.DeliciousdonuBoss",
            method = "onTrigger",
            requiredModId = "foodbundle"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "foodbundle.relics.DeliciousdonuBoss",
            method = "onEquip",
            requiredModId = "foodbundle"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class DeliciousDecaBoss {
        @SpirePatch2(
            cls = "bundlecore.relics.DeliciousDecaBoss",
            method = "onTrigger",
            requiredModId = "bundlecore"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                FourthKeyInitializer.logger.info(equipping);
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "bundlecore.relics.DeliciousDecaBoss",
            method = "onEquip",
            requiredModId = "bundlecore"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class TemporalTimepieceBoss {
        @SpirePatch2(
            cls = "bundlecore.relics.TemporalTimepieceBoss",
            method = "onTrigger",
            requiredModId = "bundlecore"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "bundlecore.relics.TemporalTimepieceBoss",
            method = "onEquip",
            requiredModId = "bundlecore"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class MenacingRavenBoss {
        @SpirePatch2(
            cls = "bundlecore.relics.MenacingRavenBoss",
            method = "onTrigger",
            requiredModId = "bundlecore"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "bundlecore.relics.MenacingRavenBoss",
            method = "onEquip",
            requiredModId = "bundlecore"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class CursedBrandBoss {
        @SpirePatch2(
            cls = "bundlecore.relics.CursedBrandBoss",
            method = "onTrigger",
            requiredModId = "bundlecore"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "bundlecore.relics.CursedBrandBoss",
            method = "onEquip",
            requiredModId = "bundlecore"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class InsultingNoteQuest {
        @SpirePatch2(
            cls = "bundle_of_content.relics.InsultingNoteQuest",
            method = "onTrigger",
            requiredModId = "bundle_of_content"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "bundle_of_content.relics.InsultingNoteQuest",
            method = "onEquip",
            requiredModId = "bundle_of_content"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }

    public static class CursedfishQuest {
        @SpirePatch2(
            cls = "terrabundle.relics.CursedfishQuest",
            method = "onTrigger",
            requiredModId = "Bundle_Of_Terra"
        )
        public static class BalkTrigger {
            public static SpireReturn<Void> Prefix() {
                if (countKeys() > 2 && !equipping)
                    return SpireReturn.Return();
                equipping = false;
                return SpireReturn.Continue();
            }
        }

        @SpirePatch2(
            cls = "terrabundle.relics.CursedfishQuest",
            method = "onEquip",
            requiredModId = "Bundle_Of_Terra"
        )
        public static class CheckKeyCombos {
            public static void Prefix() {
                if (Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
                    equipping = true;
            }

            public static void Postfix(AbstractRelic __instance) {
                if (triggerOnEquip()) {
                    equipping = true;
                    __instance.onTrigger();
                }
            }
        }
    }
}
