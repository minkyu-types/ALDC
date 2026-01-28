# CLAUDE.MD - A Long Dark Cave (ALDC)

## Project Overview

**A Long Dark Cave** is a motivation and goal-tracking app inspired by Norse mythology, specifically the legend of Ask—the first human created by the gods from the roots of Yggdrasil. The app helps users set goals ranging from daily quests to decade-long epics, with daily check-ins and optional community sharing.

**Package**: `dev.loki.ask`
**Backend**: Supabase
**Platforms**: Android, iOS (via Kotlin Multiplatform)

## Project Structure

```
ALDC/
├── domain/              # Business logic & domain models
├── sharedUI/            # Compose Multiplatform UI (shared across platforms)
├── androidApp/          # Android-specific code
├── iosApp/              # iOS-specific code
├── gradle/              # Gradle wrapper
└── build.gradle.kts     # Root build configuration
```

### Module Responsibilities

- **domain**: Clean architecture domain layer containing:
  - Models (Motivation, Goal, User, Achievement, etc.)
  - Repositories (interfaces)
  - UseCases (business logic)
  - Types/Enums (Category, Priority, Mood, Gender, etc.)

- **sharedUI**: Compose Multiplatform UI layer containing:
  - Screens (Dashboard, Home, NewMotivation)
  - ViewModels (using MVI pattern with UiState/Event/Effect)
  - Reusable components (Topbar, ReadableIcon, etc.)
  - Navigation (NavRoute)

- **androidApp**: Android-specific implementation
- **iosApp**: iOS-specific implementation

## Tech Stack

### Core Technologies
- **Language**: Kotlin (Multiplatform)
- **UI Framework**: Compose Multiplatform
- **Architecture**: Clean Architecture + MVI pattern
- **DI**: Koin
- **HTTP Client**: Ktorfit
- **Async**: Kotlin Coroutines & Flow
- **Serialization**: Kotlinx Serialization
- **Date/Time**: Kotlinx Datetime
- **Logging**: Kermit
- **Animations**: Compottie (Lottie for Compose Multiplatform)
- **Build**: Gradle with KSP

### Backend
- Supabase (authentication, database, storage)

## Domain Models

Key domain models include:

- **Motivation**: Core entity for user motivations/goals
- **Goal**: User goals with tracking
- **Mission**: Specific tasks or missions
- **Routine**: Recurring activities
- **Achievement**: User achievements
- **CaveDiary**: Journal entries
- **DailyData/MonthlyData**: Calendar tracking data
- **Quote**: Inspirational quotes
- **User**: User profile
- **Milestone**: Goal milestones
- **ThankfulThing**: Gratitude tracking
- **Weakness**: Personal challenges to overcome

### Domain Types
- **Category**: Goal categories
- **Priority**: Task priorities
- **Mood**: User mood tracking
- **Gender**: User gender
- **RepeatType**: Recurring task patterns
- **AdventureStatus**: Status tracking for goals/adventures

## Architecture Patterns

### MVI (Model-View-Intent)
All screens follow MVI pattern:
- **UiState**: Immutable state data class
- **Event**: User interactions/intents
- **Effect**: One-time side effects (navigation, toasts, etc.)
- **ViewModel**: State management

Example:
```kotlin
// Dashboard screen
- DashboardUiState
- DashboardViewModel
- DashboardScreen
```

### Clean Architecture Layers
1. **Domain Layer** (`domain/`): Pure Kotlin, platform-independent
   - Models, UseCases, Repository interfaces
2. **Presentation Layer** (`sharedUI/`): Compose Multiplatform UI
   - ViewModels, Screens, Components
3. **Data Layer** (likely in data module or implementation): Repository implementations

## Use Cases

Organized by feature:

### Calendar
- `GetDailyDataUseCase`
- `GetMonthlyDataUseCase`

### Motivation
- `CreateMotivationUseCase`
- `UpdateMotivationUseCase`
- `DeleteMotivationUseCase`

### User
- `SignInUseCase`
- `SignUpUseCase`
- `GetMyProfileUseCase`
- `UpdateMyProfileUseCase`

