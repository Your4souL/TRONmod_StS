package TRONmod.powers;

import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static TRONmod.TRONMod.makePowerPath;

public class DistractionPower extends AbstractPower {

    public static final String POWER_ID = TRONMod.makeID("DistractionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("distraction_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("distraction_power32.png"));

    private AbstractCreature source;

    public DistractionPower(AbstractCreature owner, AbstractCreature source, int distractionAmt) {

        name = NAME;
        ID = "DistractionPower";

        this.owner = owner;
        this.source = source;
        this.amount = distractionAmt;
        if (this.amount >= 999)
            this.amount = 999;
        updateDescription();
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    @Override
    public void onInitialApplication() {
        if (!this.owner.isDead && !this.owner.isDying) {
            if (this.owner.hasPower("Next Turn Block")) {
                int bAmount = this.owner.getPower("Next Turn Block").amount;
                if (this.amount >= bAmount) {
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Next Turn Block"));
                    addToBot(new ReducePowerAction(this.owner, this.owner, this.owner.getPower("DistractionPower"), bAmount));
                } else {
                    addToBot(new ReducePowerAction(this.owner, this.owner, this.owner.getPower("Next Turn Block"), this.amount));
                    addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "DistractionPower"));
                }
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}