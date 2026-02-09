-- ============================================
-- A Long Dark Cave - Seed Data
-- ============================================
-- 초기 데이터 (명언, 샘플 데이터 등)
-- Version: 1.0
-- Date: 2026-02-09

-- ============================================
-- QUOTES (명언)
-- ============================================

-- 환골탈태 관련 명언
INSERT INTO public.quotes (title, author, category, is_active) VALUES
-- 극복과 성장
('어둠 속에서도 빛은 결국 찾아옵니다. 당신의 한 걸음 한 걸음이 그 빛을 만들어갑니다.', 'Anonymous', 'growth', true),
('실패는 끝이 아닙니다. 다시 시작할 수 있는 기회입니다.', 'F. Scott Fitzgerald', 'resilience', true),
('한 치 앞도 보이지 않는 어둠 속에서도, 빛은 결국 찾아옵니다.', 'Anonymous', 'hope', true),
('작은 진전도 진전입니다. 어제보다 나은 오늘이면 충분합니다.', 'Anonymous', 'growth', true),

-- 인내와 끈기
('성공은 작은 노력들의 합입니다. 매일 반복되는 노력이 결국 큰 변화를 만듭니다.', 'Robert Collier', 'perseverance', true),
('천 리 길도 한 걸음부터 시작됩니다.', 'Lao Tzu', 'perseverance', true),
('가장 어두운 밤이 지나면 가장 밝은 아침이 옵니다.', 'Florence Nightingale', 'hope', true),
('멈추지 않는 한, 얼마나 천천히 가는지는 중요하지 않습니다.', 'Confucius', 'perseverance', true),

-- 자기 성찰
('진정한 변화는 자신을 받아들이는 것에서 시작됩니다.', 'Carl Rogers', 'self-reflection', true),
('과거의 나를 부끄러워하지 마세요. 그것이 지금의 당신을 만들었습니다.', 'Anonymous', 'self-reflection', true),
('당신이 생각하는 것보다 훨씬 강합니다.', 'Christopher Robin', 'strength', true),

-- 용기와 도전
('용기는 두려움이 없는 것이 아니라, 두려움에도 불구하고 나아가는 것입니다.', 'Nelson Mandela', 'courage', true),
('넘어지는 것은 실패가 아닙니다. 일어나지 않는 것이 실패입니다.', 'Mary Pickford', 'resilience', true),
('완벽을 기다리지 마세요. 시작하는 것이 중요합니다.', 'Mark Twain', 'action', true),

-- 희망과 긍정
('희망은 깃털을 가진 것입니다. 영혼 속에 앉아 말없이 노래합니다.', 'Emily Dickinson', 'hope', true),
('오늘의 고통은 내일의 힘이 됩니다.', 'Anonymous', 'strength', true),
('변화는 고통스럽지만, 성장으로 가는 유일한 길입니다.', 'Anonymous', 'growth', true),

-- 습관과 루틴
('우리는 반복적으로 하는 행동의 결과입니다. 따라서 탁월함은 행동이 아니라 습관입니다.', 'Aristotle', 'habits', true),
('매일 조금씩, 조금씩 나아지세요. 그것이 유일한 방법입니다.', 'John Wooden', 'habits', true),
('작은 승리들이 모여 큰 성취가 됩니다.', 'Anonymous', 'achievement', true),

-- Norse 신화 테마
('Yggdrasil의 뿌리에서 Ask가 탄생했듯이, 당신도 새롭게 태어날 수 있습니다.', 'Norse Mythology', 'mythology', true),
('어둠의 동굴을 지나야만 빛을 볼 수 있습니다.', 'Anonymous', 'mythology', true),
('모든 영웅의 여정은 어둠에서 시작됩니다.', 'Joseph Campbell', 'mythology', true),

-- 감사와 마음가짐
('감사는 과거를 의미 있게 하고, 오늘에 평화를 가져오며, 내일을 위한 비전을 만듭니다.', 'Melody Beattie', 'gratitude', true),
('당신이 가진 것에 감사하세요. 그러면 더 많이 가지게 될 것입니다.', 'Oprah Winfrey', 'gratitude', true),

-- 목표와 비전
('목표는 꿈에 기한을 정한 것입니다.', 'Napoleon Hill', 'goals', true),
('비전 없는 목표는 단지 바람일 뿐입니다.', 'Antoine de Saint-Exupéry', 'vision', true),
('가장 좋은 시작 시간은 지금입니다.', 'Chinese Proverb', 'action', true),

-- 극복과 회복
('상처는 빛이 들어오는 곳입니다.', 'Rumi', 'healing', true),
('고난은 당신을 부수거나 단련시킵니다. 선택은 당신의 몫입니다.', 'Anonymous', 'resilience', true),
('폭풍은 영원하지 않습니다. 평온이 반드시 옵니다.', 'Anonymous', 'hope', true)

ON CONFLICT DO NOTHING;

-- ============================================
-- CATEGORIES 참고
-- ============================================

-- 명언 카테고리:
-- growth: 성장
-- resilience: 회복력
-- hope: 희망
-- perseverance: 인내
-- self-reflection: 자기 성찰
-- strength: 강인함
-- courage: 용기
-- action: 행동
-- habits: 습관
-- achievement: 성취
-- mythology: 신화 (Norse)
-- gratitude: 감사
-- goals: 목표
-- vision: 비전
-- healing: 치유

-- ============================================
-- 샘플 사용자 (개발 환경용) - 주석 처리
-- ============================================

-- 실제 환경에서는 사용하지 말 것!
-- 개발/테스트 환경에서만 필요 시 주석 해제

/*
-- 테스트 사용자는 Supabase Auth에서 먼저 생성해야 함
-- 예시:
INSERT INTO public.user_profiles (id, username, display_name, is_public) VALUES
('00000000-0000-0000-0000-000000000001', 'test_user', 'Test User', false);

INSERT INTO public.adventure_status (user_id) VALUES
('00000000-0000-0000-0000-000000000001');
*/
