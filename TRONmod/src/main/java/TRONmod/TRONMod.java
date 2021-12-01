package TRONmod;

import TRONmod.cards.AbstractDefaultCard;
import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import TRONmod.characters.TheANON;
import TRONmod.events.IdentityCrisisEvent;
import TRONmod.potions.PlaceholderPotion;
import TRONmod.relics.BottledPlaceholderRelic;
import TRONmod.relics.DefaultClickableRelic;
import TRONmod.relics.BasisDisk;
import TRONmod.relics.PlaceholderRelic2;
import TRONmod.util.IDCheckDontTouchPls;
import TRONmod.util.TextureLoader;
import TRONmod.variables.DefaultCustomVariable;
import TRONmod.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class TRONMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(TRONMod.class.getName());
    private static String modID;

    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;

    private static final String MODNAME = "TRON Mod";
    private static final String AUTHOR = "Your4souL";
    private static final String DESCRIPTION = "Restore System stability as Monitor ANON.";

    public static final Color ANON_CYAN = CardHelper.getColor(35.0f, 156.0f, 156.0f);

    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown

    private static final String ATTACK_DEFAULT_GRAY = "TRONmodResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "TRONmodResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "TRONmodResources/images/512/bg_power_default_gray.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "TRONmodResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "TRONmodResources/images/512/card_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "TRONmodResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "TRONmodResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "TRONmodResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "TRONmodResources/images/1024/card_default_gray_orb.png";

    private static final String THE_DEFAULT_BUTTON = "TRONmodResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "TRONmodResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "TRONmodResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "TRONmodResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "TRONmodResources/images/char/defaultCharacter/corpse.png";

    public static final String BADGE_IMAGE = "TRONmodResources/images/Badge.png";

    public static int StatusesToExhaust;

    //public static final String THE_DEFAULT_SKELETON_ATLAS = "TRONmodResources/images/char/defaultCharacter/skeleton.atlas";
    //public static final String THE_DEFAULT_SKELETON_JSON = "TRONmodResources/images/char/defaultCharacter/skeleton.json";

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public TRONMod() {
        logger.info("Subscribe to BaseMod hooks");

        BaseMod.subscribe(this);

        setModID("TRONmod");

        logger.info("Done subscribing");

        logger.info("Creating the color " + TheANON.Enums.COLOR_CYAN.toString());

        BaseMod.addColor(TheANON.Enums.COLOR_CYAN, ANON_CYAN, ANON_CYAN, ANON_CYAN,
                ANON_CYAN, ANON_CYAN, ANON_CYAN, ANON_CYAN,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        logger.info("Done creating the color");


        logger.info("Adding mod settings");
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); //
        try {
            SpireConfig config = new SpireConfig("TRONMod", "TRONConfig", theDefaultDefaultSettings);
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");

    }

    public static void setModID(String ID) {
        Gson coolG = new Gson();
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TRONMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }

    public static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8));
        InputStream in = TRONMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = TRONMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }

    public static void initialize() {
        logger.info("========================= Initializing TRON Mod. Hi. =========================");
        TRONMod tronmod = new TRONMod();
        logger.info("========================= /TRON Mod Initialized. Hello World./ =========================");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheANON.Enums.ANON.toString());

        BaseMod.addCharacter(new TheANON("ANON", TheANON.Enums.ANON), THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheANON.Enums.ANON);

        receiveEditPotions();
        logger.info("Added " + TheANON.Enums.ANON.toString());
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enablePlaceholder,
                settingsPanel,
                (label) -> {},
                (button) -> {

                    enablePlaceholder = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("TRONMod", "TRONConfig", theDefaultDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        settingsPanel.addUIElement(enableNormalsButton);

        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class)
                .dungeonID(TheCity.ID)
                .playerClass(TheANON.Enums.ANON)
                .create();

        BaseMod.addEvent(eventParams);

        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheANON.Enums.ANON);

        logger.info("Done editing potions");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelicToCustomPool(new BasisDisk(), TheANON.Enums.COLOR_CYAN);
        BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheANON.Enums.COLOR_CYAN);
        BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheANON.Enums.COLOR_CYAN);

        BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);

        UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        logger.info("Add variables");
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());

        logger.info("Adding cards");

        try {
            new AutoAdd("TRONmodArtifact").packageFilter(AbstractDefaultCard.class).setDefaultSeen(true).cards();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Done adding cards!");
    }

    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        BaseMod.loadCustomStringsFile(CardStrings.class,getModID() + "Resources/localization/eng/TRONMod-Card-Strings.json");

        BaseMod.loadCustomStringsFile(PowerStrings.class,getModID() + "Resources/localization/eng/TRONMod-Power-Strings.json");

        BaseMod.loadCustomStringsFile(RelicStrings.class,getModID() + "Resources/localization/eng/TRONMod-Relic-Strings.json");

        BaseMod.loadCustomStringsFile(EventStrings.class,getModID() + "Resources/localization/eng/TRONMod-Event-Strings.json");

        BaseMod.loadCustomStringsFile(PotionStrings.class,getModID() + "Resources/localization/eng/TRONMod-Potion-Strings.json");

        BaseMod.loadCustomStringsFile(CharacterStrings.class,getModID() + "Resources/localization/eng/TRONMod-Character-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,getModID() + "Resources/localization/eng/TRONMod-UI-Strings.json");

        logger.info("Done edittting strings");
    }


    @Override
    public void receiveEditKeywords() {

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/TRONMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
