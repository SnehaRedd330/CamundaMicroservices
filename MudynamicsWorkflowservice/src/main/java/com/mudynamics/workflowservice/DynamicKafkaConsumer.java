package com.mudynamics.workflowservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.mudynamics.workflowservice.controllers.KafkaTopicControllerImpl;
import com.mudynamics.workflowservice.models.TopicDetailsDTO;





@Component
public class DynamicKafkaConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DynamicKafkaConsumer.class);

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServer;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;
	
	@Value("${spring.kafka.consumer.key-deserializer}")
	private String keyDeserializerClass;
	
	@Value("${spring.kafka.consumer.value-deserializer}")
	private String valueDeserializerClass;
	

	
	@Autowired
	CamundaProcessInvoker camundaProcessInvoker;
	
	@Autowired
	private KafkaTopicControllerImpl kafkaTopicControllerImpl;
	
	private Map<String, KafkaMessageListenerContainer<Integer, String>> topicConsumerMap;

	KafkaMessageListenerContainer<Integer, String> container;

	public DynamicKafkaConsumer() {
		this.topicConsumerMap = new HashMap<String, KafkaMessageListenerContainer<Integer, String>>();
	}


	public boolean createDynamicConsumer(String topic) {
		LOGGER.info(" started createDynamicConsumer With Topic Name  ----->>>>  ");

		if (topic != null && !this.topicConsumerMap.containsKey(topic)) {
			LOGGER.info(" Starting Dynamic consumer for  Topic: " + topic);
			this.createConsumer(topic);

			return true;
		} else
			return false;
	}

	private void createConsumer(String topic) {
		LOGGER.info("consumer started for TOPIC: " + topic);

		ContainerProperties containerProps = new ContainerProperties(topic);

		containerProps.setMessageListener(new MessageListener<Integer, String>() {

			@Override
			public void onMessage(ConsumerRecord<Integer, String> data) {
				try {
					// invokes camunda process
					LOGGER.info("in message listener with data: " + data);
					camundaProcessInvoker.invokeProcess(topic, data);
				} catch (Exception exception) {

					LOGGER.info("Error occured in createConsumer " + exception);
				}

			}

		});

		KafkaMessageListenerContainer<Integer, String> container = createContainer(containerProps);
		container.setBeanName(topic);
		if (!container.isRunning()) {
			container.start();
			this.topicConsumerMap.put(topic, container);

		}

	}

	private KafkaMessageListenerContainer<Integer, String> createContainer(ContainerProperties containerProps) {
		String groupId = "WF_";
		groupId += containerProps.getTopics()[0];
		containerProps.setGroupId(groupId);
		LOGGER.info(" GroupId from containerProps after setting new GroupId in createContainer  -------->>>>>> "
				+ containerProps.getGroupId());
		Map<String, Object> props = consumerProps(groupId);
		DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<Integer, String>(props);
		this.container = new KafkaMessageListenerContainer<>(cf, containerProps);
		return container;
	}

	public Map<String, Object> consumerProps(String groupId) {
		Map<String, Object> propsMap = new HashMap<String, Object>();
		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);	
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	
		return propsMap;
	}

	@PreDestroy
	public void destroy() {
		LOGGER.debug("*********************In destroy method****************************");
		if (this.topicConsumerMap != null && !this.topicConsumerMap.isEmpty()) {
			for (Entry<String, KafkaMessageListenerContainer<Integer, String>> consumer : this.topicConsumerMap
					.entrySet()) {
				LOGGER.info("Inside Delete****************************");
				consumer.getValue().stop();
			}
			if (this.container != null)
				this.container.stop();
		}
	}

	@PostConstruct
	public void init() {
		LOGGER.info("********************In init method*************************");
		try {
			ResponseEntity<List<TopicDetailsDTO>> kafkaMapList = kafkaTopicControllerImpl.getKafkaTopics();
			if (!kafkaMapList.getBody().isEmpty()) {
				kafkaMapList.getBody().forEach(topic -> {

					this.createDynamicConsumer(topic.getTopicName());
				});
			}
		} catch (Exception exception) {
			LOGGER.error("Error occured in init while staring  kafka consumer from existing kafkaRedisMap "
					+ exception);
		}
		LOGGER.info(" Init process completed all the consumers for existing topic in DB has been started");
	}
	
	public void stopDynamicsConsumer(String topic) {
		if (this.topicConsumerMap != null && !this.topicConsumerMap.isEmpty()) {
			for (Entry<String, KafkaMessageListenerContainer<Integer, String>> consumer : this.topicConsumerMap
					.entrySet()) {
			if(topic.equals(consumer.getKey())) {
				LOGGER.info("stopping created consumer for Topic:"+topic);
				consumer.getValue().stop();
				this.topicConsumerMap.remove(topic);
			}
			}
		}
	}
}
