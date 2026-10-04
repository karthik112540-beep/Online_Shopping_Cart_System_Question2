
import java.util.Scanner;

class InvalidProductException extends Exception
{
    public InvalidProductException(String msg)
    {
        super(msg);
    }
}

class InvalidQuantityException extends Exception
{
    public InvalidQuantityException(String msg)
    {
        super(msg);
    }
}

class StockException extends Exception
{
    public StockException(String msg)
    {
        super(msg);
    }
}

class PaymentException extends Exception
{
    public PaymentException(String msg)
    {
        super(msg);
    }
}

public class OnlineShoppingSystem
{
    Scanner sc = new Scanner(System.in);

    int[] productId = {101, 102, 103, 104};

    String[] productName = {
        "Laptop", "Mobile", "Headphones", "Keyboard"
    };

    double[] price = {50000, 20000, 2000, 1500};

    int[] stock = {5, 10, 8, 15};

    int[] cartId = new int[10];
    int[] cartQuantity = new int[10];

    int cartCount = 0;

    int findProduct(int id) throws InvalidProductException
    {
        for (int i = 0; i < productId.length; i++)
        {
            if (productId[i] == id)
            {
                return i;
            }
        }

        throw new InvalidProductException("Invalid Product ID!");
    }

    void displayProducts()
    {
        System.out.println("\n===== PRODUCTS =====");

        for (int i = 0; i < productId.length; i++)
        {
            System.out.println(
                "ID: " + productId[i] +
                " | Name: " + productName[i] +
                " | Price: " + price[i] +
                " | Stock: " + stock[i]
            );
        }
    }

    void addToCart(int id, int quantity)
        throws InvalidProductException,
               InvalidQuantityException,
               StockException
    {
        int index = findProduct(id);

        if (quantity <= 0)
        {
            throw new InvalidQuantityException("Invalid Quantity!");
        }

        if (quantity > stock[index])
        {
            throw new StockException("Insufficient Stock!");
        }

        cartId[cartCount] = id;
        cartQuantity[cartCount] = quantity;
        cartCount++;

        System.out.println("Product added to cart successfully!");
    }

    double displayCart() throws InvalidProductException
    {
        if (cartCount == 0)
        {
            System.out.println("Cart is empty!");
            return 0;
        }

        double total = 0;

        System.out.println("\n===== CART =====");

        for (int i = 0; i < cartCount; i++)
        {
            int index = findProduct(cartId[i]);

            double amount = price[index] * cartQuantity[i];

            System.out.println(
                productName[index] +
                " | Quantity: " + cartQuantity[i] +
                " | Amount: " + amount
            );

            total = total + amount;
        }

        System.out.println("Total Amount: " + total);

        return total;
    }

    void makePayment(double total, double payment)
        throws PaymentException
    {
        if (payment <= 0)
        {
            throw new PaymentException("Invalid Payment Amount!");
        }

        if (payment != total)
        {
            throw new PaymentException("Payment Failed!");
        }

        System.out.println("Payment Successful!");
    }

    void placeOrder(double payment)
        throws InvalidProductException,StockException,PaymentException
    {
        if (cartCount == 0)
        {
            throw new PaymentException("Cart is empty!");
        }

        double total = displayCart();

        makePayment(total, payment);

        for (int i = 0; i < cartCount; i++)
        {
            int index = findProduct(cartId[i]);

            stock[index] = stock[index] - cartQuantity[i];
        }

        cartCount = 0;

        System.out.println("Order placed successfully!");
    }

    public static void main(String[] args)
    {
        OnlineShoppingSystem osc = new OnlineShoppingSystem();

        try
        {
            int choice;

            do
            {
                System.out.println("\n===== ONLINE SHOPPING SYSTEM =====");
                System.out.println("1. Display Products");
                System.out.println("2. Add to Cart");
                System.out.println("3. View Cart");
                System.out.println("4. Place Order");
                System.out.println("5. Exit");

                System.out.print("Enter choice: ");
                choice = osc.sc.nextInt();

                try
                {
                    if (choice == 1)
                    {
                        osc.displayProducts();
                    }
                    else if (choice == 2)
                    {
                        System.out.print("Enter Product ID: ");
                        int id = osc.sc.nextInt();

                        System.out.print("Enter Quantity: ");
                        int quantity = osc.sc.nextInt();

                        osc.addToCart(id, quantity);
                    }
                    else if (choice == 3)
                    {
                        osc.displayCart();
                    }
                    else if (choice == 4)
                    {
                        double total = osc.displayCart();

                        if (total > 0)
                        {
                            System.out.print("Enter Payment Amount: ");
                            double payment = osc.sc.nextDouble();

                            osc.placeOrder(payment);
                        }
                    }
                    else if (choice == 5)
                    {
                        System.out.println("Thank you for shopping!");
                    }
                    else
                    {
                        throw new IllegalArgumentException("Invalid Choice!");
                    }
                }
                catch (InvalidProductException |
                       InvalidQuantityException |
                       StockException |
                       PaymentException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }

            } while (choice != 5);
        }
        catch (Exception e)
        {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
        finally
        {
            osc.sc.close();
            System.out.println("Scanner closed.");
        }
    }
}

