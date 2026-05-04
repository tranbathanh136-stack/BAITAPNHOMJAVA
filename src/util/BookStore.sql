IF DB_ID('BookStore') IS NULL
    CREATE DATABASE BookStore;
GO

USE BookStore;
GO

CREATE TABLE roles (
    role_id INT PRIMARY KEY IDENTITY(1,1),
    role_name NVARCHAR(50) NOT NULL,
    description NVARCHAR(255)
);

CREATE TABLE users (
    user_id INT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    role_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE employees (
    employee_id INT PRIMARY KEY IDENTITY(1,1),
    user_id INT UNIQUE NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    position NVARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY IDENTITY(1,1),
    full_name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    email NVARCHAR(100),
    address NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE()
);

CREATE TABLE authors (
    author_id INT PRIMARY KEY IDENTITY(1,1),
    author_name NVARCHAR(150) NOT NULL,
    biography NVARCHAR(MAX)
);

CREATE TABLE categories (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name NVARCHAR(100) NOT NULL
);

CREATE TABLE publishers (
    publisher_id INT PRIMARY KEY IDENTITY(1,1),
    publisher_name NVARCHAR(150) NOT NULL,
    address NVARCHAR(255),
    phone NVARCHAR(20)
);

CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY IDENTITY(1,1),
    supplier_name NVARCHAR(150) NOT NULL,
    contact_person NVARCHAR(100),
    phone NVARCHAR(20),
    address NVARCHAR(255)
);

CREATE TABLE books (
    book_id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(200) NOT NULL,
    isbn NVARCHAR(20) UNIQUE,
    category_id INT,
    publisher_id INT,
    publication_year INT,
    price DECIMAL(12,2) NOT NULL,
    selling_price DECIMAL(12,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    description NVARCHAR(MAX),
    image_path NVARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (publisher_id) REFERENCES publishers(publisher_id)
);

CREATE TABLE book_authors (
    book_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (author_id) REFERENCES authors(author_id)
);

CREATE TABLE invoices (
    invoice_id INT PRIMARY KEY IDENTITY(1,1),
    customer_id INT,
    employee_id INT NOT NULL,
    invoice_date DATETIME DEFAULT GETDATE(),
    total_amount DECIMAL(12,2) NOT NULL,
    discount DECIMAL(12,2) DEFAULT 0,
    final_amount DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE invoice_details (
    detail_id INT PRIMARY KEY IDENTITY(1,1),
    invoice_id INT NOT NULL,
    book_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal AS (quantity * unit_price) PERSISTED,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

CREATE TABLE import_invoices (
    import_id INT PRIMARY KEY IDENTITY(1,1),
    supplier_id INT NOT NULL,
    employee_id INT NOT NULL,
    import_date DATETIME DEFAULT GETDATE(),
    total_cost DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE import_details (
    detail_id INT PRIMARY KEY IDENTITY(1,1),
    import_id INT NOT NULL,
    book_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    import_price DECIMAL(12,2) NOT NULL,
    subtotal AS (quantity * import_price) PERSISTED,
    FOREIGN KEY (import_id) REFERENCES import_invoices(import_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id)
);

CREATE INDEX idx_invoice_date ON invoices(invoice_date);
CREATE INDEX idx_import_date ON import_invoices(import_date);
CREATE INDEX idx_book_title ON books(title);
CREATE INDEX idx_customer_name ON customers(full_name);
CREATE INDEX idx_employee_name ON users(full_name);