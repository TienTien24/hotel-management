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

// mở trang register
WebUI.navigateToUrl('http://localhost:3000/register')

// nhập dữ liệu
WebUI.setText(findTestObject('Register/input_fullname'), 'Test User')
WebUI.setText(findTestObject('Register/input_username'), 'staff')
WebUI.setText(findTestObject('Register/input_email'), 'test@gmail.com')
WebUI.setText(findTestObject('Register/input_phone'), '0988888888')
WebUI.setText(findTestObject('Register/input_password'), '123456')

// click đăng ký
WebUI.click(findTestObject('Register/button_register'))

// đợi alert xuất hiện
WebUI.waitForAlert(5)

// lấy nội dung alert
String alertText = WebUI.getAlertText()

println(alertText)

// verify đúng lỗi username tồn tại
WebUI.verifyEqual(alertText.contains('Username is already taken'),true)

// xem popup 3 giây
WebUI.delay(3)

// bấm OK popup
WebUI.acceptAlert()

WebUI.closeBrowser()