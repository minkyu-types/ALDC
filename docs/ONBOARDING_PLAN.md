# 온보딩 화면 구현 플랜 - A Long Dark Cave

## 컨셉 개요

**타겟 사용자**: 큰 시련을 겪은 뒤 환골탈태를 위해 이 악물고 노력하는 사람들
**앱 역할**:
- 🧠 생각상자 (CaveDiary - 회고/감사)
- 🔄 루틴 관리 시스템 (Routine/Mission)
- 🏆 성취 기록장 (Achievement/Milestone)
- 📅 계획 플래너 (Goal/Motivation)

**테마**: 어둠 속 동굴을 헤매다 빛을 찾아가는 여정 (Norse 신화 - Ask의 탄생)

---

## 온보딩 흐름 설계

### 스토리텔링 아크: "긴 어둠의 동굴에서 빛을 향해"

```
어둠 → 깨달음 → 다짐 → 첫 걸음 → 시작
```

---

## 화면 구성 (총 7단계)

### 1단계: 스플래시 & 인트로 애니메이션 (3-5초)
**화면**: `SplashScreen`
```
┌─────────────────────┐
│                     │
│    [어두운 배경]     │
│                     │
│    "A Long Dark     │
│       Cave"         │
│                     │
│   [희미한 빛 효과]   │
│                     │
└─────────────────────┘
```

**목적**: 분위기 조성, 브랜드 각인
**애니메이션**:
- 어두운 배경에서 서서히 로고와 타이틀 페이드인
- 희미한 빛 번짐 효과 (Lottie)
- 자동으로 2단계로 전환

---

### 2단계: 공감 & 위로
**화면**: `OnboardingWelcomeScreen`

```
┌─────────────────────┐
│                     │
│   긴 어둠 속에서    │
│   당신은 혼자가     │
│    아닙니다.        │
│                     │
│  [동굴 속 실루엣]   │
│                     │
│   지금 이 순간,     │
│   한 걸음을 내딛는  │
│   용기만 있으면     │
│     됩니다.         │
│                     │
│    [다음 →]         │
└─────────────────────┘
```

**목적**: 사용자의 감정 상태 공감, 심리적 안정감
**텍스트**:
- "긴 어둠 속에서 당신은 혼자가 아닙니다"
- "지금 이 순간, 한 걸음을 내딛는 용기만 있으면 됩니다"
**비주얼**: 동굴 속 실루엣, 따뜻한 톤

---

### 3단계: Ask의 전설 (스토리텔링)
**화면**: `OnboardingStoryScreen`

```
┌─────────────────────┐
│                     │
│  Norse 신화 속      │
│  첫 인간 'Ask'는    │
│  Yggdrasil의        │
│  뿌리에서 태어나    │
│  어둠을 헤치고      │
│  빛을 찾았습니다    │
│                     │
│ [Yggdrasil 일러스트]│
│                     │
│  당신도 Ask처럼     │
│  새롭게 태어날      │
│  준비가 되셨나요?   │
│                     │
│    [다음 →]         │
└─────────────────────┘
```

**목적**: 브랜드 스토리 전달, 환골탈태 메타포
**텍스트**: Ask 전설을 현대적으로 재해석
**비주얼**: Yggdrasil 일러스트, 신비로운 분위기

---

### 4단계: 앱 핵심 기능 소개 (3-4 페이지 슬라이드)
**화면**: `OnboardingFeaturesScreen` (HorizontalPager)

#### 4-1. 생각상자 (CaveDiary)
```
┌─────────────────────┐
│                     │
│   🧠 생각상자       │
│                     │
│  [일기장 아이콘]    │
│                     │
│  당신의 하루를      │
│  기록하고 돌아보는  │
│  개인적인 동굴      │
│                     │
│  • 회고 일기        │
│  • 감사 기록        │
│  • 감정 추적        │
│                     │
│   [• • • ○]         │
└─────────────────────┘
```

