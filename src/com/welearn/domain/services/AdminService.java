/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.welearn.domain.services;

import com.welearn.domain.entities.Actor;
import com.welearn.forms.DVDStoreAdmin;
import com.welearn.utils.HibernateUtil;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alexander
 */
public class AdminService {

    /**
     * @return the queryResult
     */
    public DefaultTableModel getQueryResult() {
        return queryResult;
    }

    /**
     * @param queryResult the queryResult to set
     */
    public void setQueryResult(DefaultTableModel queryResult) {
        this.queryResult = queryResult;
    }

    private static String QUERY_BASED_ON_FIRST_NAME = "from Actor a where a.firstName like '";
    private static String QUERY_BASED_ON_LAST_NAME = "from Actor a where a.lastName like '";
    private DefaultTableModel queryResult;


    private DefaultTableModel displayResult(List resultList) {
        Vector<String> tableHeaders = new Vector<String>();
        Vector tableData = new Vector();
        tableHeaders.add("ActorId");
        tableHeaders.add("FirstName");
        tableHeaders.add("LastName");
        tableHeaders.add("LastUpdated");

        for (Object o : resultList) {
            Actor actor = (Actor) o;
            Vector<Object> oneRow = new Vector<Object>();
            oneRow.add(actor.getActorId());
            oneRow.add(actor.getFirstName());
            oneRow.add(actor.getLastName());
            oneRow.add(actor.getLastUpdate());
            tableData.add(oneRow);
        }
        // resultTable.setModel(new DefaultTableModel(tableData, tableHeaders));
        return new DefaultTableModel(tableData, tableHeaders);
    }

    private void executeHQLQuery(String hql) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery(hql);
            List resultList = q.list();
            setQueryResult(displayResult(resultList));
            session.getTransaction().commit();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
    }
    
    public DefaultTableModel getActorByFirstName(String _firstname)
    {
        executeHQLQuery(QUERY_BASED_ON_FIRST_NAME +_firstname+"%'");
        return getQueryResult();
    }
    
    public DefaultTableModel getActorByLastName(String _lastname)
    {
        executeHQLQuery(QUERY_BASED_ON_LAST_NAME +_lastname+"%'");
        return getQueryResult();
    }

}
