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

WebUI.setText(findTestObject('Login/input_username'), 'test1')

WebUI.setText(findTestObject('Login/input_password'), '123456')

WebUI.click(findTestObject('Login/button_login'))

WebUI.waitForPageLoad(10)

// ROOM PAGE
WebUI.navigateToUrl('http://localhost:3000/rooms')

WebUI.click(findTestObject('Search/btn_view_room'))

println(WebUI.getUrl())

// CHỜ NÚT BOOK NOW XUẤT HIỆN
WebUI.waitForElementVisible(findTestObject('Booking/button_book_now'), 15)

WebUI.scrollToElement(findTestObject('Booking/button_book_now'), 10)

WebUI.click(findTestObject('Booking/button_book_now'))

// INVALID DATE
WebUI.setText(findTestObject('Booking/input_checkin'), '05-10-2026')

WebUI.setText(findTestObject('Booking/input_checkout'), '05-08-2026')

WebUI.waitForElementVisible(findTestObject('Booking/input_name'), 10)

WebUI.setText(findTestObject('Booking/input_name'), 'ET')

WebUI.setText(findTestObject('Booking/input_phone'), '03975438634')

WebUI.setText(findTestObject('Booking/input_address'), 'Gia Lai')

// CONFIRM BOOKING
WebUI.click(findTestObject('Booking/button_confirm'))

// WAIT TO SEE RESULT
WebUI.delay(5)

WebUI.closeBrowser()