/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oodt.cas.filemgr.catalog;

//JDK imports
import java.util.logging.Logger;

//OODT imports
import org.apache.oodt.cas.filemgr.util.GenericFileManagerObjectFactory;

/**
 * @author starchmd
 * @version $Revision$
 *
 * <p>
 * A Factory for creating {@link Catalog} with a spacer (intercept) layer.
 * </p>
 *
 */
public abstract class SpacerCatalogFactory implements CatalogFactory {

    /** Catalog factory property */
    private String catalogFactory;

    /* our log stream */
    private static final Logger LOG = Logger.getLogger(SpacerCatalogFactory.class.getName());

    /** Default constructor */
    public SpacerCatalogFactory() throws IllegalArgumentException {
        this.catalogFactory = System.getProperty("org.apache.oodt.cas.filemgr.catalog.spacer.subfactory");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.oodt.cas.filemgr.catalog.CatalogFactory#createCatalog()
     */
    public Catalog createCatalog() {
        Catalog subordinate = GenericFileManagerObjectFactory.getCatalogServiceFromFactory(this.catalogFactory);
        return getWrapper(subordinate);
    }

    /**
     * Wrapper to create
     * @param sub
     * @return
     */
    protected abstract Catalog getWrapper(Catalog sub);
}