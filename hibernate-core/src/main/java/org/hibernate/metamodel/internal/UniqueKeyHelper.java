/* 
 * Hibernate, Relational Persistence for Idiomatic Java
 * 
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.metamodel.internal;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.internal.util.StringHelper;
import org.hibernate.metamodel.spi.relational.Column;
import org.hibernate.metamodel.spi.relational.TableSpecification;
import org.hibernate.metamodel.spi.relational.UniqueKey;

/**
 * @author Brett Meyer
 */
public class UniqueKeyHelper {

	private Map<TableSpecification, UniqueKey> naturalIdUniqueKeys
			= new HashMap<TableSpecification, UniqueKey>();

	/**
	 * Natural ID columns must reside in one single UniqueKey within the Table.
	 * To prevent separate UniqueKeys from being created, this keeps track of
	 * them in a HashMap.
	 * 
	 * @param table
	 * @param column
	 */
	public void addUniqueConstraintForNaturalIdColumn(
			final TableSpecification table, final Column column) {
		UniqueKey uniqueKey;
		if ( naturalIdUniqueKeys.containsKey( table ) ) {
			uniqueKey = naturalIdUniqueKeys.get( table );
		}
		else {
			uniqueKey = table.getOrCreateUniqueKey(
					StringHelper.randomFixedLengthHex( UniqueKey.GENERATED_NAME_PREFIX ) );
			naturalIdUniqueKeys.put( table, uniqueKey );
		}
		uniqueKey.addColumn( column );
	}
}