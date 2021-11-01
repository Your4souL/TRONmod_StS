package TRONmod.cards;

import TRONmod.actions.DoubleDecayAction;
import TRONmod.powers.BugPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.badlogic.gdx.graphics.Color;
import TRONmod.TRONMod;
import TRONmod.characters.TheANON;

import static TRONmod.TRONMod.makeCardPath;

public class InfectedIncision extends AbstractDynamicCard {

    public static final String ID = TRONMod.makeID(InfectedIncision.class.getSimpleName());
    public static final String IMG = makeCardPath("InfectedIncision.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheANON.Enums.COLOR_CYAN;

    private static final int COST = 3;

    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final int CONTAGION_AMT = 1;
    private static final int CONTAGION_PLUS_AMT = 1;

    public InfectedIncision() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = CONTAGION_AMT;
        baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.OLIVE, Color.WHITE), 0.1F));
        }
        addToBot(new ApplyPowerAction(m, p, new BugPower(m, p, this.magicNumber), this.magicNumber));
        addToBot(new DoubleDecayAction(m, p));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(CONTAGION_PLUS_AMT);
        }
    }
}