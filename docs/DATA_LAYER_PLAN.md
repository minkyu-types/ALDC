# 데이터 레이어 구축 플랜 - A Long Dark Cave

## 목표

Supabase를 백엔드로 사용하는 완전한 데이터 레이어 구축:
1. **Supabase 데이터베이스 스키마 설계**
2. **Supabase Authentication 설정**
3. **Data 모듈 생성 및 Repository 구현**
4. **REST API 연동 (Ktor Client)**
5. **로컬 캐싱 전략 (선택적)**

---

## 1. Supabase 데이터베이스 스키마

### 1.1 테이블 설계

#### users (확장 프로필)
Supabase Auth의 기본 users 테이블을 확장하는 프로필 테이블

```sql
CREATE TABLE public.user_profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    username VARCHAR(50) UNIQUE NOT NULL,
    display_name VARCHAR(100),
    gender VARCHAR(10),
    birth_date DATE,
    avatar_url TEXT,
    bio TEXT,
    is_public BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- RLS 정책
ALTER TABLE public.user_profiles ENABLE ROW LEVEL SECURITY;

-- 자신의 프로필은 읽기/쓰기 가능
CREATE POLICY "Users can view own profile"
    ON public.user_profiles FOR SELECT
    USING (auth.uid() = id);

CREATE POLICY "Users can update own profile"
    ON public.user_profiles FOR UPDATE
    USING (auth.uid() = id);

-- 공개 프로필은 모두가 읽기 가능
CREATE POLICY "Public profiles are viewable by everyone"
    ON public.user_profiles FOR SELECT
    USING (is_public = true);
```

**컬럼 설명**:
- `id`: auth.users와 1:1 관계
- `username`: 고유한 사용자명
- `display_name`: 표시 이름
- `gender`: 성별 (MALE/FEMALE)
- `is_public`: 커뮤니티 공개 여부

---

#### goals (목표)
```sql
CREATE TABLE public.goals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50) NOT NULL,
    priority VARCHAR(20) DEFAULT 'NORMAL',
    icon VARCHAR(50),
    i_was TEXT,
    i_want_to_be TEXT,
    start_date DATE,
    target_date DATE,
    is_completed BOOLEAN DEFAULT false,
    completed_at TIMESTAMPTZ,
    is_public BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 인덱스
CREATE INDEX idx_goals_user_id ON public.goals(user_id);
CREATE INDEX idx_goals_category ON public.goals(category);
CREATE INDEX idx_goals_is_completed ON public.goals(is_completed);

-- RLS
ALTER TABLE public.goals ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own goals"
    ON public.goals
    USING (auth.uid() = user_id);

CREATE POLICY "Public goals are viewable"
    ON public.goals FOR SELECT
    USING (is_public = true);
```

**컬럼 설명**:
- `category`: 건강·운동, 커리어·공부, 경제, 관계·커뮤니티, 마인드셋, 자기성장, 창의성, 취미
- `priority`: LOW, NORMAL, HIGH
- `i_was`: 과거의 나 (환골탈태 전)
- `i_want_to_be`: 되고 싶은 나 (환골탈태 후)

---

#### milestones (중간 목표)
```sql
CREATE TABLE public.milestones (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    goal_id UUID NOT NULL REFERENCES public.goals(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    order_index INTEGER NOT NULL,
    start_date DATE,
    end_date DATE,
    is_completed BOOLEAN DEFAULT false,
    completed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_milestones_goal_id ON public.milestones(goal_id);
CREATE INDEX idx_milestones_user_id ON public.milestones(user_id);

ALTER TABLE public.milestones ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own milestones"
    ON public.milestones
    USING (auth.uid() = user_id);
```

**컬럼 설명**:
- `order_index`: 마일스톤 순서 (1, 2, 3...)
- Goal 삭제 시 연관된 Milestone도 자동 삭제 (CASCADE)

---

