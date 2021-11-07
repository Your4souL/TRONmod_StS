package TRONmod.cards;

import TRONmod.actions.DoubleNextTurnBlockAction;
import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import TRONmod.TRONMod;
import TRONmod.characters.TheANON;

import static TRONmod.TRONMod.makeCardPath;

public class FromWallToWall extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(FromWallToWall.class.getSimpleName());
    public static final String IMG = makeCardPath("FromWallToWall.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 2;
    private static final int MAGIC = 5;
    private static final int MAGIC_UPGRADE = 3;

    public FromWallToWall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ParkourPower(p, p, this.magicNumber), this.magicNumber));
        addToBot(new DoubleNextTurnBlockAction(p, p));
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