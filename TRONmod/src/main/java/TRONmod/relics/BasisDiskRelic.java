package TRONmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

import static TRONmod.TRONMod.makeRelicOutlinePath;
import static TRONmod.TRONMod.makeRelicPath;

public class BasisDiskRelic extends CustomRelic {

    public static final String ID = TRONMod.makeID("BasisDisk");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("StartDisk_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("StartDisk_relic.png"));

    private int preventCount = 2;

    public BasisDiskRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        this.counter = preventCount;
    }

    @Override
    public void atBattleStartPreDraw() {
        this.counter = preventCount;
    }

    @Override
    public void onVictory() {
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            preventCount += 1;
        }
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + preventCount + this.DESCRIPTIONS[1];
    }

    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        super.updateDescription(c);
        description = DESCRIPTIONS[0] + preventCount + DESCRIPTIONS[1];
    }
}
