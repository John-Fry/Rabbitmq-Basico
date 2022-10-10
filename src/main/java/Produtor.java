import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {

    private static final String TASK_QUEUE_NAME = "PDist";

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");

        try (Connection connection = connectionFactory.newConnection();
             Channel canal = connection.createChannel()){
            canal.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String mensagem = "Olá, John Ewerton Marques Meireles";

            canal.basicPublish ("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    mensagem.getBytes("UTF-8"));
            System.out.println ("[x] Enviado '" + mensagem + "'");


        }
    }
}