#### achievements (성과)
```sql
CREATE TABLE public.achievements (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    milestone_id UUID NOT NULL REFERENCES public.milestones(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    is_completed BOOLEAN DEFAULT false,
    completed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_achievements_milestone_id ON public.achievements(milestone_id);
CREATE INDEX idx_achievements_user_id ON public.achievements(user_id);

ALTER TABLE public.achievements ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own achievements"
    ON public.achievements
    USING (auth.uid() = user_id);
```

---

#### routines (루틴)
```sql
CREATE TABLE public.routines (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    repeat_type VARCHAR(20) NOT NULL, -- DAILY, WEEKLY, MONTHLY
    repeat_days INTEGER[], -- [0,1,2,3,4,5,6] for weekdays (0=Sunday)
    time_of_day TIME,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_routines_user_id ON public.routines(user_id);

ALTER TABLE public.routines ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own routines"
    ON public.routines
    USING (auth.uid() = user_id);
```

**컬럼 설명**:
- `repeat_type`: DAILY (매일), WEEKLY (주간), MONTHLY (월간)
- `repeat_days`: 요일 선택 (예: [1,3,5] = 월,수,금)
- `time_of_day`: 루틴 수행 시간

---

#### missions (미션)
```sql
CREATE TABLE public.missions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    routine_id UUID REFERENCES public.routines(id) ON DELETE SET NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    priority VARCHAR(20) DEFAULT 'NORMAL',
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_completed BOOLEAN DEFAULT false,
    completed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_missions_user_id ON public.missions(user_id);
CREATE INDEX idx_missions_routine_id ON public.missions(routine_id);
CREATE INDEX idx_missions_date_range ON public.missions(start_date, end_date);

ALTER TABLE public.missions ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own missions"
    ON public.missions
    USING (auth.uid() = user_id);
```

**컬럼 설명**:
- `routine_id`: 루틴에서 생성된 미션인 경우 연결
- 기간이 정해진 일회성 또는 반복 작업

---

#### cave_diaries (회고 일기)
```sql
CREATE TABLE public.cave_diaries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    mood VARCHAR(50) NOT NULL,
    content TEXT,
    is_public BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(user_id, date) -- 하루에 하나의 일기만
);

CREATE INDEX idx_cave_diaries_user_id ON public.cave_diaries(user_id);
CREATE INDEX idx_cave_diaries_date ON public.cave_diaries(date);

ALTER TABLE public.cave_diaries ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own diaries"
    ON public.cave_diaries
    USING (auth.uid() = user_id);

CREATE POLICY "Public diaries are viewable"
    ON public.cave_diaries FOR SELECT
    USING (is_public = true);
```

**컬럼 설명**:
- `mood`: 한 치 앞도 보이지 않는, 방황하고 흔들리는, 묵묵히 나아갈 뿐, 희망이 보이는, 노력이 헛되지 않음
- `date`: 일기 날짜 (하루 1개 제한)

---

#### thankful_things (감사 항목)
```sql
CREATE TABLE public.thankful_things (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    diary_id UUID NOT NULL REFERENCES public.cave_diaries(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_thankful_things_diary_id ON public.thankful_things(diary_id);
CREATE INDEX idx_thankful_things_user_id ON public.thankful_things(user_id);

ALTER TABLE public.thankful_things ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own thankful things"
    ON public.thankful_things
    USING (auth.uid() = user_id);
```

**설명**: CaveDiary에 여러 감사 항목을 추가

---

#### weaknesses (약점)
```sql
CREATE TABLE public.weaknesses (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    is_overcome BOOLEAN DEFAULT false,
    overcome_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_weaknesses_user_id ON public.weaknesses(user_id);

ALTER TABLE public.weaknesses ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own weaknesses"
    ON public.weaknesses
    USING (auth.uid() = user_id);
```

**설명**: 극복해야 할 약점/도전 과제

---

