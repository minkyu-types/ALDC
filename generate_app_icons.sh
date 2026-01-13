#!/bin/bash

# ALDC 앱 아이콘 생성 스크립트
# img_aldc_logo.png로부터 Android 및 iOS용 아이콘을 생성합니다.

set -e

# 색상 코드
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo "🚀 ALDC 앱 아이콘 생성 시작..."

# 소스 이미지 경로
SOURCE_IMAGE="sharedUI/src/commonMain/composeResources/drawable/img_aldc_logo.png"

if [ ! -f "$SOURCE_IMAGE" ]; then
    echo -e "${RED}❌ 소스 이미지를 찾을 수 없습니다: $SOURCE_IMAGE${NC}"
    exit 1
fi

echo -e "${GREEN}✓ 소스 이미지 확인: $SOURCE_IMAGE${NC}"

echo ""
echo "📱 Android 아이콘 생성 중..."

# Android 아이콘 생성 함수
generate_android_icon() {
    local density=$1
    local size=$2
    local output_dir="androidApp/src/main/res/mipmap-${density}"
    
    echo "  - ${density}: ${size}x${size}px"
    
    # ic_launcher.png 생성
    sips -z $size $size "$SOURCE_IMAGE" --out "${output_dir}/ic_launcher.png" >/dev/null 2>&1
    
    # ic_launcher_foreground.png 생성 (adaptive icon용, 108dp)
    local foreground_size=$((size * 108 / 48))
    sips -z $foreground_size $foreground_size "$SOURCE_IMAGE" --out "${output_dir}/ic_launcher_foreground.png" >/dev/null 2>&1
}

# Android 아이콘 생성
generate_android_icon "mdpi" 48
generate_android_icon "hdpi" 72
generate_android_icon "xhdpi" 96
generate_android_icon "xxhdpi" 144
generate_android_icon "xxxhdpi" 192

echo -e "${GREEN}✓ Android 아이콘 생성 완료${NC}"

echo ""
echo "🍎 iOS 아이콘 생성 중..."

# iOS 아이콘 생성 함수
generate_ios_icon() {
    local filename=$1
    local size=$2
    local ios_output_dir="iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"
    
    echo "  - ${filename}: ${size}x${size}px"
    sips -z $size $size "$SOURCE_IMAGE" --out "${ios_output_dir}/${filename}" >/dev/null 2>&1
}

# iOS 아이콘 생성 (모든 크기)
generate_ios_icon "AppIcon-20~ipad.png" 20
generate_ios_icon "AppIcon-20@2x.png" 40
generate_ios_icon "AppIcon-20@2x~ipad.png" 40
generate_ios_icon "AppIcon-20@3x.png" 60
generate_ios_icon "AppIcon-29.png" 29
generate_ios_icon "AppIcon-29~ipad.png" 29
generate_ios_icon "AppIcon-29@2x.png" 58
generate_ios_icon "AppIcon-29@2x~ipad.png" 58
generate_ios_icon "AppIcon-29@3x.png" 87
generate_ios_icon "AppIcon-40~ipad.png" 40
generate_ios_icon "AppIcon-40@2x.png" 80
generate_ios_icon "AppIcon-40@2x~ipad.png" 80
generate_ios_icon "AppIcon-40@3x.png" 120
generate_ios_icon "AppIcon@2x.png" 120
generate_ios_icon "AppIcon@3x.png" 180
generate_ios_icon "AppIcon-60@2x~car.png" 120
generate_ios_icon "AppIcon-60@3x~car.png" 180
generate_ios_icon "AppIcon~ipad.png" 76
generate_ios_icon "AppIcon@2x~ipad.png" 152
generate_ios_icon "AppIcon-83.5@2x~ipad.png" 167
generate_ios_icon "AppIcon~ios-marketing.png" 1024

echo -e "${GREEN}✓ iOS 아이콘 생성 완료${NC}"

echo ""
echo -e "${GREEN}🎉 모든 아이콘 생성이 완료되었습니다!${NC}"
echo ""
echo "다음 단계:"
echo "  1. Android Studio 또는 Xcode에서 프로젝트를 빌드하세요"
echo "  2. 앱을 실행하여 새 아이콘을 확인하세요"
