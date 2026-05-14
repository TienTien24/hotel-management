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

WebUI.setText(findTestObject('Login/input_username'), 'admin')
WebUI.setText(findTestObject('Login/input_password'), 'password')

WebUI.click(findTestObject('Login/button_login'))

WebUI.waitForPageLoad(10)

// ROOM MANAGEMENT
WebUI.click(findTestObject('Room/menu_room_management'))

WebUI.waitForPageLoad(10)

// CLICK DELETE ROOM
WebUI.waitForElementClickable(findTestObject('Room/button_delete_empty_room'), 10)

WebUI.click(findTestObject('Room/button_delete_empty_room'))

// WAIT ALERT
WebUI.waitForAlert(5)

// GET ALERT TEXT
String alertText = WebUI.getAlertText()

println(alertText)

// VERIFY ALERT
WebUI.verifyEqual(alertText.contains('xóa phòng'), true)

// DỪNG 5 GIÂY ĐỂ NHÌN POPUP
WebUI.delay(5)

// CLICK CANCEL
WebUI.dismissAlert()

// DỪNG 3 GIÂY
WebUI.delay(3)

WebUI.closeBrowser()