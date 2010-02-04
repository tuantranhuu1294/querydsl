/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.sql;

import java.sql.SQLException;
import java.util.List;

import com.mysema.query.types.OrderSpecifier;

/**
 * Union defines an interface for Union queries
 * 
 * @author tiwe
 *
 * @param <RT>
 */
public interface Union<RT> {
    
    /**
     * @param o
     * @return
     */
    Union<RT> orderBy(OrderSpecifier<?>... o);

    /**
     * @return
     * @throws SQLException
     */
    List<RT> list() throws SQLException;

}
