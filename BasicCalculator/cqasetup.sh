#!/bin/bash

function prepare() {
	androidRelease
	gradle installDebug installDebugTest
}

function test() {
	adb shell am instrument -w -e class com.dennisideler.calculator.test.ui.CalculatorTest#$1 com.dennisideler.calculator.test/.DisableAnimationTestRunner
}