### Quote
- `GetTodayQuoteUseCase`

## Development Guidelines

### Code Style
- Follow Kotlin coding conventions
- Use immutable data classes for state
- Prefer composition over inheritance
- Use dependency injection (Koin)
- Keep ViewModels platform-agnostic

### Naming Conventions
- **Screens**: `[Feature]Screen.kt`, `[Feature]ViewModel.kt`, `[Feature]UiState.kt`
- **UseCases**: `[Action][Entity]UseCase.kt` (e.g., `CreateMotivationUseCase`)
- **Repositories**: `[Entity]Repository.kt`
- **Models**: PascalCase, descriptive names

### File Organization
- Group by feature, not by type
- Keep related files together (Screen, ViewModel, UiState in same package)

## Key Features

Based on domain models and screens:

1. **Goal Setting**: Create motivations/goals with categories and priorities
2. **Daily Tracking**: Check-ins and progress updates
3. **Calendar View**: Monthly and daily data visualization
4. **Achievements**: Track and celebrate milestones
5. **Quotes**: Daily inspirational quotes
6. **Community**: Share progress with others (public feed)
7. **Diary/Journal**: Cave diary for reflections
8. **Gratitude Tracking**: Record thankful things
9. **Routine Management**: Recurring tasks and habits

## Navigation

Main routes (defined in `NavRoute.kt`):
- Dashboard
- Home
- NewMotivation
- (Other routes as defined in the app)

## Utilities

- **LocalDateTimeExtensions.kt**: Extension functions for date/time manipulation
- **HapticProperties.kt**: Haptic feedback configuration

## UI Components

Reusable components in `sharedUI/src/commonMain/kotlin/dev/loki/ask/component/`:
- `Topbar`: App bar/toolbar
- `ReadableIcon`: Icon with accessibility support
- `FullWidthTextButton`: Full-width button component
- `GradientCircularProgressIndicator`: Custom progress indicator
- `CircleBgIcon`: Circular background icon

## Build & Development

### IDEs
- Android Studio (recommended for Android/shared code)
- Xcode (required for iOS development)

### Build Commands
```bash
./gradlew build                    # Build all modules
./gradlew :androidApp:assembleDebug # Build Android app
./gradlew :sharedUI:build          # Build shared UI module
./gradlew :domain:build            # Build domain module
```

### Icon Generation
- Use `generate_app_icons.sh` for app icon generation

## CI/CD
See `CI-CD-SETUP.md` for continuous integration and deployment setup.

## Norse Mythology Theme

The app uses Norse mythology naming and concepts:
- **Ask**: First human created by the gods (app mascot/identity)
- **Yggdrasil**: World tree (community/connection metaphor)
- Goals are "quests" or "adventures"
- Progress tracking as a hero's journey

## Common Tasks

### Adding a New Feature
1. Create domain model in `domain/src/commonMain/kotlin/dev/loki/domain/model/`
2. Create repository interface in `domain/src/commonMain/kotlin/dev/loki/domain/repository/`
3. Create use cases in `domain/src/commonMain/kotlin/dev/loki/domain/usecase/[feature]/`
4. Create screen package in `sharedUI/src/commonMain/kotlin/dev/loki/ask/screen/[feature]/`
5. Implement UiState, ViewModel, and Screen composables
6. Add navigation route in `NavRoute.kt`
7. Wire up DI in Koin modules

### Adding a New UI Component
1. Create in `sharedUI/src/commonMain/kotlin/dev/loki/ask/component/`
2. Make it `@Composable` and platform-agnostic
3. Use material design guidelines
4. Consider accessibility (haptics, screen readers)

## Notes for AI Assistants

- This is a **Kotlin Multiplatform** project; code in `commonMain` must be platform-agnostic
- Use **Compose Multiplatform** for UI, not Android-specific Compose APIs
- Follow **MVI pattern** for screens (UiState/Event/Effect)
- Repository implementations are likely in a separate data module (not visible in current structure)
- The app has a **Norse mythology theme**; maintain this aesthetic in naming and UX
- All business logic belongs in **UseCases**, not ViewModels
- ViewModels should be thin orchestrators that call UseCases