#### 4-2. 루틴 관리
```
┌─────────────────────┐
│                     │
│   🔄 루틴 관리      │
│                     │
│  [체크리스트 아이콘]│
│                     │
│  매일의 작은        │
│  실천이 모여        │
│  큰 변화를 만듭니다 │
│                     │
│  • 일일 미션        │
│  • 반복 루틴        │
│  • 진행률 추적      │
│                     │
│   [• • ○ •]         │
└─────────────────────┘
```

#### 4-3. 성취 기록장
```
┌─────────────────────┐
│                     │
│   🏆 성취 기록      │
│                     │
│  [트로피 아이콘]    │
│                     │
│  작은 승리도        │
│  기록되고           │
│  축하받아야 합니다  │
│                     │
│  • 달성 기록        │
│  • 마일스톤         │
│  • 성장 추적        │
│                     │
│   [• ○ • •]         │
└─────────────────────┘
```

#### 4-4. 계획 플래너
```
┌─────────────────────┐
│                     │
│   📅 계획 플래너    │
│                     │
│  [달력 아이콘]      │
│                     │
│  명확한 목표와      │
│  구체적인 계획으로  │
│  빛을 향해 나아가세요│
│                     │
│  • 목표 설정        │
│  • 기간 관리        │
│  • 우선순위         │
│                     │
│   [○ • • •]         │
└─────────────────────┘
```

**목적**: 앱의 4가지 핵심 기능을 직관적으로 전달
**인터랙션**: 좌우 스와이프, 페이지 인디케이터

---

### 5단계: 알림 권한 요청 (선택적)
**화면**: `OnboardingPermissionScreen`

```
┌─────────────────────┐
│                     │
│  🔔 일일 알림       │
│                     │
│  [알림 벨 아이콘]   │
│                     │
│  매일 정해진 시간   │
│  작은 알림으로      │
│  당신의 여정을      │
│  응원합니다         │
│                     │
│  • 루틴 리마인더    │
│  • 회고 시간 알림   │
│  • 동기부여 메시지  │
│                     │
│  [알림 허용]        │
│  [나중에]           │
│                     │
└─────────────────────┘
```

**목적**: 푸시 알림 권한 요청 (부담스럽지 않게)
**옵션**: "나중에" 선택 가능

---

### 6단계: 첫 목표 설정 (Quick Start)
**화면**: `OnboardingFirstGoalScreen`

```
┌─────────────────────┐
│                     │
│  첫 번째 목표를     │
│  설정해볼까요?      │
│                     │
│  ┌───────────────┐  │
│  │ 목표 제목...  │  │
│  └───────────────┘  │
│                     │
│  카테고리 선택:     │
│  [건강] [커리어]    │
│  [경제] [관계]...   │
│                     │
│  기간:              │
│  [1개월] [3개월]    │
│  [6개월] [1년]      │
│                     │
│  [건너뛰기] [완료]  │
│                     │
└─────────────────────┘
```

**목적**: 첫 목표 설정으로 즉시 사용 유도 (선택적)
**옵션**: "건너뛰기" 가능 - 부담 최소화
**저장**: NewMotivationScreen 로직 재사용

---

### 7단계: 환영 메시지 & 앱 시작
**화면**: `OnboardingCompleteScreen`

```
┌─────────────────────┐
│                     │
│   환영합니다,       │
│    [사용자명]       │
│                     │
│  [빛이 보이는       │
│   동굴 출구 이미지] │
│                     │
│   당신의 여정이     │
│   지금 시작됩니다   │
│                     │
│  "한 치 앞도        │
│   보이지 않는       │
│   어둠 속에서도     │
│   빛은 결국         │
│   찾아옵니다"       │
│                     │
│   [시작하기]        │
│                     │
└─────────────────────┘
```

**목적**: 감동적인 마무리, 앱으로 자연스러운 전환
**액션**: "시작하기" → HomeScreen으로 이동

