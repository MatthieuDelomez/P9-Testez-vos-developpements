<<<<<<< HEAD
=======

>>>>>>> 8b22d1891cdd2999c4100e0063da5c2c3b26db45
package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


public class JournalTest {

    


    @Test
    public void getCodeTest() {

        
        //Nous allons créer des journaux comptables pour le test
        JournalComptable banque = new JournalComptable("bq", "banque");

        JournalComptable achat = new JournalComptable("ach", "achat");

        JournalComptable vente = new JournalComptable("vte", "vente");

        

        //Journal que nous allonst test
        JournalComptable journalTest = null;

        

        //Nous allons ajouter les journaux à une liste
        List<JournalComptable> testList = new ArrayList<JournalComptable>();
        
        testList.addAll(Arrays.asList(banque, achat, vente));

        
        

        /*
        Méthode de test
        */
        journalTest = JournalComptable.getByCode(testList, "bq");
        

        Assert.assertTrue(journalTest.toString(),journalTest.getCode() =="bq" && journalTest.getLibelle().equals("banque"));



        

    }
<<<<<<< HEAD:myerp-model/src/test/java/com/dummy/myerp/model/bean/comptabilite/JournalTest.java
}
=======
    
<<<<<<< HEAD
}
=======
}
>>>>>>> 8b22d1891cdd2999c4100e0063da5c2c3b26db45
>>>>>>> 695dacd54f1be4e1d9152e8e15ea9567c5c24ded:src/myerp-model/src/test/java/com/dummy/myerp/model/bean/comptabilite/JournalTest.java
