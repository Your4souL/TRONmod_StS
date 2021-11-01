package TRONmod.relics;

import TRONmod.actions.DiskToHandAction;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;

import static TRONmod.TRONMod.makeRelicOutlinePath;
import static TRONmod.TRONMod.makeRelicPath;

public class StartDiskRelic extends CustomRelic {

    public static final String ID = TRONMod.makeID("StartDisk_relic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StartDisk_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StartDisk_relic.png"));

    public StartDiskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStartPreDraw() {}

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
