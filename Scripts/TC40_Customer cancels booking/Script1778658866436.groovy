import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')
WebUI.maximizeWindow()

// LOGIN
WebUI.navigateToUrl('http://localhost:3000/login')

WebUI.setText(findTestObject('Login/input_username'), 'test1')
WebUI.setText(findTestObject('Login/input_password'), '123456')

WebUI.click(findTestObject('Login/button_login'))

WebUI.waitForPageLoad(10)

// BOOKING PAGE
WebUI.click(findTestObject('Booking/menu_booking'))

// CANCEL BOOKING
WebUI.waitForElementClickable(findTestObject('Booking/button_cancel_booking'), 10)

WebUI.click(findTestObject('Booking/button_cancel_booking'))

// ALERT
WebUI.waitForAlert(5)

String alertText = WebUI.getAlertText()

println(alertText)

// VERIFY
WebUI.verifyEqual(alertText.contains('hủy'), true)

// DỪNG 5 GIÂY
WebUI.delay(5)

// CANCEL ALERT
WebUI.dismissAlert()

WebUI.delay(5)

WebUI.closeBrowser()
