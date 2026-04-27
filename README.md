# 💰 Expense Management Microservices

## 📌 Overview

هذا المشروع هو نظام **Microservices** لإدارة المصروفات (Expense Management) مبني باستخدام **Spring Boot** ويعتمد على **Event-Driven Architecture** للتواصل بين الخدمات.

الهدف من المشروع هو توضيح كيفية بناء نظام موزع (Distributed System) باستخدام التواصل غير المتزامن (Asynchronous Communication).

---

## 🧩 Microservices Architecture

يتكون المشروع من خدمتين رئيسيتين:

### 🧾 Expense Service

* تستقبل طلبات إضافة المصروفات عبر REST API.
* عند إضافة مصروف جديد:

  * يتم حفظه (أو طباعته حالياً).
  * يتم إرسال Event إلى RabbitMQ.
* تمثل نقطة الإدخال للنظام.

---

### 💳 Budget Service

* تعمل في الخلفية (Background Service).
* تستمع للأحداث القادمة من RabbitMQ.
* عند استلام حدث:

  * تقوم بمحاكاة تحديث الميزانية.

---

## 🔄 Communication

* يتم استخدام **RabbitMQ** كـ Message Broker.
* التواصل بين الخدمات:

  * **Asynchronous**
  * **Decoupled** (الخدمات غير مرتبطة مباشرة ببعضها)

📌 هذا يعني:

* Expense Service لا تنتظر رد من Budget Service
* النظام أكثر مرونة وقابلية للتوسع

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot
* Spring AMQP
* RabbitMQ (via Docker)
* Maven

---

## 📂 Project Structure

```bash
.
├── docker-compose.yml
├── expense-service/
└── budget-service/
```

### التفاصيل:

* **docker-compose.yml**
  يحتوي على إعدادات تشغيل RabbitMQ داخل Docker

* **expense-service**
  يحتوي على REST Controller لإضافة المصروفات

* **budget-service**
  يحتوي على Event Listener لمعالجة الرسائل

---

## ▶️ How to Run

### 1. تشغيل RabbitMQ

```bash
docker-compose up -d
```

---

### 2. تشغيل الخدمات

لكل خدمة:

```bash
cd expense-service
mvn spring-boot:run
```

```bash
cd budget-service
mvn spring-boot:run
```

---

### 3. إرسال طلب إضافة مصروف

مثال باستخدام Postman أو curl:

```bash
POST /api/expenses
```

Body:

```json
{
  "amount": 50,
  "description": "Lunch"
}
```

---

## ⚙️ How It Works

1. يقوم المستخدم بإرسال طلب لإضافة مصروف
2. تقوم Expense Service بمعالجة الطلب
3. يتم نشر Event إلى RabbitMQ
4. تستقبل Budget Service الحدث
5. يتم تحديث الميزانية تلقائياً

---

## 🚀 Features

* Microservices Architecture
* Event-Driven Design
* Asynchronous Communication
* Loose Coupling بين الخدمات
* قابل للتطوير (Scalable)

---

## 📌 Future Improvements

* ربط قاعدة بيانات حقيقية (MySQL / PostgreSQL)
* إضافة API Gateway
* إضافة Authentication & Authorization
* تحسين Logging و Monitoring

---


## 📄 License

This project is open-source and available under the MIT License.