#### quotes (명언)
```sql
CREATE TABLE public.quotes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- 관리자만 명언 추가 가능
ALTER TABLE public.quotes ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Anyone can view quotes"
    ON public.quotes FOR SELECT
    USING (is_active = true);
```

**설명**: 시스템에서 제공하는 명언 (관리자가 관리)

---

#### adventure_status (모험 상태)
```sql
CREATE TABLE public.adventure_status (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID UNIQUE NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    consecutive_days INTEGER DEFAULT 0,
    total_completed_goals INTEGER DEFAULT 0,
    total_completed_milestones INTEGER DEFAULT 0,
    total_completed_achievements INTEGER DEFAULT 0,
    total_completed_missions INTEGER DEFAULT 0,
    last_activity_date DATE,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_adventure_status_user_id ON public.adventure_status(user_id);

ALTER TABLE public.adventure_status ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can view own adventure status"
    ON public.adventure_status FOR SELECT
    USING (auth.uid() = user_id);

CREATE POLICY "Users can update own adventure status"
    ON public.adventure_status FOR UPDATE
    USING (auth.uid() = user_id);
```

**설명**: 사용자의 전체 진행 상황 추적

---

### 1.2 ER Diagram 관계

```
auth.users (Supabase Auth)
    ↓ 1:1
user_profiles

auth.users
    ↓ 1:N
goals → milestones → achievements
    ↓ 1:N        ↓ 1:N
routines    missions
    ↓ 1:N
cave_diaries → thankful_things
    ↓ 1:N
weaknesses

adventure_status (1:1 with users)

quotes (독립)
```

---

## 2. Supabase Authentication 설계

### 2.1 인증 방법

**지원할 인증:**
1. **Email/Password** (기본)
2. **소셜 로그인** (선택적 - Phase 2)
   - Google
   - Apple (iOS 필수)

### 2.2 Auth Flow

#### 회원가입
```kotlin
// 1. Supabase Auth 회원가입
val authResponse = supabaseClient.auth.signUp {
    email = userEmail
    password = userPassword
}

// 2. user_profiles 테이블에 초기 프로필 생성
val userId = authResponse.user?.id
supabaseClient.from("user_profiles").insert(
    UserProfileDto(
        id = userId,
        username = username,
        display_name = displayName
    )
)

// 3. adventure_status 초기화
supabaseClient.from("adventure_status").insert(
    AdventureStatusDto(user_id = userId)
)
```

#### 로그인
```kotlin
val authResponse = supabaseClient.auth.signInWith(Email) {
    email = userEmail
    password = userPassword
}

// 세션 토큰 저장
val accessToken = authResponse.session?.accessToken
```

#### 로그아웃
```kotlin
supabaseClient.auth.signOut()
// 로컬 토큰 삭제
```

### 2.3 세션 관리

- **Access Token**: API 요청에 사용
- **Refresh Token**: Access Token 갱신
- **자동 갱신**: Supabase SDK가 자동 처리

---

## 3. Data 모듈 구조

### 3.1 모듈 생성

```
ALDC/
├── domain/           # 이미 존재
├── sharedUI/         # 이미 존재
├── androidApp/       # 이미 존재
├── iosApp/          # 이미 존재
└── data/            # 🆕 새로 생성
    └── src/
        └── commonMain/
            └── kotlin/
                └── dev/
                    └── loki/
                        └── data/
                            ├── repository/      # Repository 구현체
                            ├── datasource/      # 데이터 소스
                            │   ├── remote/      # API 호출
                            │   └── local/       # 로컬 저장소
                            ├── dto/             # Data Transfer Object
                            ├── mapper/          # DTO ↔ Domain Model 변환
                            └── di/              # Dependency Injection
```

### 3.2 build.gradle.kts (data 모듈)

