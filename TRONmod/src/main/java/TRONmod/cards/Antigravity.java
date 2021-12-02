package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.characters.TheANON;
import TRONmod.powers.AntigravityPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class Antigravity extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(Antigravity.class.getSimpleName());
    public static final String IMG = makeCardPath("AntiGravity.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 1;
    private static final int MAGIC = 1;

    public Antigravity() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
        this.cardsToPreview = new GravityAnchor();
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AntigravityPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}