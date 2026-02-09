-- ============================================
-- A Long Dark Cave - Database Schema
-- ============================================
-- Supabase PostgreSQL Database
-- Version: 1.0
-- Date: 2026-02-09

-- ============================================
-- 1. USER PROFILES
-- ============================================

CREATE TABLE IF NOT EXISTS public.user_profiles (
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

COMMENT ON TABLE public.user_profiles IS '사용자 프로필 확장 테이블 (auth.users 1:1 관계)';

-- ============================================
-- 2. GOALS (목표)
-- ============================================

CREATE TABLE IF NOT EXISTS public.goals (
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

COMMENT ON TABLE public.goals IS '사용자의 최종 목표 (환골탈태)';
COMMENT ON COLUMN public.goals.i_was IS '과거의 나';
COMMENT ON COLUMN public.goals.i_want_to_be IS '되고 싶은 나';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_goals_user_id ON public.goals(user_id);
CREATE INDEX IF NOT EXISTS idx_goals_category ON public.goals(category);
CREATE INDEX IF NOT EXISTS idx_goals_is_completed ON public.goals(is_completed);

-- ============================================
-- 3. MILESTONES (중간 목표)
-- ============================================

CREATE TABLE IF NOT EXISTS public.milestones (
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

COMMENT ON TABLE public.milestones IS '목표 달성을 위한 중간 단계';
COMMENT ON COLUMN public.milestones.order_index IS '마일스톤 순서 (1, 2, 3...)';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_milestones_goal_id ON public.milestones(goal_id);
CREATE INDEX IF NOT EXISTS idx_milestones_user_id ON public.milestones(user_id);

-- ============================================
-- 4. ACHIEVEMENTS (성과)
-- ============================================

CREATE TABLE IF NOT EXISTS public.achievements (
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

COMMENT ON TABLE public.achievements IS '최소 단위의 성과 (마일스톤의 세부 항목)';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_achievements_milestone_id ON public.achievements(milestone_id);
CREATE INDEX IF NOT EXISTS idx_achievements_user_id ON public.achievements(user_id);

-- ============================================
-- 5. ROUTINES (루틴)
-- ============================================

CREATE TABLE IF NOT EXISTS public.routines (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    repeat_type VARCHAR(20) NOT NULL,
    repeat_days INTEGER[],
    time_of_day TIME,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE public.routines IS '사용자의 반복 루틴';
COMMENT ON COLUMN public.routines.repeat_type IS 'DAILY, WEEKLY, MONTHLY';
COMMENT ON COLUMN public.routines.repeat_days IS '요일 배열 [0,1,2,3,4,5,6] (0=일요일)';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_routines_user_id ON public.routines(user_id);

-- ============================================
-- 6. MISSIONS (미션)
-- ============================================

CREATE TABLE IF NOT EXISTS public.missions (
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

COMMENT ON TABLE public.missions IS '기간이 정해진 미션 (루틴에서 생성되거나 독립적)';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_missions_user_id ON public.missions(user_id);
CREATE INDEX IF NOT EXISTS idx_missions_routine_id ON public.missions(routine_id);
CREATE INDEX IF NOT EXISTS idx_missions_date_range ON public.missions(start_date, end_date);

-- ============================================
-- 7. CAVE DIARIES (회고 일기)
-- ============================================

CREATE TABLE IF NOT EXISTS public.cave_diaries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    date DATE NOT NULL,
    mood VARCHAR(50) NOT NULL,
    content TEXT,
    is_public BOOLEAN DEFAULT false,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(user_id, date)
);

COMMENT ON TABLE public.cave_diaries IS '매일의 회고 일기 (생각상자)';
COMMENT ON COLUMN public.cave_diaries.mood IS '기분: 한 치 앞도 보이지 않는, 방황하고 흔들리는, 묵묵히 나아갈 뿐, 희망이 보이는, 노력이 헛되지 않음';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_cave_diaries_user_id ON public.cave_diaries(user_id);
CREATE INDEX IF NOT EXISTS idx_cave_diaries_date ON public.cave_diaries(date);

-- ============================================
-- 8. THANKFUL THINGS (감사 항목)
-- ============================================

CREATE TABLE IF NOT EXISTS public.thankful_things (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    diary_id UUID NOT NULL REFERENCES public.cave_diaries(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES auth.users(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE public.thankful_things IS '일기에 포함되는 감사 항목';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_thankful_things_diary_id ON public.thankful_things(diary_id);
CREATE INDEX IF NOT EXISTS idx_thankful_things_user_id ON public.thankful_things(user_id);

-- ============================================
-- 9. WEAKNESSES (약점)
-- ============================================

CREATE TABLE IF NOT EXISTS public.weaknesses (
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

COMMENT ON TABLE public.weaknesses IS '극복해야 할 약점 및 도전 과제';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_weaknesses_user_id ON public.weaknesses(user_id);

-- ============================================
-- 10. QUOTES (명언)
-- ============================================

CREATE TABLE IF NOT EXISTS public.quotes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title TEXT NOT NULL,
    author VARCHAR(100),
    category VARCHAR(50),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE public.quotes IS '시스템 제공 명언 (관리자 관리)';

-- ============================================
-- 11. ADVENTURE STATUS (모험 상태)
-- ============================================

CREATE TABLE IF NOT EXISTS public.adventure_status (
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

COMMENT ON TABLE public.adventure_status IS '사용자의 전체 진행 상황 추적';

-- 인덱스
CREATE INDEX IF NOT EXISTS idx_adventure_status_user_id ON public.adventure_status(user_id);

-- ============================================
-- UPDATED_AT TRIGGER FUNCTION
-- ============================================

CREATE OR REPLACE FUNCTION public.handle_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION public.handle_updated_at() IS 'updated_at 자동 갱신 트리거 함수';

-- ============================================
-- APPLY UPDATED_AT TRIGGERS
-- ============================================

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.user_profiles
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.goals
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.milestones
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.achievements
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.routines
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.missions
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.cave_diaries
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.weaknesses
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();

CREATE TRIGGER set_updated_at
    BEFORE UPDATE ON public.adventure_status
    FOR EACH ROW
    EXECUTE FUNCTION public.handle_updated_at();