---

## 기술 구현 상세

### 화면별 구현 요소

#### SplashScreen
```kotlin
- LaunchedEffect + delay(3000)
- AnimatedVisibility로 페이드 인/아웃
- Lottie 애니메이션 (빛 효과)
- 자동 전환 to OnboardingWelcomeScreen
```

#### OnboardingWelcomeScreen
```kotlin
- Column + Spacer 레이아웃
- 텍스트 애니메이션 (SlideIn)
- 동굴 일러스트 (PNG/SVG)
- "다음" 버튼 → OnboardingStoryScreen
```

#### OnboardingStoryScreen
```kotlin
- 스토리텔링 텍스트
- Yggdrasil 일러스트
- 페이드 인 애니메이션
- "다음" 버튼 → OnboardingFeaturesScreen
```

#### OnboardingFeaturesScreen
```kotlin
- HorizontalPager (4 pages)
- 각 페이지: 아이콘 + 제목 + 설명 + 불릿 포인트
- 페이지 인디케이터 (dots)
- "다음" 버튼 (마지막 페이지에서만 → 다음 단계)
```

#### OnboardingPermissionScreen
```kotlin
- 알림 권한 요청 (Android/iOS native API)
- "알림 허용" → 권한 요청 다이얼로그
- "나중에" → 다음 단계로 스킵
```

#### OnboardingFirstGoalScreen
```kotlin
- TextField (목표 제목)
- Category 선택 (LazyRow)
- 기간 선택 (Radio buttons)
- "건너뛰기" → OnboardingCompleteScreen
- "완료" → Goal 저장 + OnboardingCompleteScreen
```

#### OnboardingCompleteScreen
```kotlin
- 환영 메시지
- 동굴 출구 이미지 (빛 효과)
- 명언 표시
- "시작하기" → HomeScreen + 온보딩 완료 플래그 저장
```

---

## 데이터 저장

### SharedPreferences / DataStore
```kotlin
data class OnboardingState(
    val isCompleted: Boolean = false,
    val currentStep: Int = 0,
    val hasFirstGoal: Boolean = false,
    val notificationPermissionAsked: Boolean = false
)
```

**저장 위치**: `domain/src/.../model/OnboardingState.kt`
**Repository**: `OnboardingRepository` 생성 필요

---

## UX 고려사항

### 1. 스킵 가능성
- 모든 단계는 "건너뛰기" 또는 "나중에" 옵션 제공
- 사용자에게 부담을 주지 않음
- 최소한의 정보만 수집

### 2. 진행률 표시
- 상단에 프로그레스바 표시 (1/7, 2/7...)
- 사용자가 현재 위치와 남은 단계를 알 수 있도록

### 3. 뒤로가기 허용
- 모든 단계에서 이전 단계로 돌아갈 수 있음
- 단, 스플래시는 제외

### 4. 다크모드 우선
- 앱 컨셉에 맞게 다크 테마가 기본
- 어두운 배경 + 따뜻한 accent 컬러 (호박색/금색)

### 5. 애니메이션 최소화
- 과도한 애니메이션은 피로감 유발
- 자연스러운 페이드/슬라이드만 사용

### 6. 접근성
- 모든 텍스트는 충분한 크기 (16sp 이상)
- 컨트라스트 비율 준수 (WCAG AA)
- 스크린 리더 지원

---

## 구현 우선순위

### Phase 1: 핵심 온보딩 (P0)
**목표**: 최소한의 온보딩으로 앱 사용 시작
```
1. SplashScreen (필수)
2. OnboardingWelcomeScreen (공감/위로)
3. OnboardingStoryScreen (스토리텔링)
4. OnboardingCompleteScreen (시작)
→ 바로 HomeScreen 진입
```

**예상 작업 시간**: 2-3일
**필요 요소**:
- 기본 레이아웃
- 네비게이션
- 온보딩 완료 플래그 저장

---

