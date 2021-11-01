package TRONmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;

import static TRONmod.TRONMod.makeRelicOutlinePath;
import static TRONmod.TRONMod.makeRelicPath;

public class DefaultClickableRelic extends CustomRelic implements ClickableRelic {

    public static final String ID = TRONMod.makeID("DefaultClickableRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    private boolean usedThisTurn = false;
    private boolean isPlayerTurn = false;

    public DefaultClickableRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onRightClick() {
        if (!isObtained || usedThisTurn || !isPlayerTurn) {
            return;
        }
        
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            usedThisTurn = true;
            flash();
            stopPulse();

            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, DESCRIPTIONS[1], 4.0f, 2.0f));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(AbstractDungeon.getRandomMonster().hb.cX, AbstractDungeon.getRandomMonster().hb.cY), 2.0F));

            //AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
        }

    }
    
    @Override
    public void atPreBattle() {
        usedThisTurn = false;
        beginLongPulse();
    }

    public void atTurnStart() {
        usedThisTurn = false;
        isPlayerTurn = true;
        beginLongPulse();
    }
    
    @Override
    public void onPlayerEndTurn() {
        isPlayerTurn = false;
        stopPulse();
    }
    

    @Override
    public void onVictory() {
        stopPulse();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
