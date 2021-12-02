package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.ThrowAction;
import TRONmod.characters.TheANON;
import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class SwoopingThrow extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(SwoopingThrow.class.getSimpleName());
    public static final String IMG = makeCardPath("SwoopingThrow.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 0;
    private static final int MAGIC = 5;

    public SwoopingThrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
        exhaust = true;
        isInnate = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new ParkourPower(p, p, this.magicNumber), this.magicNumber));
        addToBot(new ThrowAction(p, p, 1, 0, false));
        if (upgraded) addToBot(new DrawCardAction(p, 1));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}