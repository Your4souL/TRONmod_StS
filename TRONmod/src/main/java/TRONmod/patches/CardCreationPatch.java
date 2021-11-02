package TRONmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class CardCreationPatch {

    @SpireInsertPatch(rloc = 127)
    public static SpireReturn<Void> Insert(AbstractPlayer instance) {

        if (instance.hasPower("AbraxasCode")) {
            instance.currentHealth = 0;
            instance.getPower("AbraxasCode").onSpecificTrigger();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
