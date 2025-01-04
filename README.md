


# Registration SDK

A **two-step** Android registration flow using **Jetpack Compose**, **CameraX**, and **ML Kit**:

1. **Step One:** Collect user data (Name, Phone, Email, Password) with validation.
2. **Step Two:** Automatically capture a selfie when the user **smiles** or **blinks**, then save to a local database.

<div align="center">
  <img src="https://github.com/user-attachments/assets/edbafa94-7f5e-44c9-ad09-ee76b8ed4753" width="250" alt="Screenshot Step 1"/>
  <img src="https://github.com/user-attachments/assets/c58c4731-6424-40d6-a815-2df3ac5a9326" width="250" alt="Screenshot Step 2"/>
  <img src="https://github.com/user-attachments/assets/297cfbb6-8344-41c0-99b3-2c8a68d302ba" width="250" alt="Screenshot User List"/>
</div>


This SDK demonstrates **clean architecture**, **SOLID principles**, **Hilt DI**, **Coroutines**, and **Room** while maintaining a clear separation of concerns.

---

## Features

- **Data Input & Validation**  
  - Username, phone, email, and password must be valid.  
  - Validation logic (TDD approach) resides in separate use case classes (e.g. `ValidateEmailUseCase`).

- **Face Detection Pipeline**  
  - Integrates **ML Kit** to detect smiles or blinks in real time.  
  - **`FaceWorkflow`** orchestrates detection steps (e.g. `SmileDetectionStep`, `EyesBlinkDetectionStep`) without modifying existing tasks (Open/Closed Principle).

- **Shared ViewModel**  
  - Holds user data from Step 1 so it persists seamlessly into Step 2.  
  - Avoids passing complex data via navigation arguments; we share state across screens.

- **Extensibility**  
  - Additional detection steps (e.g., `RotateHeadDetectionStep`) can be added by implementing the `DetectionStep` interface, no need to rewrite existing logic.

- **Room Database**  
  - Stores user records and captured images.  
  - Includes tested DAOs (`UserDaoTest`) and repository logic (`RegisterRepositoryImplTest`).

---

## Project Structure

```
├── detection
│    ├── FaceWorkflow.kt
│    ├── FaceAnalysisProcessor.kt
│    └── tasks
│         ├── DetectionStep.kt
│         ├── SmileDetectionStep.kt
│         └── EyesBlinkDetectionStep.kt
├── presentation
│    ├── screens
│    │    ├── register (FirstStepScreen, SecondStepScreen, shared ViewModel)
│    │    └── user_list
│    └── core (base classes, navigation, components)
├── domain
│    ├── model (UserModel, etc.)
│    ├── usecase (Validation logic, RegisterUseCase)
│    └── repository (RegisterRepository interface)
├── data
│    ├── local (AppDatabase, UserDao)
│    └── repository (RegisterRepositoryImpl)
└── ...
```

---

## How It Works

1. **User Data Collection**  
   - The **FirstStepScreen** validates name, phone, email, and password.  
   - Successful validation triggers navigation to **SecondStepScreen**.

2. **Smile/Blink Detection**  
   - **SecondStepScreen** uses **CameraX** + **ML Kit** to preview the front camera.  
   - A **FaceAnalysisProcessor** feeds each frame into a **FaceWorkflow**, which runs one or more `DetectionStep`s.  
   - On smile or blink detection, the screen captures and saves the photo automatically.

3. **Shared Registration**  
   - The **SharedRegistrationViewModel** holds user info from Step 1.  
   - Once the selfie is captured, the final data (including image path) is written to a **Room** DB.

---

## Why a Shared ViewModel?

- **Multi-Screen Data**: We maintain state from Step 1 (user details) into Step 2 (camera capture).  
- **Consistent Updates**: Any change in user data automatically reflects in both screens.  
- **Cleaner Navigation**: We avoid passing large objects across routes, simplifying the NavController usage.

- Once you exit this nested navigation flow (pop out of the graph), the associated shared ViewModel is cleared
---

## Adding More Detection Steps

The **Open/Closed** design means you simply:
1. Create a new class implementing `DetectionStep` (e.g., `EyesBlinkDetectionStep`).
2. Inject it into the **FaceWorkflow** steps list.

No rewrites needed for existing tasks or workflow logic.

---

## Testing

- **Validation Use Cases**  
  - Thorough JUnit tests ensure correct behavior for email, phone, password checks.  
- **Data Layer**  
  - DAO tested with an in-memory Room DB (`UserDaoTest`).  
  - Repository tested with Mockito to verify correct mapping (`RegisterRepositoryImplTest`).

---

## Getting Started

1. **Clone** this repo and open in **Android Studio**.  
2. **Run** the app on a device/emulator with camera support.  
3. **Step 1**: Enter valid user info.  
4. **Step 2**: Smile or Blink to auto-capture your selfie.  
5. Check “Users List” to see saved records in the local database.

---



**Thank you for reviewing** this Registration SDK!  
For questions or feedback, please contact [kareemaboelatta@gmail.com].
