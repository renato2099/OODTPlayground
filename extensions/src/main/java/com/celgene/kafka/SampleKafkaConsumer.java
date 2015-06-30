package com.celgene.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Sample Kafka consumer.
 */
public class SampleKafkaConsumer extends  Thread {
    final static String clientId = "SimpleConsumerDemoClient";
//    final static String TOPIC = "pythontest";
    ConsumerConnector consumerConnector;


    public static void main(String[] argv) throws UnsupportedEncodingException {
        SampleKafkaConsumer kConsumer = new SampleKafkaConsumer();

        try {
            kConsumer.start();
            kConsumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SampleKafkaConsumer(){
        Properties properties = new Properties();
        properties.put("zookeeper.connect","localhost:2181");
        properties.put("group.id","test-group");
        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
    }

    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(SampleKafkaProducer.TOPIC, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream =  consumerMap.get(SampleKafkaProducer.TOPIC).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();

        while(it.hasNext()) {
            System.out.println(new String(it.next().message()));
        }
    }

    private static void printMessages(ByteBufferMessageSet messageSet) throws UnsupportedEncodingException {
        for(MessageAndOffset messageAndOffset: messageSet) {
            ByteBuffer payload = messageAndOffset.message().payload();
            byte[] bytes = new byte[payload.limit()];
            payload.get(bytes);
            System.out.println(new String(bytes, "UTF-8"));
        }
    }
}