```kotlin
// data/build.gradle.kts
plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    // 플랫폼 설정
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":domain"))

                // Ktor Client (HTTP)
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("io.ktor:ktor-client-auth:2.3.7")
                implementation("io.ktor:ktor-client-logging:2.3.7")

                // Supabase
                implementation("io.github.jan-tennert.supabase:postgrest-kt:2.0.0")
                implementation("io.github.jan-tennert.supabase:auth-kt:2.0.0")
                implementation("io.github.jan-tennert.supabase:storage-kt:2.0.0")

                // Kotlinx Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // Koin (DI)
                implementation("io.insert-koin:koin-core:3.5.0")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
            }
        }

        val iosMain by creating {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.7")
            }
        }
    }
}
```

---

## 4. Repository 구현

### 4.1 UserRepository 구현

#### UserRepository 인터페이스 (domain)
```kotlin
// domain/src/commonMain/kotlin/dev/loki/domain/repository/UserRepository.kt
interface UserRepository : BaseRepository {
    suspend fun signUp(email: String, password: String, username: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun getProfile(userId: String): Result<User>
    suspend fun updateProfile(user: User): Result<User>
    suspend fun isAuthenticated(): Boolean
}
```

#### UserRepositoryImpl (data)
```kotlin
// data/src/commonMain/kotlin/dev/loki/data/repository/UserRepositoryImpl.kt
class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun signUp(
        email: String,
        password: String,
        username: String
    ): Result<User> = runCatching {
        // 1. Supabase Auth 회원가입
        val authResponse = remoteDataSource.signUp(email, password)

        // 2. 프로필 생성
        val userId = authResponse.user?.id ?: throw Exception("User ID is null")
        val profile = remoteDataSource.createProfile(userId, username)

        // 3. Domain 모델로 변환
        val user = profile.toDomain()

        // 4. 로컬에 저장
        localDataSource.saveUser(user)

        user
    }

    override suspend fun signIn(email: String, password: String): Result<User> = runCatching {
        val authResponse = remoteDataSource.signIn(email, password)
        val userId = authResponse.user?.id ?: throw Exception("User ID is null")

        // 프로필 조회
        val profile = remoteDataSource.getProfile(userId)
        val user = profile.toDomain()

        // 로컬에 저장
        localDataSource.saveUser(user)

        user
    }

    override suspend fun signOut(): Result<Unit> = runCatching {
        remoteDataSource.signOut()
        localDataSource.clearUser()
    }

    override suspend fun getCurrentUser(): Result<User?> = runCatching {
        // 1. 로컬에서 먼저 확인
        localDataSource.getUser()?.let { return@runCatching it }

        // 2. 세션이 있으면 서버에서 조회
        val userId = remoteDataSource.getCurrentUserId() ?: return@runCatching null
        val profile = remoteDataSource.getProfile(userId)
        val user = profile.toDomain()

        localDataSource.saveUser(user)
        user
    }

    override suspend fun updateProfile(user: User): Result<User> = runCatching {
        val dto = user.toDto()
        val updated = remoteDataSource.updateProfile(dto)
        val updatedUser = updated.toDomain()

        localDataSource.saveUser(updatedUser)
        updatedUser
    }

    override suspend fun isAuthenticated(): Boolean {
        return remoteDataSource.isAuthenticated()
    }
}
```

---

### 4.2 MotivationRepository 구현

#### 인터페이스 (domain)
```kotlin
interface MotivationRepository : BaseRepository {
    suspend fun createGoal(goal: Goal): Result<Goal>
    suspend fun getGoals(userId: String): Result<List<Goal>>
    suspend fun getGoal(goalId: String): Result<Goal>
    suspend fun updateGoal(goal: Goal): Result<Goal>
    suspend fun deleteGoal(goalId: String): Result<Unit>

    suspend fun createMilestone(milestone: Milestone): Result<Milestone>
    suspend fun getMilestones(goalId: String): Result<List<Milestone>>
    suspend fun updateMilestone(milestone: Milestone): Result<Milestone>
    suspend fun deleteMilestone(milestoneId: String): Result<Unit>

    suspend fun createAchievement(achievement: Achievement): Result<Achievement>
    suspend fun getAchievements(milestoneId: String): Result<List<Achievement>>
    suspend fun updateAchievement(achievement: Achievement): Result<Achievement>
    suspend fun deleteAchievement(achievementId: String): Result<Unit>
}
```

