/*
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.consistency.checking.incremental;

import org.neo4j.consistency.checking.InconsistentStoreException;
import org.neo4j.consistency.checking.full.ConsistencyCheckIncompleteException;
import org.neo4j.consistency.report.ConsistencySummaryStatistics;
import org.neo4j.consistency.store.DiffStore;
import org.neo4j.logging.Log;
import org.neo4j.logging.LogProvider;

import java.io.File;

public abstract class DiffCheck
{
    final LogProvider logProvider;
    final Log log;

    public DiffCheck( LogProvider logProvider )
    {
        this.logProvider = logProvider;
        this.log = logProvider.getLog( getClass() );
    }

    public final void check( File storeDir, DiffStore diffs ) throws InconsistentStoreException, ConsistencyCheckIncompleteException
    {
        verify( diffs, execute( storeDir, diffs ) );
    }

    public abstract ConsistencySummaryStatistics execute( File storeDir, DiffStore diffs )
            throws ConsistencyCheckIncompleteException;

    protected void verify( DiffStore diffs, ConsistencySummaryStatistics summary )
            throws InconsistentStoreException
    {
        if ( !summary.isConsistent() )
        {
            throw new InconsistentStoreException( summary );
        }
    }
}
