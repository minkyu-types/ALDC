package dev.loki.domain.type

sealed interface Mood {
    val title: String
    val icon: String
    val step: Int // 1 ~ 5, 높을수록 좋음
    val description: String
    
    data object LostInDarkness: Mood {
        override val title: String
            get() = "한 치 앞도 보이지 않는"
        override val icon: String
            get() = ""
        override val step: Int
            get() = 1
        override val description: String
            get() = "현재 상황이 힘들고 방향을 모르겠는 상태"
    }

    data object WanderingAndConfused: Mood {
        override val title: String
            get() = "방황하고 흔들리는"
        override val icon: String
            get() = ""
        override val step: Int
            get() = 1
        override val description: String
            get() = "뭔가 잘못되고 있는 것 같지만 확신이 없는 상태"
    }

    data object SteadyProgress: Mood {
        override val title: String
            get() = "그저 묵묵히 나아갈 뿐"
        override val icon: String
            get() = ""
        override val step: Int
            get() = 1
        override val description: String
            get() = "정진."
    }

    data object GlimpseOfLight: Mood {
        override val title: String
            get() = "이루어내고 말 것이다"
        override val icon: String
            get() = ""
        override val step: Int
            get() = 1
        override val description: String
            get() = "희망이 보이는 상태"
    }

    data object NearTheExit: Mood {
        override val title: String
            get() = "노력이 헛되지 않았구나"
        override val icon: String
            get() = ""
        override val step: Int
            get() = 1
        override val description: String
            get() = "큰 성장을 이루고 목표 달성 직전인 상태"
    }
}