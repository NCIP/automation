// Place your Spring DSL code here
import org.apache.activemq.ActiveMQConnectionFactory

beans = {
    	connectionFactory(ActiveMQConnectionFactory) {
		brokerURL = "tcp://localhost:61616"
	}	
}