package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.ThrowAction;
import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TRONmod.characters.TheANON;

import static TRONmod.TRONMod.makeCardPath;

public class AcrobaticThrow extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(AcrobaticThrow.class.getSimpleName());
    public static final String IMG = makeCardPath("AcrobaticThrow.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int MAGIC_UPGRADE = 1;

    public AcrobaticThrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new ParkourPower(p, p, this.magicNumber), this.magicNumber));
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new ThrowAction(p, p, 1, 0, false));

    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(MAGIC_UPGRADE);
            initializeDescription();
        }
    }
}