#### 구현 (data)
```kotlin
class MotivationRepositoryImpl(
    private val remoteDataSource: MotivationRemoteDataSource
) : MotivationRepository {

    override suspend fun createGoal(goal: Goal): Result<Goal> = runCatching {
        val dto = goal.toDto()
        val created = remoteDataSource.insertGoal(dto)
        created.toDomain()
    }

    override suspend fun getGoals(userId: String): Result<List<Goal>> = runCatching {
        val dtos = remoteDataSource.fetchGoals(userId)
        dtos.map { it.toDomain() }
    }

    // ... 나머지 메소드들
}
```

---

## 5. Data Source 구현

### 5.1 UserRemoteDataSource

```kotlin
// data/src/commonMain/kotlin/dev/loki/data/datasource/remote/UserRemoteDataSource.kt
class UserRemoteDataSource(
    private val supabaseClient: SupabaseClient
) {
    suspend fun signUp(email: String, password: String): AuthResponse {
        return supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signIn(email: String, password: String): AuthResponse {
        return supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signOut() {
        supabaseClient.auth.signOut()
    }

    suspend fun getCurrentUserId(): String? {
        return supabaseClient.auth.currentUserOrNull()?.id
    }

    suspend fun createProfile(userId: String, username: String): UserProfileDto {
        val dto = UserProfileDto(
            id = userId,
            username = username,
            displayName = username,
            isPublic = false
        )

        return supabaseClient.from("user_profiles")
            .insert(dto)
            .decodeSingle<UserProfileDto>()
    }

    suspend fun getProfile(userId: String): UserProfileDto {
        return supabaseClient.from("user_profiles")
            .select()
            .eq("id", userId)
            .decodeSingle<UserProfileDto>()
    }

    suspend fun updateProfile(dto: UserProfileDto): UserProfileDto {
        return supabaseClient.from("user_profiles")
            .update(dto)
            .eq("id", dto.id)
            .decodeSingle<UserProfileDto>()
    }

    fun isAuthenticated(): Boolean {
        return supabaseClient.auth.currentSessionOrNull() != null
    }
}
```

---

### 5.2 UserLocalDataSource

```kotlin
// data/src/commonMain/kotlin/dev/loki/data/datasource/local/UserLocalDataSource.kt
interface UserLocalDataSource {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}

// 구현 (SharedPreferences/DataStore 사용)
class UserLocalDataSourceImpl(
    private val preferences: Settings // Multiplatform Settings
) : UserLocalDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun saveUser(user: User) {
        val jsonString = json.encodeToString(User.serializer(), user)
        preferences.putString("current_user", jsonString)
    }

    override suspend fun getUser(): User? {
        val jsonString = preferences.getStringOrNull("current_user") ?: return null
        return json.decodeFromString(User.serializer(), jsonString)
    }

    override suspend fun clearUser() {
        preferences.remove("current_user")
    }
}
```

---

## 6. DTO (Data Transfer Object)

### 6.1 UserProfileDto

