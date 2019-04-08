<<<<<<< HEAD
=======

>>>>>>> 8b22d1891cdd2999c4100e0063da5c2c3b26db45
package com.dummy.myerp.model.bean.comptabilite;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;


public class CompteComptableTest {

    

    @Test
    public void getNumeroTest() {

        

        //Création d'un compte pour pouvoir le tester
        CompteComptable banque = new CompteComptable(512, "banque");

        CompteComptable client = new CompteComptable(411, "client");

        CompteComptable fournisseur = new CompteComptable(401, "fournisseur");


        //Compte que nous allons tester 
        //que nous initialisons à null

        CompteComptable compteTest = null;

        

        //Nous ajoutons les comptes dans une liste
        List<CompteComptable> testList = new ArrayList<CompteComptable>();

        
        testList.addAll(Arrays.asList(banque, client, fournisseur));

        

        

        /*
        Méthode de test
        */

        compteTest = CompteComptable.getByNumero(testList, 512);

        Assert.assertTrue(compteTest.toString(), compteTest.getNumero() == 512 && compteTest.getLibelle().equals("banque"));

    }
<<<<<<< HEAD:myerp-model/src/test/java/com/dummy/myerp/model/bean/comptabilite/CompteComptableTest.java
}
=======
    
<<<<<<< HEAD
}
=======
}
>>>>>>> 8b22d1891cdd2999c4100e0063da5c2c3b26db45
>>>>>>> 695dacd54f1be4e1d9152e8e15ea9567c5c24ded:src/myerp-model/src/test/java/com/dummy/myerp/model/bean/comptabilite/CompteComptableTest.java
