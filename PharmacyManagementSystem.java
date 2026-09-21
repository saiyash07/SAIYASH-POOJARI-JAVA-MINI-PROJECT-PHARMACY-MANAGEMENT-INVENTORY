import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

class Medicine {

    String id;
    String name;
    String category;
    double price;
    int stock;
    String expiry;

    Medicine(String id, String name, String category,
             double price, int stock, String expiry) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.expiry = expiry;
    }
}

class Customer {
    String name;
    Customer(String name) {
        this.name = name;
    }
}

class Billing {

    static double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}

public class PharmacyManagementSystem extends JFrame {

    // Medicine information using arrays
    String[] medicineId = {"M101", "M102", "M103", "M104", "M105"};
    String[] medicineName = {"Paracetamol", "Amoxicillin",
                             "Cetirizine", "Vitamin C", "Ibuprofen"};
    String[] category = {"Tablet", "Antibiotic", "Tablet",
                         "Supplement", "Tablet"};
    double[] price = {20, 80, 35, 50, 60};
    int[] stock = {50, 30, 40, 25, 35};
    String[] expiry = {"12/2027", "08/2027", "05/2028",
                       "10/2027", "03/2028"};

    // Collections
    HashMap<String, Integer> medicineSearch = new HashMap<>();
    TreeMap<String, String> sortedMedicines = new TreeMap<>();
    LinkedList<String> salesRecords = new LinkedList<>();

    JTextField customerField;
    JTextField idField;
    JTextField quantityField;

    JTextArea outputArea;

