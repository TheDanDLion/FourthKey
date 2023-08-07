package fourthKey;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomSavableRaw;
import basemod.devcommands.ConsoleCommand;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.EventStrings;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fourthKey.console.*;
import fourthKey.events.*;
import fourthKey.patches.characters.AbstractPlayerPatch;
import fourthKey.patches.shop.ShopScreenPatch;
import fourthKey.patches.ui.panels.TopPanelPatch;
import fourthKey.patches.vfx.ObtainKeyEffectPatch;
import fourthKey.util.IDCheckDontTouchPls;
import fourthKey.util.TextureLoader;

@SpireInitializer
public class ModInitializer implements
    EditStringsSubscriber,
    PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(ModInitializer.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties fourthKeyProperties = new Properties();
    public static final String DISABLE_ACT_4_DIFFICULTY = "disableAct4Difficulty";
    public static boolean disableAct4Difficulty = false;
    public static final String DISABLE_AMETHYST_KEY = "disableAmethystKey";
    public static boolean disableAmethystKey = false;

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Fourth Key";
    private static final String AUTHOR = "dandylion1740";
    private static final String DESCRIPTION = "Adds a fourth key and increased difficulty for Act 4.";

    // =============== INPUT TEXTURE LOCATION =================

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "fourthKeyResources/images/Badge.png";

    // =============== MAKE IMAGE PATHS =================

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    // =============== /MAKE IMAGE PATHS/ =================


    // =============== SUBSCRIBE, INITIALIZE =================

    public ModInitializer(){
        logger.info("Subscribe to base mod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Adding mod settings");
        fourthKeyProperties.setProperty(DISABLE_ACT_4_DIFFICULTY, "FALSE");
        fourthKeyProperties.setProperty(DISABLE_AMETHYST_KEY, "FALSE");
        try {
            SpireConfig config = new SpireConfig("fourthKey", "fourthKeyConfig", fourthKeyProperties);
            config.load();
            disableAct4Difficulty = config.getBool(DISABLE_ACT_4_DIFFICULTY);
            disableAmethystKey = config.getBool(DISABLE_AMETHYST_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }

    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = ModInitializer.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO

    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH

    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = ModInitializer.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = ModInitializer.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    // ====== YOU CAN EDIT AGAIN ======

    //Used by @SpireInitializer
    public static void initialize(){
        logger.info("========================= Initializing Fourth Key Mod. =========================");
        //This creates an instance of our classes and gets our code going after BaseMod and ModTheSpire initialize.
        new ModInitializer();
        setModID("fourthKey");
        logger.info("========================= /Fourth Key Mod Initialized./ =========================");
    }

    // ============== /SUBSCRIBE, INITIALIZE/ =================


    // =============== POST-INITIALIZE =================

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Create the on/off button:
        ModLabeledToggleButton disableAct4DifficultyButton = new ModLabeledToggleButton("Disable Act 4 difficulty increases",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                disableAct4Difficulty,
                settingsPanel,
                (label) -> {},
                (button) -> {

            disableAct4Difficulty = button.enabled;
            try {
                SpireConfig config = new SpireConfig("fourthKey", "fourthKeyConfig", fourthKeyProperties);
                config.setBool(DISABLE_ACT_4_DIFFICULTY, disableAct4Difficulty);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ModLabeledToggleButton disableAmethystKeyButton = new ModLabeledToggleButton("Disable Amethyst Key",
                350.0f, 650.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                disableAmethystKey,
                settingsPanel,
                (label) -> {},
                (button) -> {

            disableAmethystKey = button.enabled;
            try {
                SpireConfig config = new SpireConfig("fourthKey", "fourthKeyConfig", fourthKeyProperties);
                config.setBool(DISABLE_AMETHYST_KEY, disableAmethystKey);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        settingsPanel.addUIElement(disableAct4DifficultyButton);
        settingsPanel.addUIElement(disableAmethystKeyButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

        // =============== EVENTS =================
        logger.info("Adding events");
        BaseMod.addEvent(new AddEventParams.Builder(Sacrifice.ID, Sacrifice.class)
            .eventType(EventUtils.EventType.NORMAL)
            .bonusCondition(new Condition() {
                @Override
                public boolean test() {
                    return !disableAmethystKey
                        && AbstractDungeon.player != null
                        && !AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player);
                }
            })
            .create());
        // =============== /EVENTS/ =================

        // =============== TEXTURES =================
        logger.info("Adding textures");

        ObtainKeyEffectPatch.amethystKey = TextureLoader.getTexture(makeUIPath("topPanel/purpleKey.png"));
        ObtainKeyEffectPatch.sapphireKey = TextureLoader.getTexture(makeUIPath("topPanel/blueKey.png"));
        ObtainKeyEffectPatch.rubyKey = TextureLoader.getTexture(makeUIPath("topPanel/redKey.png"));
        ObtainKeyEffectPatch.emeraldKey = TextureLoader.getTexture(makeUIPath("topPanel/greenKey.png"));

        ShopScreenPatch.amethystKey = TextureLoader.getTexture(makeUIPath("amethystKey.png"));
        ShopScreenPatch.keyY = (800.0F - ShopScreenPatch.amethystKey.getHeight()) * Settings.yScale;
        ShopScreenPatch.keyHitbox = new Hitbox(ShopScreenPatch.keyX, ShopScreenPatch.keyY - ShopScreenPatch.amethystKey.getHeight() * Settings.scale, ShopScreenPatch.amethystKey.getWidth(), ShopScreenPatch.amethystKey.getHeight());

        TopPanelPatch.rubyKey = TextureLoader.getTexture(makeUIPath("topPanel/redKey.png"));
        TopPanelPatch.sapphireKey = TextureLoader.getTexture(makeUIPath("topPanel/blueKey.png"));
        TopPanelPatch.emeraldKey = TextureLoader.getTexture(makeUIPath("topPanel/greenKey.png"));
        TopPanelPatch.amethystKey = TextureLoader.getTexture(makeUIPath("topPanel/purpleKey.png"));
        TopPanelPatch.keySlots = TextureLoader.getTexture(makeUIPath("topPanel/keySlots.png"));
        // =============== /TEXTURES/ =================

        // =============== COMMANDS =================
        logger.info("Adding commands");
        ConsoleCommand.addCommand("purpleKey", PurpleKey.class);
        // =============== /COMMANDS/ =================

        // =============== SAVE FIELDS =================
        logger.info("Adding save fields");
        BaseMod.addSaveField("purpleKey", new CustomSavableRaw() {

            @Override
            public void onLoadRaw(JsonElement json) {
                if (AbstractDungeon.player != null) {
                    logger.info(json);
                    if (json == null) {
                        AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, false);
                    } else {
                        JsonElement key = json.getAsJsonObject().get("purpleKey");
                        AbstractPlayerPatch.hasAmethystKey.set(AbstractDungeon.player, key == null ? false : key.getAsBoolean());
                    }
                }
            }

            @Override
            public JsonElement onSaveRaw() {
                JsonParser parser = new JsonParser();
                return parser.parse(
                    "{\"purpleKey\":\""
                    + (AbstractDungeon.player != null ? AbstractPlayerPatch.hasAmethystKey.get(AbstractDungeon.player) : false)
                    + "\"}"
                );
            }
        });
        // =============== /SAVE FIELDS/ =================

    }

    // =============== /POST-INITIALIZE/ =================


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        pathCheck();

        BaseMod.loadCustomStringsFile(EventStrings.class,
            getModID() + "Resources/localization/eng/fourthKey-Event-Strings.json");

        logger.info("Done editing strings");
    }

    // ================ /LOAD THE TEXT/ ===================

    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
