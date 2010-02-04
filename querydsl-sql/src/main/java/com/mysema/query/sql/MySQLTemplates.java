/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.sql;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.mysema.query.types.operation.Ops;

/**
 * MySQLTemplates is an SQL dialect for MySQL
 * 
 * @author tiwe
 *
 */
public class MySQLTemplates extends SQLTemplates {
    {
        addClass2TypeMappings("signed", 
                Byte.class, 
                Integer.class,
                Long.class, 
                Short.class, 
                BigInteger.class);
        addClass2TypeMappings("decimal", 
                Double.class, 
                Float.class,
                BigDecimal.class);
        addClass2TypeMappings("char(256)", String.class);
        
        add(Ops.CONCAT, "concat({0}, {1})");
        add(Ops.MATCHES, "{0} regexp {1}");
        add(Ops.DateTimeOps.YEAR_MONTH, "extract(year_month from {0})");
    }
}