# Task 12: Day-4 Design Challenge – Re-evaluate and Adapt the Design

**Course Outcome**: CO1 – Java fundamentals and object-oriented programming  
**Bloom’s Level**: K5 – Evaluate  
**Student Name**: Korasika Sudheer  
**Register Number**: 192324313  

---

## 1. Section A: Impact Analysis (2 Marks)

### 1.1 Changed Requirements Overview
The campus smart-parking facility has undergone four major evolutionary changes:
1. **New Category 1 – E-Bikes**: Charged a **flat fee** (e.g., Rs. 20.00 flat per stay) rather than duration-based hourly rates.
2. **New Category 2 – Delivery Vans**: Charged at **double the standard car rate** (Rs. 60.00/hr instead of Rs. 30.00/hr) during peak commercial delivery hours (**9:00 AM – 5:00 PM**), but standard car rate outside peak hours.
3. **New Privilege Model – Visitor One-Day Pass**: Visitors can now purchase a fixed-price One-Day Pass (e.g., Rs. 150.00/day) granting unlimited parking for a 24-hour cycle without hourly accumulation.
4. **Physical Expansion**: Facility expanded from 3 to 5 floors, with non-uniform slot counts per floor (e.g., Floor 0: 20 slots, Floor 1: 40 slots, Floor 2: 60 slots, Floor 3: 80 slots, Floor 4: 100 slots).

### 1.2 Impact on Architecture, Classes, and Data Structures
- **Impact on Vehicle Base Class**: The original template method `calculateFee(int minutes) = ((minutes + 59) / 60) * ratePerHour()` assumed that *every* vehicle is billed linearly on hours.
  - This assumption **breaks** for E-Bikes (flat fee regardless of duration).
  - This assumption **breaks** for Delivery Vans (rate is non-uniform and depends on time of day).
- **Impact on Array Structure**: Fixed-capacity rectangular arrays `Vehicle[5][100]` would waste massive memory because floor sizes range from 20 to 100. The **Jagged Array** `new Vehicle[5][]` directly accepts an array `int[] slotsPerFloor = {20, 40, 60, 80, 100}`, accommodating non-uniform levels with zero code modification.
- **Classes Affected**:
  - `Vehicle` hierarchy: Add `EBike` and `DeliveryVan`.
  - Pricing subsystem: Refactor from hardcoded method returns to a **Strategy Pattern (`PricingRule`)** or polymorphic method overload taking `LocalTime`.
  - Pass subsystem: Extract a polymorphic `Pass` abstraction (`MonthlyPass`, `DayPass`).

---

## 2. Section B: Evaluation of the Original Design (2 Marks)

### 2.1 Critical Architectural Assessment
1. **Can new vehicle types be added only by writing new subclasses?**
   - In the original design, yes: `EBike extends Vehicle` and `DeliveryVan extends Vehicle`. However, because `ratePerHour()` returned a static `double`, a delivery van cannot express dynamic peak-hour rate doubling without access to arrival/departure timestamps.
2. **Are pricing rules centralized or scattered?**
   - In the original design, rates were embedded inside subclass methods (`return 10;`, `return 30;`). When time-window rules and pass waivers are introduced, spreading pricing logic across subclasses violates the **Single Responsibility Principle**.
3. **Can existing tested classes remain unchanged?**
   - In our original design, `Vehicle`, `TwoWheeler`, `Car`, and `ElectricCar` were cleanly encapsulated. By extending rather than modifying them, our previously tested classes remain **100% untouched**, preserving existing unit test guarantees.

---

## 3. Section C: Adaptation or Redesign (2 Marks)

### Decision: ADAPT via Strategy Pattern & Polymorphic Overload
Rather than a destructive rewrite, we **ADAPT** the design by introducing:
1. **`PricingRule` Strategy Interface**: Encapsulates diverse fee algorithms (Hourly, Flat, Time-Window Peak).
2. **`Pass` Hierarchy**: `Pass` interface with `MonthlyPass` (100% waiver) and `DayPass` (fixed flat 24hr rate).
3. **`EBike` Subclass**: Implements flat rate billing.
4. **`DeliveryVan` Subclass**: Evaluates peak hours ($09:00 \le t \le 17:00$) to double rates.
5. **Dynamic 5-Floor Jagged Facility**: Supports variable floor layouts $\{20, 40, 60, 80, 100\}$.

