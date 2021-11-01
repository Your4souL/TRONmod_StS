package TRONmod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;

public class AbraxasCodePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = TRONMod.makeID("AbraxasCodePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("TRONmodResources/images/powers/abraxas_code_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("TRONmodResources/images/powers/abraxas_code_power32.png");

    public AbraxasCodePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {

        int HealAmt = 1;

        if (this.owner.hasPower("BugPower")) {
            HealAmt = this.owner.getPower("BugPower").amount;
        }

        addToBot(new HealAction(this.owner, this.owner, HealAmt));
        this.owner.decreaseMaxHealth(5);

        this.amount--;
        if (this.amount == 0) addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "AbraxasCodePower"));
    }


    @Override
    public void updateDescription() {

        int Bug;

        if (this.owner.hasPower("BugPower")) {
            Bug = this.owner.getPower("BugPower").amount;
        } else Bug = 1;

        if (amount == 1) {
            description = DESCRIPTIONS[0] + Bug + DESCRIPTIONS[3];
        } else if (amount > 1) {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + Bug + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new AbraxasCodePower(owner, source, amount);
    }
}
