package TRONmod.cards;

import TRONmod.TRONMod;
import TRONmod.actions.SlashAction;
import TRONmod.characters.TheANON;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TRONmod.TRONMod.makeCardPath;

public class VerticalSlash extends AbstractSlashCard {

    public static final String ID = TRONMod.makeID(VerticalSlash.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("VerticalSlash.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int DRAW = 1;
    private static final int UPGRADE_DRAW = 1;

    public VerticalSlash() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = DRAW;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SlashAction(p, m, this, TARGET, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DrawCardAction(p, magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_DRAW);
            initializeDescription();
        }
    }
}
