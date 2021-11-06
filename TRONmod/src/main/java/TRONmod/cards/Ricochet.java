package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.RicochetAction;
import TRONmod.actions.ThrowAction;
import TRONmod.characters.TheANON;
import TRONmod.powers.ParkourPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class Ricochet extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(Ricochet.class.getSimpleName());
    public static final String IMG = makeCardPath("AcrobaticThrow.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = -1;

    public Ricochet() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RicochetAction(p, this.upgraded, this.freeToPlayOnce, this.energyOnUse));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}