package TRONmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;

import static TRONmod.TRONMod.makeRelicOutlinePath;
import static TRONmod.TRONMod.makeRelicPath;

public class BasisDisk extends CustomRelic {

    public static final String ID = TRONMod.makeID("BasisDisk");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StartDisk_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StartDisk_relic.png"));

    public BasisDisk() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {}

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
