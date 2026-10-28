package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		Select startHour = new Select(webDriver.findElement(By.id("startHour1")));
		startHour.selectByIndex(0);

		Select endMinute = new Select(webDriver.findElement(By.id("endMinute1")));
		endMinute.selectByIndex(0);

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollTo(String.valueOf(updateBtn.getLocation().getY()));
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		List<WebElement> errors = webDriver.findElements(By.className("error"));
		List<WebElement> errorInputs = webDriver.findElements(By.className("errorInput"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("* 出勤時間が正しく入力されていません。", errors.get(0).getText());
		assertEquals("* 退勤時間が正しく入力されていません。", errors.get(1).getText());
		assertEquals("startHour1", errorInputs.get(0).getAttribute("id"));
		assertEquals("endMinute1", errorInputs.get(1).getAttribute("id"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		Select startHour = new Select(webDriver.findElement(By.id("startHour1")));
		startHour.selectByIndex(0);
		Select startMinute = new Select(webDriver.findElement(By.id("startMinute1")));
		startMinute.selectByIndex(0);

		Select endMinute = new Select(webDriver.findElement(By.id("endMinute1")));
		endMinute.selectByVisibleText("00");

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollTo(String.valueOf(updateBtn.getLocation().getY()));
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement errorMsg = webDriver.findElement(By.className("error"));
		List<WebElement> errorInputs = webDriver.findElements(By.className("errorInput"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errorMsg.getText());
		assertEquals("startHour1", errorInputs.get(0).getAttribute("id"));
		assertEquals("startMinute1", errorInputs.get(1).getAttribute("id"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		Select startHour = new Select(webDriver.findElement(By.id("startHour1")));
		startHour.selectByVisibleText("19");
		Select startMinute = new Select(webDriver.findElement(By.id("startMinute1")));
		startMinute.selectByVisibleText("00");

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollTo(String.valueOf(updateBtn.getLocation().getY()));
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement errorMsg = webDriver.findElement(By.className("error"));
		List<WebElement> errorInputs = webDriver.findElements(By.className("errorInput"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("* 退勤時刻[1]は出勤時刻[1]より後でなければいけません。", errorMsg.getText());
		assertEquals("endHour1", errorInputs.get(0).getAttribute("id"));
		assertEquals("endMinute1", errorInputs.get(1).getAttribute("id"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		Select startHour = new Select(webDriver.findElement(By.id("startHour1")));
		startHour.selectByVisibleText("17");

		Select blankTime = new Select(
				webDriver.findElement(By.cssSelector("select[name=\"attendanceList[1].blankTime\"]")));
		blankTime.selectByVisibleText("2時間");

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollTo(String.valueOf(updateBtn.getLocation().getY()));
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement errorMsg = webDriver.findElement(By.className("error"));
		WebElement errorInput = webDriver.findElement(By.className("errorInput"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("* 中抜け時間が勤務時間を超えています。", errorMsg.getText());
		assertEquals("attendanceList[1].blankTime", errorInput.getAttribute("name"));

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		Select startHour = new Select(webDriver.findElement(By.id("startHour1")));
		startHour.selectByVisibleText("09");

		WebElement note = webDriver.findElement(By.cssSelector("input[name=\"attendanceList[1].note\"]"));
		note.clear();
		note.sendKeys(
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

		WebElement updateBtn = webDriver.findElement(By.cssSelector("input[value=\"更新\"]"));
		scrollTo(String.valueOf(updateBtn.getLocation().getY()));
		updateBtn.click();

		//"更新します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement errorMsg = webDriver.findElement(By.className("error"));
		WebElement errorInput = webDriver.findElement(By.className("errorInput"));

		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		assertEquals("* 備考の長さが最大値(100)を超えています。", errorMsg.getText());
		assertEquals("attendanceList[1].note", errorInput.getAttribute("name"));

		getEvidence(new Object() {
		});
	}

}
