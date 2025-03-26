class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized deposit method
    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New Balance: " + balance);
    }

    // Synchronized withdraw method
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New Balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + ". Insufficient funds!");
        }
    }

    // Get current balance
    public synchronized double getBalance() {
        return balance;
    }
}

public class BankAccountManagementSystem {
    public static void main(String[] args) {
        BankAccount sharedAccount = new BankAccount(1000.0); // Initial balance

        // Create threads simulating different users
        Thread user1 = new Thread(() -> {
            sharedAccount.deposit(500);
            sharedAccount.withdraw(200);
        }, "User1");

        Thread user2 = new Thread(() -> {
            sharedAccount.withdraw(300);
            sharedAccount.deposit(700);
        }, "User2");

        Thread user3 = new Thread(() -> {
            sharedAccount.withdraw(1200);
            sharedAccount.deposit(100);
        }, "User3");

        // Start all threads
        user1.start();
        user2.start();
        user3.start();

        // Wait for threads to finish
        try {
            user1.join();
            user2.join();
            user3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print final account balance
        System.out.println("Final Balance: " + sharedAccount.getBalance());
    }
}
