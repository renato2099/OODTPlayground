package com.celgene.kafka;

import org.apache.oodt.cas.metadata.Metadata;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

/**
 * Factory to create different types of metadata
 */
public class MetadataFactory {

    public enum MetadataType {
        HASHTABLE("hashtable"), LIST("list"), SIMPLE("simple"), COMPLEX("complex");

        private String val;

        MetadataType(String val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return this.val;
        }
    }

    public static Metadata buildMetadata(int type) {
        Metadata md = null;
        switch(type) {
            case 0:
                md = createHashTableMd();
                break;
            case 1:
                md = createListMd();
                break;
            case 2:
                md = createSimpleMd();
                break;
            case 3:
                md = createComplexMd();
                break;
            default:
                throw new IllegalArgumentException("Metadata type not supported!");
        }
        return md;
    }

    public static Metadata buildMetadata(MetadataType type) {
        Metadata md = null;
        switch(type) {
            case HASHTABLE:
                md = createHashTableMd();
                break;
            case LIST:
                md = createListMd();
                break;
            case SIMPLE:
                md = createSimpleMd();
                break;
            case COMPLEX:
                md = createComplexMd();
                break;
            default:
                throw new IllegalArgumentException("Metadata type not supported!");
        }
        return md;
    }

    private static Metadata createComplexMd() {
        Metadata m1 = new Metadata();
        m1.addMetadata("Group1/key1", "val1");
        m1.addMetadata("Group1/key2", "val2");

        m1.addMetadata("Group2/key2", "val3");
        m1.addMetadata("Group2/key2/key3", "val3");

        m1.addMetadata("Group1/sub1/key2", "val3");
        m1.addMetadata("Group1/sub2/key2/key3", "val3");
//        List<String> keys = m1.getAllKeysWithName("key2");
//        assertEquals(keys.size(), 3);
//        assertEquals(keys.get(0), "Group2/key2");
//        assertEquals(keys.get(1), "Group1/sub1/key2");
//        assertEquals(keys.get(2), "Group1/key2");
//        keys = m1.getAllKeysWithName("key1");
//        assertEquals(keys.size(), 1);
//        assertEquals(keys.get(0), "Group1/key1");
        return m1;
    }

    private static Metadata createSimpleMd() {
        Metadata m1 = new Metadata();
        m1.addMetadata("key1", "val1");
        m1.addMetadata("key2", "val2");
        m1.addMetadata("key2", "val3");
        return m1;
    }

    private static Metadata createListMd() {
        Metadata m1 = new Metadata();
        List counting = new Vector();
        counting.add("1");
        counting.add("2");
        counting.add("3");
        m1.addMetadata("ManyTest", counting);
        return m1;
    }

    private static Metadata createHashTableMd() {
        Hashtable testHash = new Hashtable();
        testHash.put("key1", "val1");
        testHash.put("key2", "val2");

        Metadata m1 = new Metadata();
        m1.addMetadata("key3", "val3");
        m1.addMetadata(testHash);
        return m1;
    }
}