```kotlin
// data/src/commonMain/kotlin/dev/loki/data/dto/UserProfileDto.kt
@Serializable
data class UserProfileDto(
    val id: String,
    val username: String,
    @SerialName("display_name") val displayName: String? = null,
    val gender: String? = null,
    @SerialName("birth_date") val birthDate: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    val bio: String? = null,
    @SerialName("is_public") val isPublic: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

// Mapper
fun UserProfileDto.toDomain(): User = User(
    id = id,
    username = username,
    displayName = displayName,
    gender = gender?.let { Gender.valueOf(it) },
    birthDate = birthDate?.let { LocalDate.parse(it) },
    avatarUrl = avatarUrl,
    bio = bio,
    isPublic = isPublic
)

fun User.toDto(): UserProfileDto = UserProfileDto(
    id = id,
    username = username,
    displayName = displayName,
    gender = gender?.name,
    birthDate = birthDate?.toString(),
    avatarUrl = avatarUrl,
    bio = bio,
    isPublic = isPublic
)
```

---

### 6.2 GoalDto

```kotlin
@Serializable
data class GoalDto(
    val id: String? = null,
    @SerialName("user_id") val userId: String,
    val title: String,
    val description: String? = null,
    val category: String,
    val priority: String = "NORMAL",
    val icon: String? = null,
    @SerialName("i_was") val iWas: String? = null,
    @SerialName("i_want_to_be") val iWantToBe: String? = null,
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("target_date") val targetDate: String? = null,
    @SerialName("is_completed") val isCompleted: Boolean = false,
    @SerialName("completed_at") val completedAt: String? = null,
    @SerialName("is_public") val isPublic: Boolean = false,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

// Mapper
fun GoalDto.toDomain(): Goal = Goal(
    id = id ?: "",
    userId = userId,
    title = title,
    description = description,
    category = Category.valueOf(category),
    priority = Priority.valueOf(priority),
    icon = icon,
    iWas = iWas,
    iWantToBe = iWantToBe,
    startDate = startDate?.let { LocalDate.parse(it) },
    targetDate = targetDate?.let { LocalDate.parse(it) },
    isCompleted = isCompleted,
    completedAt = completedAt?.let { Instant.parse(it) },
    isPublic = isPublic,
    milestones = emptyList() // 별도 로드
)

fun Goal.toDto(): GoalDto = GoalDto(
    id = id,
    userId = userId,
    title = title,
    description = description,
    category = category.name,
    priority = priority.name,
    icon = icon,
    iWas = iWas,
    iWantToBe = iWantToBe,
    startDate = startDate?.toString(),
    targetDate = targetDate?.toString(),
    isCompleted = isCompleted,
    completedAt = completedAt?.toString(),
    isPublic = isPublic
)
```

---

## 7. Dependency Injection (Koin)

### 7.1 DI 모듈

```kotlin
// data/src/commonMain/kotlin/dev/loki/data/di/DataModule.kt
val dataModule = module {

    // Supabase Client
    single {
        createSupabaseClient(
            supabaseUrl = "https://qeyaxqvtoaxqbkihwfuj.supabase.co",
            supabaseKey = "sb_publishable_I5FPQ_VoyJlZgz6Xg7auGA_VHB1LXQS"
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
        }
    }

    // Data Sources
    single { UserRemoteDataSource(get()) }
    single<UserLocalDataSource> { UserLocalDataSourceImpl(get()) }
    single { MotivationRemoteDataSource(get()) }
    single { CalendarRemoteDataSource(get()) }
    single { QuoteRemoteDataSource(get()) }

    // Repositories
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<MotivationRepository> { MotivationRepositoryImpl(get()) }
    single<CalendarRepository> { CalendarRepositoryImpl(get()) }
    single<QuoteRepository> { QuoteRepositoryImpl(get()) }
}
```

---

## 8. UseCase 구현

### 8.1 SignInUseCase

```kotlin
// domain/src/commonMain/kotlin/dev/loki/domain/usecase/user/SignInUseCase.kt
class SignInUseCase(
    private val repository: UserRepository
) : BaseUseCase<UserRepository> {
    override val repository: UserRepository = repository

    suspend operator fun invoke(email: String, password: String): Result<User> {
        return repository.signIn(email, password)
    }
}
```

### 8.2 CreateMotivationUseCase

