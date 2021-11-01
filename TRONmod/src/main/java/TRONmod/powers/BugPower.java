package TRONmod.powers;

import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TRONmod.TRONMod.makePowerPath;

public class BugPower extends AbstractPower {

    public static final String POWER_ID = TRONMod.makeID("BugPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("bug_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("bug_power32.png"));

    private AbstractCreature source;

    public BugPower(AbstractCreature owner, AbstractCreature source, int poisonAmt) {

        name = NAME;
        ID = "Bug";

        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        if (this.amount >= 9999)
            this.amount = 9999;
        updateDescription();
        this.type = AbstractPower.PowerType.DEBUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    public void onGainedBlock(float blockAmount) {
        if (blockAmount <= this.amount) {
            addToBot(new DamageAction(owner, new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS)));
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 2));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}