### Phase 2: 기능 소개 (P1)
**목표**: 앱의 4가지 핵심 기능 설명
```
추가:
- OnboardingFeaturesScreen (HorizontalPager, 4 pages)
```

**예상 작업 시간**: 1-2일
**필요 요소**:
- HorizontalPager 구현
- 페이지 인디케이터
- 아이콘/일러스트

---

### Phase 3: Quick Start (P2)
**목표**: 첫 목표 설정으로 즉시 사용 유도
```
추가:
- OnboardingFirstGoalScreen
```

**예상 작업 시간**: 1일
**필요 요소**:
- NewMotivation 로직 재사용
- Goal 저장 기능

---

### Phase 4: 권한 요청 (P3)
**목표**: 알림 권한 요청
```
추가:
- OnboardingPermissionScreen
- 플랫폼별 권한 요청 로직
```

**예상 작업 시간**: 1일
**필요 요소**:
- Android: PermissionManager
- iOS: UserNotifications framework

---

## 파일 구조

```
sharedUI/src/commonMain/kotlin/dev/loki/ask/
├── screen/
│   └── onboarding/
│       ├── SplashScreen.kt
│       ├── OnboardingWelcomeScreen.kt
│       ├── OnboardingStoryScreen.kt
│       ├── OnboardingFeaturesScreen.kt
│       ├── OnboardingPermissionScreen.kt
│       ├── OnboardingFirstGoalScreen.kt
│       ├── OnboardingCompleteScreen.kt
│       ├── OnboardingViewModel.kt
│       ├── OnboardingUiState.kt
│       ├── OnboardingEvent.kt
│       └── OnboardingEffect.kt
│
├── component/
│   └── onboarding/
│       ├── OnboardingProgressBar.kt
│       ├── OnboardingButton.kt
│       ├── FeaturePage.kt
│       └── PageIndicator.kt
│
└── navigation/
    └── OnboardingNavigation.kt

domain/src/commonMain/kotlin/dev/loki/domain/
├── model/
│   └── OnboardingState.kt
├── repository/
│   └── OnboardingRepository.kt
└── usecase/
    └── onboarding/
        ├── CompleteOnboardingUseCase.kt
        ├── GetOnboardingStateUseCase.kt
        └── SaveFirstGoalUseCase.kt
```

---

## 디자인 에셋 필요 항목

### 일러스트/이미지
1. 동굴 속 실루엣 (OnboardingWelcomeScreen)
2. Yggdrasil 세계수 (OnboardingStoryScreen)
3. 동굴 출구 with 빛 (OnboardingCompleteScreen)
4. 각 기능별 아이콘 (생각상자, 루틴, 성취, 플래너)

### Lottie 애니메이션
1. 빛 번짐 효과 (SplashScreen)
2. 반짝임 효과 (선택적)

### 컬러 팔레트
```kotlin
// 다크 테마 기본
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val AccentAmber = Color(0xFFFFB74D) // 호박색
val AccentGold = Color(0xFFFFD700)  // 금색
val TextPrimary = Color(0xFFE0E0E0)
val TextSecondary = Color(0xFFB0B0B0)
```

---

## 성공 지표 (KPI)

### 온보딩 완료율
- 목표: 80% 이상
- 측정: 온보딩 시작 → 완료 비율

### 첫 목표 설정율
- 목표: 50% 이상
- 측정: 온보딩 중 첫 Goal 생성 비율

### 알림 권한 허용율
- 목표: 60% 이상
- 측정: 알림 권한 요청 → 허용 비율

### 1일 재방문율
- 목표: 40% 이상
- 측정: 온보딩 완료 후 24시간 내 재방문

---

## 다음 단계

1. **디자인 에셋 준비** (일러스트, 아이콘)
2. **Phase 1 구현 시작** (SplashScreen부터)
3. **OnboardingRepository & UseCase 구현**
4. **테스트 및 UX 검증**

이 플랜으로 진행할까요?
