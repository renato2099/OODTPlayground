package com.celgene.kafka;

import kafka.producer.KeyedMessage;
import org.apache.oodt.cas.filemgr.catalog.Catalog;
import org.apache.oodt.cas.filemgr.catalog.SpacerCatalog;
import org.apache.oodt.cas.filemgr.structs.Product;
import org.apache.oodt.cas.filemgr.structs.exceptions.CatalogException;
import org.apache.oodt.cas.metadata.Metadata;

import java.util.Properties;

/**
 * Class extending SpaceCatalog to monitor changes from a specific catalog.
 */
public class SampleKafkaCatalog extends SpacerCatalog{

    SampleKafkaProducer kafkaProducer;

    /** Default Constructor */
    public SampleKafkaCatalog(Catalog catalog) {
        super(catalog);
        this.kafkaProducer = new SampleKafkaProducer();
    }

    /** Constructor */
    public SampleKafkaCatalog(Catalog catalog, Properties properties) {
        super(catalog);
        this.kafkaProducer = new SampleKafkaProducer(properties);
    }

    @Override
    public synchronized void addMetadata(Metadata m, Product product)
            throws CatalogException {
        super.addMetadata(m,product);
        this.kafkaProducer.sendKafka(new KeyedMessage<String, String>(SampleKafkaProducer.TOPIC, String.format("%s", SampleKafkaProducer.Metadata2Json(m))));
    }

}
