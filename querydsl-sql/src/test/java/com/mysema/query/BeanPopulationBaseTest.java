/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mysema.query.sql.dml.BeanMapper;
import com.mysema.query.sql.domain.Employee;
import com.mysema.query.sql.domain.QEmployee;

public abstract class BeanPopulationBaseTest extends AbstractBaseTest{

    private final QEmployee e = new QEmployee("e");

    @Test
    public void Insert_Update_Query_and_Delete(){
        // Insert
        Employee employee = new Employee();
        employee.setFirstname("John");
        Integer id = insert(e).populate(employee).executeWithKey(e.id);
        assertNotNull(id);
        employee.setId(id);

        // Update
        employee.setLastname("S");
        assertEquals(1l, update(e).populate(employee).where(e.id.eq(employee.getId())).execute());

        // Query
        Employee smith = query().from(e).where(e.lastname.eq("S")).limit(1).uniqueResult(e);
        assertEquals("John", smith.getFirstname());

        // Delete (no changes needed)
        assertEquals(1l, delete(e).where(e.id.eq(employee.getId())).execute());
    }

    @Test
    public void Populate_With_BeanMapper() {
        Employee employee = new Employee();
        employee.setFirstname("John");
        insert(e).populate(employee, new BeanMapper()).execute();        
    }

    @Test
    public void CustomProjection(){
        // Insert
        Employee employee = new Employee();
        employee.setFirstname("John");
        Integer id = insert(e).populate(employee).executeWithKey(e.id);
        employee.setId(id);

        // Update
        employee.setLastname("S");
        assertEquals(1l, update(e).populate(employee).where(e.id.eq(employee.getId())).execute());

        // Query
        Employee smith = extQuery().from(e).where(e.lastname.eq("S"))
            .limit(1)
            .uniqueResult(Employee.class, e.lastname, e.firstname);
        assertEquals("John", smith.getFirstname());
        assertEquals("S", smith.getLastname());

        // Query with alias
        smith = extQuery().from(e).where(e.lastname.eq("S"))
            .limit(1)
            .uniqueResult(Employee.class, e.lastname.as("lastname"), e.firstname.as("firstname"));
        assertEquals("John", smith.getFirstname());
        assertEquals("S", smith.getLastname());

        // Query into custom type
        OtherEmployee other = extQuery().from(e).where(e.lastname.eq("S"))
            .limit(1)
            .uniqueResult(OtherEmployee.class, e.lastname, e.firstname);
        assertEquals("John", other.getFirstname());
        assertEquals("S", other.getLastname());

        // Delete (no changes needed)
        assertEquals(1l, delete(e).where(e.id.eq(employee.getId())).execute());
    }


    protected ExtendedSQLQuery extQuery() {
        return new ExtendedSQLQuery(Connections.getConnection(), templates);
    }

}
