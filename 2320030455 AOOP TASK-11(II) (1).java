import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class SharedBuffer {
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public void produce(String message) {
        try {
            queue.put(message); // Adds message to the queue
            System.out.println(Thread.currentThread().getName() + " produced: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer interrupted!");
        }
    }

    public void consume() {
        try {
            String message = queue.take(); // Removes and retrieves a message from the queue
            System.out.println(Thread.currentThread().getName() + " consumed: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer interrupted!");
        }
    }
}

class Producer implements Runnable {
    private final SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) { // Produces 5 messages
            String message = "Message " + i;
            buffer.produce(message);

            try {
                Thread.sleep(500); // Simulates delay in message production
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer thread interrupted!");
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedBuffer buffer;

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) { // Consumes 5 messages
            buffer.consume();

            try {
                Thread.sleep(700); // Simulates delay in message consumption
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer thread interrupted!");
            }
        }
    }
}

public class MessagingApplication {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();

        // Create Producer and Consumer threads
        Thread producerThread = new Thread(new Producer(buffer), "Producer");
        Thread consumerThread = new Thread(new Consumer(buffer), "Consumer");

        // Start threads
        producerThread.start();
        consumerThread.start();

        // Wait for threads to finish
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Messaging Application finished.");
    }
}
