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

// mở trang login
WebUI.navigateToUrl('http://localhost:3000/login')

WebUI.delay(2)

// nhập username đúng
WebUI.setText(findTestObject('Login/input_username'), 'test1')

// nhập password sai
WebUI.setText(findTestObject('Login/input_password'), '13579abc')

// click login
WebUI.click(findTestObject('Login/button_login'))

// đợi alert
WebUI.waitForAlert(5)

// lấy text alert
String alertText = WebUI.getAlertText()

// verify chứa nội dung lỗi
assert alertText.contains('Sai tên đăng nhập hoặc mật khẩu')

// đóng alert
WebUI.acceptAlert()

WebUI.delay(3)

WebUI.closeBrowser()