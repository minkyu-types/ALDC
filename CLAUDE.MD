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

## MCP Server Configuration

This project uses **Supabase MCP Server** to enable Claude Code to interact directly with the Supabase database.

### Setup Instructions

1. **Copy the example configuration:**
   ```bash
   cp .mcp.json.example .mcp.json
   ```

2. **Edit `.mcp.json` with your Supabase credentials:**
   ```json
   {
     "mcpServers": {
       "supabase": {
         "command": "npx",
         "args": ["-y", "@supabase-community/supabase-mcp"],
         "env": {
           "SUPABASE_URL": "https://your-project-id.supabase.co",
           "SUPABASE_KEY": "your-supabase-anon-key"
         }
       }
     }
   }
   ```

3. **Get your Supabase credentials:**
   - Go to [Supabase Dashboard](https://supabase.com/dashboard)
   - Select your project
   - Settings → API
   - Copy `Project URL` and `anon public` key

### Security Notes

- ⚠️ **Never commit `.mcp.json`** to Git (already in `.gitignore`)
- ✅ Use **development/staging project** only, not production
- ✅ `.mcp.json.example` is safe to commit (no real credentials)
- 🔒 The `anon` key is protected by Row Level Security (RLS) policies

### Available MCP Commands

Once configured, Claude Code can:
- Query database tables
- Create/update database records
- Run SQL queries
- Manage database schema
- Work with Supabase Storage

### Troubleshooting

- **MCP not working?** Restart Claude Code after editing `.mcp.json`
- **Permission errors?** Check RLS policies in Supabase Dashboard
- **Connection issues?** Verify your project URL and API key

For more details, see: https://github.com/supabase-community/supabase-mcp

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

## Git Workflow for AI Assistants

### 브랜치 전략

Claude Code와 작업할 때는 다음과 같은 계층적 브랜치 구조를 사용합니다:

1. **세션 브랜치** (Session Branch)
   - 각 Claude 세션마다 하나의 메인 브랜치 생성
   - 네이밍: `claude/session-{SESSION_ID}`
   - 예시: `claude/session-51CS3`
   - 세션의 모든 작업을 위한 베이스 브랜치

2. **피처 브랜치** (Feature Branch)
   - 세션 내에서 각 기능/피처마다 별도 브랜치 생성
   - 네이밍: `claude/session-{SESSION_ID}/{feature-name}`
   - 예시:
     - `claude/session-51CS3/add-login-feature`
     - `claude/session-51CS3/fix-calendar-bug`
     - `claude/session-51CS3/refactor-viewmodel`

3. **작업 흐름**
   ```bash
   # 1. 세션 브랜치 생성 (세션 시작 시 한 번만)
   git checkout -b claude/session-{SESSION_ID}

   # 2. 피처 작업 시작
   git checkout -b claude/session-{SESSION_ID}/{feature-name}

   # 3. 작업 완료 후 커밋
   git add .
   git commit -m "feat: 기능 설명"

   # 4. 푸시 및 PR 생성
   git push -u origin claude/session-{SESSION_ID}/{feature-name}
   gh pr create --base claude/session-{SESSION_ID} --title "피처 제목" --body "설명"

   # 5. 다음 피처로 이동 (세션 브랜치로 돌아가기)
   git checkout claude/session-{SESSION_ID}
   git checkout -b claude/session-{SESSION_ID}/{next-feature-name}
   ```

4. **PR 생성 규칙**
   - 각 피처 완료 시 **반드시 PR 생성**
   - Base branch: 세션 브랜치 (`claude/session-{SESSION_ID}`)
   - PR 제목: 명확하고 간결하게
   - PR 본문: Summary, Test plan, 세션 링크 포함

5. **커밋 메시지 규칙**
   - Conventional Commits 형식 사용
   - 타입: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`
   - 예시: `feat: Add user authentication`, `fix: Resolve calendar crash`
   - 항상 세션 링크 포함: `https://claude.ai/code/session_{SESSION_ID}`

### 예시 시나리오

**세션 51CS3에서 3개의 피처 작업:**

```bash
# 세션 브랜치 생성
git checkout -b claude/session-51CS3

# 피처 1: 로그인 기능
git checkout -b claude/session-51CS3/add-login-feature
# ... 작업 ...
git commit -m "feat: Add login feature"
git push -u origin claude/session-51CS3/add-login-feature
gh pr create --base claude/session-51CS3

# 피처 2: 프로필 화면
git checkout claude/session-51CS3
git checkout -b claude/session-51CS3/add-profile-screen
# ... 작업 ...
git commit -m "feat: Add profile screen"
git push -u origin claude/session-51CS3/add-profile-screen
gh pr create --base claude/session-51CS3

# 피처 3: 버그 수정
git checkout claude/session-51CS3
git checkout -b claude/session-51CS3/fix-dashboard-crash
# ... 작업 ...
git commit -m "fix: Resolve dashboard crash on empty data"
git push -u origin claude/session-51CS3/fix-dashboard-crash
gh pr create --base claude/session-51CS3
```

## Notes for AI Assistants

- This is a **Kotlin Multiplatform** project; code in `commonMain` must be platform-agnostic
- Use **Compose Multiplatform** for UI, not Android-specific Compose APIs
- Follow **MVI pattern** for screens (UiState/Event/Effect)
- Repository implementations are likely in a separate data module (not visible in current structure)
- The app has a **Norse mythology theme**; maintain this aesthetic in naming and UX
- All business logic belongs in **UseCases**, not ViewModels
- ViewModels should be thin orchestrators that call UseCases
- **IMPORTANT**: 위의 Git Workflow를 반드시 따를 것 - 세션 브랜치 + 피처별 서브 브랜치 + 각 피처마다 PR 생성
