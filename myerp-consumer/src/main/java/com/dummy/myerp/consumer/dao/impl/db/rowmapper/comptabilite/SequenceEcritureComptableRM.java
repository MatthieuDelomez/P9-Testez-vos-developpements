
package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/*
Classe test d'intégration qui va Mapper la SeqenceEcritureComptable
*/
public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {
    
    @Override
    public SequenceEcritureComptable mapRow(ResultSet resultSet, int pRowNum) throws SQLException {
        
        SequenceEcritureComptable bean = new SequenceEcritureComptable();
        
        bean.setAnnee(resultSet.getInt("annee"));
        bean.setDerniereValeur(resultSet.getInt("derniere_valeur"));
        
        return bean;
    }
    
}
