import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


public class Consumidor {

    private static final String TASK_QUEUE_NAME = "PDist";

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("Admin123XX_");

        final Connection connection = connectionFactory.newConnection();
        final Channel canal = connection.createChannel();

        canal.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println("[*] Aguardando mensagens. Para sair, pressione CTRL + C");

        canal.basicQos(1);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody(), "UTF-8");

            System.out.println("[x] Recebido '" + mensagem + "'");
            try {
                doWork(mensagem);
            } finally {
                System.out.println("[x] Done");
                canal.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        canal.basicConsume(TASK_QUEUE_NAME, false, callback, consumerTag -> {
        });

    };

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
};


