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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		WebElement detailBtn = webDriver
				.findElement(
						By.cssSelector("#main > div > div:nth-of-type(2) tr:nth-of-type(2) input[type=\"submit\"]"));
		detailBtn.click();

		pageLoadTimeout(10);
		WebElement date = webDriver.findElement(By.cssSelector("#sectionDetail small"));
		WebElement confirmBtn = webDriver
				.findElement(
						By.cssSelector(
								"#sectionDetail table:nth-of-type(2) tr:nth-of-type(3) input[type=\"submit\"]"));

		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("2022年10月2日", date.getText());
		assertEquals("提出済み週報【デモ】を確認する", confirmBtn.getAttribute("value"));

		scrollBy("100");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		WebElement cofirmBtn = webDriver
				.findElement(
						By.cssSelector(
								"#sectionDetail table:nth-of-type(2) tr:nth-of-type(3) input[type=\"submit\"]"));
		cofirmBtn.click();

		pageLoadTimeout(10);
		WebElement date = webDriver.findElement(By.cssSelector("#main small"));
		WebElement learningTopic = webDriver.findElement(By.id("intFieldName_0"));
		WebElement understandingDegree = webDriver.findElement(By.id("intFieldValue_0"));
		WebElement goalAchievementDegree = webDriver.findElement(By.id("content_0"));
		WebElement impression = webDriver.findElement(By.id("content_1"));
		WebElement weeklyReflection = webDriver.findElement(By.id("content_2"));

		assertEquals("レポート登録 | LMS", webDriver.getTitle());
		assertEquals("2022年10月2日", date.getText());
		assertEquals("ITリテラシー①", learningTopic.getAttribute("value"));
		assertEquals("2", understandingDegree.findElement(By.cssSelector("option[selected]")).getText());
		assertEquals("5", goalAchievementDegree.getText());
		assertEquals("週報のサンプルです。", impression.getText());
		assertEquals(
				"日報・週報などの入力項目は管理者権限で作成・変更することが可能です。「週報」の入力項目は管理画面のレポート作成機能を用いて設定し、登録されたレポートのフォーマットはデータベースの「m_daily_report」テーブルと「m_daily_report_detail」テーブルに登録されています。",
				weeklyReflection.getText());

		getEvidence(new Object() {
		}, "1");
		scrollBy("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		WebElement learningTopic = webDriver.findElement(By.id("intFieldName_0"));
		WebElement understandingDegree = webDriver.findElement(By.id("intFieldValue_0"));
		WebElement goalAchievementDegree = webDriver.findElement(By.id("content_0"));
		WebElement impression = webDriver.findElement(By.id("content_1"));
		WebElement weeklyReflection = webDriver.findElement(By.id("content_2"));

		learningTopic.clear();
		learningTopic.sendKeys("テスト修正後");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加
	}

}
