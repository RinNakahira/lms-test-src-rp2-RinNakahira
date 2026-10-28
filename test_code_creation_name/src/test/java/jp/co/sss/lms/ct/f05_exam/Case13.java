package jp.co.sss.lms.ct.f05_exam;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト 試験実施機能
 * ケース13
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース13 受講生 試験の実施 結果0点")
public class Case13 {

	/** テスト07およびテスト08 試験実施日時 */
	static Date date;

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
	@DisplayName("テスト03 「試験有」の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		WebElement detailBtn = webDriver
				.findElement(
						By.cssSelector("#main > div > div:nth-of-type(2) tr:nth-of-type(2) input[type=\"submit\"]"));
		detailBtn.click();

		pageLoadTimeout(10);
		WebElement date = webDriver.findElement(By.cssSelector("#sectionDetail small"));

		assertEquals("セクション詳細 | LMS", webDriver.getTitle());
		assertEquals("2022年10月2日", date.getText());

		scrollBy("80");
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「本日の試験」エリアの「詳細」ボタンを押下し試験開始画面に遷移")
	void test04() {
		WebElement detailBtn = webDriver.findElement(By.cssSelector("input[value=\"詳細\"]"));
		detailBtn.click();

		pageLoadTimeout(10);
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		}, "1");
		scrollBy("400");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「試験を開始する」ボタンを押下し試験問題画面に遷移")
	void test05() {
		WebElement examStartBtn = webDriver.findElement(By.cssSelector("input[value=\"試験を開始する\"]"));
		examStartBtn.click();

		pageLoadTimeout(10);
		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 未回答の状態で「確認画面へ進む」ボタンを押下し試験回答確認画面に遷移")
	void test06() {
		WebElement confirmBtn = webDriver.findElement(By.cssSelector("input[value=\"確認画面へ進む\"]"));
		scrollTo(String.valueOf(confirmBtn.getLocation().getY()));
		confirmBtn.click();

		pageLoadTimeout(10);
		WebElement answerCount = webDriver.findElement(By.cssSelector("#examBeing small"));

		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());
		assertEquals("回答数：0／12問 ", answerCount.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 「回答を送信する」ボタンを押下し試験結果画面に遷移")
	void test07() throws InterruptedException {
		WebElement sendBtn = webDriver.findElement(By.id("sendButton"));
		scrollTo(String.valueOf(sendBtn.getLocation().getY()));
		sendBtn.click();

		//"回答を送信します。よろしいですか？"アラートが表示される
		Alert alert = webDriver.switchTo().alert();
		alert.accept();

		pageLoadTimeout(10);
		WebElement myScore = webDriver.findElement(By.cssSelector("#examBeing small"));

		assertEquals("ITリテラシー① | LMS", webDriver.getTitle());
		assertEquals("あなたのスコア：0.0点   正答数：0／12", myScore.getText());

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 「戻る」ボタンを押下し試験開始画面に遷移後当該試験の結果が反映される")
	void test08() {
		WebElement returnBtn = webDriver.findElement(By.cssSelector("input[value=\"戻る\"]"));
		scrollTo(String.valueOf(returnBtn.getLocation().getY()));
		returnBtn.click();

		pageLoadTimeout(10);
		getEvidence(new Object() {
		});
		assertEquals("エラー画面", webDriver.getTitle());

		/**
		WebElement target = webDriver
				.findElement(By.cssSelector("#main > div table:nth-of-type(2) tr:last-child"));
		WebElement targetScore = target.findElement(By.cssSelector("td:nth-of-type(2)"));
		WebElement targetDate = target.findElement(By.cssSelector("td:nth-of-type(4)"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		String nowDate = df.format(new Date());
		
		assertEquals("試験【ITリテラシー①】 | LMS", webDriver.getTitle());
		assertEquals("0.0点", targetScore.getText());
		assertEquals(nowDate, targetDate.getText().substring(0, 11));
		
		getEvidence(new Object() {
		}, "1");
		scrollBy("400");
		getEvidence(new Object() {
		}, "2");*/
	}

}
