-- ============================================
-- A Long Dark Cave - RLS (Row Level Security) Policies
-- ============================================
-- Supabase PostgreSQL Database
-- Version: 1.0
-- Date: 2026-02-09

-- ============================================
-- 1. USER PROFILES
-- ============================================

ALTER TABLE public.user_profiles ENABLE ROW LEVEL SECURITY;

-- 자신의 프로필은 읽기/쓰기 가능
CREATE POLICY "Users can view own profile"
    ON public.user_profiles FOR SELECT
    USING (auth.uid() = id);

CREATE POLICY "Users can insert own profile"
    ON public.user_profiles FOR INSERT
    WITH CHECK (auth.uid() = id);

CREATE POLICY "Users can update own profile"
    ON public.user_profiles FOR UPDATE
    USING (auth.uid() = id);

-- 공개 프로필은 모두가 읽기 가능
CREATE POLICY "Public profiles are viewable by everyone"
    ON public.user_profiles FOR SELECT
    USING (is_public = true);

-- ============================================
-- 2. GOALS
-- ============================================

ALTER TABLE public.goals ENABLE ROW LEVEL SECURITY;

-- 자신의 목표 관리
CREATE POLICY "Users can manage own goals"
    ON public.goals
    FOR ALL
    USING (auth.uid() = user_id);

-- 공개 목표는 모두가 읽기 가능
CREATE POLICY "Public goals are viewable"
    ON public.goals FOR SELECT
    USING (is_public = true);

-- ============================================
-- 3. MILESTONES
-- ============================================

ALTER TABLE public.milestones ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own milestones"
    ON public.milestones
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 4. ACHIEVEMENTS
-- ============================================

ALTER TABLE public.achievements ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own achievements"
    ON public.achievements
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 5. ROUTINES
-- ============================================

ALTER TABLE public.routines ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own routines"
    ON public.routines
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 6. MISSIONS
-- ============================================

ALTER TABLE public.missions ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own missions"
    ON public.missions
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 7. CAVE DIARIES
-- ============================================

ALTER TABLE public.cave_diaries ENABLE ROW LEVEL SECURITY;

-- 자신의 일기 관리
CREATE POLICY "Users can manage own diaries"
    ON public.cave_diaries
    FOR ALL
    USING (auth.uid() = user_id);

-- 공개 일기는 모두가 읽기 가능
CREATE POLICY "Public diaries are viewable"
    ON public.cave_diaries FOR SELECT
    USING (is_public = true);

-- ============================================
-- 8. THANKFUL THINGS
-- ============================================

ALTER TABLE public.thankful_things ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own thankful things"
    ON public.thankful_things
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 9. WEAKNESSES
-- ============================================

ALTER TABLE public.weaknesses ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Users can manage own weaknesses"
    ON public.weaknesses
    FOR ALL
    USING (auth.uid() = user_id);

-- ============================================
-- 10. QUOTES
-- ============================================

ALTER TABLE public.quotes ENABLE ROW LEVEL SECURITY;

-- 모두가 활성화된 명언을 읽을 수 있음
CREATE POLICY "Anyone can view active quotes"
    ON public.quotes FOR SELECT
    USING (is_active = true);

-- 관리자만 명언 관리 가능 (service_role key 사용)
-- 일반 사용자는 INSERT/UPDATE/DELETE 불가

-- ============================================
-- 11. ADVENTURE STATUS
-- ============================================

ALTER TABLE public.adventure_status ENABLE ROW LEVEL SECURITY;

-- 자신의 모험 상태 읽기
CREATE POLICY "Users can view own adventure status"
    ON public.adventure_status FOR SELECT
    USING (auth.uid() = user_id);

-- 자신의 모험 상태 업데이트
CREATE POLICY "Users can update own adventure status"
    ON public.adventure_status FOR UPDATE
    USING (auth.uid() = user_id);

-- 모험 상태는 회원가입 시 자동 생성되므로 INSERT는 제한적으로 허용
CREATE POLICY "Users can insert own adventure status"
    ON public.adventure_status FOR INSERT
    WITH CHECK (auth.uid() = user_id);
