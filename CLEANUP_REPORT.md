# Java Project Cleanup Report

## Executive Summary
- **Total Files to Delete:** 23 files (10 model classes, 6 DAO classes, 7 service classes)
- **Critical Issues Found:** 6 missing class dependencies + 2 missing DAO references causing compilation errors
- **Duplicate Components:** English translations of Vietnamese-named classes that are unused in the UI

---

## SECTION 1: DUPLICATE MODEL CLASSES TO DELETE (English Translations)

### Status: UNUSED - Safe to delete
These are English translations of Vietnamese models. All actual usage is via Vietnamese names.

| File | Reason | Referenced By |
|---|---|---|
| `model/Invoice.java` | Duplicate of `HoaDon.java` | Only InvoiceDAO, InvoiceDetailDAO, InvoiceService (all unused) |
| `model/InvoiceDetail.java` | Duplicate of `HoaDonChiTiet.java` | Only InvoiceDetailDAO (unused) |
| `model/Employee.java` | Duplicate of `NhanVien.java` | Only EmployeeService (unused) |
| `model/Category.java` | Duplicate of `TheLoai.java` | Only CategoryService, CategoryDAO (unused) |
| `model/Publisher.java` | Duplicate of `NhaXuatBan.java` | Only PublisherService, PublisherDAO (unused) |
| `model/ImportInvoice.java` | Duplicate of `PhieuNhap.java` | Only ImportInvoiceDAO, ImportService (unused) |
| `model/ImportDetail.java` | Duplicate of `ChiTietNhap.java` | Only ImportDetailDAO, ImportService (unused) |
| `model/Cart.java` | Not used in views | Only referenced in HoaDonService (unused service) |
| `model/CartItem.java` | Not used in views | Only referenced in HoaDonService (unused service) |
| `model/Author.java` | Not used in views | Only AuthorService, AuthorDAO (unused) |

---

## SECTION 2: MISSING MODEL CLASSES (CAUSE COMPILATION ERRORS)

### Status: CRITICAL - These are imported but don't exist
Need to either create these classes or delete the services that import them.

| Missing Class | Imported By | Files Affected | Recommendation |
|---|---|---|---|
| `model/Session.java` | AuthorService, CategoryService, EmployeeService, PublisherService, ReportService | 5 services | DELETE those services (unused anyway) |
| `model/InventoryStatus.java` | ReportService, ReportDAO | 2 files | DELETE ReportService (unused) |
| `model/SalesByDay.java` | ReportService, ReportDAO | 2 files | DELETE ReportService (unused) |
| `model/TopSellingBook.java` | ReportService, ReportDAO | 2 files | DELETE ReportService (unused) |

---

## SECTION 3: DUPLICATE DAO CLASSES TO DELETE (English Translations)

### Status: UNUSED - Safe to delete
These are English translations of Vietnamese DAOs. All active views use Vietnamese-named DAOs.

| File | Reason | Used By |
|---|---|---|
| `dao/InvoiceDAO.java` | Duplicate of `HoaDonDAO.java` | Only InvoiceService (unused) |
| `dao/InvoiceDetailDAO.java` | Duplicate of `HoaDonChiTietDAO.java` | Only InvoiceService, InvoiceDAO (unused) |
| `dao/ImportInvoiceDAO.java` | Duplicate of `PhieuNhapDAO.java` | Only ImportService (unused) |
| `dao/ImportDetailDAO.java` | Duplicate of `ChiTietNhapDAO.java` | Only ImportService (unused) |
| `dao/CategoryDAO.java` | Duplicate of `TheLoaiDAO.java` | Only CategoryService (unused) |
| `dao/PublisherDAO.java` | Duplicate of `NhaXuatBanDAO.java` | Only PublisherService (unused) |

---

## SECTION 4: UNUSED SERVICE CLASSES TO DELETE

### Status: NOT USED IN ANY VIEW - Safe to delete
These services are never instantiated or called from any UI component.

