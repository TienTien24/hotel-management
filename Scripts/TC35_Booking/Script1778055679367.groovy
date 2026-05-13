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

// ===== LOGIN =====
WebUI.navigateToUrl('http://localhost:3000/login')

WebUI.setText(findTestObject('Login/input_username'), 'test1')
WebUI.setText(findTestObject('Login/input_password'), '123456')

WebUI.click(findTestObject('Login/button_login'))
WebUI.waitForPageLoad(10)

// ===== VÀO TRANG PHÒNG =====
WebUI.click(findTestObject('Search/menu_room'))

// đợi load danh sách
WebUI.waitForElementVisible(findTestObject('Search/room_item'), 10)

// hover để hiện nút view
WebUI.mouseOver(findTestObject('Search/room_item'))

// click xem phòng
WebUI.waitForElementClickable(findTestObject('Search/btn_view_room'), 10)
WebUI.click(findTestObject('Search/btn_view_room'))

WebUI.waitForPageLoad(10)
WebUI.delay(2)

// ===== CLICK ĐẶT PHÒNG =====
WebUI.waitForElementClickable(findTestObject('Booking/button_book_now'), 10)
WebUI.click(findTestObject('Booking/button_book_now'))

// ===== FORM BOOKING =====
WebUI.waitForElementVisible(findTestObject('Booking/input_checkin'), 15)

// ⚠️ input type=date → dùng sendKeys
WebUI.sendKeys(findTestObject('Booking/input_checkin'), '07-05-2026')
WebUI.sendKeys(findTestObject('Booking/input_checkout'), '10-05-2026')

// thông tin khách
WebUI.setText(findTestObject('Booking/input_name'), 'AB')
WebUI.setText(findTestObject('Booking/input_phone'), '0954679323')
WebUI.setText(findTestObject('Booking/input_email'), 'test@gmail.com')
WebUI.setText(findTestObject('Booking/input_people'), '2')
WebUI.setText(findTestObject('Booking/input_address'), 'Quy Nhon')

// submit
WebUI.click(findTestObject('Booking/button_confirm'))

// đảm bảo event chạy
WebUI.delay(2)

// alert
WebUI.waitForAlert(5)
WebUI.acceptAlert()

// đợi backend
WebUI.delay(3)


//WebUI.closeBrowser()