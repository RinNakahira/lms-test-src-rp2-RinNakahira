package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
		WebElement target = webDriver.findElement(By.cssSelector("#main > div > div:nth-of-type(2) tr:nth-of-type(2)"));
		WebElement targetDate = target.findElement(By.cssSelector("td"));
		WebElement targetStatus = target.findElement(By.tagName("span"));

		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		assertEquals("2022年10月2日(日)", targetDate.getText());
		assertEquals("提出済み", targetStatus.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		WebElement userDetailLink = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		userDetailLink.click();

		pageLoadTimeout(10);
		WebElement target = webDriver
				.findElement(By.cssSelector("#main > table:nth-of-type(3) tr:nth-last-of-type(3)"));
		WebElement targetDate = target.findElement(By.tagName("td"));
		WebElement targetType = target.findElement(By.cssSelector("td:nth-of-type(2)"));

		assertEquals("ユーザー詳細", webDriver.getTitle());
		assertEquals("2022年10月2日(日)", targetDate.getText());
		assertEquals("週報【デモ】", targetType.getText());

		getEvidence(new Object() {
		}, "1");
		scrollBy("500");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		WebElement fixBtn = webDriver
				.findElement(
						By.cssSelector(
								"#main > table:nth-of-type(3) tr:nth-last-of-type(3) input[value=\"修正する\"]"));
		fixBtn.click();

		pageLoadTimeout(10);
		WebElement date = webDriver.findElement(By.cssSelector("#main small"));
		WebElement learningTopic = webDriver.findElement(By.id("intFieldName_0"));
		WebElement understandingDegree = webDriver.findElement(By.id("intFieldValue_0"));
		WebElement goalAchievementDegree = webDriver.findElement(By.id("content_0"));
		WebElement impression = webDriver.findElement(By.id("content_1"));
		WebElement weeklyReflection = webDriver.findElement(By.id("content_2"));

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("2022年10月2日", date.getText());
		assertEquals("テスト修正後", learningTopic.getAttribute("value"));
		assertEquals("3", understandingDegree.findElement(By.cssSelector("option[selected]")).getText());
		assertEquals("10", goalAchievementDegree.getText());
		assertEquals("週報のサンプル修正後です。", impression.getText());
		assertEquals("テスト修正後のテキストです。", weeklyReflection.getText());

		getEvidence(new Object() {
		}, "1");
		scrollBy("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