```mermaid
classDiagram
    direction TB
    class PricingRule {
        <<interface>>
        +computeFee(Vehicle v, int minutes, LocalTime entryTime) double
    }
    class HourlyPricingRule {
        +computeFee(...) double
    }
    class FlatPricingRule {
        +computeFee(...) double
    }
    class PeakWindowPricingRule {
        +computeFee(...) double
    }

    PricingRule <|.. HourlyPricingRule
    PricingRule <|.. FlatPricingRule
    PricingRule <|.. PeakWindowPricingRule

    class Vehicle {
        <<abstract>>
        -String regNo
        +calculateFee(int minutes) double
        +calculateFee(int minutes, LocalTime entryTime) double
    }
    class EBike {
        +calculateFee(int minutes) double
    }
    class DeliveryVan {
        +calculateFee(int minutes, LocalTime entryTime) double
    }

    Vehicle <|-- EBike
    Vehicle <|-- DeliveryVan
```

---

## 4. Section D: Evidence-Based Defence (2 Marks)

### 4.1 Quantitative Defence Metrics
1. **Number of Existing Classes Modified**: **Zero (0)**.
   - `TwoWheeler`, `Car`, `ElectricCar`, and `ParkingTicket` from Task 08/Task 11 required **zero lines of code changed**.
   - `EBike` and `DeliveryVan` were added as pure extensions, validating compliance with the **Open-Closed Principle (OCP)**.
2. **Ease of Adding Future Vehicle Types**:
   - Adding a future category (e.g., "Autonomous Shuttle" or "Heavy Construction Truck") requires merely creating a new subclass or attaching an existing `PricingRule`. No modifications to the central parking facility are needed.
3. **Test Results for New Categories**:
   - **E-Bike**: 15 mins $\to$ Rs. 20.0; 300 mins $\to$ Rs. 20.0 (Flat fee verified).
   - **Delivery Van (Peak 10:00 AM)**: 2 hours @ double car rate (Rs. 60/hr) = Rs. 120.0 (Peak rate verified).
   - **Delivery Van (Off-Peak 8:00 PM)**: 2 hours @ regular car rate (Rs. 30/hr) = Rs. 60.0 (Off-peak rate verified).
   - **Visitor One-Day Pass**: 8 hours stay billed at Rs. 150.0 flat (Pass verified).
4. **Design Trade-Offs**:
   - *Trade-off*: Introducing `LocalTime` adds temporal awareness, requiring time parameters at checkout.
   - *Benefit*: Full real-world applicability with zero breaking changes to existing methods through method overloading.

---

## Execution Output & Verification Evidence

### Compilation & Execution Commands
```bash
javac Task12/src/*.java -d Task12/bin && java -cp Task12/bin AdaptiveSystemTest
```

### Verified Terminal Output
```text
===============================================================
TASK 12: DAY-4 DESIGN CHALLENGE - ADAPTIVE SYSTEM VALIDATION
Student: Korasika Sudheer | Register Number: 192324313
===============================================================

--- 1. Testing E-Bike (Flat Fee Rule) ---
E-Bike 20 mins fee:  Rs. 20.0 (Expected: Rs. 20.0)
E-Bike 300 mins fee: Rs. 20.0 (Expected: Rs. 20.0)

--- 2. Testing Delivery Van (Peak vs Off-Peak Dynamic Pricing) ---
Van parked at 11:00 AM (Peak) for 2 hrs:    Rs. 120.0 (Expected: Rs. 120.0)
Van parked at 07:30 PM (Off-Peak) for 2 hrs:Rs. 60.0 (Expected: Rs. 60.0)

--- 3. Testing Visitor One-Day Pass (Capped at Rs. 150) ---
Standard 8-hour Car Fee: Rs. 240.0
With Visitor One-Day Pass (Capped at Rs. 150): Rs. 150.0 (Expected: Rs. 150.0)

--- 4. Testing 5-Floor Extended Facility Layout ---
Total Facility Floors:   5
Total Facility Capacity: 300 slots.
  Floor 0 capacity: 20 slots
  Floor 1 capacity: 40 slots
  Floor 2 capacity: 60 slots
  Floor 3 capacity: 80 slots
  Floor 4 capacity: 100 slots
Parked E-Bike at: [0, 0]

>>> DAY-4 DESIGN CHALLENGE VALIDATION: ALL TESTS PASSED SUCCESSFULLY! <<<
```

### Execution Output Screenshot
![Task12 Output Screenshot](output/screenshot.png)
