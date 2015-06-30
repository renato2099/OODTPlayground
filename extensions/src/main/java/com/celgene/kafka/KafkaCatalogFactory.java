package com.celgene.kafka;

import org.apache.oodt.cas.filemgr.catalog.Catalog;
import org.apache.oodt.cas.filemgr.catalog.SpacerCatalogFactory;

import java.util.Properties;

public class KafkaCatalogFactory extends SpacerCatalogFactory {

    @Override
    protected  Catalog getWrapper(Catalog sub) {
        Properties properties = new Properties();
        properties.put("metadata.broker.list", System.getProperty("org.apache.oodt.cas.filemgr.catalog.kafka.url"));
        properties.put("serializer.class", System.getProperty("org.apache.oodt.cas.filemgr.catalog.kafka.serializer"));
        properties.put("topic-name", System.getProperty("org.apache.oodt.cas.filemgr.catalog.kafka.topic"));
        return new SampleKafkaCatalog(sub, properties);
    }
}
