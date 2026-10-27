package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		WebElement editLink = webDriver.findElement(By.linkText("勤怠情報を直接編集する"));
		editLink.click();

		pageLoadTimeout(10);
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		WebElement yRegularHoursBtn = webDriver.findElement(By.cssSelector("button[value=\"0\"]"));
		yRegularHoursBtn.click();
		WebElement tRegularHoursBtn = webDriver.findElement(By.cssSelector("button[value=\"1\"]"));
		tRegularHoursBtn.click();

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollBy("200");
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement updateMsg = webDriver.findElement(By.cssSelector(".alert > span"));
		WebElement yPunchInTime = webDriver.findElement(By.cssSelector("tr td:nth-of-type(3)"));
		WebElement yPunchOutTime = webDriver.findElement(By.cssSelector("tr td:nth-of-type(4)"));
		WebElement tPunchInTime = webDriver.findElement(By.cssSelector("tr:nth-of-type(2) td:nth-of-type(3)"));
		WebElement tPunchOutTime = webDriver.findElement(By.cssSelector("tr:nth-of-type(2) td:nth-of-type(4)"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("勤怠情報の登録が完了しました。", updateMsg.getText());
		assertEquals("09:00", yPunchInTime.getText());
		assertEquals("18:00", yPunchOutTime.getText());
		assertEquals("09:00", tPunchInTime.getText());
		assertEquals("18:00", tPunchOutTime.getText());

		getEvidence(new Object() {
		});
	}

}
