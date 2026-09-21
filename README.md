# Pharmacy Management System

A Java Swing desktop application for managing a pharmacy's medicine inventory, billing, and sales history.

## Features

- **Search** a medicine by ID and view its full record (name, category, price, stock, expiry).
- **Sell** a medicine — validates input, checks stock, generates a bill, and updates inventory.
- **View inventory** — full listing plus an automatically sorted view by medicine ID.
- **View sales history** — every completed sale for the current session.
- **Save sales** permanently to `sales.txt`, so records survive restarting the app.
- **Clear** the form to start a new transaction.

## Built With

- **Java Swing** — `JFrame`, `JTextField`, `JButton`, `JTextArea`, `JScrollPane` for the GUI, wired up with `ActionListener`.
- **OOP** — `Medicine`, `Customer`, and `Billing` classes model the domain; a constructor initializes every `Medicine` field.
- **Arrays** — six parallel arrays (`id`, `name`, `category`, `price`, `stock`, `expiry`) act as the in-memory inventory store.
- **Collections Framework**
  - `HashMap<String, Integer>` — maps a medicine ID to its array index for O(1) search.
  - `TreeMap<String, String>` — keeps medicines sorted by ID automatically.
  - `LinkedList<String>` — stores sales records as they happen and is traversed to show history.
- **Exception Handling** — `try`/`catch` around quantity parsing plus explicit checks for missing medicines, empty names, and insufficient stock.
- **File Handling** — `FileWriter` in append mode writes each sale to `sales.txt` without overwriting previous records.

## Running It

```bash
javac PharmacyManagementSystem.java
java PharmacyManagementSystem
```

Requires a JDK with Swing support (JDK 8+).

## Sample Inventory

| ID | Name | Category | Price | Stock | Expiry |
|----|------|----------|-------|-------|--------|
| M101 | Paracetamol | Tablet | ₹20 | 50 | 12/2027 |
| M102 | Amoxicillin | Antibiotic | ₹80 | 30 | 08/2027 |
| M103 | Cetirizine | Tablet | ₹35 | 40 | 05/2028 |
| M104 | Vitamin C | Supplement | ₹50 | 25 | 10/2027 |
| M105 | Ibuprofen | Tablet | ₹60 | 35 | 03/2028 |

## Team

| Member | Focus |
|--------|-------|
| Saaiyash | OOP & data model — `Medicine`/`Customer`/`Billing` classes, constructor, inventory arrays |
| Antara | Collections & GUI — `HashMap`/`TreeMap`/`LinkedList`, Swing layout, event handling |
| Atharva | Logic & validation — search logic, input checks, exception handling |
| Aditya | History & persistence — sales traversal, `FileWriter` append-mode saving |
