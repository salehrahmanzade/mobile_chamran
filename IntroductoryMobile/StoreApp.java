import java.util.*;
import java.text.SimpleDateFormat;

// اینترفیس
interface Printable {
    void printInvoice(); // پلی مورفیزم
}

// کلاس محصول
class Product {
    // کپسوله سازی
    private int id;
    private String name;
    private double price;
    private int stock;
    private Category category;

    public Product(int id, String name, double price, int stock, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public Category getCategory() { return category; }
    public void setStock(int stock) { this.stock = stock; }
}

// کلاس دسته بندی
class Category {
    // کپسوله سازی
    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}

// کلاس کاربر
class User {
    // کپسوله سازی
    private int id;
    private String name;
    private String email;
    private String phone;

    public User(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

// کلاس آیتم فاکتور
class InvoiceItem {
    // کپسوله سازی
    private Product product;
    private int quantity;

    public InvoiceItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

// ارث بری از Printable
class Invoice implements Printable {
    // کپسوله سازی
    private int id;
    private User user;
    List<InvoiceItem> items;
    private Date date;

    public Invoice(int id, User user) {
        this.id = id;
        this.user = user;
        this.items = new ArrayList<>();
        this.date = new Date();
    }

    public void addItem(Product product, int quantity) {
        if (product.getStock() >= quantity) {
            items.add(new InvoiceItem(product, quantity));
            product.setStock(product.getStock() - quantity);
        } else {
            System.out.println("موجودی کافی نیست برای محصول: " + product.getName());
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (InvoiceItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public void printInvoice() { // پلی مورفیزم
        System.out.println("------------------------------");
        System.out.println("فاکتور شماره: " + id);
        System.out.println("تاریخ: " + date);
        System.out.println("مشتری: " + user.getName());
        System.out.println("محصولات:");
        for (InvoiceItem item : items) {
            System.out.println(item.getProduct().getName() + " x " + item.getQuantity() +
                    " = " + item.getTotalPrice() + " تومان");
        }
        double total = calculateTotal();
        System.out.println("مبلغ کل: " + total + " تومان");
        System.out.println("------------------------------");
    }
}

// کلاس اصلی برنامه 
public class StoreApp {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Product> products = new ArrayList<>();
    private static List<Category> categories = new ArrayList<>();
    private static List<User> users = new ArrayList<>();
    private static List<Invoice> invoices = new ArrayList<>();
    private static int invoiceCounter = 1;

    public static void main(String[] args) {
        // اضافه شدن لیست محصولات در لود
        seedData();

        while (true) {
            try {
                System.out.println("\n1. افزودن کاربر");
                System.out.println("2. مشاهده محصولات");
                System.out.println("3. خرید محصول");
                System.out.println("4. مشاهده کاربران");
                System.out.println("5. مشاهده فاکتورها");
                System.out.println("6. خروج");
                System.out.print("انتخاب شما: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> addUser();
                    case 2 -> viewProducts();
                    case 3 -> makePurchase();
                    case 4 -> viewUsers();
                    case 5 -> viewInvoices();
                    case 6 -> {
                        System.out.println("خروج از برنامه");
                        return;
                    }
                    default -> System.out.println("انتخاب نامعتبر!");
                }
            } catch (Exception e) {
                System.out.println("خطا رخ داد: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    // لیست محصولات
    private static void seedData() {
        Category electronics = new Category(1, "الکترونیک");
        Category clothing = new Category(2, "پوشاک");
        categories.add(electronics);
        categories.add(clothing);

        products.add(new Product(101, "لپ‌تاپ", 30000000, 10, electronics));
        products.add(new Product(102, "ماوس", 300000, 50, electronics));
        products.add(new Product(103, "هدفون", 800000, 30, electronics));
        products.add(new Product(104, "تلفن همراه", 20000000, 15, electronics));
        products.add(new Product(105, "مانیتور", 12000000, 8, electronics));
        products.add(new Product(106, "پاوربانک", 600000, 18, electronics));
        products.add(new Product(201, "تی‌شرت", 150000, 20, clothing));
        products.add(new Product(202, "شلوار", 250000, 25, clothing));
        products.add(new Product(203, "کفش", 400000, 10, clothing));
        products.add(new Product(204, "کلاه", 80000, 12, clothing));
        products.add(new Product(205, "کاپشن", 500000, 6, clothing));
    }

    // اضافه کردن کاربر
    private static void addUser() {
        System.out.print("نام: ");
        String name = scanner.nextLine();
        System.out.print("ایمیل: ");
        String email = scanner.nextLine();
        System.out.print("تلفن: ");
        String phone = scanner.nextLine();

        int id = users.size() + 1;
        users.add(new User(id, name, email, phone));
        System.out.println("کاربر با موفقیت اضافه شد.");
    }

    private static void viewProducts() {
        for (Product p : products) {
            System.out.printf("%d - %s (%s) | قیمت: %.0f | موجودی: %d\n",
                    p.getId(), p.getName(), p.getCategory().getName(), p.getPrice(), p.getStock());
        }
    }

    private static void viewUsers() {
        for (User u : users) {
            System.out.printf("%d - %s | ایمیل: %s | تلفن: %s\n",
                    u.getId(), u.getName(), u.getEmail(), u.getPhone());
        }
    }

    private static void viewInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("هیچ فاکتوری ثبت نشده است.");
        } else {
            for (Invoice invoice : invoices) {
                invoice.printInvoice();
            }
        }
    }

    // فانکشن خرید محصول 
    private static void makePurchase() {
        if (users.isEmpty()) {
            System.out.println("ابتدا یک کاربر ایجاد کنید.");
            return;
        }

        System.out.print("نام کاربری: ");
        String username = scanner.nextLine();

        User buyer = null;
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(username)) {
                buyer = u;
                break;
            }
        }

        if (buyer == null) {
            System.out.print("کاربر یافت نشد. لطفا شماره تلفن را وارد کنید: ");
            String phone = scanner.nextLine();
            for (User u : users) {
                if (u.getPhone().equals(phone)) {
                    buyer = u;
                    break;
                }
            }

            if (buyer == null) {
                System.out.println("کاربر با این اطلاعات یافت نشد.");
                return;
            }
        }

        Invoice invoice = new Invoice(invoiceCounter++, buyer);

        // لیست خرید محصول تکرار شونده خروج با صفر
        while (true) {
            viewProducts();
            System.out.print("شناسه محصول برای خرید (0 برای اتمام): ");
            int pid = scanner.nextInt();
            if (pid == 0) break;

            System.out.print("تعداد: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            Product selected = null;
            for (Product p : products) {
                if (p.getId() == pid) {
                    selected = p;
                    break;
                }
            }

            if (selected != null) {
                invoice.addItem(selected, quantity);
            } else {
                System.out.println("محصول یافت نشد.");
            }
        }

        invoice.printInvoice();
        invoices.add(invoice);
    }
}
