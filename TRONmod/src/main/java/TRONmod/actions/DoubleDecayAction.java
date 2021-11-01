package TRONmod.actions;

import TRONmod.powers.BugPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class DoubleDecayAction extends AbstractGameAction {
    public DoubleDecayAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
        this.actionType = AbstractGameAction.ActionType.DEBUFF;
        this.attackEffect = AbstractGameAction.AttackEffect.FIRE;
    }

    public void update() {
        if (this.target != null && this.target.hasPower("Decay"))
            addToTop(new ApplyPowerAction(this.target, this.source, new BugPower(this.target, this.source, (this.target.getPower("Decay")).amount), (this.target.getPower("Decay")).amount));
        this.isDone = true;
    }
}