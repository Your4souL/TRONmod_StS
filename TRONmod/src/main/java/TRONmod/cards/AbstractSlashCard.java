package TRONmod.cards;

import TRONmod.util.CustomTags;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractSlashCard extends AbstractDefaultCard {

    public AbstractSlashCard(final String id, final String img, final int cost, final CardType type, final CardColor color, final CardRarity rarity, final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);
        this.cantUseMessage = languagePack.getCardStrings(id).EXTENDED_DESCRIPTION[0];
        this.tags.add(CustomTags.SLASH);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;
        canUse = false;

        for (AbstractCard c : p.hand.group) if (c.hasTag(CustomTags.DISK)) canUse = true;

        return canUse;
    }
}