    public PharmacyManagementSystem() {

        // HashMap and TreeMap
        for (int i = 0; i < medicineId.length; i++) {

            medicineSearch.put(medicineId[i], i);

            sortedMedicines.put(
                medicineId[i],
                medicineName[i]
            );
        }

        setTitle("Pharmacy Management System");
        setSize(700, 550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Pharmacy Inventory and Billing");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(200, 20, 350, 30);
        add(title);

        // Customer
        JLabel customerLabel = new JLabel("Customer Name:");
        customerLabel.setBounds(30, 70, 120, 25);
        add(customerLabel);

        customerField = new JTextField();
        customerField.setBounds(150, 70, 220, 25);
        add(customerField);

        // Medicine ID
        JLabel idLabel = new JLabel("Medicine ID:");
        idLabel.setBounds(30, 110, 120, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(150, 110, 220, 25);
        add(idField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(390, 108, 100, 30);
        add(searchButton);

        // Quantity
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(30, 150, 120, 25);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(150, 150, 220, 25);
        add(quantityField);

        JButton sellButton = new JButton("Sell");
        sellButton.setBounds(390, 148, 100, 30);
        add(sellButton);

        // Buttons
        JButton inventoryButton = new JButton("Inventory");
        inventoryButton.setBounds(30, 195, 120, 30);
        add(inventoryButton);

        JButton salesButton = new JButton("Sales");
        salesButton.setBounds(170, 195, 100, 30);
        add(salesButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(290, 195, 100, 30);
        add(clearButton);

        JButton saveButton = new JButton("Save Sales");
        saveButton.setBounds(410, 195, 120, 30);
        add(saveButton);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(30, 240, 620, 230);
        add(scrollPane);

        // Button actions
        searchButton.addActionListener(e -> searchMedicine());

        sellButton.addActionListener(e -> sellMedicine());

        inventoryButton.addActionListener(e -> showInventory());

        salesButton.addActionListener(e -> showSales());

        saveButton.addActionListener(e -> saveSales());

        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    // Search medicine using HashMap
    void searchMedicine() {

        String id = idField.getText();

        if (medicineSearch.containsKey(id)) {

            int index = medicineSearch.get(id);

            outputArea.setText(
                "====== MEDICINE DETAILS ======\n\n" +
                "Medicine ID: " + medicineId[index] + "\n" +
                "Name: " + medicineName[index] + "\n" +
                "Category: " + category[index] + "\n" +
                "Price: ₹" + price[index] + "\n" +
                "Stock: " + stock[index] + "\n" +
                "Expiry: " + expiry[index]
            );

        } else {

            outputArea.setText("Medicine not found.");
        }
    }

    // Sell medicine
    void sellMedicine() {

        String customerName = customerField.getText();
        String id = idField.getText();

        if (customerName.isEmpty()) {
            outputArea.setText("Please enter customer name.");
            return;
        }

        try {

            int quantity =
                Integer.parseInt(quantityField.getText());

            if (!medicineSearch.containsKey(id)) {

                outputArea.setText("Medicine not found.");
                return;
            }

            int index = medicineSearch.get(id);

            if (quantity <= 0) {

                outputArea.setText("Enter a valid quantity.");
                return;
            }

            if (quantity > stock[index]) {

                outputArea.setText("Not enough stock available.");
                return;
            }

            // Customer object
            Customer customer =
                new Customer(customerName);

            // Calculate bill using Billing class
            double total =
                Billing.calculateTotal(price[index], quantity);

            // Update stock
            stock[index] =
                stock[index] - quantity;

            // Create sales record
            String sale =
                customer.name + " | " +
                medicineName[index] + " | " +
                quantity + " | ₹" +
                total;

            // Store in LinkedList
            salesRecords.add(sale);

            // Display bill
            outputArea.setText(
                "========== PHARMACY BILL ==========\n\n" +
                "Customer: " + customer.name + "\n" +
                "Medicine ID: " + medicineId[index] + "\n" +
                "Medicine: " + medicineName[index] + "\n" +
                "Category: " + category[index] + "\n" +
                "Quantity: " + quantity + "\n" +
                "Price: ₹" + price[index] + "\n" +
                "Expiry: " + expiry[index] + "\n" +
                "-----------------------------------\n" +
                "Total Amount: ₹" + total + "\n" +
                "Remaining Stock: " + stock[index] + "\n" +
                "==================================="
            );

        } catch (NumberFormatException e) {

            outputArea.setText(
                "Please enter a valid quantity."
            );
        }
    }

    // Display inventory
    void showInventory() {

        outputArea.setText(
            "========== MEDICINE INVENTORY ==========\n\n"
        );

        for (int i = 0; i < medicineId.length; i++) {

            outputArea.append(
                "ID: " + medicineId[i] +
                " | Name: " + medicineName[i] +
                " | Category: " + category[i] +
                " | Price: ₹" + price[i] +
                " | Stock: " + stock[i] +
                " | Expiry: " + expiry[i] +
                "\n"
            );
        }

        outputArea.append(
            "\n====== SORTED MEDICINES ======\n"
        );

        for (String id : sortedMedicines.keySet()) {

            outputArea.append(
                id + " - " +
                sortedMedicines.get(id) +
                "\n"
            );
        }
    }

    // Display sales records
    void showSales() {

        outputArea.setText(
            "========== SALES RECORDS ==========\n\n"
        );

        if (salesRecords.isEmpty()) {

            outputArea.append(
                "No sales recorded."
            );

        } else {

            for (String sale : salesRecords) {

                outputArea.append(
                    sale + "\n"
                );
            }
        }
    }
    // Save sales using File Handling
    void saveSales() {

        try {

            FileWriter writer =
                new FileWriter("sales.txt", true);

            for (String sale : salesRecords) {

                writer.write(sale + "\n");
            }

            writer.close();

            outputArea.setText(
                "Sales records saved successfully\n" +
                "in sales.txt"
            );

        } catch (IOException e) {

            outputArea.setText(
                "Error while saving sales."
            );
        }
    }

    // Clear fields
    void clearFields() {

        customerField.setText("");
        idField.setText("");
        quantityField.setText("");
        outputArea.setText("");
    }

    public static void main(String[] args) {

        new PharmacyManagementSystem();
    }
}

