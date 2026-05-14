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

// DELETE BOOKED ROOM
WebUI.waitForElementVisible(findTestObject('Room/button_delete_booked_room'), 15)

WebUI.scrollToElement(findTestObject('Room/button_delete_booked_room'), 10)

WebUI.delay(5)

WebUI.click(findTestObject('Room/button_delete_booked_room'))

WebUI.delay(2)

// ALERT 1
WebUI.acceptAlert()

// WAIT ALERT 2
WebUI.waitForAlert(10)

// GET ALERT TEXT
String alertText = WebUI.getAlertText()

// VERIFY MESSAGE
WebUI.verifyMatch(alertText, '.*Không thể xóa phòng.*', true)

// CLOSE ALERT 2
WebUI.acceptAlert()

WebUI.delay(3)

//WebUI.closeBrowser()