```kotlin
class CreateMotivationUseCase(
    private val repository: MotivationRepository
) : BaseUseCase<MotivationRepository> {
    override val repository: MotivationRepository = repository

    suspend operator fun invoke(goal: Goal): Result<Goal> {
        return repository.createGoal(goal)
    }
}
```

---

## 9. 구현 우선순위

### Phase 1: 기본 인증 및 프로필 (P0) - 3-4일
**목표**: 회원가입/로그인/로그아웃 완성

1. ✅ data 모듈 생성
2. ✅ Supabase 클라이언트 설정
3. ✅ UserRepository 인터페이스 메소드 정의
4. ✅ UserRemoteDataSource 구현
5. ✅ UserLocalDataSource 구현
6. ✅ UserRepositoryImpl 구현
7. ✅ SignInUseCase, SignUpUseCase 구현
8. ✅ Koin DI 설정
9. ✅ 테스트

**Supabase 작업**:
- `user_profiles` 테이블 생성
- RLS 정책 설정
- Auth 설정 확인

---

### Phase 2: Goal/Milestone/Achievement (P0) - 3-4일
**목표**: 동기부여 시스템 완성

1. ✅ MotivationRepository 인터페이스 정의
2. ✅ MotivationRemoteDataSource 구현
3. ✅ MotivationRepositoryImpl 구현
4. ✅ CreateMotivationUseCase 등 구현
5. ✅ 테스트

**Supabase 작업**:
- `goals`, `milestones`, `achievements` 테이블 생성
- RLS 정책 설정

---

### Phase 3: Calendar/Diary (P1) - 2-3일
**목표**: 회고 및 캘린더 기능

1. ✅ CalendarRepository 구현
2. ✅ CaveDiary 관련 Repository 구현
3. ✅ UseCase 구현

**Supabase 작업**:
- `cave_diaries`, `thankful_things` 테이블 생성

---

### Phase 4: Routine/Mission (P1) - 2-3일
**목표**: 루틴 및 미션 시스템

**Supabase 작업**:
- `routines`, `missions` 테이블 생성

---

### Phase 5: Quote/AdventureStatus (P2) - 1-2일
**목표**: 명언 및 상태 추적

**Supabase 작업**:
- `quotes`, `adventure_status` 테이블 생성

---

## 10. 테스트 전략

### 10.1 Repository 테스트

```kotlin
class UserRepositoryImplTest {
    @Test
    fun `signUp should create user and profile`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val username = "testuser"

        // When
        val result = repository.signUp(email, password, username)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(username, result.getOrNull()?.username)
    }
}
```

---

## 11. 보안 고려사항

### 11.1 RLS (Row Level Security)
- ✅ 모든 테이블에 RLS 활성화
- ✅ 사용자는 본인 데이터만 접근
- ✅ 공개 데이터는 모두가 읽기 가능

### 11.2 API Key 관리
- ⚠️ **절대** anon key를 하드코딩하지 말 것
- ✅ BuildConfig 또는 환경 변수 사용
- ✅ `.gitignore`에 API 키 제외

### 11.3 Input Validation
- ✅ 이메일 형식 검증
- ✅ 비밀번호 강도 검증 (최소 8자)
- ✅ SQL Injection 방지 (Supabase SDK가 자동 처리)

---

## 12. 다음 단계

1. **Supabase 프로젝트에 테이블 생성** (SQL 실행)
2. **data 모듈 생성 및 기본 구조 설정**
3. **Phase 1 구현 시작** (UserRepository)
4. **테스트 및 검증**
5. **온보딩 화면 구현**

---

## 부록: 전체 SQL 스크립트

데이터베이스 초기화를 위한 전체 SQL 스크립트는 별도 파일로 제공:
- `database/schema.sql`: 전체 테이블 생성
- `database/rls_policies.sql`: RLS 정책
- `database/seed.sql`: 초기 데이터 (명언 등)

---

이 플랜으로 진행하시겠습니까?
