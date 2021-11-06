package TRONmod.patches;

import TRONmod.TRONMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

@SpirePatch(clz = com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction.class, method = "update")
public class CardCreationInDrawPilePatch {

    @SpireInsertPatch(rloc = 2)
    public static SpireReturn<Void> Insert() {

        if (TRONMod.StatusesToExhaust > 0) {
            
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