| File | Reason | Dependencies | Issues |
|---|---|---|---|
| `service/InvoiceService.java` | Not used in views | Imports missing `BookDAO` | ❌ Compilation error |
| `service/EmployeeService.java` | Not used in views | Uses missing `Session`, missing `EmployeeDAO` | ❌ Compilation error |
| `service/ImportService.java` | Duplicate of `NhapSachService` | Imports missing `BookDAO` | ❌ Compilation error |
| `service/AuthorService.java` | Not used in views | Uses missing `Session` | ❌ Compilation error |
| `service/PublisherService.java` | Not used in views | Uses missing `Session` | ❌ Compilation error |
| `service/CategoryService.java` | Not used in views | Uses missing `Session` | ❌ Compilation error |
| `service/ReportService.java` | Not used in views | Uses missing `Session`, `InventoryStatus`, `SalesByDay`, `TopSellingBook` | ❌ Multiple compilation errors |

---

## SECTION 5: MISSING DAO CLASSES (CAUSE COMPILATION ERRORS)

### Status: CRITICAL - These are instantiated but don't exist
Will cause runtime errors if those services are ever called.

| Missing Class | Instantiated In | Recommendation |
|---|---|---|
| `dao/BookDAO.java` | InvoiceService, ImportService | DELETE those services (both unused anyway) |
| `dao/EmployeeDAO.java` | EmployeeService | DELETE EmployeeService (unused) |

---

## SECTION 6: CURRENTLY USED COMPONENTS (DO NOT DELETE)

### Active Views and Their Dependencies
✅ All these files are actively used and must be kept:

**LoginFrame.java** → AuthService → UserDAO, User

**MainFrame.java** → User

**SachFrame.java** → SachService, TheLoaiService, NhaXuatBanService → Sach, TheLoai, NhaXuatBan

**HoaDonFrame.java** → HoaDonService, KhachHangService, SachService → HoaDon, HoaDonChiTiet, KhachHang, Sach

**NhapSachFrame.java** → NhapSachService, SupplierService, SachService, TheLoaiService, NhaXuatBanService → PhieuNhap, ChiTietNhap, Sach, Supplier, TheLoai, NhaXuatBan

**KhachHangFrame.java** → KhachHangService → KhachHang

**NhanVienFrame.java** → NhanVienService → NhanVien

### Protected Files (Must Keep)
✅ `dao/DBConnection.java` - Core database connection
✅ All models in `model/` that are used (User, Sach, KhachHang, NhanVien, TheLoai, NhaXuatBan, HoaDon, HoaDonChiTiet, PhieuNhap, ChiTietNhap, Supplier)
✅ All DAOs in `dao/` that are used (UserDAO, SachDAO, KhachHangDAO, NhanVienDAO, TheLoaiDAO, NhaXuatBanDAO, HoaDonDAO, HoaDonChiTietDAO, PhieuNhapDAO, ChiTietNhapDAO, SupplierDAO, ReportDAO)
✅ All services in `service/` that are instantiated in views

---

## SECTION 7: SUMMARY OF DELETIONS

### Model Classes to Delete (10 files)
```
model/Invoice.java
model/InvoiceDetail.java
model/Employee.java
model/Category.java
model/Publisher.java
model/ImportInvoice.java
model/ImportDetail.java
model/Cart.java
model/CartItem.java
model/Author.java
```

### DAO Classes to Delete (6 files)
```
dao/InvoiceDAO.java
dao/InvoiceDetailDAO.java
dao/ImportInvoiceDAO.java
dao/ImportDetailDAO.java
dao/CategoryDAO.java
dao/PublisherDAO.java
```

### Service Classes to Delete (7 files)
```
service/InvoiceService.java
service/EmployeeService.java
service/ImportService.java
service/AuthorService.java
service/PublisherService.java
service/CategoryService.java
service/ReportService.java
```

Also need to update:
```
dao/AuthorDAO.java - DELETE (Author model deleted)
```

---

## NEXT STEPS

1. ✅ **Review this report** - Confirm all deletions are appropriate
2. 🔄 **Delete 23 files** - Listed in Section 7
3. 🔧 **Fix imports** - Remove references to deleted classes
4. 🏗️ **Rebuild project** - Verify no compilation errors
5. ✓ **Test application** - Ensure all views still work

---

## FILES THAT WILL BE DELETED

Total: **24 files** (23 Java classes + 1 bonus: AuthorDAO)

**Duration:** ~2-3 minutes for cleanup + rebuild