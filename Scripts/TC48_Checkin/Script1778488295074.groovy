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

WebUI.navigateToUrl('http://localhost:3000/login')

WebUI.waitForPageLoad(10)

WebUI.setText(findTestObject('Login/input_username'), 'staff')

WebUI.setText(findTestObject('Login/input_password'), 'password')

WebUI.click(findTestObject('Login/button_login'))

WebUI.delay(3)

WebUI.navigateToUrl('http://localhost:3000/bookings')

WebUI.delay(3)

WebUI.click(findTestObject("BookingManage/btn_checkin"))

WebUI.delay(2)

// nhập CCCD
WebUI.executeJavaScript(
"document.querySelector('input[placeholder*=\"định danh\"]').value='095837555124'",
null)

// nhập URL ảnh
WebUI.executeJavaScript(
"document.querySelector('input[placeholder*=\"URL\"]').value='https://test.com/image.jpg'",
null)

WebUI.delay(1)

// click hoàn tất nhận phòng
WebUI.executeJavaScript(
"Array.from(document.querySelectorAll('button')).find(b => b.innerText.includes('Hoàn tất nhận phòng')).click()",
null)

WebUI.delay(3)

// reload kiểm tra bug
WebUI.refresh()

WebUI.delay(3)

// verify
WebUI.verifyTextPresent('ĐANG LƯU TRÚ', false)

WebUI.closeBrowser()