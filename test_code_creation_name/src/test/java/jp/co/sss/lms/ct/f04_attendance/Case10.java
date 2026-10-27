package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:8080/lms/");
		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.clear();
		loginId.sendKeys("StudentAA01");

		WebElement password = webDriver.findElement(By.id("password"));
		password.clear();
		password.sendKeys("Password123");

		WebElement loginBtn = webDriver.findElement(By.className("btn"));
		loginBtn.click();

		pageLoadTimeout(10);
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		WebElement kintaiLink = webDriver.findElement(By.linkText("勤怠"));
		kintaiLink.click();

		//"過去日の勤怠に未入力があります。"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement punchInTime = webDriver.findElement(By.cssSelector(".info td:nth-of-type(3)"));
		WebElement punchOutTime = webDriver.findElement(By.cssSelector(".info td:nth-of-type(4)"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("", punchInTime.getText());
		assertEquals("", punchOutTime.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		WebElement punchIn = webDriver.findElement(By.cssSelector("input[value=\"出勤\"]"));
		punchIn.click();

		//"打刻します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement updateMsg = webDriver.findElement(By.cssSelector(".alert > span"));
		WebElement punchInTime = webDriver.findElement(By.cssSelector(".info td:nth-of-type(3)"));
		WebElement punchOutTime = webDriver.findElement(By.cssSelector(".info td:nth-of-type(4)"));
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formatDate = df.format(date);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("勤怠情報の登録が完了しました。", updateMsg.getText());
		assertEquals(formatDate, punchInTime.getText());
		assertEquals("", punchOutTime.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		WebElement punchOut = webDriver.findElement(By.cssSelector("input[value=\"退勤\"]"));
		punchOut.click();

		//"打刻します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement updateMsg = webDriver.findElement(By.cssSelector(".alert > span"));
		WebElement punchOutTime = webDriver.findElement(By.cssSelector(".info td:nth-of-type(4)"));
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		String formatDate = df.format(date);

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("勤怠情報の登録が完了しました。", updateMsg.getText());
		assertEquals(formatDate, punchOutTime.getText());

		getEvidence(new Object() {
		});
	}

}
