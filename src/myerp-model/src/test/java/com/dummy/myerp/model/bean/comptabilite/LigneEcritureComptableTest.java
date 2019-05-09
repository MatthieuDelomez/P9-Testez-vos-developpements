
package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;


public class LigneEcritureComptableTest {
    

    @Test
    public void ligneEcriture() {
        
        LigneEcritureComptable vLine = new LigneEcritureComptable();
        
        vLine.setCompteComptable(null);
        
        vLine.setLibelle("libelle");
        
        vLine.setCredit(BigDecimal.valueOf(10));
        
        vLine.setDebit(null);

        
        Assert.assertEquals(vLine.toString(),
                
                "LigneEcritureComptable" +
                        
                "{compteComptable=null, libelle='libelle', debit=null, credit=10}");
    }
}