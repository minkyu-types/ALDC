# Database Schema - A Long Dark Cave

Supabase PostgreSQL 데이터베이스 스키마 및 초기 설정 파일

## 📁 파일 구조

```
database/
├── schema.sql          # 테이블 생성 스크립트
├── rls_policies.sql    # Row Level Security 정책
├── seed.sql            # 초기 데이터 (명언 등)
└── README.md           # 이 파일
```

## 🚀 설치 방법

### 방법 1: Supabase Dashboard (추천)

1. **Supabase Dashboard 접속**
   - https://supabase.com/dashboard
   - 프로젝트 선택: `qeyaxqvtoaxqbkihwfuj`

2. **SQL Editor 열기**
   - 왼쪽 메뉴: `SQL Editor` 클릭
   - `+ New Query` 클릭

3. **스크립트 실행 (순서대로)**
   ```sql
   -- 1. 테이블 생성
   -- schema.sql 내용 복사 & 붙여넣기 → Run

   -- 2. RLS 정책 적용
   -- rls_policies.sql 내용 복사 & 붙여넣기 → Run

   -- 3. 초기 데이터 입력
   -- seed.sql 내용 복사 & 붙여넣기 → Run
   ```

4. **확인**
   ```sql
   -- 테이블 목록 확인
   SELECT table_name
   FROM information_schema.tables
   WHERE table_schema = 'public';

   -- 명언 데이터 확인
   SELECT COUNT(*) FROM public.quotes;
   ```

---

### 방법 2: Supabase MCP (로컬에서 실행)

Claude Code에 Supabase MCP가 설정되어 있다면 로컬에서 실행 가능:

```bash
# 1. .mcp.json 확인
cat .mcp.json

# 2. Claude Code에서 Supabase 연결 테스트
# (MCP가 자동으로 처리)

# 3. SQL 파일 실행 요청
# Claude에게 "database/schema.sql을 Supabase에 실행해줘" 요청
```

---

### 방법 3: Supabase CLI (고급 사용자)

```bash
# Supabase CLI 설치
npm install -g supabase

# 로그인
supabase login

# 프로젝트 링크
supabase link --project-ref qeyaxqvtoaxqbkihwfuj

# 마이그레이션 실행
supabase db push
```

---

## 📊 테이블 구조

### 1. user_profiles
- auth.users 확장 프로필
- 사용자명, 프로필 이미지, bio 등

### 2. goals → milestones → achievements
- 목표 계층 구조
- Goal: 최종 목표
- Milestone: 중간 단계
- Achievement: 최소 단위 성과

### 3. routines → missions
- Routine: 반복 루틴
- Mission: 기간 설정된 미션

### 4. cave_diaries → thankful_things
- CaveDiary: 회고 일기 (생각상자)
- ThankfulThing: 감사 항목

### 5. weaknesses
- 극복할 약점 관리

### 6. quotes
- 시스템 제공 명언

### 7. adventure_status
- 사용자 진행 상황 추적

---

## 🔒 보안 (RLS 정책)

모든 테이블에 Row Level Security가 적용되어 있습니다:

### 기본 원칙
- ✅ 사용자는 **본인 데이터만** 접근 가능
- ✅ **공개 설정된 데이터**는 모두가 읽기 가능
- ✅ quotes는 모두가 읽기 가능

### 정책 예시
```sql
-- 자신의 목표만 관리 가능
CREATE POLICY "Users can manage own goals"
    ON public.goals
    FOR ALL
    USING (auth.uid() = user_id);

-- 공개 목표는 모두가 읽기 가능
CREATE POLICY "Public goals are viewable"
    ON public.goals FOR SELECT
    USING (is_public = true);
```

---

## 🌱 초기 데이터

`seed.sql` 실행 시 자동으로 삽입되는 데이터:

- **명언 30개**
  - 카테고리: growth, resilience, hope, perseverance, gratitude 등
  - Norse 신화 테마 포함
  - 환골탈태 컨셉에 맞춘 선별

---

## 🧪 테스트

### 테이블 생성 확인
```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;
```

**예상 결과**:
- achievements
- adventure_status
- cave_diaries
- goals
- milestones
- missions
- quotes
- routines
- thankful_things
- user_profiles
- weaknesses

### RLS 정책 확인
```sql
SELECT tablename, policyname
FROM pg_policies
WHERE schemaname = 'public'
ORDER BY tablename;
```

### 명언 데이터 확인
```sql
SELECT COUNT(*), category
FROM public.quotes
GROUP BY category
ORDER BY COUNT(*) DESC;
```

### 트리거 확인
```sql
SELECT trigger_name, event_object_table
FROM information_schema.triggers
WHERE trigger_schema = 'public'
ORDER BY event_object_table;
```

---

## 🔧 마이그레이션

### 테이블 수정 시
1. 새로운 마이그레이션 파일 생성
2. `ALTER TABLE` 구문 사용
3. 버전 관리

### 예시
```sql
-- migrations/001_add_goal_color.sql
ALTER TABLE public.goals
ADD COLUMN color VARCHAR(7);

COMMENT ON COLUMN public.goals.color IS '목표 색상 (Hex)';
```

---

## 📝 카테고리 값

### Goal/Routine/Mission Categories
```
건강·운동 (HEALTH_FITNESS)
커리어·공부 (CAREER_STUDY)
경제 (FINANCE)
관계·커뮤니티 (RELATIONSHIP_COMMUNITY)
마인드셋 (MINDSET)
자기성장 (SELF_GROWTH)
창의성 (CREATIVITY)
취미 (HOBBY)
```

### Priority
```
LOW
NORMAL
HIGH
```

### Repeat Type
```
DAILY
WEEKLY
MONTHLY
```

### Mood (기분)
```
한 치 앞도 보이지 않는
방황하고 흔들리는
묵묵히 나아갈 뿐
희망이 보이는
노력이 헛되지 않음
```

---

## ⚠️ 주의사항

### 프로덕션 환경
- ❌ `seed.sql`의 테스트 사용자 데이터는 **절대 사용 금지**
- ✅ RLS 정책 반드시 활성화
- ✅ 백업 자동화 설정

### 개발 환경
- ✅ 테스트 데이터 자유롭게 사용
- ✅ 스키마 변경 시 마이그레이션 파일 작성

---

## 🛠️ 문제 해결

### "permission denied for table" 에러
→ RLS 정책 확인 (`rls_policies.sql` 재실행)

### "relation does not exist" 에러
→ 테이블 생성 확인 (`schema.sql` 재실행)

### "duplicate key value violates unique constraint"
→ seed 데이터 중복 (정상, 무시 가능)

---

## 📚 참고 문서

- [Supabase Database Documentation](https://supabase.com/docs/guides/database)
- [PostgreSQL Row Level Security](https://www.postgresql.org/docs/current/ddl-rowsecurity.html)
- [Supabase Auth](https://supabase.com/docs/guides/auth)

---

## 🔄 업데이트 히스토리

### v1.0 (2026-02-09)
- 초기 스키마 생성
- 11개 테이블 정의
- RLS 정책 설정
- 명언 30개 추가
