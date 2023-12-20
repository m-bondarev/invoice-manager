package com.oci.invoicemanager.service;

import com.oci.invoicemanager.data.InvoiceEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class InvoiceDAO extends JdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        setDataSource(dataSource);
        System.out.println("Datasource used: " + dataSource);
    }

    public List<InvoiceEntity> getAllInvoices() {
        final String sql = "SELECT id, userId, description, status FROM invoices";
        return getJdbcTemplate().query(sql,
                                       (rs, rowNum) -> new InvoiceEntity(rs.getLong("id"),
                                                                         rs.getLong("userId"),
                                                                         rs.getString("description"),
                                                                         rs.getString("status")
                                       ));


    }

}
