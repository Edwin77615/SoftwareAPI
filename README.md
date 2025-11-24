# RawSource Backend

Backend para gestión de órdenes, inventarios y productos para un marketplace multi-usuario (admin, provider, buyer).

---
## Usuarios de prueba

### ADMIN
Email: `john@mail.com`  
Password: `123456`

### PROVIDER
Email: `diana@mail.com`  
Password: `123456`

### BUYER
Email: `pedro@mail.com`  
Password: `123456`

## **Requisitos**

- Java 17+
- Maven
- PostgreSQL

---

## **Configuración**

Configura tus variables de entorno en `application.properties`:

```
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<database>
SPRING_DATASOURCE_USERNAME=<user>
SPRING_DATASOURCE_PASSWORD=<password>
```

---

## **Roles**

- **ADMIN:** Puede ver y aprobar órdenes, ver todos los productos, eliminar productos (si está habilitado).
- **PROVIDER:** Puede crear productos, aprobar y entregar órdenes, gestionar su inventario.
- **BUYER:** Puede crear órdenes, cancelar sus propias órdenes, ver sus órdenes.

---

## **Endpoints principales**

### **Autenticación**
- `POST /api/auth/login`  
  Login de usuario, retorna JWT.

---

### **Productos**
- `POST /api/products`  
  Crea un producto (solo PROVIDER).  
  **Nota:** El producto se agrega automáticamente al inventario del provider con cantidad 0.

- `PUT /api/products/{id}`  
  Actualiza un producto (solo el provider dueño).

- `DELETE /api/products/{id}`  
  Elimina un producto (solo el provider dueño o admin, si está habilitado).

- `GET /api/products`  
  Lista todos los productos.

---

### **Inventario**
- `GET /api/inventories/current`  
  Devuelve la información del inventario del usuario autenticado.

- `GET /api/inventories/{inventoryId}/products`  
  Devuelve los productos del inventario.

- `POST /api/inventories/deliver-order`  
  Entrega una orden (solo PROVIDER).  
  **Body:**  
  ```json
  { "orderId": "uuid-de-la-orden" }
  ```
  - Los productos y cantidades se suman al inventario del buyer.

---

### **Órdenes**
- `POST /api/orders/create`  
  Crea una orden (solo BUYER).

- `PUT /api/orders/{orderId}/status`  
  Cambia el estado de una orden.  
  **Body:**  
  ```json
  { "newStatus": "APPROVED" } // o CANCELLED, DELIVERED
  ```
  - Solo PROVIDER puede aprobar (`APPROVED`).
  - PROVIDER o BUYER pueden cancelar (`CANCELLED`).

- `DELETE /api/orders/{orderId}`  
  Elimina una orden (solo BUYER, si está en estado PENDING).

---

### **Items**
- `POST /api/items/order/{orderId}/modify`  
  Modifica los items de una orden (solo BUYER, si está en estado PENDING).

---

## **Reglas de negocio importantes**

- Un producto debe estar en el inventario del provider para poder ser entregado.
- Al entregar una orden, los productos y cantidades se suman al inventario del buyer.
- Solo el provider o el admin pueden eliminar un producto (si está habilitado).
- Solo el provider puede aprobar y entregar órdenes.
- Solo el buyer o el provider pueden cancelar una orden.

---

## **Errores comunes**

- **403 Forbidden:** Intento de acción sin permisos (ej: eliminar producto de otro provider).
- **409/500:** Integridad referencial (ej: eliminar producto usado en órdenes).
- **400 Bad Request:** Body mal formado o transición de estado inválida.
