# StreamFlow Android 项目 - 打包说明

## 项目结构
标准 Gradle 项目，适配 Android Studio / Android Code Studio 等支持 Gradle 的 IDE。

## 打包前准备

### 1. 签名文件
签名文件 `streamflow.jks` 已生成在项目根目录，配置信息：
- 密钥库密码：`streamflow123`
- 密钥别名：`streamflow`
- 密钥密码：`streamflow123`

**正式发布前请替换为自己的签名文件！**

### 2. 修改签名（可选）
编辑 `app/build.gradle` 中的 `signingConfigs` 部分：
```gradle
signingConfigs {
    release {
        storeFile file("../your_keystore.jks")
        storePassword "your_password"
        keyAlias "your_alias"
        keyPassword "your_password"
    }
}
```

## 打包方式

### 方式一：Android Code Studio / Android Studio 图形界面
1. 打开项目（选择 `/workspace/android` 目录）
2. 等待 Gradle 同步完成
3. 点击菜单：`Build` → `Generate Signed Bundle / APK`
4. 选择 `APK`，点击 Next
5. 签名配置已预设，直接点击 Next
6. 选择 `release`，点击 Finish
7. APK 输出路径：`app/build/outputs/apk/release/StreamFlow_v1.0.0_release.apk`

### 方式二：命令行打包
```bash
cd /workspace/android
./gradlew assembleRelease
```

输出 APK 位置：
- Debug：`app/build/outputs/apk/debug/StreamFlow_v1.0.0_debug.apk`
- Release：`app/build/outputs/apk/release/StreamFlow_v1.0.0_release.apk`

## 打包配置说明

| 配置项 | 说明 |
|--------|------|
| `minifyEnabled true` | 启用代码混淆压缩 |
| `shrinkResources true` | 移除未使用资源 |
| `zipAlignEnabled true` | APK ZIP 对齐优化 |
| `abiFilters` | 只打包 armeabi-v7a 和 arm64-v8a |
| ProGuard | 已配置完整混淆规则 |

## 注意事项
1. 首次导入项目时，Gradle 会自动下载依赖，需要网络连接
2. 如果下载慢，可在 `build.gradle` 中配置国内镜像源（已预设阿里云镜像）
3. 项目使用 `compileSdk 34`，请确保 IDE 中已安装 Android SDK 34
