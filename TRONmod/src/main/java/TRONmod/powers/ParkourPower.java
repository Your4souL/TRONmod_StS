package TRONmod.powers;

import TRONmod.TRONMod;
import TRONmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static TRONmod.TRONMod.makePowerPath;

public class ParkourPower extends AbstractPower {

    public static final String POWER_ID = TRONMod.makeID("ParkourPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("parkour_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("parkour_power32.png"));

    private AbstractCreature source;

    public ParkourPower(AbstractCreature owner, AbstractCreature source, int parkourAmt) {

        name = NAME;
        ID = "Parkour";

        this.owner = owner;
        this.source = source;
        this.amount = parkourAmt;
        if (this.amount >= 999)
            this.amount = 999;
        updateDescription();
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_DEXTERITY", 0.05F);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        addToBot(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), this.amount));
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (!m.isDead && !m.isDying) {
                if (!m.hasPower("DistractionPower")) {
                    addToBot(new ApplyPowerAction(m, this.owner, new NextTurnBlockPower(m, this.amount), this.amount));
                } else {
                    if (m.getPower("DistractionPower").amount > this.amount) {
                        addToBot(new ReducePowerAction(m, this.owner, m.getPower("DistractionPower"), this.amount));
                    } else {
                        int blockedBlock = m.getPower("DistractionPower").amount;
                        addToBot(new RemoveSpecificPowerAction(m, m, "DistractionPower"));
                        addToBot(new ApplyPowerAction(m, this.owner, new NextTurnBlockPower(m, this.amount - blockedBlock), this.amount - blockedBlock));
                    }
                }
            }
        }
    }

    @Override
    public void onInitialApplication() {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new NextTurnBlockPower(this.owner, this.amount), this.amount));
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (!m.isDead && !m.isDying) {
                if (!m.hasPower("DistractionPower")) {
                    addToBot(new ApplyPowerAction(m, this.owner, new NextTurnBlockPower(m, this.amount), this.amount));
                } else {
                    if (m.getPower("DistractionPower").amount > this.amount) {
                        addToBot(new ReducePowerAction(m, this.owner, m.getPower("DistractionPower"), this.amount));
                    } else {
                        int blockedBlock = m.getPower("DistractionPower").amount;
                        addToBot(new RemoveSpecificPowerAction(m, m, "DistractionPower"));
                        addToBot(new ApplyPowerAction(m, this.owner, new NextTurnBlockPower(m, this.amount - blockedBlock), this.amount - blockedBlock));
                    }
                }
            }
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (this.amount > 1) {
                addToBot(new ReducePowerAction(this.owner, this.owner, "Parkour", Math.round(this.amount * 0.5F)));
            } else {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Parkour"));
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}