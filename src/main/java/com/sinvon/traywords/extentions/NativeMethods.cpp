// NativeMethods.cpp
#include <jni.h>
#include <Windows.h>

extern "C" JNIEXPORT void JNICALL Java_com_sinvon_traywords_TrayWordsApplication_setTopMost(JNIEnv *, jclass, jlong hwnd) {
    SetWindowPos((HWND)hwnd, HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
}
